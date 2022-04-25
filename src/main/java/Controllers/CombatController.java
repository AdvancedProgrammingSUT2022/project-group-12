package Controllers;

import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.*;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.CityCombatController.*;
import static Controllers.MovingController.findTheShortestPath;
import static Controllers.UnitCombatController.*;
import static java.lang.Math.exp;

public class CombatController extends GameController {
    public CombatController(Game newGame) {
        super(newGame);
    }
    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if(isAttackToCity(row,col,civilization)){return AttackToCity(row,col,game.getTileGrid(),currentTile,civilization,game.getTileGrid().getTile(row, col).getCity());}
        if(!isEnemyIsReadyForAttack(row,col,civilization,currentTile.getCombatUnit())) return "enemy doesn't exists there";
        if(currentTile.getCombatUnit() instanceof RangedUnit){ return  AttackRangedUnit(row,col,game.getTileGrid(),currentTile,civilization,(RangedUnit) currentTile.getCombatUnit());}
        else return AttackNonRangedUnit(row,col, game.getTileGrid(),currentTile,civilization,(NonRangedUnit) currentTile.getCombatUnit());
    }
    public static String AttackCity(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if(isAttackToCity(row,col,civilization)){return cityAttackToCity(row,col,game.getTileGrid(),currentTile,civilization,game.getTileGrid().getTile(row, col).getCity());}
        if(!isEnemyIsReadyForAttack(row,col,civilization,currentTile.getCombatUnit())) return "enemy doesn't exists there";
         return CityAttackRangedUnit(row,col,game.getTileGrid(),currentTile,civilization,currentTile.getCity());
    }


    protected static void calculateDamage(Unit unit, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        unit.setHealthBar(unit.getHealthBar()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }
    protected static void calculateDamage(NonRangedUnit nonRangedUnit, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        nonRangedUnit.setHealthBar(nonRangedUnit.getHealthBar()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }
    protected static void calculateDamage(City city, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        city.setHitPoint(city.getHitPoint()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }



    protected static int HealthBarAffect(int strength, Unit unit) {
        return (unit.getHealthBar()/100)*strength;
    }
    private static int HealthBarAffect(int strength, City city) {
        return (city.getHitPoint()/2000)*strength;
    }
    protected static int calculateCombatStrength(Unit unit, Tile itsTile){
        int strength=unit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,unit);
        return strength;
    }
    protected static int calculateCombatStrength(City city, Tile cityTile) {
        int strength= (int) city.getCombatStrength();
        strength=AffectCityFeatures(city);
        strength=HealthBarAffect(strength,city);
        return strength;
    }
    private static int calculateCombatStrength(RangedUnit rangedUnit, Tile itsTile){
        int strength=rangedUnit.getRangedCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,rangedUnit);
        return strength;
    }
    private static int calculateCombatStrength(NonRangedUnit nonRangedUnit, Tile itsTile){
        int strength=nonRangedUnit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,nonRangedUnit);
        return strength;
    }



}
