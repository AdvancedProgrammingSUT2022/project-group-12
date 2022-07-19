package Server.Controllers.ValidateGameMenuFuncs;

import Project.Models.Location;
import Project.Models.Tiles.TileGrid;
import Project.Utils.Constants;
import Server.Controllers.GameController;
import Server.Views.TileGridPrinter;

public class MapFuncs {

    public void showMapPosition(Location location) {
        GameController.getGame().updateRevealedTileGrid(GameController.getGame().getCurrentCivilization());
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
//        TileGrid tileGrid = GameController.getGame().getTileGrid();
        String output = new TileGridPrinter(tileGrid, Constants.TERMINAL_HEIGHT, Constants.TERMINAL_WIDTH).print(location);
        System.out.print(output);
    }

    public void moveMapByDirection(String direction, int amount) {
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
