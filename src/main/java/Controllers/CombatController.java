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
import static Controllers.MovingController.moveUnit;


public class CombatController extends GameController {

    public CombatController(Game newGame) {
        super(newGame);
    }

    public static String AttackUnit(Unit unit, Location location) throws CommandException {
        if (!(unit instanceof CombatUnit combatUnit)) {
            throw new CommandException(CommandResponse.ATTACK_UNIT_IS_NOT_COMBAT);
        }
        if (unit.getAvailableMoveCount() <= 0) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
        }
        Civilization civilization = combatUnit.getCivilization();
        Tile currentTile = GameController.getGameTile(combatUnit.getLocation());
        Tile enemyTile = GameController.getGameTile(location);
        if (isAnEnemyCityAt(location, civilization)) {
            return CityCombatController.affectAttackToCity(combatUnit, enemyTile.getCity(), civilization, currentTile, enemyTile);
        }
        if (!isAttackableEnemyOn(location, civilization, combatUnit)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        if (combatUnit instanceof RangedUnit rangedUnit) {
            if (checkForDistance(rangedUnit, location, currentTile))
                return UnitCombatController.affectRangeAttack(rangedUnit, enemyTile.getCombatUnit(), currentTile, enemyTile);
        } else if (combatUnit instanceof NonRangedUnit nonRangedUnit) {
            if (checkForDistance(nonRangedUnit, location, currentTile)) {
                moveUnit(location, currentTile, unit);
                return UnitCombatController.affectNonRangedAttack(nonRangedUnit, enemyTile.getCombatUnit(), currentTile, enemyTile);
            }
        }
        //never
        return null;
    }

    public static String AttackCity(City city, Location location, String combatType) throws CommandException {
        Civilization civilization = city.getCivilization();
        Tile currentTile = GameController.getGameTile(city.getLocation());
        Tile enemyTile = GameController.getGameTile(location);
        if (isAnEnemyCityAt(location, civilization)) {
           if (checkForDistance(city,location,currentTile)) {
                return CityCombatController.affectCityAttackToCity(city, GameController.getGameTile(location).getCity());
            }
        }
        if (!isAttackableEnemyOn(location, civilization, city)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        if (enemyTile.getCombatUnit() != null) {
            return CityCombatController.affectCityAttackToUnit(currentTile.getCity(),enemyTile.getCombatUnit(), currentTile, enemyTile);
        } else {
            return CityCombatController.affectCityAttackToUnit(currentTile.getCity(),enemyTile.getNonCombatUnit(), currentTile, enemyTile);
        }
    }
    public static boolean checkForDistance(CombatUnit combatUnit,Location destLocation,Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(destLocation, currentTile);
        if(combatUnit instanceof  RangedUnit) {
            if (combatUnit.getType().getRange() < path.size()) {
                throw new CommandException(CommandResponse.NOT_ENOUGH_RANGE_ATTACK_COUNT);
            }
        }else{
            if (combatUnit.getAvailableMoveCount() <= (MovingController.findPathLength(path) - path.get(path.size() - 1).calculateMovementCost())) {
                throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
            }
        }
        return true;
    }
    public static boolean checkForDistance(City city,Location destLocation,Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(destLocation, currentTile);
        if (city.getRange() < path.size()) {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
        return true;
    }
}

