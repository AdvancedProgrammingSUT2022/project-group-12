package Server.Controllers;

import Project.Enums.CombatTypeEnum;
import Project.Enums.UnitEnum;
import Project.Enums.UnitStates;
import Project.Models.Tiles.Tile;
import Project.Models.Units.*;
import Project.Utils.CommandResponse;
import Server.Models.Civilization;
import Server.Models.Game;
import Server.Utils.CommandException;


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
        if (nonRangedUnit.getUnitType().canMoveAfterAttack()) {
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
            deleteUnit(combatUnitTile.getCombatUnit());
            combatUnit = null;
        }

        if (unitEnemy.getHealth() <= 0) {
            isHaveKill = true;
            message.append(" enemy unit was killed");
            deleteUnit(unitEnemy);
            if (combatUnit instanceof NonRangedUnit) {
                combatUnitTile.transferUnitTo(combatUnit, unitEnemyTile);
                if(unitEnemyTile.getNonCombatUnit() != null){
                    captureTheNonCombatUnitOrKillIt(unitEnemyTile,GameController.getCivByName(combatUnit.getCivName()));
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
        if (capturedUnit.getUnitType() == UnitEnum.WORKER || capturedUnit.getUnitType() == UnitEnum.SETTLER) {
            /*
             non combat unit has captured
             */
            GameController.getGame().getCivByName(capturedUnit.getCivName()).getUnits().remove(capturedUnit);
            capturedUnit.setUnitType(UnitEnum.WORKER);
            capturedUnit.setCiv(civ.getName());
        } else {
            /*
             * nonCombat has killed
             */
            GameController.getGame().getCivByName(capturedUnit.getCivName()).getUnits().remove(capturedUnit);
            tile.setUnitNull(capturedUnit);
        }
    }

    public static String affectRangeAttack(RangedUnit rangedUnit, Unit enemyUnit, Tile rangedUnitTile, Tile enemyTile) throws CommandException {
        if (rangedUnit.getUnitType().requiresSetup() && !rangedUnit.isSetup()) {
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
        if (!(unit instanceof CombatUnit combatUnit) || combatUnit.getUnitType().getCombatType() != CombatTypeEnum.SIEGE) {
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
