package Project.Client.Utils;

import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;

import java.util.Locale;

public class Cookies {
    private String loginToken = null;
    private String currentGameId = null;
    private Unit selectedUnit = null;
    private City selectedCity = null;
    private Tile selectedTile = null;
    private Location selectedTileLocation = null;

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
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
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
}
