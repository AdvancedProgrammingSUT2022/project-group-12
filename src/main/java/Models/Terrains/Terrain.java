package Models.Terrains;

import Enums.ResourcesEnum;
import Enums.TerrainsEnum;

import java.util.ArrayList;

public class Terrain {
    protected String terrain;
    protected int foodCount;
    protected int productsCount;
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<TerrainsEnum> features = new ArrayList<>();
    protected ArrayList<ResourcesEnum> resources = new ArrayList<>();

}
