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


public class UnitCombatController extends CombatController {

    public UnitCombatController(Game newGame) {
        super(newGame);
    }

    protected static String affectNonRangedAttack(NonRangedUnit nonRangedUnit, CombatUnit enemyUnit, Tile nonRangedTile, Tile enemyTile) {
        String response;
        double combatStrength = nonRangedUnit.calculateCombatStrength(nonRangedUnit, nonRangedTile, "combatstrength");
        double enemyCombatStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyTile, "combatstrength");

        double strengthDiff = combatStrength - enemyCombatStrength;

        System.out.println("strengthDiff = " + strengthDiff);
        
        System.out.println(nonRangedUnit.getHealth());
        System.out.println(enemyUnit.getHealth());

        System.out.println(nonRangedUnit.calculateDamage(nonRangedUnit, -strengthDiff));
        System.out.println(enemyUnit.calculateDamage(enemyUnit, strengthDiff));

        nonRangedUnit.decreaseHealth(nonRangedUnit.calculateDamage(nonRangedUnit, -strengthDiff));
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(enemyUnit, strengthDiff));

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



    public static String affectRangeAttack(RangedUnit rangedUnit, Unit enemyUnit, Tile rangedUnitTile, Tile enemyTile) {
        String response;
        double strengthRangedUnit = rangedUnit.calculateCombatStrength(rangedUnit, rangedUnitTile, "rangedcombatstrength");
        double enemyUnitStrength = enemyUnit.calculateCombatStrength(enemyUnit, enemyTile, "combatstrength");
        double strengthDiff = strengthRangedUnit - enemyUnitStrength;
        enemyUnit.decreaseHealth(enemyUnit.calculateDamage(enemyUnit, strengthDiff));
        rangedUnit.setAvailableMoveCount(0);
        response = checkForKill(rangedUnit, enemyUnit, rangedUnitTile, enemyTile);
        return response;
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
