package Project.Server.Controllers;

import Project.Models.Cities.City;
import Project.Models.Civilization;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonRangedUnit;
import Project.Models.Units.RangedUnit;
import Project.Models.Units.Unit;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;

import java.util.ArrayList;

import static Project.Server.Controllers.MovingController.findTheShortestPath;
import static Project.Server.Controllers.MovingController.moveUnit;


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
        Tile currentTile = getGameTile(combatUnit.getLocation());
        Tile enemyTile = getGameTile(location);
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

    public static String AttackCity(City city, Location location) throws CommandException {
        Civilization civilization = city.getCivilization();
        Tile currentTile = getGameTile(city.getLocation());
        Tile enemyTile = getGameTile(location);
        if (isAnEnemyCityAt(location, civilization)) {
           if (checkForDistance(city, location, currentTile)) {
               return CityCombatController.affectCityAttackToCity(city, getGameTile(location).getCity());
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
        ArrayList<Tile> path = findTheShortestPath(destLocation, currentTile, (tile -> tile.getTerrain().getTerrainType().isRangeAttackable()));
        int distance = getGame().getTileGrid().calculateStraightDistanceWith(currentTile, getGameTile(destLocation));
        if(combatUnit instanceof  RangedUnit) {
            if (combatUnit.getUnitType().getRange() < distance) {
                throw new CommandException(CommandResponse.TARGET_OUT_OF_UNIT_RANGE);
            } else if (path.size() > distance && !combatUnit.getUnitType().canFireIndirectly()) {
                throw new CommandException(CommandResponse.IMPASSABLE_TILE_BETWEEN_UNIT_AND_TARGET);
            }
        }else{
            if (combatUnit.getAvailableMoveCount() <= (MovingController.findPathLength(path) - path.get(path.size() - 1).calculateMovementCost())) {
                throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
            }
        }
        return true;
    }
    public static boolean checkForDistance(City city,Location destLocation,Tile currentTile) throws CommandException {
        ArrayList<Tile> path = findTheShortestPath(destLocation, currentTile, (tile -> tile.getTerrain().getTerrainType().isRangeAttackable()));
        if (city.getRange() < path.size()) {
            throw new CommandException(CommandResponse.ATTACK_ISNT_POSSIBLE);
        }
        return true;
    }
}

