package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Tile {
    protected int row;
    protected int col;
    protected Terrain terrain;
    protected ArrayList<ResourceEnum> resources;
    protected CombatUnit combatUnit;
    protected NonCombatUnit nonCombatUnit;
    protected City city;
    protected int HP;
    protected boolean isDamaged;

    public Tile(int row, int col, Terrain terrain, ArrayList<ResourceEnum> resources) {
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
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Tile.class);
    }
}