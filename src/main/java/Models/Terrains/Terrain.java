package Models.Terrains;

import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;

import java.util.ArrayList;

public class Terrain {
    private final TerrainEnum terrainType;
    private int foodCount;
    private int productsCount;
    private int goldCount;
    private int combatModifier;
    private int movementCost;
    private final ArrayList<TerrainEnum> features;
    private final ArrayList<ResourceEnum> resources;
    private VisibilityEnum state;

    protected ArrayList<ImprovementEnum> improvements;

    public Terrain(TerrainEnum type) {
        this.terrainType = type;
        this.foodCount = type.getFoodCount();
        this.productsCount = type.getProductsCount();
        this.goldCount = type.getGoldCount();
        this.combatModifier = type.getCombatModifier();
        this.movementCost = type.getMovementCost();
        this.features = type.getFeatures();
        this.resources = type.getResources();
        if (features != null) for (TerrainEnum feature : this.features) {
            if (feature == null) continue;
            this.foodCount += feature.getFoodCount();
            this.productsCount += feature.getProductsCount();
            this.goldCount += feature.getGoldCount();
            this.combatModifier += feature.getCombatModifier();
            this.movementCost += feature.getMovementCost();
        }
        this.state = VisibilityEnum.FOG_OF_WAR;
    }

    public VisibilityEnum getState() {
        return this.state;
    }

    public void setState(VisibilityEnum state) {
        this.state = state;
    }

    public TerrainEnum getTerrainType() {
        return this.terrainType;
    }

    public int getFoodCount() {
        return this.foodCount;
    }

    public int getProductsCount() {
        return this.productsCount;
    }

    public int getGoldCount() {
        return this.goldCount;
    }

    public int getMovementCost() {
        return this.movementCost;
    }

    public ArrayList<ResourceEnum> getResources() {
        return this.resources;
    }

    public ArrayList<TerrainEnum> getFeatures() {
        return this.features;
    }

    public ArrayList<ImprovementEnum> getImprovements() {
        return this.improvements;
    }

    public int getCombatModifier() {
        return this.combatModifier;
    }
}
