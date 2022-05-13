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


    protected static String AttackNonRangedUnit(NonRangedUnit nonRangedUnit, CombatUnit enemyUnit, Civilization civilization, Tile currentTile, Tile enemyTile) {
        ArrayList<Tile> path = findTheShortestPath(enemyUnit.getLocation(), currentTile);
        if (nonRangedUnit.getAvailableMoveCount() >= path.size()) {
            return calculateNonRangeAttack(nonRangedUnit, enemyUnit, currentTile, enemyTile);
        } else {
            return "Attack is not possible";
        }

    }

    protected static String AttackRangedUnit(RangedUnit ownRangedUnit, CombatUnit enemyUnit, Civilization civilization, Tile currentTile, Tile enemyTile) {
        ArrayList<Tile> path = findTheShortestPath(enemyUnit.getLocation(), currentTile);
        if (ownRangedUnit.getType().getRange() >= path.size()) {
            if (GameController.isEnemyExists(enemyUnit.getLocation(), civilization)) {
                return calculateRangeAttack(ownRangedUnit, enemyUnit, currentTile, enemyTile);
            } else {
                return calculateRangeAttack(ownRangedUnit,enemyUnit, currentTile, enemyTile);
            }
        } else {
            return "Attack is not possible";
        }
    }

    private static String calculateNonRangeAttack(NonRangedUnit nonRangedUnit, CombatUnit combatUnit, Tile nonRangedTile, Tile combatUnitTile) {
        String response = "Attack happened successfully";
        double combatStrength1 = calculateCombatStrength(nonRangedUnit, nonRangedTile);
        double combatStrength2 = calculateCombatStrength(combatUnit, combatUnitTile);
        calculateNonRangeAttackDamage(nonRangedUnit, combatStrength1, combatUnit, combatStrength2);
        response = checkForKill(nonRangedUnit, combatUnit, nonRangedTile, combatUnitTile);
        return null;
    }

    private static String calculateRangeAttack(RangedUnit rangedUnit, Unit unit, Tile rangedUnitTile, Tile combatUnitTile) {
        String response = "Attack happened successfully";
        double strengthRangedUnit = calculateCombatStrength(rangedUnit, rangedUnitTile);
        double EnemyUnitStrength = calculateCombatStrength(unit, combatUnitTile);
        calculateRangeAttackDamage(rangedUnit, strengthRangedUnit, unit, EnemyUnitStrength);
        response = checkForKill(rangedUnit, unit, rangedUnitTile, combatUnitTile);
        return response;
    }

    private static String checkForKill(CombatUnit combatUnit, Unit unitEnemy, Tile combatUnitTile, Tile unitEnemyTile) {
        boolean isHaveKill = false;
        if (combatUnit.getHealthBar() <= 0) {
            isHaveKill = true;
            GameController.deleteUnit(combatUnitTile.getCombatUnit());
            combatUnit = null;
        }

        if (unitEnemy.getHealthBar() <= 0) {
            isHaveKill = true;
            GameController.deleteUnit(unitEnemy);
            if (combatUnit instanceof NonRangedUnit) {
                combatUnitTile.transferUnitTo(combatUnit, unitEnemyTile);
            }
        }
        if (isHaveKill) return "Unit was killed";
        return "both are damaged and attack happened successfully";
    }

    public static void setupUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit combatUnit) || combatUnit.getType().getCombatType() != CombatTypeEnum.SIEGE) {
            throw new CommandException(CommandResponse.UNIT_IS_NOT_SIEGE);
        }

        if (combatUnit.isSetup()) {
            throw new CommandException(CommandResponse.UNIT_HAS_ALREADY_SAT_UP);
        }
        if (combatUnit.getAvailableMoveCount() < 1) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COST);
        }
        combatUnit.setAvailableMoveCount(combatUnit.getAvailableMoveCount() - 1);
        combatUnit.setState(UnitStates.SETUP);
    }

    private static void calculateNonRangeAttackDamage(NonRangedUnit nonRangedUnit, double combatStrength1, CombatUnit combatUnit, double combatStrength2) {
        double strengthDiff = combatStrength1 - combatStrength2;
        Random random = new Random();
        calculateDamage(nonRangedUnit, -strengthDiff, random);
        calculateDamage(combatUnit, strengthDiff, random);
    }

    private static void calculateRangeAttackDamage(RangedUnit rangedUnit, double strengthRangedUnit, Unit unit, double combatUnitStrength) {
        double strengthDiff = strengthRangedUnit - combatUnitStrength;
        Random random = new Random();
        calculateDamage(unit, strengthDiff, random);
    }

}
