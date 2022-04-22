package Views.ViewFuncs;

import Enums.CommandResponse;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

public class GameMemuFuncs {
    private Game game;

    public Game getGame() {
        return game;
    }

    protected Civilization getCurrentCivilization() {
        return game.getCivTurn().get(game.getCivTurn().size() - 1);
    }

    protected Tile getCurrentTile() {
        return game.getCivTurn().get(game.getCivTurn().size() - 1).getCurrentTile();
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

    private CommandResponse isCorrectPosition(String amount_s, Game game, String direction) {
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



}
