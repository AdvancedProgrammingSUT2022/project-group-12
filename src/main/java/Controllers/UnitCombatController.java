package Controllers;

import Enums.CombatTypeEnum;
import Enums.UnitStates;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.MovingController.findTheShortestPath;
import static Models.Units.NonRangedUnit.calculateDamage;
import static Models.Units.Unit.calculateCombatStrength;

public class UnitCombatController extends CombatController {

    public UnitCombatController(Game newGame) {
        super(newGame);
    }


    protected static String AttackNonRangedUnit(NonRangedUnit nonRangedUnit, CombatUnit enemyUnit, Civilization civilization, Tile currentTile, Tile enemyTile) throws CommandException {
        // todo: not path size, but sum of movement costs
        ArrayList<Tile> path = findTheShortestPath(enemyUnit.getLocation(), currentTile);
        if (nonRangedUnit.getAvailableMoveCount() >= path.size()) {
            return affectNonRangeAttack(nonRangedUnit, enemyUnit, currentTile, enemyTile);
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }

    }

    protected static String affectNonRangeAttack(NonRangedUnit nonRangedUnit, CombatUnit enemyUnit, Tile nonRangedTile, Tile enemyTile) {
        String response;
        double combatStrength1 = calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength");
        double combatStrength2 = calculateCombatStrength(enemyUnit, enemyTile, "combatstrength");

        double strengthDiff = combatStrength1 - combatStrength2;

        System.out.println("strengthDiff = " + strengthDiff);
        
        System.out.println(nonRangedUnit.getHealth());
        System.out.println(enemyUnit.getHealth());

        System.out.println(calculateDamage(nonRangedUnit, -strengthDiff));
        System.out.println(calculateDamage(enemyUnit, strengthDiff));

        nonRangedUnit.decreaseHealth(calculateDamage(nonRangedUnit, -strengthDiff));
        enemyUnit.decreaseHealth(calculateDamage(enemyUnit, strengthDiff));

        System.out.println(nonRangedUnit.getHealth());
        System.out.println(enemyUnit.getHealth());

        nonRangedUnit.setAvailableMoveCount(0);
        response = checkForKill(nonRangedUnit, enemyUnit, nonRangedTile, enemyTile);
        return response;
    }

    public static String checkForKill(CombatUnit combatUnit, Unit unitEnemy, Tile combatUnitTile, Tile unitEnemyTile) {
        boolean isHaveKill = false;
        if (combatUnit.getHealth() <= 0) {
            isHaveKill = true;
            GameController.deleteUnit(combatUnitTile.getCombatUnit());
            combatUnit = null;
        }

        if (unitEnemy.getHealth() <= 0) {
            isHaveKill = true;
            GameController.deleteUnit(unitEnemy);
            if (combatUnit instanceof NonRangedUnit) {
                combatUnitTile.transferUnitTo(combatUnit, unitEnemyTile);
            }
        }
        if (isHaveKill) return "Unit was killed";
        return "both are damaged and attack happened successfully";
    }

    public static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, double combatStrength1, CombatUnit combatUnit, double combatStrength2) {
        double strengthDiff = combatStrength1 - combatStrength2;
        Random random = new Random();
        calculateDamage(nonRangedUnit, -strengthDiff);
        calculateDamage(combatUnit, strengthDiff);
    }

    protected static String AttackRangedUnit(RangedUnit ownRangedUnit, CombatUnit enemyUnit, Civilization civilization, Tile currentTile, Tile enemyTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(enemyUnit.getLocation(), currentTile);
        if (ownRangedUnit.getType().getRange() >= path.size()) {
            return affectRangeAttack(ownRangedUnit, enemyUnit, currentTile, enemyTile);
        } else {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
    }

    private static String affectRangeAttack(RangedUnit rangedUnit, Unit unit, Tile rangedUnitTile, Tile combatUnitTile) {
        String response;
        double strengthRangedUnit = calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength");
        double EnemyUnitStrength = calculateCombatStrength(unit, combatUnitTile, "combatstrength");
        calculateRangeAttackDamage(rangedUnit, strengthRangedUnit, unit, EnemyUnitStrength);
        rangedUnit.setAvailableMoveCount(0);
        response = checkForKill(rangedUnit, unit, rangedUnitTile, combatUnitTile);
        return response;
    }

    private static void calculateRangeAttackDamage(RangedUnit rangedUnit, double strengthRangedUnit, Unit unit, double combatUnitStrength) {
        double strengthDiff = strengthRangedUnit - combatUnitStrength;
        Random random = new Random();
        calculateDamage(unit, strengthDiff);
    }

    public static void setupUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit combatUnit) || combatUnit.getType().getCombatType() != CombatTypeEnum.SIEGE) {
            throw new CommandException(CommandResponse.UNIT_IS_NOT_SIEGE);
        }

        if (combatUnit.isSetup()) {
            throw new CommandException(CommandResponse.UNIT_HAS_ALREADY_SAT_UP);
        }
        if (combatUnit.getAvailableMoveCount() < 1) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
        }
        combatUnit.setAvailableMoveCount(combatUnit.getAvailableMoveCount() - 1);
        combatUnit.setState(UnitStates.SETUP);
    }

}
