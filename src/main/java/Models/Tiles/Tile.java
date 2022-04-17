package Models.Tiles;

import Enums.ResourcesEnum;
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
    protected ArrayList<ResourcesEnum> resources = new ArrayList<ResourcesEnum>();

    public Tile(Terrain terrain, ArrayList<ResourcesEnum> resources) {
        this.terrain = terrain;
        this.resources = resources;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Tile deepcopy() {
        return null; // todo
    }
}