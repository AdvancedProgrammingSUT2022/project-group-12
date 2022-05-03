package Models;

import Controllers.CivilizationController;
import Enums.TechnologyEnum;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {

    private final User user;
    private final String name;
    private final CivilizationController controller;
    private final ArrayList<City> cities;
    private final ArrayList<String> notifications;
    private final ArrayList<Tile> ownedTiles;
    private final ArrayList<Unit> units = new ArrayList<>();
    private final TileGrid revealedTileGrid;
    private int gold;
    private int beaker;
    private int happiness;
    private int production;
    private ArrayList<TechnologyEnum> technologies;
    private Tile currentTile;
    private HashMap<TechnologyEnum, Integer> researchingTechnologies;
    private TechnologyEnum currentTech;
    private ArrayList<CombatUnit> combatUnits;
    private ArrayList<NonCombatUnit> nonCombatUnits;
    private City capital;
    private ArrayList<Civilization> isInWarWith;
    private Location currentGridLocation = new Location(0, 0);

    public Civilization(User user) {
        this.user = user;
        this.gold = 0;
        this.production = 0;
        this.isInWarWith = new ArrayList<>();
        this.name = user.getNickname();
        this.revealedTileGrid = new TileGrid(this, Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        this.controller = new CivilizationController();
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.ownedTiles = null;
    }

    public Civilization(User user, TileGrid tileGrid) {
        this.user = user;
        this.name = user.getNickname();
        this.revealedTileGrid = tileGrid;
        this.controller = new CivilizationController();
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.ownedTiles = null;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean isInWarWith(Civilization civilization) {
        return this.isInWarWith.contains(civilization);
    }

    public boolean isHaveThisTech(TechnologyEnum tech) {
        return this.technologies.contains(tech);
    }

    public void goToWarWith(Civilization civilization) {
        this.isInWarWith.add(civilization);
    }

    public void endWarWith(Civilization civilization) {
        this.isInWarWith.remove(civilization);
    }

    public int getHappiness() {
        return this.happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness += happiness;
    }

    public HashMap<TechnologyEnum, Integer> getResearchingTechnologies() {
        return researchingTechnologies;
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

    public ArrayList<CombatUnit> getCombatUnits() {
        return combatUnits;
    }

    public ArrayList<NonCombatUnit> getNonCombatUnits() {
        return nonCombatUnits;
    }

    public ArrayList<Tile> getOwnedTiles() {
        return this.ownedTiles;
    }

    public TileGrid getRevealedTileGrid() {
        return this.revealedTileGrid;
    }

    public City getCapital() {
        return this.capital;
    }

    public TechnologyEnum getCurrentTech() {
        return currentTech;
    }

    public void setCurrentTech(TechnologyEnum currentTech) {
        this.currentTech = currentTech;
    }

    public ArrayList<Civilization> getIsInWarWith() {
        return this.isInWarWith;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return this.name.substring(0, Math.min(4, this.name.length()));
    }

    public CivilizationController getController() {
        return this.controller;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public StringBuilder getNotifications() {
        StringBuilder notificationList = new StringBuilder();
        for (String message : this.notifications) {
            notificationList.append(message).append("\n");
        }
        return notificationList;
    }

    public void sendMessage(String message) {
        this.notifications.add(message);
    }

    public User civUser() {
        return this.user;
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public Tile getCurrentTile() { // todo: dummy
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Location getCurrentGridLocation() {
        return currentGridLocation;
    }

    public void setCurrentGridLocation(Location currentGridLocation) {
        this.currentGridLocation = currentGridLocation;
    }

    public void applyNotes() {
        for (City city : this.cities) {
             city.applyBuildingNotes();
        }
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }
}