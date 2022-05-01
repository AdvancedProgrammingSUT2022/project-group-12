package Controllers;

import Models.Civilization;
import Models.Tiles.Tile;
import Models.Units.Unit;
import Utils.CommandResponse;

public class GameMenuController {
//    public static boolean validateCity(String key, Game game) {
//        for (Tile tile : game.getCivTurn().get(game.getCivTurn().size() - 1).getTiles()) {
//            if (tile.getCity() != null && tile.getCity().getName().equals(key)) return true;
//        }
//        return false;
//    }

    private static CommandResponse validateAttacking(Tile currentTile, Civilization civilization) {
        //TODO : complete
        return null;
    }

    public CommandResponse battle(Civilization attacking, Civilization defending) {
        return CommandResponse.OK;
    }

    public CommandResponse movement(Unit moving) {
        return CommandResponse.OK;
    }
}