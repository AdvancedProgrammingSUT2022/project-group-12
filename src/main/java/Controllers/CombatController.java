package Controllers;

import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;
import static java.lang.Math.exp;

public class CombatController extends GameController {
    public CombatController(Game newGame) {
        super(newGame);
    }
    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if(!isEnemyExists(row,col,civilization)) return "enemy doesn't exists there";
        if(currentTile.getCombatUnit() instanceof RangedUnit){ return  AttackRangedUnit(row,col,game.getTileGrid(),currentTile,civilization,(RangedUnit) currentTile.getCombatUnit());}
        else return AttackNonRangedUnit(row,col, game.getTileGrid(),currentTile,civilization,(NonRangedUnit) currentTile.getCombatUnit());
    }

    private static String AttackNonRangedUnit(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, NonRangedUnit nonRangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(nonRangedUnit.getMovement() >= path.size()){
            return  caculateNonRangeAttack(nonRangedUnit,tileGrid.getTile(row,col).getCombatUnit(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";

    }
    private static String AttackRangedUnit(int row, int col,TileGrid tileGrid, Tile currentTile, Civilization civilization,RangedUnit rangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(rangedUnit.getType().getRange() >= path.size()){
            return  caculateRangeAttack(rangedUnit,tileGrid.getTile(row,col).getCombatUnit(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    private static String caculateNonRangeAttack(NonRangedUnit nonRangedUnit, CombatUnit combatUnit,Tile nonRangedTile,Tile combatUnitTile) {
        String response=new String("Attack happened successfully");
        int combatStrength1=calculateCombatStrength(nonRangedUnit,nonRangedTile);
        int combatStrength2=calculateCombatStrength(combatUnit,combatUnitTile);
        caculateNonRangeAttackDamage(nonRangedUnit,combatStrength1,combatUnit,combatStrength2);
        response=checkForKill(nonRangedUnit,combatUnit,nonRangedTile,combatUnitTile);
        return null;
    }

    private static String caculateRangeAttack(RangedUnit rangedUnit, CombatUnit combatUnit,Tile rangedUnitTile,Tile combatUnitTile) {
        String response=new String("Attack happened successfully");
        int strengthRangedUnit=calculateCombatStrength(rangedUnit,rangedUnitTile);
        int combatUnitStrength=calculateCombatStrength(combatUnit,combatUnitTile);
        caculateRangeAttackDamage(rangedUnit,strengthRangedUnit,combatUnit,combatUnitStrength);
        response=checkForKill(rangedUnit,combatUnit,rangedUnitTile,combatUnitTile);
        return response;
    }
    private static String checkForKill(CombatUnit combatUnit1, CombatUnit combatUnit2,Tile combatUnit1Tile,Tile combatUnit2Tile) {
        boolean isHaveKill=false;
        if(combatUnit2.getHealthBar()<=0){
            isHaveKill=true; combatUnit2Tile.setCombatUnit(null);
            combatUnit1Tile.setCombatUnit(null);
            combatUnit2Tile.setCombatUnit(combatUnit1);
        }
        if(combatUnit1.getHealthBar()<= 0){isHaveKill=true; combatUnit1Tile.setCombatUnit(null);}
        if(isHaveKill)return "Unit was killed";
        return "both are damaged and attack happened successfully";
    }
    private static void caculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, int combatStrength1, CombatUnit combatUnit, int combatStrength2) {
        int strengthDiff=combatStrength1-combatStrength2;
        Random random= new Random();
        calculateDamage(nonRangedUnit,-strengthDiff, random);
        calculateDamage(combatUnit,strengthDiff,random);
    }
    private static void caculateRangeAttackDamage(RangedUnit rangedUnit, int strengthRangedUnit, CombatUnit combatUnit, int combatUnitStrength) {
        int strengthDiff=strengthRangedUnit-combatUnitStrength;
        Random random= new Random();
        calculateDamage(combatUnit,strengthDiff,random);
    }


    private static void calculateDamage(NonRangedUnit nonRangedUnit, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        nonRangedUnit.setHealthBar(nonRangedUnit.getHealthBar()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }
    private static void calculateDamage(CombatUnit combatUnit, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        combatUnit.setHealthBar(combatUnit.getHealthBar()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }

    private static int calculateCombatStrength(NonRangedUnit nonRangedUnit, Tile itsTile){
        int strength=nonRangedUnit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,nonRangedUnit);
        return strength;
    }

    private static int HealthBarAffect(int strength, CombatUnit combatUnit) {
        return (combatUnit.getHealthBar()/100)*strength;
    }

    private static int calculateCombatStrength(CombatUnit combatUnit, Tile itsTile){
        int strength=combatUnit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,combatUnit);
        return strength;
    }
    private static int calculateCombatStrength(RangedUnit rangedUnit, Tile itsTile){
        int strength=rangedUnit.getRangedCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,rangedUnit);
        return strength;
    }
    private static int AffectTerrainFeatures(int strength, Tile itsTile) {
        //Todo : affect the terrain features
        return 0;
    }




}
