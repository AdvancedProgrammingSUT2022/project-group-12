package Models.Cities;

import Enums.BuildingEnum;
import Enums.ResourceEnum;
import Models.Buildings.Building;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private final Civilization isOwnedBy;
    private final int citizensCount;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Tile cityTile;
    private final ArrayList<Production> productionQueue;
    protected boolean isCapital;
    private String name;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int gold;
    private double production;
    private int food;
    private ArrayList<ResourceEnum> resources;
    private int hitPoint;
    private double combatStrength;
    private int beaker;
    private int happiness;


    public City(ArrayList<Tile> tiles, Civilization civ, Tile tile, boolean isCapital) {
        this.tiles = tiles;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.gold = tile.getTerrain().getGoldCount();
        this.production = 1 + tile.getTerrain().getProductsCount();
        this.resources = tile.getTerrain().getResources();
        this.hitPoint = 2000;
        this.combatStrength = 10;
        this.isCapital = isCapital;
        this.citizensCount = 0;
        this.food = 0;
        this.happiness = 0;
        this.buildings = new ArrayList<Building>();
        this.isOwnedBy = civ;
        this.range = 2;
        this.cityTile = tile;
        //TODO : check the range of the city and the combat strength of that
        this.productionQueue = new ArrayList<>();
    }

    public static int calculateCombatStrength(City city, Tile cityTile) {
        int strength = (int) city.getCombatStrength();
        strength = AffectCityFeatures(city);
        strength = HealthBarAffect(strength, city);
        return strength;
    }

    protected static int HealthBarAffect(int strength, City city) {
        return (city.getHitPoint() / 2000) * strength;
    }

    static int AffectCityFeatures(City city) {
        //todo : affect the citizen and buildings on combat strength
        return 0;
    }

    public static void calculateDamage(City city, int strengthDiff, Random random) {
        double random_number = (random.nextInt(50) + 75) / 100;
        city.setHitPoint(city.getHitPoint() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public int getRange() {
        return range;
    }

    public Location getLocation() {
        return this.cityTile.getLocation();
    }

    public Civilization getCivilization() {
        return this.isOwnedBy;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<ResourceEnum> getResources() {
        return this.resources;
    }

    public void setResources(ArrayList<ResourceEnum> resources) {
        this.resources = resources;
    }

    public int getHitPoint() {
        return this.hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public double getCombatStrength() {
        return this.combatStrength;
    }

    public void setCombatStrength(double combatStrength) {
        this.combatStrength = combatStrength;
    }

    public int getCitizensCount() {
        return citizensCount;
    }

    public int getBeaker() {
        return beaker;
    }

    public void setBeaker(int beaker) {
        this.beaker = beaker;
    }

    public boolean isCapital() {
        return this.isCapital;
    }

    public void setCapital(boolean isCapital) {
        this.isCapital = isCapital;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public boolean hasBuilding(BuildingEnum buildingName) {
        return this.buildings.contains(buildingName);
    }

    public void addBuilding(Building cityStructure) {
        this.buildings.add(cityStructure);
    }

    public String getName() {
        return this.name;
    }

    public Civilization getIsOwnedBy() {
        return isOwnedBy;
    }

    public void applyBuildingNotes() {
        for (Building building : this.buildings) {
            //  building.getNote().note(this); // todo: how to call the note function?
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void advanceProductionQueue() {
        productionQueue.get(0).decreaseRemainedProduction(this.getProduction());
        if (productionQueue.get(0).getRemainedProduction() <= 0) {
            Production production = productionQueue.remove(0);
            if (production instanceof Building) {
                this.buildings.add((Building) production);
            } else if (production instanceof Unit) {
                this.getCivilization().addUnit((Unit) production);
                // todo: what if there is already a unit on city tile?
                if (production instanceof CombatUnit) {
                    this.cityTile.setCombatUnit((CombatUnit) production);
                } else if (production instanceof NonCombatUnit) {
                    this.cityTile.setNonCombatUnit((NonCombatUnit) production);
                }
            }
        }
    }

    public Tile getTile() {
        return this.cityTile;
    }
}
