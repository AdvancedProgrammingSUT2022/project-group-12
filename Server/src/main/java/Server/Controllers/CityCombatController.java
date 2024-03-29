package Server.Controllers;

import Project.Enums.CityTypeEnum;
import Project.Enums.CombatTypeEnum;
import Project.Models.Cities.City;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonRangedUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Server.Models.Civilization;
import Server.Models.Game;
import Server.Utils.CommandException;

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
        return null; // never
    }


    protected static String affectCityAttackToUnit(City city, Unit enemyUnit, Tile currentTile, Tile enemyUnitTile) throws CommandException {
        double cityStrength = CityHandler.calculateCombatStrength(city);
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
                deleteUnit(unit);
                return "combat unit has killed";
            } else {
                deleteUnit(unit);
                return "unit has killed";
            }
        }
        if (city.getHealth() <= 0) {
            if (unit instanceof RangedUnit) {
                city.setHealth(1);
                throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_RANGED_COMBAT);
            } else if (unit instanceof NonRangedUnit) {
                unitTile.transferUnitTo(unit, cityTile);
                Civilization civ = GameController.getCivByName(city.getCivName());
                civ.removeCity(city);
                if (cityTile.getCity().isCapital()) {
                    return "capital captured";
                } else {
                    return "city captured"; // view will prompt a dialog for annex or destroy
                }
            }
        }
        return "both are damaged !!";
    }

    public static void destroyCity(City city) {
        city.getBuildings().clear();
        for (Tile tile : CityHandler.getCityTiles(city)) {
            tile.setCitizen(null);
            tile.getImprovements().clear();
        }
        setNewCivForCityTiles(city, null);
    }

    public static void makeCityAnnexed(City city, Civilization civ) {
        city.setCityState(CityTypeEnum.ANNEXED);
        city.setCivName(civ.getName());
        Random random = new Random();
        int length = city.getBuildings().size();
        for (int i = length - 1; i >= 0; i--) {
            if (city.getBuildings().get(i).getBuildingType().isCombatBuilding()) {
                city.getBuildings().remove(city.getBuildings().get(i));
            } else {
                if (random.nextInt(3) < 2) {
                    city.getBuildings().remove(city.getBuildings().get(i));
                }
            }
        }
        civ.addCity(city);
        setNewCivForCityTiles(city, civ);
    }

    private static void setNewCivForCityTiles(City city, Civilization civ) {
        for (Tile tile : CityHandler.getCityTiles(city)) {
            tile.setCivilization(civ.getName());
        }
    }


    protected static String affectCityAttackToCity(City city, City enemyCity) throws CommandException {
        double strengthRangedUnit = CityHandler.calculateCombatStrength(city);
        double enemyUnitStrength = CityHandler.calculateCombatStrength(enemyCity);
        double strengthDiff = strengthRangedUnit - enemyUnitStrength;
        enemyCity.decreaseHealth(CityHandler.calculateDamage(strengthDiff));
        String response = checkForKill(city, enemyCity);
        return response;
    }

    private static String checkForKill(City city, City enemyCity) throws CommandException {
        if (enemyCity.getHealth() <= 0) {
            enemyCity.setHealth(1);
            throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY);
        }
        return "city is damaged!";
    }

    private static String affectRangeAttackToCity(RangedUnit rangedUnit, City enemyCity, Tile rangedUnitTile, Tile cityTile) throws CommandException {
        double strengthRangedUnit = rangedUnit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength", null);
        if (rangedUnit.getUnitType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit *= Constants.SIEGE_BONUS;
        }
        double enemyCityStrength = CityHandler.calculateCombatStrength(enemyCity);
        double strengthDiff = strengthRangedUnit - enemyCityStrength;
        enemyCity.decreaseHealth(CityHandler.calculateDamage(strengthDiff));
        rangedUnit.setAvailableMoveCount(0);
        String response = checkForKill(rangedUnit, enemyCity, rangedUnitTile, cityTile);
        return response;
    }

    private static String affectNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) throws CommandException {
        if (nonRangedUnit.isHasAttack()) {
            throw new CommandException(CommandResponse.UNIT_CAN_ATTACK_ONCE);
        }
        double combatStrengthNonRangedUnit = nonRangedUnit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength", null);
        double enemyCityStrength = CityHandler.calculateCombatStrength(city);
        double strengthDiff = combatStrengthNonRangedUnit - enemyCityStrength;
        city.decreaseHealth(CityHandler.calculateDamage(strengthDiff));
        nonRangedUnit.decreaseHealth(nonRangedUnit.calculateDamage(-strengthDiff));
        if (nonRangedUnit.getUnitType().canMoveAfterAttack()) {
            nonRangedUnit.setHasAttack(true);
            nonRangedUnit.setAvailableMoveCount(nonRangedUnit.getAvailableMoveCount() - 1);
        } else {
            nonRangedUnit.setAvailableMoveCount(0);
        }
        nonRangedUnit.setAvailableMoveCount(0);
        return checkForKill(nonRangedUnit, city, nonRangedTile, cityTile);
    }

}
