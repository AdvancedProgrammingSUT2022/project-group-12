package Controllers;

import Enums.CommandResponseEnum;
import Models.Game;
import Models.Tiles.TileGrid;
import Models.Units.Unit;

public class GameMenuController {
    private final Game game;

    public GameMenuController(Game newGame) {
        this.game = newGame;
    }

    public CommandResponseEnum battle(Unit Attacking, Unit defending) {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum movement(Unit moving) {
        return CommandResponseEnum.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
