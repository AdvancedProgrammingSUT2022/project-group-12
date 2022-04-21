package Controllers;

import Enums.CommandResponse;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.Unit;

public class GameMenuController {
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



    private static CommandResponse validateAttacking(Tile currentTile, Civilization civilization) {
        //TODO : complete
        return null;
    }

    private static boolean isCorrectPosition(int row, int col,Game game){
        if(row > game.getTileGrid().getHeight() || row < 0 ||  col > game.getTileGrid().getWidth() || col < 0) return false;
        return true;
    }
    public static CommandResponse showMapOnPosition(int row, int col, Game game) {
        if(isCorrectPosition(row, col,game)){
            //TODO : show map
            return CommandResponse.OK;
        }else {
            return CommandResponse.INVALID_POSITION;
        }
    }

    public CommandResponse battle(Civilization attacking, Civilization defending) {
        return CommandResponse.OK;
    }

    public CommandResponse movement(Unit moving) {
        return CommandResponse.OK;
    }
}