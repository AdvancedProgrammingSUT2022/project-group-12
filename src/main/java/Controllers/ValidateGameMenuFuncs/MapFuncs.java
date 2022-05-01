package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Exceptions.CommandException;
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
        if ((command.getOption("position")) != null) {
            try {
                Location location = command.getLocationOption("position");
                showMapPosition(location);
            } catch (CommandException e) {
                e.print();
            }
        } else {
            showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentGridLocation());
        }
    }

    public void showMapPosition(Location location) {
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        String output = new TileGridPrinter(tileGrid, 20, 120).print(location);
        System.out.print(output);
    }

    public void moveMapByDirection(Command command, String direction) {
        int amount = Integer.parseInt(command.getOption("amount"));
        Location currentGridLocation = new Location(GameController.getGame().getCurrentCivilization().getCurrentGridLocation());
        switch (direction) {
            case "down" -> currentGridLocation.addRow(amount);
            case "up" -> currentGridLocation.addRow(amount * -1);
            case "right" -> currentGridLocation.addCol(amount);
            case "left" -> currentGridLocation.addCol(amount * -1);
        }
        GameController.getGame().getCurrentCivilization().setCurrentGridLocation(correctGridLocation(currentGridLocation));
    }

    private Location correctGridLocation(Location location) {
        Location newLocation = new Location(location);
        int height = GameController.getGame().getTileGrid().getHeight();
        int width = GameController.getGame().getTileGrid().getWidth();
        if (newLocation.getRow() < 0) newLocation.setRow(0);
        if (newLocation.getCol() < 0) newLocation.setCol(0);
        if (newLocation.getRow() >= height) newLocation.setRow(height - 1);
        if (newLocation.getCol() >= width) newLocation.setCol(width - 1);
        return newLocation;
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
