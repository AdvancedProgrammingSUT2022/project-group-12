package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Utils.CommandException;
import Utils.CommandResponse;

public class GameMenuFuncs {
    protected static Game game;

    public GameMenuFuncs(Game game) {
        GameMenuFuncs.game = game;
    }

    protected static Civilization getCurrentCivilization() {
        return game.getCurrentCivilization();
    }

    protected static Tile getCurrentTile() {
        return game.getTileGrid().getTile(getCurrentCivilization().getCurrentGridLocation());
    }

    public static void isCorrectPosition(String row_s, String col_s) throws CommandException {
        try {
            int row = Integer.parseInt(row_s);
            int col = Integer.parseInt(col_s);
            if (!GameController.getGame().getTileGrid().isLocationValid(row, col)) {
                throw new CommandException(CommandResponse.INVALID_POSITION);
            }
        } catch (Exception e) {
            throw new CommandException(CommandResponse.INVALID_COMMAND);
        }
    }

    public Game getGame() {
        return game;
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

    public City getCityWithThisName(Civilization currentCivilization, String name) throws CommandException {
        for (City city : currentCivilization.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }
}
