package Project.Controllers;

import Project.Enums.CityTypeEnum;
import Project.Enums.CombatTypeEnum;
import Project.Models.Cities.City;
import Project.Models.Civilization;
import Project.Models.Game;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonRangedUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.ServerViews.GameMenu;
import Project.ServerViews.MenuStack;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;

import java.util.Random;

public class CityCombatController extends CombatController {

    public CityCombatController(Game newGame) {
        super(newGame);
    }

    protected static String affectAttackToCity(CombatUnit combatUnit, City enemyCity, Civilization currentCiv, Tile currentTile, Tile enemyTile) throws CommandException {
//        ArrayList<Tile> path = findTheShortestPath(enemyCity.getLocation(), currentTile);
        if (combatUnit instanceof RangedUnit rangedUnit) {
            if (checkForDistance(rangedUnit, enemyCity.getLocation(), currentTile))
                return affectRangeAttackToCity(rangedUnit, enemyCity, currentTile, enemyTile);
        } else if (combatUnit instanceof NonRangedUnit nonRangedUnit) {
            if (checkForDistance(nonRangedUnit, enemyCity.getLocation(), currentTile)) {
                MovingController.moveUnit(enemyCity.getLocation(), currentTile, combatUnit);
                return affectNonRangeAttackToCity(nonRangedUnit, enemyCity, currentTile, enemyTile);
            }
        }
        return null;
    }


    protected static String affectCityAttackToUnit(City city, Unit enemyUnit, Tile currentTile, Tile enemyUnitTile) throws CommandException {
        double cityStrength = city.calculateCombatStrength();
        double enemyUnitStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyUnitTile, "combatstrength", null);
        double strengthDiff = cityStrength - enemyUnitStrength;
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(strengthDiff));
        return checkForKill(enemyUnit, city, enemyUnitTile, currentTile);
    }




    static String checkForKill(Unit unit, City city, Tile unitTile, Tile cityTile) throws CommandException {
        if (unit.getHealth() <= 0) {
            if (unit instanceof CombatUnit) {
                if (city.getHealth() <= 0) {
                    city.setHealth(1);
                }
                GameController.deleteUnit(unit);
                return "combat unit has killed";
            } else {
                GameController.deleteUnit(unit);
                return "unit has killed";
            }
        }
        if (city.getHealth() <= 0) {
            if (unit instanceof RangedUnit) {
                city.setHealth(1);
                throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_RANGEDCOMBAT);
            } else if (unit instanceof NonRangedUnit) {
                unitTile.transferUnitTo(unit, cityTile);
                return captureTheCity(unit.getCivilization(), unit, city, cityTile, city.getCivilization());
            }
        }
        return "both are damaged !!";
    }

    // todo: integrate with view
    private static String captureTheCity(Civilization civ, Unit unit, City city, Tile cityTile, Civilization capturedCiv) {
          String message = MenuStack.getInstance().getOptionForAttack();
        GetMessageLoop:
        while (true) {
            switch (message) {
                case "Annexed" -> {
                    city.setCityState(CityTypeEnum.ANNEXED);
                    makeCityAnnexed(city, cityTile, civ);
                    break GetMessageLoop;
                }
                case "Destroy" -> {
                    if (city.isCapital()) {
                        message = MenuStack.getInstance().getOptionForAttack("you can't destroy capital");
                        continue GetMessageLoop;
                    }
                    destroyCity(city, cityTile, civ);
                    break GetMessageLoop;
                }
                default -> {
                    GameMenu.printError(CommandResponse.INVALID_COMMAND);
                    message = MenuStack.getInstance().getOptionForAttack();
                }
            }
        }
        return "wow you have captured the city";
    }

    private static void destroyCity(City city, Tile cityTile, Civilization civ) {
        city.setCivilization(civ);
        Random random = new Random();
        city.getBuildings().removeAll(city.getBuildings());
        for (Tile tile :
                city.getTiles()) {
            if (tile.getCitizen() != null) {
                tile.setCitizen(null);
            }
            tile.getImprovements().removeAll(tile.getImprovements());
        }
        setNewCivForCityTiles(city, cityTile, null);
        city.getTile().setCity(null);
    }

    private static void makeCityAnnexed(City city, Tile cityTile, Civilization civ) {
        city.setCivilization(civ);
        Random random = new Random();
        int length = city.getBuildings().size();
        for (int i = length - 1; i >= 0; i--) {
            if (city.getBuildings().get(i).getType().isCombatBuilding()) {
                city.getBuildings().remove(city.getBuildings().get(i));
            } else {
                if (random.nextInt(3) > 2) {
                    city.getBuildings().remove(city.getBuildings().get(i));
                }
            }
        }
        setNewCivForCityTiles(city, cityTile, civ);
    }

    private static void setNewCivForCityTiles(City city, Tile cityTile, Civilization civ) {
        for (Tile tile : city.getTiles()) {
            tile.setCivilization(civ);
        }
    }


    protected static String affectCityAttackToCity(City city, City enemyCity) throws CommandException {
        double strengthRangedUnit = city.calculateCombatStrength();
        double enemyUnitStrength = enemyCity.calculateCombatStrength();
        double strengthDiff = strengthRangedUnit - enemyUnitStrength;
        enemyCity.decreaseHealth(enemyCity.calculateDamage(strengthDiff));
        String response = checkForKill(city, enemyCity, city.getTile(), enemyCity.getTile());
        return response;
    }

    private static String checkForKill(City city, City enemyCity, Tile enemyCityTile, Tile cityTile) throws CommandException {
        if (enemyCity.getHealth() <= 0) {
            enemyCity.setHealth(1);
            throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY);
        }
        return "city is damaged!";
    }

    private static String affectRangeAttackToCity(RangedUnit rangedUnit, City enemyCity, Tile rangedUnitTile, Tile cityTile) throws CommandException {
        double strengthRangedUnit = rangedUnit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength", null);
        if (rangedUnit.getType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit *= Constants.SIEGE_BONUS;
        }
        double enemyCityStrength = enemyCity.calculateCombatStrength();
        double strengthDiff = strengthRangedUnit - enemyCityStrength;
        enemyCity.decreaseHealth(enemyCity.calculateDamage(strengthDiff));
        rangedUnit.setAvailableMoveCount(0);
        String response = checkForKill(rangedUnit, enemyCity, rangedUnitTile, cityTile);
        return response;
    }

    private static String affectNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) throws CommandException {
        if (nonRangedUnit.isHasAttack()) {
            throw new CommandException(CommandResponse.UNIT_CAN_ATTACK_ONCE);
        }
        double combatStrengthNonRangedUnit = nonRangedUnit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength", null);
        double enemyCityStrength = city.calculateCombatStrength();
        double strengthDiff = combatStrengthNonRangedUnit - enemyCityStrength;
        city.decreaseHealth(city.calculateDamage(strengthDiff));
        nonRangedUnit.decreaseHealth(nonRangedUnit.calculateDamage(-strengthDiff));
        if (nonRangedUnit.getType().canMoveAfterAttack()) {
            nonRangedUnit.setHasAttack(true);
            nonRangedUnit.setAvailableMoveCount(nonRangedUnit.getAvailableMoveCount() - 1);
        } else {
            nonRangedUnit.setAvailableMoveCount(0);
        }
        nonRangedUnit.setAvailableMoveCount(0);
        String response = checkForKill(nonRangedUnit, city, nonRangedTile, cityTile);
        return response;
    }

}
