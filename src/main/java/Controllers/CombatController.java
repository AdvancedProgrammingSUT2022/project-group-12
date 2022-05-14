package Controllers;

import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;

import static Controllers.MovingController.findTheShortestPath;


public class CombatController extends GameController {

    public CombatController(Game newGame) {
        super(newGame);
    }

    public static String AttackUnit(Unit unit, Location location) throws CommandException {
        if (!(unit instanceof CombatUnit combatUnit)) {
            throw new CommandException(CommandResponse.ATTACK_UNIT_IS_NOT_COMBAT);
        }
//        if (unit.getAvailableMoveCount() <= 0) {
//            throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
//        }
        Civilization civilization = combatUnit.getCivilization();
        Tile currentTile = GameController.getGameTile(combatUnit.getLocation());
        Tile enemyTile = GameController.getGameTile(location);
        if (isAnEnemyCityAt(location, civilization)) {
            return CityCombatController.AttackToCity(combatUnit, enemyTile.getCity(), civilization, currentTile, enemyTile);
        }
        if (!isAttackableEnemyOn(location, civilization, combatUnit)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        ArrayList<Tile> path = findTheShortestPath(location, currentTile);

        if (combatUnit instanceof RangedUnit rangedUnit) {
            // todo
//            return UnitCombatController.AttackRangedUnit(rangedUnit, enemyTile.getCombatUnit(), civilization, currentTile, enemyTile);
            return null;
        } else if (combatUnit instanceof NonRangedUnit nonRangedUnit) {
//            return UnitCombatController.AttackNonRangedUnit(nonRangedUnit, enemyTile.getCombatUnit(), civilization, currentTile, enemyTile);
            if (unit.getAvailableMoveCount() < path.size()) {
                throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
            }
            return UnitCombatController.affectNonRangeAttack(nonRangedUnit, enemyTile.getCombatUnit(), currentTile, enemyTile);
        } else { // never
            return null;
        }
    }

    public static String AttackCity(City city, Location location, String combatType) throws CommandException {
        Civilization civilization = city.getCivilization();
        Tile currentTile = GameController.getGameTile(city.getLocation());
        Tile enemyTile = GameController.getGameTile(location);
        if (isAnEnemyCityAt(location, civilization)) {
            return CityCombatController.cityAttackToCity(GameController.getGameTile(location).getCity(), currentTile, civilization, GameController.getGameTile(location).getCity());
        }
        if (!isAttackableEnemyOn(location, civilization, city)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        if (enemyTile.getCombatUnit() != null) {
            return CityCombatController.CityAttackRangedUnit(enemyTile.getCombatUnit(), currentTile.getCity(), enemyTile, currentTile);
        } else {
            return CityCombatController.CityAttackRangedUnit(enemyTile.getNonCombatUnit(), currentTile.getCity(), enemyTile, currentTile);
        }
    }
}

