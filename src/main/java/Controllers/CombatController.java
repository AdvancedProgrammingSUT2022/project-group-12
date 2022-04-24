package Controllers;

import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;

import java.util.ArrayList;

import static Controllers.MovingController.findTheShortestPath;

public class CombatController extends GameController {
    public CombatController(Game newGame) {
        super(newGame);
    }

    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if (!isEnemyExists(row, col, civilization)) return "enemy doesn't exists there";
        if (currentTile.getCombatUnit() instanceof RangedUnit) {
            return AttackRangedUnit(row, col, game, currentTile, civilization, (RangedUnit) currentTile.getCombatUnit());
        } else {
            return AttackNonRangedUnit(row, col, GameController.game.getTileGrid(), currentTile, civilization, (NonRangedUnit) currentTile.getCombatUnit());
        }
    }

    private static String AttackNonRangedUnit(int row, int col, TileGrid tileGrid, Tile currentTile, Civilization civilization, NonRangedUnit nonRangedUnit) {
        ArrayList<Tile> path = findTheShortestPath(row, col, currentTile, currentTile.getCombatUnit());
        if (nonRangedUnit.getMovement() >= path.size()) {
            return calculateNonRangeAttack(nonRangedUnit, tileGrid.getTile(row, col).getCombatUnit());
        } else return "Attack is not possible";

    }

    private static String calculateNonRangeAttack(NonRangedUnit nonRangedUnit, CombatUnit combatUnit) {
        return null;
    }

    private static String AttackRangedUnit(int row, int col, Game game, Tile currentTile, Civilization civilization, RangedUnit rangedUnit) {
        return null;
    }
}
