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
import Views.GameMenu;
import Views.MenuStack;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;

public class CityCombatController extends CombatController{

    public CityCombatController(Game newGame) {
        super(newGame);
    }

    protected static String AttackToCity(CombatUnit combatUnit, City city, Civilization currentCiv, Tile currentTile, Tile enemyTile) throws CommandException {
        if (combatUnit instanceof RangedUnit) {
            return AttackRangedUnitToCity(enemyTile, city, currentCiv, (RangedUnit) combatUnit,currentTile);
        } else {
            return AttackNonRangedUnitToCity(enemyTile, city, currentCiv, (NonRangedUnit) combatUnit,currentTile);
        }
    }

    private static String AttackNonRangedUnitToCity(Tile enemyTile, City enemyCity, Civilization civilization, NonRangedUnit nonRangedUnit,Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(enemyCity.getLocation(), currentTile);
        if (nonRangedUnit.getAvailableMoveCount() >= path.size()) {
            return calculateNonRangeAttackToCity(nonRangedUnit, enemyCity, currentTile, enemyTile);
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
    }

    private static String AttackRangedUnitToCity(Tile enemyTile, City enemyCity, Civilization civilization, RangedUnit rangedUnit, Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(enemyCity.getLocation(),currentTile);
        if (rangedUnit.getType().getRange() >= path.size()) {
            return calculateRangeAttackToCity(rangedUnit, enemyCity, currentTile, enemyTile);
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
    }

    private static double calculateRangeOfCityAttack(Tile currentTile, City city) {
        return city.getRange();
    }

    protected static String CityAttackRangedUnit(Unit unit, City city, Tile enemyTile, Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(unit.getLocation(), currentTile);
        double range = city.getRange();
        range = calculateRangeOfCityAttack(currentTile, city);
        if (city.getRange() >= path.size()) {
            return calculateCityRangeAttack(currentTile.getCombatUnit(), city, enemyTile , currentTile);
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
    }

    protected static String cityAttackToCity(City enemyCity, Tile currentTile, Civilization civilization, City city) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(enemyCity.getLocation(), currentTile);
        double range = city.getRange();
        range = calculateRangeOfCityAttack(currentTile, city);
        if (city.getRange() >= path.size()) {
            return calculateCityRangeAttack(city, enemyCity, currentTile, GameController.getGame().getTileGrid().getTile(enemyCity.getLocation()));
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
    }

    private static String calculateCityRangeAttack(City city, City enemyCity, Tile cityTile, Tile enemyCityTile) throws CommandException {
        String response = "Attack happened successfully";
        double strengthRangedUnit = enemyCity.calculateCombatStrength();
        double EnemyUnitStrength = city.calculateCombatStrength();
        calculateRangeAttackDamage(enemyCity, strengthRangedUnit, city, EnemyUnitStrength);
        response = checkForKill(city, enemyCity, enemyCityTile, cityTile);
        return response;
    }


    private static String calculateCityRangeAttack(CombatUnit enemyUnit, City city, Tile enemyUnitTile, Tile cityTile) throws CommandException {
        String response = new String("");
        double strengthRangedUnit = city.calculateCombatStrength();
        double EnemyUnitStrength = Unit.calculateCombatStrength(enemyUnit, enemyUnitTile, "combatstrength");
        calculateRangeAttackDamage(city, strengthRangedUnit, enemyUnit, EnemyUnitStrength);
        response = checkForKill(enemyUnit, city, enemyUnitTile, cityTile);
        return response;
    }

    private static String calculateRangeAttackToCity(RangedUnit rangedUnit, City city, Tile rangedUnitTile, Tile cityTile) throws CommandException {
        String response = "Attack happened successfully";
        double strengthRangedUnit = Unit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength");
        if (rangedUnit.getType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit = bonusForAttackToCity(rangedUnit, strengthRangedUnit);
        }
        double EnemyUnitStrength = city.calculateCombatStrength();
        calculateRangeAttackDamage(rangedUnit, strengthRangedUnit, city, EnemyUnitStrength);
        response = checkForKill(rangedUnit, city, rangedUnitTile, cityTile);
        return response;
    }

    public static double bonusForAttackToCity(RangedUnit rangedUnit, double strengthRangedUnit) {
        return (double) (strengthRangedUnit * 1.5);
    }

    private static String calculateNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) throws CommandException {
        String response = "Attack happened successfully";
        double combatStrengthNonRangedUnit = Unit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength");
        double EnemyCityStrength = city.calculateCombatStrength();
        calculateNonRangeAttackDamage(nonRangedUnit, combatStrengthNonRangedUnit, city, EnemyCityStrength);
        response = checkForKill(nonRangedUnit, city, nonRangedTile, cityTile);
        return response;
    }

    public static String checkForKill(Unit unit, City city, Tile unitTile, Tile cityTile) throws CommandException {
        StringBuilder message = new StringBuilder("");
        if (unit.getHealthBar() <= 0) {
            if (unit instanceof CombatUnit) {
                if(city.getHitPoint() <= 0){city.setHitPoint(1);}
                GameController.deleteUnit(unit);
                return "combat unit has killed";
            } else {
                GameController.deleteUnit(unit);
                return "unit has killed";
            }
        }
        if (city.getHitPoint() <= 0) {
            if (unit instanceof RangedUnit) {
                city.setHitPoint(1);
                throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_RANGEDCOMBAT);
            } else if (unit instanceof NonRangedUnit) {
                unitTile.transferUnitTo(unit, cityTile);
                return captureTheCity(unit.getCivilization(), unit, city, cityTile, city.getCivilization());
            }
        }
        return "both are damaged !!";
    }

    public static String checkForKill(City city, City enemyCity, Tile enemyCityTile, Tile cityTile) throws CommandException {
        if (enemyCity.getHitPoint() <= 0) {
            enemyCity.setHitPoint(1);
            throw new CommandException(CommandResponse.YOU_CANT_DESTROY_CITY_BY_CITY);
        }
        return "city is damaged!";
    }

    // todo: integrate with view
    private static String captureTheCity(Civilization civ, Unit unit, City city, Tile cityTile, Civilization capturedCiv) {
      //  String message = MenuStack.getInstance().getOption("enter your capture type : Annexed / Destroy");"
        //test
        String message = new String("Destroy");
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
                    continue GetMessageLoop;
                }
                destroyCity(city, cityTile, civ);
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
        for (Building building : city.getBuildings()) {
           city.getBuildings().remove(building);
        }
        for (Tile tile:
             city.getTiles()) {
            if(tile.getCitizen() != null){tile.setCitizen(null);}
            tile.getImprovements().removeAll(tile.getImprovements());
        }
        setNewCivForCityTiles(city, cityTile, null);
        city.getTile().setCity(null);
    }

