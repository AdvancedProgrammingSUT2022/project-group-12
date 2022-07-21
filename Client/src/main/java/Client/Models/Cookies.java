package Client.Models;

import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;

public class Cookies {
    private String loginToken = null;
    private String currentGameId = null;
    private City city;
    private Unit selectedUnit = null;
    private Tile selectedTile = null;
    private City selectedCity = null;
    private Location selectedTileLocation = null;
    private final SettingOptions settingOptions = new SettingOptions();

    public String getLoginToken() {
        return "???";
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }


    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public City getSelectedCity() {
        return this.selectedCity;
    }

    public Tile getSelectedTile() {
        return this.selectedTile;
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

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
