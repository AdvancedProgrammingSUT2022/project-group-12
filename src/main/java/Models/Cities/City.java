package Models.Cities;

import Enums.BuildingEnum;
import Enums.ResourceEnum;
import Models.Buildings.Building;
import Models.Civilization;
import Models.Production;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final Civilization isOwnedBy;
    private final int citizensCount;
    private final int range;
    private final ArrayList<BuildingEnum> cityStructure;
    protected boolean isCapital;
    private String name;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private boolean hasACombatUnit;
    private boolean hasANonCombatUnit;
    private int gold;
    private double productionSpeed;
    private int food;
    private ArrayList<ResourceEnum> resources;
    private int hitPoint;
    private double combatStrength;
    private int unitCount;
    private int beaker;
    private int happiness;
    private int row;
    private int col;
    private ArrayList<Production> productionsQueue;


    public City(Civilization civ, Tile tile, boolean isCapital) {
        this.combatUnit = null;
        this.productionsQueue = new ArrayList<>();
        this.nonCombatUnit = null;
        this.gold = tile.getTerrain().getGoldCount();
        this.productionSpeed = 1 + tile.getTerrain().getProductsCount();
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

        //TODO : check the range of the city and the combat strength of that
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

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public double getProductionSpeed() {
        return productionSpeed;
    }

    public void setProductionSpeed(double productionSpeed) {
        this.productionSpeed = productionSpeed;
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void addProduction(Production production) {
        this.productionsQueue.add(production);
    }

    public void productionImplementation() {
        for (Production production : this.productionsQueue) {
            if (production instanceof Building) {
                //TODO
            }
        }
    }
}