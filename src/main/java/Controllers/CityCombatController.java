package Controllers;

import Enums.CityTypeEnum;
import Enums.CombatTypeEnum;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;
import Views.GameMenu;
import Views.MenuStack;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;
import static Controllers.MovingController.moveUnit;

public class CityCombatController extends CombatController {

    public CityCombatController(Game newGame) {
        super(newGame);
    }

    public static String affectAttackToCity(CombatUnit combatUnit, City enemyCity, Civilization currentCiv, Tile currentTile, Tile enemyTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(enemyCity.getLocation(), currentTile);
        if (combatUnit instanceof RangedUnit rangedUnit) {
            if (checkForDistance(rangedUnit,enemyCity.getLocation(),currentTile))
                return affectRangeAttackToCity(rangedUnit, enemyCity, currentTile, enemyTile);
        } else if(combatUnit instanceof  NonRangedUnit nonRangedUnit){
            if (checkForDistance(nonRangedUnit,enemyCity.getLocation(),currentTile)){
                moveUnit(enemyCity.getLocation(), currentTile, currentCiv, combatUnit);
                return affectNonRangeAttackToCity(nonRangedUnit, enemyCity, currentTile, enemyTile);
            }
        }
        return null;
    }


    public static String affectCityAttackToUnit(City city, Unit enemyUnit, Tile currentTile, Tile enemyUnitTile) throws CommandException {
        double cityStrength = city.calculateCombatStrength();
        double enemyUnitStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyUnitTile, "combatstrength");
        double strengthDiff = cityStrength - enemyUnitStrength;
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(enemyUnit,strengthDiff));
        return checkForKill(enemyUnit, city, enemyUnitTile, currentTile);
    }


    public static String checkForKill(Unit unit, City city, Tile unitTile, Tile cityTile) throws CommandException {
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
          String message = MenuStack.getInstance().getOption("enter your capture type : Annexed / Destroy");
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
                        MenuStack.getInstance().getOption("you can't destroy capital");
                        message = MenuStack.getInstance().getOption();
                        continue GetMessageLoop;
                    }
                    destroyCity(city, cityTile, civ);
                    break GetMessageLoop;
                }
                default -> {
                    GameMenu.printError(CommandResponse.INVALID_COMMAND);
                    message = MenuStack.getInstance().getOption();
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
        for (Building building : city.getBuildings()) {
            if (building.getType().iscombatBuilding()) {
                city.getBuildings().remove(building);
            } else {
                if (random.nextInt(3) > 2) {
                    city.getBuildings().remove(building);
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



    public static String affectCityAttackToCity(City city, City enemyCity) throws CommandException {
        double strengthRangedUnit = city.calculateCombatStrength();
        double enemyUnitStrength = enemyCity.calculateCombatStrength();
        double strengthDiff =  strengthRangedUnit - enemyUnitStrength;
        enemyCity.decreaseHealth(enemyCity.calculateDamage(enemyCity,-strengthDiff));
        String response = checkForKill(city, enemyCity, city.getTile(), enemyCity.getTile());
        return response;
    }

    public static String checkForKill(City city, City enemyCity, Tile enemyCityTile, Tile cityTile) throws CommandException {
        if (enemyCity.getHealth() <= 0) {
            enemyCity.setHealth(1);
            throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY);
        }
        return "city is damaged!";
    }

    public static String affectRangeAttackToCity(RangedUnit rangedUnit, City enemyCity, Tile rangedUnitTile, Tile cityTile) throws CommandException {
        double strengthRangedUnit = rangedUnit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength");
        if (rangedUnit.getType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit *= Constants.SIEGE_BONUS;
        }
        double enemyCityStrength = enemyCity.calculateCombatStrength();
        double strengthDiff = strengthRangedUnit - enemyCityStrength;
        enemyCity.decreaseHealth(enemyCity.calculateDamage(enemyCity,strengthDiff));
        rangedUnit.setAvailableMoveCount(0);
        String response = checkForKill(rangedUnit, enemyCity, rangedUnitTile, cityTile);
        return response;
    }
    
    public static String affectNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) throws CommandException {
        double combatStrengthNonRangedUnit = nonRangedUnit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength");
        double enemyCityStrength = city.calculateCombatStrength();
        double strengthDiff = combatStrengthNonRangedUnit - enemyCityStrength;
        city.decreaseHealth(city.calculateDamage(city,strengthDiff));
        nonRangedUnit.decreaseHealth(nonRangedUnit.calculateDamage(nonRangedUnit,-strengthDiff));
        nonRangedUnit.setAvailableMoveCount(0);
        String response = checkForKill(nonRangedUnit, city, nonRangedTile, cityTile);
        return response;
    }

}
