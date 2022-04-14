package Models.Terrains;

import Models.Resources.Resource;

import java.util.ArrayList;

public class TerrainFeature {
    protected String feature;
    protected Resource foods = new Resource();
    protected Resource products = new Resource();
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<Resource> resources;
}
