package Models.Tiles;

import Models.Cities.City;
import Models.Resources.Resource;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    protected CombatUnit combatUnits;
    protected NonCombatUnit nonCombatUnits;
    protected Terrain terrain;
    protected City city;
    protected boolean isCapital;
    protected ArrayList<Resource> resources = new ArrayList<>();
}