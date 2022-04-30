package Controllers;

import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.*;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;
import static Models.Units.NonRangedUnit.calculateDamage;
import static Models.Units.Unit.calculateCombatStrength;
import static java.lang.Math.exp;

public class UnitCombatController extends  CombatController{
    public UnitCombatController(Game newGame) {
        super(newGame);
    }
    protected static String AttackNonRangedUnit(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, NonRangedUnit nonRangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(nonRangedUnit.getAvailableMoveCount() >= path.size()){
            return  calculateNonRangeAttack(nonRangedUnit,tileGrid.getTile(row,col).getCombatUnit(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";

    }
    protected static String AttackRangedUnit(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, RangedUnit rangedUnit) {
        ArrayList<Tile> path=findTheShortestPath(row,col,currentTile,currentTile.getCombatUnit());
        if(rangedUnit.getType().getRange() >= path.size()){
            if(isEnemyExists(row, col, civilization)) return calculateRangeAttack(rangedUnit,tileGrid.getTile(row,col).getCombatUnit(),currentTile,tileGrid.getTile(row, col));
            else return  calculateRangeAttack(rangedUnit,tileGrid.getTile(row,col).getNonCombatUnit(),currentTile,tileGrid.getTile(row, col));
        }else return "Attack is not possible";
    }

    private static String calculateNonRangeAttack(NonRangedUnit nonRangedUnit, CombatUnit combatUnit, Tile nonRangedTile, Tile combatUnitTile) {
        String response=new String("Attack happened successfully");
        int combatStrength1=calculateCombatStrength(nonRangedUnit,nonRangedTile);
        int combatStrength2=calculateCombatStrength(combatUnit,combatUnitTile);
        calculateNonRangeAttackDamage(nonRangedUnit,combatStrength1,combatUnit,combatStrength2);
        response=checkForKill(nonRangedUnit,combatUnit,nonRangedTile,combatUnitTile);
        return null;
    }

    private static String calculateRangeAttack(RangedUnit rangedUnit, Unit unit, Tile rangedUnitTile, Tile combatUnitTile) {
        String response=new String("Attack happened successfully");
        int strengthRangedUnit=calculateCombatStrength(rangedUnit,rangedUnitTile);
        int EnemyUnitStrength=calculateCombatStrength(unit,combatUnitTile);
        calculateRangeAttackDamage(rangedUnit,strengthRangedUnit,unit,EnemyUnitStrength);
        response=checkForKill(rangedUnit,unit,rangedUnitTile,combatUnitTile);
        return response;
    }

    private static String checkForKill(CombatUnit combatUnit, Unit unitEnemy,Tile combatUnitTile,Tile unitEnemyTile) {
        boolean isHaveKill=false;
        if(combatUnit.getHealthBar()<= 0){isHaveKill=true; combatUnitTile.setCombatUnit(null); combatUnit=null;}

        if(unitEnemy.getHealthBar()<=0){
            isHaveKill=true;
            if(unitEnemy instanceof NonCombatUnit){unitEnemyTile.setNonCombatUnit(null);}
            else {unitEnemyTile.setCombatUnit(null);}

            if(combatUnit instanceof NonRangedUnit) {
                combatUnitTile.setCombatUnit(null);
                unitEnemyTile.setCombatUnit(combatUnit);
            }
        }
        if(isHaveKill)return "Unit was killed";
        return "both are damaged and attack happened successfully";
    }
    public static String setupUnit(CombatUnit combatUnit) {
        combatUnit.setAvailableMoveCount(combatUnit.getAvailableMoveCount()-1);
        combatUnit.setSetup(true);
        return "unit has setted up successfully";
    }
    private static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, int combatStrength1, CombatUnit combatUnit, int combatStrength2) {
        int strengthDiff=combatStrength1-combatStrength2;
        Random random= new Random();
        nonRangedUnit.calculateDamage(nonRangedUnit,-strengthDiff, random);
        combatUnit.calculateDamage(combatUnit,strengthDiff,random);
    }
    private static void calculateRangeAttackDamage(RangedUnit rangedUnit, int strengthRangedUnit,Unit unit, int combatUnitStrength) {
        int strengthDiff=strengthRangedUnit-combatUnitStrength;
        Random random= new Random();
        calculateDamage(unit,strengthDiff,random);
    }

}
