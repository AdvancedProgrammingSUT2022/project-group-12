package Controllers;

import Enums.Response;
import Models.Game;
import Models.Tiles.TileGrid;

public class GameMenuController {
    private final Game game;

    public GameMenuController(Game newGame) {
        this.game = newGame;
    }

    public Response battle() {
        return Response.OK;
    }

    public Response movement() {
        return Response.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
