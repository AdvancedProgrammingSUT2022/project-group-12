package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    protected CombatUnit combatUnits;
    protected NonCombatUnit nonCombatUnits;
    protected Terrain terrain;
    protected City city;
    protected ArrayList<ResourceEnum> resources;
    protected int HP;

    public Tile(Terrain terrain, ArrayList<ResourceEnum> resources) {
        this.terrain = terrain;
        this.resources = resources;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tile deepCopy() {
        return null; // todo
    }
}