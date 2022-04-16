package Models.Tiles;

import Models.Cities.City;
import Models.Resources.Resource;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    private final int row;
    private final int col;
    protected CombatUnit combatUnits;
    protected NonCombatUnit nonCombatUnits;
    protected Terrain terrain;
    protected City city = null;
    protected ArrayList<Resource> resources = new ArrayList<>();

    public Tile(int row, int col, Terrain terrain, ArrayList<Resource> resources) {
        this.row = row;
        this.col = col;
        this.terrain = terrain;
        this.resources = resources;
    }
}