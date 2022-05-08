package Enums;

import java.util.ArrayList;
import java.util.Random;

public enum TerrainEnum {
    UNKNOWN("N/A", TerrainColor.GRAY_BACKGROUND, 0, 0, 0, 0, 0, false, false, new ArrayList<>(), new ArrayList<>()),
    FALLOUT("FLT", TerrainColor.RESET, -3, -3, -3, -33, 2, true, false, new ArrayList<>(), new ArrayList<>()),
    FOREST("FORST", TerrainColor.RESET, 1, 1, 0, 25, 2, true, true, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.DEER);
        add(ResourceEnum.FUR);
        add(ResourceEnum.DYES);
        add(ResourceEnum.SILK);
    }}),
    ICE("ICE", TerrainColor.RESET, 0, 0, 0, 0, 0, false, false, new ArrayList<>(), new ArrayList<>()),
    JUNGLE("JNGLE", TerrainColor.RESET, 1, -1, 0, 25, 2, true, true, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.BANANA);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.DYES);
    }}),
    MARSH("MARSH", TerrainColor.RESET, -1, 0, 0, -33, 2, true, false, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.SUGAR);
    }}),
    OASIS("OASIS", TerrainColor.RESET, 3, 0, 1, -33, 1, true, false, new ArrayList<>(), new ArrayList<>()),
    RIVER("RIVER", TerrainColor.RESET, 0, 0, 1, 0, 999999, true, false, new ArrayList<>(), new ArrayList<>()),

    GRASSLAND("GRSLD", TerrainColor.LIGHTGREEN_BACKGROUND, 2, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
        add(TerrainEnum.MARSH);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.COAL);
        add(ResourceEnum.CATTLE);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.SHEEP);
    }}),
    HILL("HIL", TerrainColor.DARKGREEN_BACKGROUND, 0, 2, 0, 25, 2, true, true, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
        add(TerrainEnum.JUNGLE);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.COAL);
        add(ResourceEnum.DEER);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.SHEEP);
    }}),
    MOUNTAIN("MTN", TerrainColor.DARKBROWN_BACKGROUND, 0, 0, 0, 25, 0, false, true, new ArrayList<>(), new ArrayList<>()),
    OCEAN("OCN", TerrainColor.BLUE_BACKGROUND, 1, 0, 1, 0, 1, false, false, new ArrayList<>() {{
        add(TerrainEnum.ICE);
    }}, new ArrayList<>()),
    PLAIN("PLN", TerrainColor.GREEN_BACKGROUND, 1, 1, 0, -33, 1, true, false, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
        add(TerrainEnum.JUNGLE);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.COAL);
        add(ResourceEnum.WHEAT);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.IVORY);
        add(ResourceEnum.COTTON);
        add(ResourceEnum.INCENSE);
        add(ResourceEnum.SHEEP);
    }}),
    DESERT("DSERT", TerrainColor.BROWN_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(TerrainEnum.OASIS);
        add(TerrainEnum.PLAIN);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.COTTON);
        add(ResourceEnum.INCENSE);
        add(ResourceEnum.SHEEP);
    }}),
    SNOW("SNW", TerrainColor.WHITE_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.IRON);
    }}),
    TUNDRA("TNDRA", TerrainColor.DARKRED_BACKGROUND, 1, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.DEER);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.FUR);
    }});

    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<TerrainEnum> possibleTerrainFeatures;
    private final ArrayList<ResourceEnum> possibleResources;
    private final boolean canPass;
    private final boolean blocksView;
    private final TerrainColor color;

    private final String abbreviation;

    TerrainEnum(String abbreviation, TerrainColor color, int foodCount, int productsCount, int goldCount, int combatModifier, int movementCost, boolean canPass, boolean blocksView, ArrayList<TerrainEnum> possibleTerrainFeatures, ArrayList<ResourceEnum> possibleResources) {
        this.foodCount = foodCount;
        this.productsCount = productsCount;
        this.goldCount = goldCount;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleTerrainFeatures = possibleTerrainFeatures;
        this.possibleResources = possibleResources;
        this.canPass = canPass;
        this.blocksView = blocksView;
        this.color = color;
        this.abbreviation = abbreviation;
    }

    public TerrainColor getColor() {
        return color;
    }

    public boolean canBePassed() {
        return this.canPass;
    }

    public int getFoodCount() {
        int count = this.foodCount;
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                count += list.foodCount;
            }
        }
        return count;
    }

    public int getProductsCount() {
        int count = this.productsCount;
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                count += list.productsCount;
            }
        }
        return productsCount;
    }

    public int getGoldCount() {
        int count = this.goldCount;
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                count += list.goldCount;
            }
        }
        return goldCount;
    }

    public int getCombatModifier() {
        int count = this.combatModifier;
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                count += list.combatModifier;
            }
        }
        return combatModifier;
    }

    public int getMovementCost() {
        int count = this.movementCost;
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                count += list.movementCost;
            }
        }
        return movementCost;
    }

    public ArrayList<TerrainEnum> getFeatures() {
        if (this.possibleTerrainFeatures.isEmpty()) {
            return this.possibleTerrainFeatures;
        }
        ArrayList<TerrainEnum> features = new ArrayList<>();
        ArrayList<TerrainEnum> possibleFeatures = new ArrayList<>(this.possibleTerrainFeatures);
        Random random = new Random();
        int count = random.nextInt(possibleFeatures.size());
        for (int i = 0; i < count; i++) {
            int rand = random.nextInt(possibleFeatures.size());
            features.add(possibleFeatures.get(rand));
            possibleFeatures.remove(rand);
        }
        return features;
    }

    public ArrayList<ResourceEnum> getResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>(this.possibleResources);
        if (!possibleTerrainFeatures.isEmpty()) {
            for (TerrainEnum list : possibleTerrainFeatures) {
                if (!list.possibleResources.isEmpty()) {
                    resources.addAll(list.possibleResources);
                }
            }
        }
        return resources;
    }

    public boolean isBlockingView() {
        return this.blocksView;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}