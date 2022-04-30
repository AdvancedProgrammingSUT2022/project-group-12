package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Utils.CommandResponse;

public class GameMenuFuncs {
    protected static Game game;
    protected Location gridCord;

    public GameMenuFuncs(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected static Civilization getCurrentCivilization() {
        return game.getCurrentCivilization();
    }

    protected static Tile getCurrentTile() {
        return game.getCurrentCivilization().getCurrentTile();
    }

    protected static CommandResponse isCorrectPosition(String row_s, String col_s) {
        try {
            int row = Integer.parseInt(row_s);
            int col = Integer.parseInt(col_s);
            if (!GameController.game.getTileGrid().isLocationValid(row, col)) return CommandResponse.INVALID_POSITION;
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
                case "right" -> response = validateRightWardMove(amount);
                case "left" -> response = validateLeftWardMove(amount);
                case "up" -> response = validateUpWardMove(amount);
                case "down" -> response = validateDownWardMove(amount);
                default -> response = CommandResponse.INVALID_DIRECTION;
            }
            return response;
        } catch (Exception e) {
            return CommandResponse.INVALID_COMMAND;
        }
    }

    private CommandResponse validateRightWardMove(int amount) {
        //TODO : validate rightward move
        return CommandResponse.OK;
    }


    private CommandResponse validateLeftWardMove(int amount) {
        //TODO : validate rightward move
        return CommandResponse.OK;
    }

    private CommandResponse validateUpWardMove(int amount) {
        //TODO : validate rightward move
        return CommandResponse.OK;
    }

    private CommandResponse validateDownWardMove(int amount) {
        //TODO : validate rightward move
        return CommandResponse.OK;
    }

    protected City getCityWithThisName(Civilization currentCivilization, String name) {
        for (City city : currentCivilization.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }
}
