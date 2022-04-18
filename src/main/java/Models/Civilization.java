package Models;

import Controllers.CivilizationController;
import Enums.GameEnums.TechnologiesEnum;
import Enums.GameEnums.UnitsEnum;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    // todo: complete

    private final User user;
    private int gold;
    private int beaker;
    private int happiness;
    private int production;
    private ArrayList<TechnologiesEnum> technologies;
    private HashMap<UnitsEnum, Integer> combatUnits;
    private HashMap<UnitsEnum, Integer> nonCombatUnits;
    private ArrayList<Tile> tiles;
    private TileGrid revealedTileGrid;
    private City capital;
    private ArrayList<String> isInWarWith;
    private final CivilizationController controller;

    public Civilization(User user) {
        this.user = user;
        this.controller = new CivilizationController();
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

    public ArrayList<TechnologiesEnum> getTechnologies() {
        return this.technologies;
    }

    public HashMap<UnitsEnum, Integer> getCombatUnits() {
        return this.combatUnits;
    }

    public HashMap<UnitsEnum, Integer> getNonCombatUnits() {
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
}