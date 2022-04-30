package Controllers;

import Enums.GameEnums.CombatTypeEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;

public class CityCombatController extends CombatController{


    public CityCombatController(Game newGame) {
        super(newGame);
    }
    protected static String AttackToCity(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        if(currentTile.getCombatUnit() instanceof RangedUnit){ return  AttackRangedUnitToCity(row,col,game.getTileGrid(),currentTile,civilization,(RangedUnit) currentTile.getCombatUnit());}
        else return AttackNonRangedUnitToCity(row,col, game.getTileGrid(),currentTile,civilization,(NonRangedUnit) currentTile.getCombatUnit());
    }
    private static String AttackNonRangedUnitToCity(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, NonRangedUnit nonRangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(nonRangedUnit.getAvailableMoveCount() >= path.size()){
            return  calculateNonRangeAttackToCity(nonRangedUnit,tileGrid.getTile(row,col).getCity(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    private static String AttackRangedUnitToCity(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, RangedUnit rangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(rangedUnit.getType().getRange() >= path.size()){
            return   calculateRangeAttackToCity(rangedUnit,tileGrid.getTile(row, col).getCity(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    private static int calculateRangeOfCityAttack(Tile currentTile, City city) {
        //todo : calculate range of city attack
        return 0;
    }

    protected static String CityAttackRangedUnit(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        int range=city.getRange();
        range=calculateRangeOfCityAttack(currentTile,city);
        if(city.getRange() >= path.size()){
            return   calculateCityRangeAttack(currentTile.getCombatUnit(),tileGrid.getTile(row, col).getCity(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    protected static String cityAttackToCity(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, City city) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        int range=city.getRange();
        range=calculateRangeOfCityAttack(currentTile,city);
        if(city.getRange() >= path.size()){
            return   calculateCityRangeAttack(currentTile.getCity(),tileGrid.getTile(row, col).getCity(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    private static String calculateCityRangeAttack(City city, City enemyCity, Tile cityTile, Tile enemyCityTile) {
        String response=new String("Attack happened successfully");
        int strengthRangedUnit=enemyCity.calculateCombatStrength(enemyCity,cityTile);
        int EnemyUnitStrength=city.calculateCombatStrength(city,enemyCityTile);
        calculateRangeAttackDamage(enemyCity,strengthRangedUnit,city,EnemyUnitStrength);
        response=checkForKill(city,enemyCity,enemyCityTile,cityTile);
        return response;
    }




    private static String calculateCityRangeAttack(CombatUnit enemyUnit, City city, Tile enemyUnitTile, Tile cityTile) {
        String response=new String("Attack happened successfully");
        int strengthRangedUnit=city.calculateCombatStrength(city,cityTile);
        int EnemyUnitStrength=enemyUnit.calculateCombatStrength(enemyUnit,enemyUnitTile);
        calculateRangeAttackDamage(city,strengthRangedUnit,enemyUnit,EnemyUnitStrength);
        response=checkForKill(enemyUnit,city,enemyUnitTile,cityTile);
        return response;
    }

    private static String calculateRangeAttackToCity(RangedUnit rangedUnit, City city, Tile rangedUnitTile, Tile cityTile) {
        String response=new String("Attack happened successfully");
        int strengthRangedUnit=rangedUnit.calculateCombatStrength(rangedUnit,rangedUnitTile);
        if(rangedUnit.getType().getCombatType() == CombatTypeEnum.SIEGE){strengthRangedUnit=bonusForAttackToCity(rangedUnit,strengthRangedUnit);}
        int EnemyUnitStrength=city.calculateCombatStrength(city,cityTile);
        calculateRangeAttackDamage(rangedUnit,strengthRangedUnit,city,EnemyUnitStrength);
        response=checkForKill(rangedUnit,city,rangedUnitTile,cityTile);
        return response;
    }
    private static int bonusForAttackToCity(RangedUnit rangedUnit, int strengthRangedUnit) {
        return (int)(strengthRangedUnit*1.5);
    }

    private static String calculateNonRangeAttackToCity(NonRangedUnit nonRangedUnit, City city, Tile nonRangedTile, Tile cityTile) {
        String response=new String("Attack happened successfully");
        int combatStrengthNonRangedUnit=nonRangedUnit.calculateCombatStrength(nonRangedUnit,nonRangedTile);
        int EnemyCityStrength=city.calculateCombatStrength(city,cityTile);
        calculateNonRangeAttackDamage(nonRangedUnit,combatStrengthNonRangedUnit,city,EnemyCityStrength);
        response=checkForKill(nonRangedUnit,city,nonRangedTile,cityTile);
        return null;
    }

    private static String checkForKill(Unit unit, City city, Tile unitTile, Tile cityTile) {
        if(unit.getHealthBar() <= 0){
        if(unit instanceof CombatUnit){
            unitTile.setCombatUnit(null);
            return "combat unit has killed";
        }else {
            unitTile.setNonCombatUnit(null);
            return "unit has killed";
        }
        }
        if(city.getHitPoint() <= 0){
            if(unit instanceof RangedUnit){
                city.setHitPoint(1); return "you can't destroy city by ranged unit !!";
            } else if(unit instanceof NonRangedUnit){
                cityTile.setCombatUnit((CombatUnit) unit);
                unitTile.setCombatUnit(null);
                return captureTheCity(unit.getCiv(),unit,city,cityTile,city.getCivilization());
            }else {
                city.setHitPoint(0);
                return "city is destroyed but your unit is destroyed too !!!";
            }
        }
        return "both are damaged !!";
    }
    private static String checkForKill(City city, City enemyCity, Tile enemyCityTile, Tile cityTile) {
        if(enemyCity.getHitPoint()<=0){
            enemyCity.setHitPoint(1);
            return  "you can't destroy city by range attack";
        }
        return "city is damaged!";
    }
    private static String captureTheCity(Civilization civ, Unit unit, City city, Tile cityTile,Civilization capturedCiv) {
        //todo : capture the city or choose another option
        return "wow you have capture the city";
    }

    private static void calculateRangeAttackDamage(RangedUnit rangedUnit, int strengthRangedUnit,City city, int combatUnitStrength) {
        int strengthDiff=strengthRangedUnit-combatUnitStrength;
        Random random= new Random();
        city.calculateDamage(city,strengthDiff,random);
    }
    private static void calculateRangeAttackDamage(City city, int strengthRangedUnit, CombatUnit enemyUnit, int enemyUnitStrength) {
        int strengthDiff=strengthRangedUnit-enemyUnitStrength;
        Random random= new Random();
        enemyUnit.calculateDamage(enemyUnit,enemyUnitStrength,random);
    }

    private static void calculateRangeAttackDamage(City enemyCity, int enemyCityStrength, City city, int cityStrength) {
        int strengthDiff=cityStrength-enemyCityStrength;
        Random random= new Random();
        enemyCity.calculateDamage(enemyCity,cityStrength,random);
    }
    private static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, int combatStrengthNonRangedUnit, City city, int enemyCityStrength) {
        int strengthDiff=combatStrengthNonRangedUnit-enemyCityStrength;
        Random random= new Random();
        nonRangedUnit.calculateDamage(nonRangedUnit,-strengthDiff, random);
        city.calculateDamage(city,strengthDiff,random);
    }





}
