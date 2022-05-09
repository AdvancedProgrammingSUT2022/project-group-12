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
        this.terrainType = type;
        features = new ArrayList<>();
        setFeatures(type.getFeatures());
        this.goldCount = type.getGoldCount();
        this.foodCount = featureFood();
        this.productsCount = featureProducts();
        this.combatModifier = featureCombatModifier();
        this.movementCost = featureMovementCost();
        this.resource = featureResources();
        this.color = terrainType.getColor();
    }

    private ResourceEnum featureResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>(terrainType.getResources());
        if (!features.isEmpty()) {
            for (TerrainEnum feature : this.features) {
                resources.addAll(feature.getResources());
            }
        }
        if (resources.isEmpty()) return null;
        int chooseResource = new Random().nextInt(resources.size());
        return resources.get(chooseResource);
    }

    private int featureMovementCost() {
        int count = getTerrainType().getMovementCost();
        if (!features.isEmpty()) {
            for (TerrainEnum feature : features) {
                count += feature.getMovementCost();
            }
        }
        return count;
    }

    private int featureCombatModifier() {
        int count = getTerrainType().getCombatModifier();
        if (!features.isEmpty()) {
            for (TerrainEnum feature : features) {
                count += feature.getCombatModifier();
            }
        }
        return count;
    }

    private int featureProducts() {
        int count = getTerrainType().getProductsCount();
        if (!features.isEmpty()) {
            for (TerrainEnum feature : features) {
                count += feature.getProductsCount();
            }
        }
        return count;
    }

    private int featureFood() {
        int count = getTerrainType().getFoodCount();
        if (!features.isEmpty()) {
            for (TerrainEnum feature : features) {
                count += feature.getFoodCount();
            }
        }
        return count;
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

    private void setFeatures(ArrayList<TerrainEnum> possibleTerrainFeatures) {
        if (possibleTerrainFeatures.isEmpty()) return;
        int chooseRandom = new Random().nextInt(possibleTerrainFeatures.size());
        for (int i = 0; i < chooseRandom; i++) {
            TerrainEnum feature = possibleTerrainFeatures.get(new Random().nextInt(possibleTerrainFeatures.size()));
            while (this.features.contains(feature)) {
                feature = possibleTerrainFeatures.get(new Random().nextInt(possibleTerrainFeatures.size()));
            }
            this.features.add(feature);
        }
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

    public void clearLands() {

    }
}
