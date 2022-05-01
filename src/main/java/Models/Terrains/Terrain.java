package Models.Terrains;

import Enums.GameEnums.*;

import java.util.ArrayList;
import java.util.Random;

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
    private final TerrainColor color;

    protected ArrayList<ImprovementEnum> improvements;

    public Terrain(TerrainEnum type) {
        this.terrainType = type;
        this.features = type.getFeatures();
        this.foodCount = type.getFoodCount();
        this.productsCount = type.getProductsCount();
        this.goldCount = type.getGoldCount();
        this.combatModifier = type.getCombatModifier();
        this.movementCost = type.getMovementCost();
        this.resources = type.getResources();
        this.state = VisibilityEnum.FOG_OF_WAR;
        this.color = terrainType.getColor();
    }

    public TerrainColor getColor() {
        return this.color;
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

    public StringBuilder getResourcesByName() {
        StringBuilder resourcesNames = new StringBuilder("contains resources such as:");
        for (ResourceEnum list : this.resources) {
            resourcesNames.append("\n").append(list);
        }
        return resourcesNames;
    }
}
