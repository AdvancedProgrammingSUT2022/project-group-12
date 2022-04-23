package Views.ViewFuncs;

import Enums.CommandResponse;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

public class GameMemuFuncs {
    protected Game game;
    protected Location gridCord;
    public Game getGame() {
        return game;
    }
    public GameMemuFuncs(Game game){
        this.game=game;
    }
    protected Civilization getCurrentCivilization() {
        return game.getCivTurn().get(game.getCivTurn().size() - 1);
    }

    protected Tile getCurrentTile() {
//        return game.getCivTurn().get(game.getCivTurn().size() - 1).getCurrentTile();
        return null;
    }

    protected CommandResponse isCorrectPosition(String row_s, String col_s, Game game) {
        try {
            int row = Integer.parseInt(row_s);
            int col = Integer.parseInt(col_s);
            if (TileGrid.getInstance().isLocationValid(row, col))
                return CommandResponse.INVALID_POSITION;
            return CommandResponse.OK;
        } catch (Exception e) {
            return CommandResponse.INVALID_COMMAND;
        }
    }

    protected CommandResponse isCorrectPosition(String amount_s, Game game, String direction) {
        try {
            //TODO : complete
            int amount = Integer.parseInt(amount_s);
            CommandResponse response;
            switch (direction) {
                case "right" -> response= validateRightWardMove(amount);
                case "left" -> response= validateLeftWardMove(amount);
                case "up" -> response= validateUpWardMove(amount);
                case "down" -> response= validateDownWardMove(amount);
                default -> response=CommandResponse.INVALID_DIRECTION;
            }
            return response;
        }catch (Exception e){
            return CommandResponse.INVALID_COMMAND;
        }
    }

    private CommandResponse validateRightWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateLeftWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateUpWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateDownWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    protected City getCityWithThisName(Civilization currentCivilization, String key) {
        for (City city : currentCivilization.getCities()) {
            if (city.getName().equals(key)) {
                return city;
            }
        }
        return null;
    }



}
