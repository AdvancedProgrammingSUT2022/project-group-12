package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Location;
import Models.Tiles.TileGrid;
import Utils.Command;
import Utils.CommandException;
import Utils.Constants;
import Views.TileGridPrinter;

public class MapFuncs {

    public void showMap(Command command) {
        if ((command.getOption("position")) != null) {
            try {
                Location location = command.getLocationOption("position");
                showMapPosition(location);
            } catch (CommandException e) {
                e.print();
            }
        } else {
            showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
        }
    }

    public void showMapPosition(Location location) {
        GameController.getGame().updateRevealedTileGrid(GameController.getGame().getCurrentCivilization());
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        String output = new TileGridPrinter(tileGrid, Constants.TERMINAL_HEIGHT, Constants.TERMINAL_WIDTH).print(location);
        System.out.print(output);
    }

    public void moveMapByDirection(Command command, String direction) {
        int amount = Integer.parseInt(command.getOption("amount"));
        Location currentGridLocation = new Location(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
        switch (direction) {
            case "down" -> currentGridLocation.addRow(amount);
            case "up" -> currentGridLocation.addRow(amount * -1);
            case "right" -> currentGridLocation.addCol(amount);
            case "left" -> currentGridLocation.addCol(amount * -1);
        }
        GameController.getGame().getCurrentCivilization().setCurrentSelectedGridLocation(correctGridLocation(currentGridLocation));
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

}
