package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Utils.Command;
import Utils.CommandResponse;
import Views.TileGridPrinter;

public class MapFuncs extends GameMenuFuncs {


    public MapFuncs(Game game) {
        super(game);
    }

    public void showMap(Command command) {
        String key;
        if ((key = command.getOption("position")) != null) {
            String[] coordinates = key.split("\\s+");
            CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1]);
            int row = 0, col = 0;
            if (response.isOK()) {
                row = Integer.parseInt(coordinates[0]);
                col = Integer.parseInt(coordinates[1]);
            }
            showMapPosition(row, col);
        } else if ((key = command.getOption("cityname")) != null) {
            City city = getCityWithThisName(getCurrentCivilization(), key); // todo: should be able to show cities of other civs too
            if (city == null) {
                System.out.println("city doesn't exists with this name");
            } else {
                showMapPosition(city.getRow(), city.getCol());
            }
        } else {
            System.out.println(CommandResponse.CommandMissingRequiredOption);
        }
    }

    private void showMapPosition(int row, int col) {
        TileGrid tileGrid = GameController.getGame().getTileGrid();
        String output = new TileGridPrinter(tileGrid, 20, 120).print(new Location(row, col));
        System.out.print(output);
    }

    public CommandResponse moveMapByDirection(Command command, String direction) {
        CommandResponse response = validateCommandForMoveByDirection(command.getType().trim(), command.getCategory(), command.getSubCategory(), command.getSubSubCategory(), command, direction);
        int amount = 1; // todo: get amount
        Location newCord = gridCord;
        switch (direction) {
            case "down" -> newCord.moveRow(amount);
            case "up" -> newCord.moveRow(amount * -1);
            case "right" -> newCord.moveCol(amount);
            case "left" -> newCord.moveCol(amount * -1);
        }
        if (!GameController.getGame().getTileGrid().isLocationValid(newCord.getRow(), newCord.getCol())) {
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
