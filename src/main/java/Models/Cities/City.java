package Models.Cities;

import Enums.BuildingEnum;
import Enums.ResourceEnum;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private final Civilization isOwnedBy;
    private final int citizensCount;
    private final int range;
    private final ArrayList<BuildingEnum> cityStructure;
    private final Location location;
    protected boolean isCapital;
    private String name;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private boolean hasACombatUnit;
    private boolean hasANonCombatUnit;
    private int gold;
    private double production;
    private int food;
    private ArrayList<ResourceEnum> resources;
    private int hitPoint;
    private double combatStrength;
    private int unitCount;
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
        this.unitCount = 0;
        this.citizensCount = 0;
        this.food = 0;
        this.happiness = 0;
        this.cityStructure = new ArrayList<>();
        this.hasACombatUnit = false;
        this.hasANonCombatUnit = false;
        this.isOwnedBy = civ;
        this.range = 2;
        this.location = tile.getLocation();
        //TODO : check the range of the city and the combat strength of that
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
        this.hasACombatUnit = true;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
        this.hasANonCombatUnit = true;
    }

    public int getRange() {
        return range;
    }

    public Location getLocation() {
        return location;
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

    public int getUnitCount() {
        return this.unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
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
        return this.cityStructure.contains(buildingName);
    }

    public void addBuilding(BuildingEnum cityStructure) {
        this.cityStructure.add(cityStructure);
    }

    public boolean hasCombatUnit() {
        return this.hasACombatUnit;
    }

    public String getName() {
        return this.name;
    }

    public boolean hasNonCombatUnit() {
        return this.hasANonCombatUnit;
    }

    public Civilization getIsOwnedBy() {
        return isOwnedBy;
    }

}
