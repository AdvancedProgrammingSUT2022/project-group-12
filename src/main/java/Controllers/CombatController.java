package Controllers;

import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;

import static Controllers.CityCombatController.*;
import static Controllers.UnitCombatController.AttackNonRangedUnit;
import static Controllers.UnitCombatController.AttackRangedUnit;

public class CombatController extends GameController {
    public CombatController(Game newGame) {
        super(newGame);
    }

    public static String AttackUnit(Location location, Game game, Tile currentTile, Civilization civilization) {
        if (isAttackToCity(location.getRow(), location.getCol(), civilization)) {
            return AttackToCity(location, game.getTileGrid(), currentTile, civilization, game.getTileGrid().getTile(location).getCity());
        }
        if (!isEnemyIsReadyForAttack(location.getRow(), location.getCol(), civilization, currentTile.getCombatUnit())) {
            return "enemy doesn't exists there";
        }
        if (currentTile.getCombatUnit() instanceof RangedUnit) {
            return AttackRangedUnit(location, game.getTileGrid(), currentTile, civilization, (RangedUnit) currentTile.getCombatUnit());
        } else {
            return AttackNonRangedUnit(location, game.getTileGrid(), currentTile, civilization, (NonRangedUnit) currentTile.getCombatUnit());
        }
    }

    public static String AttackCity(Location location, Game game, Tile currentTile, Civilization civilization) {
        if (isAttackToCity(location.getRow(), location.getCol(), civilization)) {
            return cityAttackToCity(location, game.getTileGrid(), currentTile, civilization, game.getTileGrid().getTile(location).getCity());
        }
        if (!isEnemyIsReadyForAttack(location.getRow(), location.getCol(), civilization, currentTile.getCombatUnit())) {
            return "enemy doesn't exists there";
        }
        return CityAttackRangedUnit(location, game.getTileGrid(), currentTile, civilization, currentTile.getCity());
    }
}