    private static void setNewCivForCityTiles(City city, Tile cityTile, Civilization civ) {
        for (Tile tile : city.getTiles()) {
            tile.setCivilization(civ);
        }
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

    public static void calculateRangeAttackDamage(RangedUnit rangedUnit, double strengthRangedUnit, City city, double combatUnitStrength) {
        double strengthDiff = strengthRangedUnit - combatUnitStrength;
        Random random = new Random();
        City.calculateDamage(city, strengthDiff, random);
    }

    public static void calculateRangeAttackDamage(City city, double strengthRangedUnit, Unit enemyUnit, double enemyUnitStrength) {
        double strengthDiff = strengthRangedUnit - enemyUnitStrength;
        Random random = new Random();
        Unit.calculateDamage(enemyUnit, strengthDiff, random);
    }

    public static void calculateRangeAttackDamage(City enemyCity, double enemyCityStrength, City city, double cityStrength) {
        double strengthDiff = cityStrength - enemyCityStrength;
        Random random = new Random();
        City.calculateDamage(enemyCity, cityStrength, random);
    }

    public static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, double combatStrengthNonRangedUnit, City city, double enemyCityStrength) {
        double strengthDiff = combatStrengthNonRangedUnit - enemyCityStrength;
        Random random = new Random();
        Unit.calculateDamage(nonRangedUnit, -strengthDiff, random);
        City.calculateDamage(city, strengthDiff, random);
    }


}
