package project.Controllers;

import project.Enums.CombatTypeEnum;
import project.Enums.UnitStates;
import project.Models.Game;
import project.Models.Tiles.Tile;
import project.Models.Units.CombatUnit;
import project.Models.Units.NonRangedUnit;
import project.Models.Units.RangedUnit;
import project.Models.Units.Unit;
import project.Utils.CommandException;
import project.Utils.CommandResponse;


public class UnitCombatController extends CombatController {

    public UnitCombatController(Game newGame) {
        super(newGame);
    }

    public static String affectNonRangedAttack(NonRangedUnit nonRangedUnit, CombatUnit enemyUnit, Tile nonRangedTile, Tile enemyTile) throws CommandException {
        if (nonRangedUnit.isHasAttack()) {
            throw new CommandException(CommandResponse.UNIT_CAN_ATTACK_ONCE);
        }
        double combatStrength = nonRangedUnit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength", enemyUnit);
        double enemyCombatStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyTile, "combatstrength", nonRangedUnit);
        double strengthDiff = combatStrength - enemyCombatStrength;
        nonRangedUnit.decreaseHealth(nonRangedUnit.calculateDamage(-strengthDiff));
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(strengthDiff));
        if (nonRangedUnit.getType().canMoveAfterAttack()) {
            nonRangedUnit.setHasAttack(true);
            nonRangedUnit.setAvailableMoveCount(nonRangedUnit.getAvailableMoveCount() - 1);
        } else {
            nonRangedUnit.setAvailableMoveCount(0);
        }
        return checkForKill(nonRangedUnit, enemyUnit, nonRangedTile, enemyTile);
    }

    private static String checkForKill(CombatUnit combatUnit, Unit unitEnemy, Tile combatUnitTile, Tile unitEnemyTile) {
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


    public static String affectRangeAttack(RangedUnit rangedUnit, Unit enemyUnit, Tile rangedUnitTile, Tile enemyTile) throws CommandException {
        if (rangedUnit.getType().requiresSetup() && !rangedUnit.isSetup()) {
            throw new CommandException(CommandResponse.SIEGE_NOT_SETUP);
        }
        double strengthRangedUnit = rangedUnit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength", enemyUnit);
        double enemyUnitStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyTile, "combatstrength", enemyUnit);
        double strengthDiff = strengthRangedUnit - enemyUnitStrength;
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(-strengthDiff));
        rangedUnit.setAvailableMoveCount(0);
        return checkForKill(rangedUnit, enemyUnit, rangedUnitTile, enemyTile);
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
