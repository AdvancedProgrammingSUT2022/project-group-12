package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    protected boolean isDamaged;
    protected int row;
    protected int col;
    protected CombatUnit combatUnit;
    protected NonCombatUnit nonCombatUnit;
    protected Terrain terrain;
    protected City city;
    protected ArrayList<ResourceEnum> resources;
    protected int HP;

    public Tile(Terrain terrain, ArrayList<ResourceEnum> resources, int row, int col) {
        this.terrain = terrain;
        this.resources = resources;
        this.city = null;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.isDamaged = false;
        this.row = row;
        this.col = col;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public Tile deepCopy() {
        return null; // todo
    }
}