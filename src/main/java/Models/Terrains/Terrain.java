package Models.Terrains;

import Enums.GameEnums.ResourcesEnum;
import Enums.GameEnums.TerrainsEnum;

import java.util.ArrayList;

public class Terrain {
    protected String terrain;
    protected int foodCount;
    protected int productsCount;
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<TerrainsEnum> features;
    protected ArrayList<ResourcesEnum> resources;

    public Terrain(String type) {
        TerrainsEnum terrainType = TerrainsEnum.valueOf(type);
        this.foodCount = terrainType.getFoodCount();
        this.productsCount = terrainType.getProductsCount();
        this.goldCount = terrainType.getGoldCount();
        this.combatModifier = terrainType.getCombatModifier();
        this.movementCost = terrainType.getMovementCost();
        this.features = terrainType.getFeatures();
        this.resources = terrainType.getResources();
        for (TerrainsEnum features : this.features) {
            this.foodCount += features.getFoodCount();
            this.productsCount += features.getProductsCount();
            this.goldCount += features.getGoldCount();
            this.combatModifier += features.getCombatModifier();
            this.movementCost += features.getMovementCost();
        }
    }

}
