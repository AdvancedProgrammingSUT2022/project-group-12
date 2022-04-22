package Models.Cities;

import Enums.GameEnums.BuildingEnum;
import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TerrainEnum;
import Models.Civilization;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class City {
    private final String isOwnedBy;
    private final int citizensCount;
    private final ArrayList<BuildingEnum> cityStructure;
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

    public City(Civilization civ, TerrainEnum terrain, boolean isCapital) {
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.gold = terrain.getGoldCount();
        this.production = 1 + terrain.getProductsCount();
        this.resources = terrain.getResources();
        this.hitPoint = 20;
        this.combatStrength = 0;
        this.isCapital = isCapital;
        this.unitCount = 0;
        this.citizensCount = 0;
        this.food = 0;
        this.happiness = 0;
        this.cityStructure = new ArrayList<>();
        this.hasACombatUnit = false;
        this.hasANonCombatUnit = false;
        this.isOwnedBy = civ.getName();
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

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public boolean hasBuilding(String buildingName) {
        return this.cityStructure.contains(BuildingEnum.valueOf(buildingName));
    }

    public void setCityStructure(BuildingEnum cityStructure) {
        this.cityStructure.add(cityStructure);
    }

    public boolean hasACombatUnit() {
        return this.hasACombatUnit;
    }

    public String getName() {
        return name;
    }

    public boolean hasANonCombatUnit() {
        return this.hasANonCombatUnit;
    }
}
