package Models.Terrains;

import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TerrainEnum;
import Models.Cities.City;

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
    private boolean hasRoad;

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
        for (TerrainEnum features : this.features) {
            this.foodCount += features.getFoodCount();
            this.productsCount += features.getProductsCount();
            this.goldCount += features.getGoldCount();
            this.combatModifier += features.getCombatModifier();
            this.movementCost += features.getMovementCost();
        }
        this.hasRoad = false;
    }

    public TerrainEnum getTerrain() {
        return terrainType;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public ArrayList<ResourceEnum> getResources() {
        return resources;
    }

    public boolean isHasRoad() {
        return hasRoad;
    }

    public void buildRoad() {
        this.hasRoad = true;
    }

    public void assignCity(City city) {
        city.addTerrain(this);
    }

    public ArrayList<TerrainEnum> getFeatures() {
        return features;
    }

    public ArrayList<ImprovementEnum> getImprovements() {
        return improvements;
    }

    public int getCombatModifier() {
        return combatModifier;
    }

}
