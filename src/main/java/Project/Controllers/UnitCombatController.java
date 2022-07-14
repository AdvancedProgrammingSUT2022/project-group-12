package Project.Controllers;

import Project.Enums.CombatTypeEnum;
import Project.Enums.UnitEnum;
import Project.Enums.UnitStates;
import Project.Models.Civilization;
import Project.Models.Game;
import Project.Models.Tiles.Tile;
import Project.Models.Units.*;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;


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
        StringBuilder message = new StringBuilder("");
        if (combatUnit.getHealth() <= 0) {
            isHaveKill = true;
            message.append(" our unit was killed ");
            GameController.deleteUnit(combatUnitTile.getCombatUnit());
            combatUnit = null;
        }

        if (unitEnemy.getHealth() <= 0) {
            isHaveKill = true;
            message.append(" enemy unit was killed");
            GameController.deleteUnit(unitEnemy);
            if (combatUnit instanceof NonRangedUnit) {
                combatUnitTile.transferUnitTo(combatUnit, unitEnemyTile);
                if(unitEnemyTile.getNonCombatUnit() != null){
                    captureTheNonCombatUnitOrKillIt(unitEnemyTile,combatUnit.getCivilization());
                    message.append(" noncombat unit captured or killed");
                }
            }    // todo: refactor

        }
        if (isHaveKill) return String.valueOf(message);
        message.append(" both damaged and attack was successfully");
        return String.valueOf(message);
    }
    private static void captureTheNonCombatUnitOrKillIt(Tile tile, Civilization civ) {
        NonCombatUnit capturedUnit = tile.getNonCombatUnit();
        if (capturedUnit.getType() == UnitEnum.WORKER || capturedUnit.getType() == UnitEnum.SETTLER) {
            /*
             non combat unit has captured
             */
            capturedUnit.setType(UnitEnum.WORKER);
            capturedUnit.setCiv(civ);
        } else {
            /*
             * nonCombat has killed
             */
            tile.setUnitNull(capturedUnit);
        }
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
