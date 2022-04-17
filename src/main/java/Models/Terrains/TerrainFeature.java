package Models.Terrains;

import Enums.GameEnums.ResourcesEnum;

import java.util.ArrayList;

public class TerrainFeature {
    protected String feature;
    protected ResourcesEnum foods;
    protected ResourcesEnum products;
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<ResourcesEnum> resources;
}
