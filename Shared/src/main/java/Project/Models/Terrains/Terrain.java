package Project.Models.Terrains;

import Project.Enums.FeatureEnum;
import Project.Enums.ResourceEnum;
import Project.Enums.TerrainColor;
import Project.Enums.TerrainEnum;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {
    private final TerrainEnum terrainType;
    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final TerrainColor color;
    //test
    //making it not final
    private ResourceEnum resource;
    private ArrayList<FeatureEnum> features;

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
        Random random = new Random();
        ArrayList<ResourceEnum> resources = new ArrayList<>(terrainType.getPossibleResources());
        if (!features.isEmpty()) {
            for (FeatureEnum feature : this.features) {
                resources.addAll(feature.getPossibleResources());
            }
        }
        if (resources.isEmpty() || random.nextInt(8) != 0) return null;
        int chooseResource = random.nextInt(resources.size());
        return resources.get(chooseResource);
    }


    private int featureMovementCost() {
        int count = getTerrainType().getMovementCost();
        if (!features.isEmpty()) {
            for (FeatureEnum feature : features) {
                count += feature.getMovementCost();
            }
        }
        return count;
    }

    private int featureCombatModifier() {
        int count = getTerrainType().getCombatModifier();
        if (!features.isEmpty()) {
            for (FeatureEnum feature : features) {
                count += feature.getCombatModifier();
            }
        }
        return count;
    }

    private int featureProducts() {
        int count = getTerrainType().getProductsCount();
        if (!features.isEmpty()) {
            for (FeatureEnum feature : features) {
                count += feature.getProductsCount();
            }
        }
        return count;
    }

    private int featureFood() {
        int count = getTerrainType().getFoodCount();
        if (!features.isEmpty()) {
            for (FeatureEnum feature : features) {
                count += feature.getFoodCount();
            }
        }
        return count;
    }

    public TerrainEnum getTerrainType() {
        return this.terrainType;
    }

    public TerrainColor getColor() {
        return this.color;
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

    public void setResource(ResourceEnum resource) {
        this.resource = resource;
    }

    public ArrayList<FeatureEnum> getFeatures() {
        return this.features;
    }

    private void setFeatures(ArrayList<FeatureEnum> possibleTerrainFeatures) {
        if (possibleTerrainFeatures.isEmpty()) return;
        int chooseRandom = new Random().nextInt(possibleTerrainFeatures.size());
        for (int i = 0; i < chooseRandom; i++) {
            FeatureEnum feature = possibleTerrainFeatures.get(new Random().nextInt(possibleTerrainFeatures.size()));
            while (this.features.contains(feature)) {
                feature = possibleTerrainFeatures.get(new Random().nextInt(possibleTerrainFeatures.size()));
            }
            this.features.add(feature);
        }
    }

    public int getCombatModifier() {
        return this.combatModifier;
    }

    public StringBuilder getResourcesByName() {
        return new StringBuilder("contains resource :" + this.resource.toString());
    }

    public void clearLands() {
        if (this.features.contains(FeatureEnum.JUNGLE))
            this.features = new ArrayList<>() {{
                add(FeatureEnum.FOREST);
            }};
        else
            this.features = new ArrayList<>();

    }

    public boolean isRoughTerrain() {
        if (this.terrainType.isRough()) {
            return true;
        }
        for (FeatureEnum feature : this.features) {
            if (feature.isRough()) {
                return true;
            }
        }
        return false;
    }
}

