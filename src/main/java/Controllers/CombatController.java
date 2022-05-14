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


public class CombatController extends GameController {

    public CombatController(Game newGame) {
        super(newGame);
    }

    public static String AttackUnit(Unit unit, Location location) throws CommandException {
        Civilization civilization = unit.getCivilization();
        Tile currentTile = GameController.getGame().getTileGrid().getTile(unit.getLocation());
        Tile enemyTile = GameController.getGame().getTileGrid().getTile(location);
        if (isAttackToCity(location, civilization)) {
            return CityCombatController.AttackToCity((CombatUnit) unit,enemyTile.getCity(),civilization ,currentTile ,enemyTile);
        }
        if (!isEnemyIsReadyForAttack(location , civilization,(CombatUnit)unit)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        if (unit instanceof RangedUnit) {
            return UnitCombatController.AttackRangedUnit((RangedUnit) unit,enemyTile.getCombatUnit(),civilization,currentTile,enemyTile);
        } else {
            return UnitCombatController.AttackNonRangedUnit((NonRangedUnit) unit,enemyTile.getCombatUnit(),civilization,currentTile,enemyTile);
        }
    }

    public static String AttackCity(City city, Location location, String combatType) throws CommandException {
        Civilization civilization = city.getCivilization();
        Tile currentTile = GameController.getGame().getTileGrid().getTile(city.getLocation());
        Tile enemyTile = GameController.getGame().getTileGrid().getTile(location);
        if (isAttackToCity(location, civilization)) {
            return CityCombatController.cityAttackToCity(GameController.getGame().getTileGrid().getTile(location).getCity(),currentTile, civilization, GameController.getGame().getTileGrid().getTile(location).getCity());
        }
        if (!isEnemyIsReadyForAttack(location , civilization, city)) {
            throw new CommandException(CommandResponse.ENEMY_DOESNT_EXISTS);
        }
        if(enemyTile.getCombatUnit() != null)
            return CityCombatController.CityAttackRangedUnit(enemyTile.getCombatUnit(), currentTile.getCity(), enemyTile, currentTile);
        else
            return CityCombatController.CityAttackRangedUnit(enemyTile.getNonCombatUnit(), currentTile.getCity(),enemyTile,currentTile);
    }
    }

