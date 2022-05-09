package Models;

import Controllers.CivilizationController;
import Enums.*;
import Models.Cities.City;
import Models.Terrains.Terrain;
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
    private ArrayList<Unit> units;
    private final TileGrid revealedTileGrid;
    private int gold;
    private int beaker;
    private int happiness;
    private int production;
    private HashMap<TechnologyEnum, Integer> technologies;
    private Tile currentTile;
    private TechnologyEnum researchingTechnology;
    private HashMap<TechnologyEnum, Integer> researchingTechnologies;
    private TechnologyEnum currentTech;
    private ArrayList<CombatUnit> combatUnits;
    private ArrayList<NonCombatUnit> nonCombatUnits;
    private City capital = null;
    private ArrayList<Civilization> isInWarWith;
    private Location currentSelectedGridLocation = new Location(0, 0);
    private HappinessTypeEnum happinessType;

    public Civilization(User user) {
        this.technologies = new HashMap<>();
        this.researchingTechnology = null;
        this.user = user;
        this.units = new ArrayList<>();
        this.gold = 0;
        this.production = 0;
        this.isInWarWith = new ArrayList<>();
        this.name = user.getNickname();
        this.revealedTileGrid = new TileGrid(this, Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        this.controller = new CivilizationController(this);
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.ownedTiles = null;
        this.researchingTechnologies = new HashMap<>();
        this.happinessType = this.detectHappinessState(this.happiness);
    }

    public Civilization(User user, TileGrid tileGrid) {
        this.technologies = new HashMap<>();
        this.user = user;
        this.name = user.getNickname();
        this.revealedTileGrid = tileGrid;
        this.units = new ArrayList<>();
        this.controller = new CivilizationController(this);
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.ownedTiles = null;
        this.researchingTechnology = null;
        this.researchingTechnologies = new HashMap<>();
    }

    public void researchTech() {
        if (researchingTechnologies.get(researchingTechnology) == researchingTechnology.getCost()) {
            if (technologies.containsKey(researchingTechnology)) {
                int count = technologies.get(researchingTechnology) + 1;
                technologies.remove(researchingTechnology);
                technologies.put(researchingTechnology, count);
            }
            researchingTechnologies.remove(researchingTechnology);
            checkLeadsToTech();
            researchingTechnology = null;
        } else {
            int temp = researchingTechnologies.get(researchingTechnology) + beaker;
            researchingTechnologies.remove(researchingTechnology);
            researchingTechnologies.put(researchingTechnology, temp);
            beaker = 0;
        }
    }

    public void checkLeadsToTech() {
        ArrayList<TechnologyEnum> techs = researchingTechnology.leadsToTech();
        for (TechnologyEnum tech : techs) {
            if (!tech.hasPrerequisiteTechs(this.technologies))
                researchingTechnologies.put(tech, tech.getCost());
        }
    }

    public void startResearchOnTech(TechnologyEnum tech) {
        if (!researchingTechnologies.containsKey(tech))
            this.researchingTechnologies.put(tech, tech.getCost());
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public boolean isInWarWith(Civilization civilization) {
        return this.isInWarWith.contains(civilization);
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

    public HashMap<TechnologyEnum, Integer> getTechnologies() {
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

    public void setCapital(City capital) {
        this.capital = capital;
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

    public HashMap<TechnologyEnum, Integer> getResearch() {
        return this.researchingTechnologies;
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

    public Location getCurrentSelectedGridLocation() {
        return currentSelectedGridLocation;
    }

    public void setCurrentSelectedGridLocation(Location currentSelectedGridLocation) {
        this.currentSelectedGridLocation = currentSelectedGridLocation;
    }

    public void applyNotes() {
        for (City city : this.cities) {
            city.setHappinessFromBuildings(0);
            city.applyBuildingNotes();
        }
    }

    public boolean isPossibleToHaveThisResource(ResourceEnum sourceResourceEnum) {
        ImprovementEnum requiredImprovement = sourceResourceEnum.improvementNeeded();
        for (TechnologyEnum technology : requiredImprovement.getRequiredTechs()) {
            if (this.technologies.containsKey(technology)) return true;
        }
        return false;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public Location getCurrentGridLocation() {
        return this.currentSelectedGridLocation;
    }

    public HappinessTypeEnum detectHappinessState(double happiness) {
        if (happiness > 0) {
            return HappinessTypeEnum.HAPPY;
        }
        if (happiness > -10) {
            return HappinessTypeEnum.UNHAPPY;
        }
        return HappinessTypeEnum.VERYUNHAPPY;
    }

    public double calculateHappiness() {
        this.happiness = 0;
        for (City city :
                this.getCities()) {
            this.happiness += city.calculateCityHappiness();
        }
        int numberOfLuxuryResource = (int) this.numberOfLuxuryResources();
        this.happiness += numberOfLuxuryResource * 4;
        detectHappinessState(happiness);
        return happiness;
    }

    public double numberOfLuxuryResources() {
        double counter = 0;
        int height = this.getRevealedTileGrid().getHeight(), width = this.getRevealedTileGrid().getWidth();
        Tile[][] tiles = this.getRevealedTileGrid().getTiles();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = tiles[i][j];
                Terrain terrain = tile.getTerrain();
                ResourceEnum resource = terrain.getResource();
                if (tile.getCiv() == this && resource.isLuxury() && terrain.getImprovements().contains(resource.getImprovementNeeded())) {
                    ++counter;
                }
            }
        }
        return counter;
    }


    public void unitDelete(Unit unit) {
        this.units.remove(unit);
    }

    public TerrainColor getColor() {
        return TerrainColor.WHITE;
    }
}