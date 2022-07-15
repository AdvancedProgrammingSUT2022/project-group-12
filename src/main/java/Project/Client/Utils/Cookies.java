package Project.Client.Utils;

import Project.Models.Cities.City;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;

public class Cookies {
    private String loginToken = null;
    private Unit selectedUnit = null;
    private City selectedCity = null;
    private Tile selectedTile = null;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
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
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }
}
