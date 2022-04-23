package Views.ViewFuncs;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class SelectFuncs extends GameMenuFuncs {
    public SelectFuncs(Game game) {
        super(game);
    }



    public void selectNonCombatUnit(Command command) {
        try {
            Tile currentTile = getCurrentTile();
            String key;
            if ((key = command.getOption("position")) != null) {
                String[] coordinates = key.split("\\s+");
                CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1], this.getGame());
                System.out.println(response.isOK() ? GameController.showNonCombatInfo(currentTile.getNonCombatUnit()) : response);
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    public void selectCombatUnit(Command command) {
        try {
            Tile currentTile = getCurrentTile();
            String key;
            if ((key = command.getOption("position")) != null) {
                String[] coordinates = key.split("\\s+");
                CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1], this.getGame());
                System.out.println(response.isOK() ? GameController.showCombatInfo(currentTile.getCombatUnit()) : response);
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }
    public void selectCity(Command command) {
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
            CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1], this.getGame());
            System.out.println(!response.isOK() ? response : GameController.showCity(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), this.getGame()));
        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

}
