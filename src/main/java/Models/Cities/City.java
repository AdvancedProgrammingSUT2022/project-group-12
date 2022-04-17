package Models.Cities;

import Enums.BuildingsEnum;
import Enums.ResourcesEnum;
import Enums.TerrainsEnum;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class City {
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private boolean alreadyHasACombatUnit;
    private boolean alreadyHasANonCombatUnit;
    private int gold;
    private int production;
    private ArrayList<ResourcesEnum> resources;
    private int hitPoint;
    private int combatStrength;
    protected boolean isCapital;
    private int unitCount;
    private ArrayList<BuildingsEnum> buildings;

    public City(TerrainsEnum terrain, boolean isCapital) {
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.gold = terrain.getGoldCount();
        this.production = 1 + terrain.getProductsCount();
        this.resources = terrain.getResources();
        this.hitPoint = 20;
        this.combatStrength = 0;
        this.isCapital = isCapital;
        this.unitCount = 0;
        this.buildings = new ArrayList<>();
        this.alreadyHasACombatUnit = false;
        this.alreadyHasANonCombatUnit = false;
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public int getGold() {
        return this.gold;
    }

    public ArrayList<ResourcesEnum> getResources() {
        return this.resources;
    }

    public int getHitPoint() {
        return this.hitPoint;
    }

    public int getCombatStrength() {
        return this.combatStrength;
    }

    public boolean isCapital() {
        return this.isCapital;
    }

    public int getUnitCount() {
        return this.unitCount;
    }

    public boolean hasBuilding(String buildingName) {
        return this.buildings.contains(BuildingsEnum.valueOf(buildingName));
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
        this.alreadyHasACombatUnit = true;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
        this.alreadyHasANonCombatUnit = true;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setResources(ArrayList<ResourcesEnum> resources) {
        this.resources = resources;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setCombatStrength(int combatStrength) {
        this.combatStrength = combatStrength;
    }

    public void setCapital(boolean isCapital) {
        this.isCapital = isCapital;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public void setBuildings(BuildingsEnum buildings) {
        this.buildings.add(buildings);
    }

    public boolean hasACombatUnit() {
        return this.alreadyHasACombatUnit;
    }

    public boolean hasANonCombatUnit() {
        return this.alreadyHasANonCombatUnit;
    }
}
