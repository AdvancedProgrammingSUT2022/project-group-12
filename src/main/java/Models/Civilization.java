package Models;

import Controllers.CivilizationController;
import Enums.GameEnums.TechnologyEnum;
import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    // todo: complete

    private final User user;
    private final String name;
    private int gold;
    private int beaker;
    private int happiness;
    private int production;
    private ArrayList<TechnologyEnum> technologies;
    private HashMap<UnitEnum, Integer> combatUnits;
    private HashMap<UnitEnum, Integer> nonCombatUnits;
    private ArrayList<Tile> tiles;
    private TileGrid revealedTileGrid;
    private City capital;
    private ArrayList<City> cities;
    private ArrayList<String> isInWarWith;
    private final CivilizationController controller;

    public Civilization(User user) {
        this.name = user.getNickname();
        this.user = user;
        this.controller = new CivilizationController();
        this.cities = new ArrayList<>();
    }

    public boolean isAlreadyInWarWith(String username) {
        return this.isInWarWith.contains(username);
    }

    public void goToWarWith(String username) {
        this.isInWarWith.add(username);
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHappiness() {
        return this.happiness;
    }

    public int getGold() {
        return this.gold;
    }

    public int getBeaker() {
        return this.beaker;
    }

    public int getProduction() {
        return this.production;
    }

    public ArrayList<TechnologyEnum> getTechnologies() {
        return this.technologies;
    }

    public HashMap<UnitEnum, Integer> getCombatUnits() {
        return this.combatUnits;
    }

    public HashMap<UnitEnum, Integer> getNonCombatUnits() {
        return this.nonCombatUnits;
    }

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public TileGrid getRevealedTileGrid() {
        return this.revealedTileGrid;
    }

    public City getCapital() {
        return this.capital;
    }

    public ArrayList<String> getIsInWarWith() {
        return this.isInWarWith;
    }

    public CivilizationController getController() {
        return this.controller;
    }

    public String getName() {
        return this.name;
    }
}