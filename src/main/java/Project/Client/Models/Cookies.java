package Project.Client.Models;

import Project.Client.Utils.DatabaseQuerier;
import Project.SharedModels.Cities.City;
import Project.SharedModels.Location;
import Project.SharedModels.Tiles.Tile;
import Project.SharedModels.Units.Unit;

public class Cookies {
    private String loginToken = null;
    private String currentGameId = null;
    private Unit selectedUnit = null;
    private Tile selectedTile = null;
    private Location selectedTileLocation = null;
    private final SettingOptions settingOptions = new SettingOptions();

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }


    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public City getSelectedCity() {
        return DatabaseQuerier.getSelectedCity();
    }



    public Tile getSelectedTile() {
        return DatabaseQuerier.getTileByLocation(String.valueOf(selectedTileLocation.getRow()),String.valueOf(selectedTileLocation.getCol()));
    }


    public String getCurrentGameId() {
        return currentGameId;
    }

    public void setCurrentGameId(String currentGameId) {
        this.currentGameId = currentGameId;
    }

    public Location getSelectedTileLocation() {
        return selectedTileLocation;
    }

    public void setSelectedTileLocation(Location selectedTileLocation) {
        this.selectedTileLocation = selectedTileLocation;
    }

    public SettingOptions getSettingOptions() {
        return settingOptions;
    }
}
