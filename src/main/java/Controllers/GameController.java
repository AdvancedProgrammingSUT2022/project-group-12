package Controllers;

import Enums.CommandResponse;
import Models.Civilization;
import Models.Database;
import Models.Game;
import Models.Tiles.TileGrid;
import Models.Units.Unit;
import Models.User;
import Views.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class GameController {
    private final Game game;
    private Civilization player1;
    private Civilization player2;

    public GameController(Game newGame) {
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
