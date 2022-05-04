package Models.Terrains;

import Enums.ImprovementEnum;
import Enums.ResourceEnum;
import Enums.TerrainColor;
import Enums.TerrainEnum;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {
    private final TerrainEnum terrainType;
    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<TerrainEnum> features;
    private final ResourceEnum resource;
    private final TerrainColor color;
    protected ArrayList<ImprovementEnum> improvements;

    public Terrain(TerrainEnum type) {
        Random random = new Random();
        this.terrainType = type;
        this.features = type.getFeatures();
        this.foodCount = type.getFoodCount();
        this.productsCount = type.getProductsCount();
        this.goldCount = type.getGoldCount();
        this.combatModifier = type.getCombatModifier();
        this.movementCost = type.getMovementCost();
        this.resource = type.getResources().get(random.nextInt() % type.getResources().size());
        this.color = terrainType.getColor();
    }

    public TerrainColor getColor() {
        return this.color;
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

    public ResourceEnum getResource() {
        return this.resource;
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

    public StringBuilder getResourcesByName() {
        StringBuilder resourcesNames = new StringBuilder("contains resource :" + this.resource.toString());
        return resourcesNames;
    }
}
