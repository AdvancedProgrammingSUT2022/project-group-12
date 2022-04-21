package Controllers;

import Enums.CommandResponseEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.Unit;

import java.io.Serializable;

public class GameMenuController {
    private final Game game;
    private Civilization player1;
    private Civilization player2;

    public GameMenuController(Game newGame) {
        this.game = newGame;
    }


    public static boolean validateCity(String key,Game game){
        for (Tile tile:
                game.getCivTurn().get(game.getCivTurn().size()-1).getTiles() ) {
            if(tile.getCity() != null && tile.getCity().getName().equals(key)) return true;
        }
        return false;
    }
    public static String showMapOnCity(City city) {
        return "map moved successfully";
    }
    public static String moveMapRight(int amount) {
        return "map moved successfully";
        // TODO : move map right
    }

    public static String moveMapUp(int amount) {

        return "map moved successfully";
        // TODO : move map right
    }

    public static String moveMapLeft(int amount) {

        return "map moved successfully";
        // TODO : move map right
    }

    public static String moveMapDown(int amount) {

        return "map moved successfully";
        // TODO : move map right
    }



    private static CommandResponseEnum validateAttacking(Tile currentTile, Civilization civilization) {
        //TODO : complete
        return null;
    }


    public Game getGame() {
        return game;
    }
    private static boolean isCorrectPosition(int row, int col,Game game){
        if(row > game.getTileGrid().getHeight() || row < 0 ||  col > game.getTileGrid().getWidth() || col < 0) return false;
        return true;
    }
    public static CommandResponseEnum showMapOnPosition(int row, int col, Game game) {
        if(isCorrectPosition(row, col,game)){
            //TODO : show map
            return CommandResponseEnum.OK;
        }else {
            return CommandResponseEnum.INVALID_POSITION;
        }
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
