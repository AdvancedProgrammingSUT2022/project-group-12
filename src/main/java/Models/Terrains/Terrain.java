package Models.Terrains;

import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TerrainEnum;

import java.util.ArrayList;

public class Terrain {
    protected String terrain;
    protected int foodCount;
    protected int productsCount;
    protected int goldCount;
    protected int combatModifier;
    protected int movementCost;
    protected ArrayList<TerrainEnum> features;
    protected ArrayList<ResourceEnum> resources;

    protected ArrayList<ImprovementEnum> improvements;

    public ArrayList<TerrainEnum> getFeatures() {
        return features;
    }

    public ArrayList<ImprovementEnum> getImprovements() {
        return improvements;
    }

    public int getCombatModifier() {
        return combatModifier;
    }

    public Terrain(String type) {
        TerrainEnum terrainType = TerrainEnum.valueOf(type);
        this.foodCount = terrainType.getFoodCount();
        this.productsCount = terrainType.getProductsCount();
        this.goldCount = terrainType.getGoldCount();
        this.combatModifier = terrainType.getCombatModifier();
        this.movementCost = terrainType.getMovementCost();
        this.features = terrainType.getFeatures();
        this.resources = terrainType.getResources();
        for (TerrainEnum features : this.features) {
            this.foodCount += features.getFoodCount();
            this.productsCount += features.getProductsCount();
            this.goldCount += features.getGoldCount();
            this.combatModifier += features.getCombatModifier();
            this.movementCost += features.getMovementCost();
        }
    }

}
