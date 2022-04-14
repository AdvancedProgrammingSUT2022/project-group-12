package Models.Terrains;

import Models.Resources.Resource;

import java.util.ArrayList;

public class Terrain {
    protected String terrain;
    protected int foodCount;
    protected int productsCount;
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<TerrainFeature> features = new ArrayList<>();
    protected ArrayList<Resource> resources = new ArrayList<>();
}
