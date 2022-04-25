package Views.ViewFuncs;

import Controllers.Command;
import Controllers.GameController;
import Controllers.GameMenuController;
import Enums.CommandResponse;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;

public class MapFuncs extends GameMenuFuncs {


    public MapFuncs(Game game) {
        super(game);
    }

    public void showMap(Command command) {
        String key;
        if ((key = command.getOption("position")) != null) {
            showMapPosition(key);
        } else if ((key = command.getOption("cityname")) != null) {
            City city = getCityWithThisName(getCurrentCivilization(), key);
            System.out.println(city == null ? "city doesn't exists with this name" : GameMenuController.showMapOnCity(city));
        } else {
            System.out.println(CommandResponse.CommandMissingRequiredOption);
        }
    }

    private void showMapPosition(String key) {
        String[] coordinates = key.split("\\s+");
        CommandResponse response = isCorrectPosition((coordinates[0]), (coordinates[1]), this.getGame());
        int row = 0, col = 0;
        if (response.isOK()) {
            row = Integer.parseInt(coordinates[0]);
            col = Integer.parseInt(coordinates[1]);
        }
        System.out.println(!response.isOK() ? response : GameMenuController.showMapOnPosition(row, col, this.getGame()));
    }

    public CommandResponse moveMapByDirection(Command command, String direction) {
        CommandResponse response = validateCommandForMoveByDirection(command.getType().trim(), command.getCategory(), command.getSubCategory(), command.getSubSubCategory(), command, direction);
        int amount = 1; // todo: get amount
        Location newCord = gridCord;
        switch (direction) {
            case "down" -> newCord.moveY(amount);
            case "up" -> newCord.moveY(amount * -1);
            case "right" -> newCord.moveX(amount);
            case "left" -> newCord.moveX(amount * -1);
        }
        if (!GameController.game.getTileGrid().isLocationValid(newCord.getX(), newCord.getY())) {
            return CommandResponse.INVALID_DIRECTION; // todo: out of map
        }
        this.gridCord = newCord;
        return CommandResponse.OK;
    }

    public CommandResponse validateCommandForMoveByDirection(String type, String category, String subCategory, String subSubCategory, Command command, String direction) {
        if (type.trim().length() > (category + " " + subCategory + " " + subSubCategory).length())
            return CommandResponse.INVALID_COMMAND;
        String amount = command.getOption("amount");
        return isCorrectPosition(amount, this.getGame(), direction);
    }

    private void unitDelete(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit")) {
            case "non combat" -> {
                response = validateForNonCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.deleteNonCombatUnit(currentCivilization, currentTile);
            }
            case "combat" -> {
                response = validateForCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.deleteCombatUnit(currentCivilization, currentTile);
            }
            default -> response = CommandResponse.CommandMissingRequiredOption;
        }
        System.out.println(response.isOK() ? "unit deleted successfully" : response);
    }

    private CommandResponse validateForNonCombatUnit(Tile currentTile, Civilization civilization) {
        if (!(civilization.getCurrentTile().getNonCombatUnit().getType() == null)) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.WRONG_UNIT;
        }
        return CommandResponse.OK;
    }

    private CommandResponse validateForCombatUnit(Tile currentTile, Civilization civilization) {
        if (!(civilization.getCurrentTile().getCombatUnit().getType() == null)) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)) {
            return CommandResponse.WRONG_UNIT;
        }
        return CommandResponse.OK;
    }

}
