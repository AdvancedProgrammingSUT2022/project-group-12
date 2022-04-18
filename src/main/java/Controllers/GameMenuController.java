package Controllers;

import Enums.CommandResponseEnum;
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

    public CommandResponseEnum battle(Civilization attacking, Civilization defending) {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum movement(Unit moving) {
        return CommandResponseEnum.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
