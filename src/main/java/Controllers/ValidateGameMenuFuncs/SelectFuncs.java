package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

public class SelectFuncs extends GameMenuFuncs {
    public SelectFuncs(Game game) {
        super(game);
    }

    public void selectCity(Command command) throws CommandException {
        try {
            Tile currentTile = getCurrentTile();
            Civilization currentCivilization = getCurrentCivilization();
            String key;
            if ((key = command.getOption("cityName")) != null) {
                City city;
            } else if ((key = command.getOption("cityPosition")) != null) {
                String[] coordinates = key.split("\\s+");
                isCorrectPosition(coordinates[0], coordinates[1]);

            } else {
                System.out.println(CommandResponse.INVALID_COMMAND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StringBuilder selectCityByPosition(Location cityLocation) throws CommandException {
        isCorrectPosition(String.valueOf(cityLocation.getRow()), String.valueOf(cityLocation.getCol()));
        City city;
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        Tile cityTile = currentCiv.getRevealedTileGrid().getTile(cityLocation);
        assertCityByPosition(cityTile);
        city = cityTile.getCity();
        return GameController.showCity(city);
    }

    private void assertCityByPosition(Tile tile) throws CommandException {
        if (tile.getCity() == null) throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }

    public StringBuilder selectCityWithName(String key) throws CommandException {
        City city;
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        city = getCityWithThisName(currentCiv, key);
        return GameController.showCity(city);
    }
}
