package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
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
                if ((city = getCityWithThisName(currentCivilization, key)) != null) {
                    System.out.println(GameController.showCity(city));
                } else {
                    System.out.println("city with this name doesn't exists");
                }
            } else if ((key = command.getOption("cityPosition")) != null) {
                String[] coordinates = key.split("\\s+");
                isCorrectPosition(coordinates[0], coordinates[1]);
                System.out.println(GameController.showCity(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), this.getGame()));
            } else {
                System.out.println(CommandResponse.INVALID_COMMAND);
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
