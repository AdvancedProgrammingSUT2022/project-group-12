package Controllers;

import Enums.CityTypeEnum;
import Enums.CombatTypeEnum;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Utils.CommandResponse;
import Views.GameMenu;
import Views.MenuStack;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;

public class CityCombatController extends CombatController {


    public CityCombatController(Game newGame) {
        super(newGame);
    }

    protected static String AttackToCity(Location location, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        if (currentTile.getCombatUnit() instanceof RangedUnit) {
            return AttackRangedUnitToCity(location, game.getTileGrid(), currentTile, civilization, (RangedUnit) currentTile.getCombatUnit());
        } else {
            return AttackNonRangedUnitToCity(location, game.getTileGrid(), currentTile, civilization, (NonRangedUnit) currentTile.getCombatUnit());
        }
    }

    private static String AttackNonRangedUnitToCity(Location location, TileGrid tileGrid, Tile currentTile, Civilization civilization, NonRangedUnit nonRangedUnit) {
        ArrayList<Tile> path = findTheShortestPath(location, currentTile, currentTile.getCombatUnit());
        if (nonRangedUnit.getAvailableMoveCount() >= path.size()) {
            return calculateNonRangeAttackToCity(nonRangedUnit, tileGrid.getTile(location).getCity(), currentTile, tileGrid.getTile(location));
        } else {
            return "Attack is not possible";
        }
    }

    private static String AttackRangedUnitToCity(Location location, TileGrid tileGrid, Tile currentTile, Civilization civilization, RangedUnit rangedUnit) {
        ArrayList<Tile> path = findTheShortestPath(location, currentTile, currentTile.getCombatUnit());
        if (rangedUnit.getType().getRange() >= path.size()) {
            return calculateRangeAttackToCity(rangedUnit, tileGrid.getTile(location).getCity(), currentTile, tileGrid.getTile(location));
        } else {
            return "Attack is not possible";
        }
    }

    private static int calculateRangeOfCityAttack(Tile currentTile, City city) {
        //todo : calculate range of city attack
        return 0;
    }

    protected static String CityAttackRangedUnit(Location location, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        ArrayList<Tile> path = findTheShortestPath(location, currentTile, currentTile.getCombatUnit());
        int range = city.getRange();
        range = calculateRangeOfCityAttack(currentTile, city);
        if (city.getRange() >= path.size()) {
            return calculateCityRangeAttack(currentTile.getCombatUnit(), tileGrid.getTile(location).getCity(), currentTile, tileGrid.getTile(location));
        } else {
            return "Attack is not possible";
        }
    }

    protected static String cityAttackToCity(Location location, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        ArrayList<Tile> path = findTheShortestPath(location, currentTile, currentTile.getCombatUnit());
        int range = city.getRange();
        range = calculateRangeOfCityAttack(currentTile, city);
        if (city.getRange() >= path.size()) {
            return calculateCityRangeAttack(currentTile.getCity(), tileGrid.getTile(location).getCity(), currentTile, tileGrid.getTile(location));
        } else {
            return "Attack is not possible";
        }
    }

    private static String calculateCityRangeAttack(City city, City enemyCity, Tile cityTile, Tile enemyCityTile) {
        String response = "Attack happened successfully";
        int strengthRangedUnit = enemyCity.calculateCombatStrength();
        int EnemyUnitStrength = city.calculateCombatStrength();
        calculateRangeAttackDamage(enemyCity, strengthRangedUnit, city, EnemyUnitStrength);
        response = checkForKill(city, enemyCity, enemyCityTile, cityTile);
        return response;
    }


    private static String calculateCityRangeAttack(CombatUnit enemyUnit, City city, Tile enemyUnitTile, Tile cityTile) {
        String response = "Attack happened successfully";
        int strengthRangedUnit = city.calculateCombatStrength();
        int EnemyUnitStrength = Unit.calculateCombatStrength(enemyUnit, enemyUnitTile);
        calculateRangeAttackDamage(city, strengthRangedUnit, enemyUnit, EnemyUnitStrength);
        response = checkForKill(enemyUnit, city, enemyUnitTile, cityTile);
        return response;
    }

    private static String calculateRangeAttackToCity(RangedUnit rangedUnit, City city, Tile rangedUnitTile, Tile cityTile) {
        String response = "Attack happened successfully";
        int strengthRangedUnit = Unit.calculateCombatStrength(rangedUnit, rangedUnitTile);
        if (rangedUnit.getType().getCombatType() == CombatTypeEnum.SIEGE) {
            strengthRangedUnit = bonusForAttackToCity(rangedUnit, strengthRangedUnit);
        }
        int EnemyUnitStrength = city.calculateCombatStrength();
        calculateRangeAttackDamage(rangedUnit, strengthRangedUnit, city, EnemyUnitStrength);
        response = checkForKill(rangedUnit, city, rangedUnitTile, cityTile);
        return response;
    }

    private static int bonusForAttackToCity(RangedUnit rangedUnit, int strengthRangedUnit) {
        return (int) (strengthRangedUnit * 1.5);
    }

