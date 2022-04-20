package Controllers;

import Enums.CommandResponse;
import Models.Civilization;
import Models.Game;
import Models.Tiles.TileGrid;
import Models.Units.Unit;

public class GameMenuController {
    private final Game game;
    private Civilization player1;
    private Civilization player2;

    public GameMenuController(Game newGame) {
        this.game = newGame;
    }

    public CommandResponse battle(Civilization attacking, Civilization defending) {
        return CommandResponse.OK;
    }

    public CommandResponse movement(Unit moving) {
        return CommandResponse.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
