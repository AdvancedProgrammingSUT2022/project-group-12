package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    protected boolean isDamaged;
    protected CombatUnit combatUnit;
    protected NonCombatUnit nonCombatUnit;
    protected Terrain terrain;
    protected City city;
    protected ArrayList<ResourceEnum> resources;
    protected int HP;

    public Tile(Terrain terrain, ArrayList<ResourceEnum> resources) {
        this.terrain = terrain;
        this.resources = resources;
        this.city=null;
        this.combatUnit=null; this.nonCombatUnit=null;
        this.isDamaged = false;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public City getCity() {return city;}

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Terrain getTerrain() {return terrain; }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public Tile deepCopy() {
        return null; // todo
    }
}