    private static String calculateNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) {
        String response = "Attack happened successfully";
        int combatStrengthNonRangedUnit = Unit.calculateCombatStrength(nonRangedUnit, nonRangedTile);
        int EnemyCityStrength = city.calculateCombatStrength();
        calculateNonRangeAttackDamage(nonRangedUnit, combatStrengthNonRangedUnit, city, EnemyCityStrength);
        response = checkForKill(nonRangedUnit, city, nonRangedTile, cityTile);
        return null;
    }

    private static String checkForKill(Unit unit, City city, Tile unitTile, Tile cityTile) {
        if (unit.getHealthBar() <= 0) {
            if (unit instanceof CombatUnit) {
                unitTile.setCombatUnit(null);
                return "combat unit has killed";
            } else {
                unitTile.setNonCombatUnit(null);
                return "unit has killed";
            }
        }
        if (city.getHitPoint() <= 0) {
            if (unit instanceof RangedUnit) {
                city.setHitPoint(1);
                return "you can't destroy city by ranged unit !!";
            } else if (unit instanceof NonRangedUnit) {
                cityTile.setCombatUnit((CombatUnit) unit);
                unitTile.setCombatUnit(null);
                return captureTheCity(unit.getCivilization(), unit, city, cityTile, city.getCivilization());
            } else {
                city.setHitPoint(0);
                return "city is destroyed but your unit is destroyed too !!!";
            }
        }
        return "both are damaged !!";
    }

    private static String checkForKill(City city, City enemyCity, Tile enemyCityTile, Tile cityTile) {
        if (enemyCity.getHitPoint() <= 0) {
            enemyCity.setHitPoint(1);
            return "you can't destroy city by range attack";
        }
        return "city is damaged!";
    }

    private static String captureTheCity(Civilization civ, Unit unit, City city, Tile cityTile, Civilization capturedCiv) {
        //todo : complete capturing type aaaaaaaaaaaaaaaaaa
        String message = MenuStack.getInstance().getOption("enter your capture type : Puppet / Annexed / Destroy(if city isn't captial or Puppet)");
        GetMessageLoop:
        while (true) {
            switch (message) {
                case "Annexed" -> {
                    city.setCityState(CityTypeEnum.ANNEXED);
                    makeCityAnnexed(city, cityTile, civ);
                    break GetMessageLoop;
                }
                case "Destroy" -> destroyCity(city, cityTile, civ);
                default -> {
                    if (city.isCapital()) {
                        MenuStack.getInstance().getOption("you can't destroy capital");
                        continue GetMessageLoop;
                    }
                    if (city.getCityState() == CityTypeEnum.PUPPET) {
                        MenuStack.getInstance().getOption("you can't destroy Puppet City");
                        continue GetMessageLoop;
                    }
                    GameMenu.printError(CommandResponse.INVALID_COMMAND);
                    message = MenuStack.getInstance().getOption();
                }
            }
        }
        return "wow you have captured the city";
    }

    private static void destroyCity(City city, Tile cityTile, Civilization civ) {
        city.setCivilization(civ);
        Random random;
        for (Building building :
                city.getBuildings()) {
            //todo : if a building is a military is destroyed else with probality 33 percent it will be destory
        }
        setNewCivForCityTiles(city, cityTile, civ);
    }

    private static void setNewCivForCityTiles(City city, Tile cityTile, Civilization civ) {
        cityTile.setCivilization(civ);
        for (Tile tile :
                city.getTiles()) {
            tile.setCivilization(civ);
        }
        setNewCivForCityTiles(city, cityTile, civ);
    }

    private static void makeCityAnnexed(City city, Tile cityTile, Civilization civ) {
        city.setCivilization(civ);
        Random random;
        for (Building building :
                city.getBuildings()) {
            //todo : if a building is a military is destroyed else with probality 33 percent it will be destory
        }
        setNewCivForCityTiles(city, cityTile, civ);
    }

    private static void calculateRangeAttackDamage(RangedUnit rangedUnit, int strengthRangedUnit, City city, int combatUnitStrength) {
        int strengthDiff = strengthRangedUnit - combatUnitStrength;
        Random random = new Random();
        City.calculateDamage(city, strengthDiff, random);
    }

    private static void calculateRangeAttackDamage(City city, int strengthRangedUnit, CombatUnit enemyUnit, int enemyUnitStrength) {
        int strengthDiff = strengthRangedUnit - enemyUnitStrength;
        Random random = new Random();
        Unit.calculateDamage(enemyUnit, enemyUnitStrength, random);
    }

    private static void calculateRangeAttackDamage(City enemyCity, int enemyCityStrength, City city, int cityStrength) {
        int strengthDiff = cityStrength - enemyCityStrength;
        Random random = new Random();
        City.calculateDamage(enemyCity, cityStrength, random);
    }

    private static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, int combatStrengthNonRangedUnit, City city, int enemyCityStrength) {
        int strengthDiff = combatStrengthNonRangedUnit - enemyCityStrength;
        Random random = new Random();
        Unit.calculateDamage(nonRangedUnit, -strengthDiff, random);
        City.calculateDamage(city, strengthDiff, random);
    }


}
