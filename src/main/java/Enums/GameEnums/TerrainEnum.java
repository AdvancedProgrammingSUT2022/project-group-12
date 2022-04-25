package Enums.GameEnums;

import java.util.ArrayList;

public enum TerrainEnum {

    DESERT(TerrainColor.BROWN_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(OASIS);
        add(PLAIN);
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
    GRASSLAND(TerrainColor.LIGHTGREEN_BACKGROUND, 2, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
        add(MARSH);
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
    HILL(TerrainColor.DARKGREEN_BACKGROUND, 0, 2, 0, 25, 2, true, true, new ArrayList<>() {{
        add(FOREST);
        add(JUNGLE);
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
    MOUNTAIN(TerrainColor.DARKBROWN_BACKGROUND, 0, 0, 0, 25, 0, false, true, new ArrayList<>(), new ArrayList<>()),
    OCEAN(TerrainColor.BLUE_BACKGROUND, 1, 0, 1, 0, 1, false, false, new ArrayList<>() {{
        add(ICE);
    }}, new ArrayList<>()),
    PLAIN(TerrainColor.GREEN_BACKGROUND, 1, 1, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
        add(JUNGLE);
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
    SNOW(TerrainColor.WHITE_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.IRON);
    }}),
    TUNDRA(TerrainColor.DARKRED_BACKGROUND, 1, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.DEER);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.FUR);
    }}),
    FALLOUT(TerrainColor.RESET, -3, -3, -3, -33, 2, true, false, new ArrayList<>(), new ArrayList<>()),
    FOREST(TerrainColor.RESET, 1, 1, 0, 25, 2, true, true, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.DEER);
        add(ResourceEnum.FUR);
        add(ResourceEnum.DYES);
        add(ResourceEnum.SILK);
    }}),
    ICE(TerrainColor.RESET, 0, 0, 0, 0, 0, false, false, new ArrayList<>(), new ArrayList<>()),
    JUNGLE(TerrainColor.RESET, 1, -1, 0, 25, 2, true, true, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.BANANA);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.DYES);
    }}),
    MARSH(TerrainColor.RESET, -1, 0, 0, -33, 2, true, false, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.SUGAR);
    }}),
    OASIS(TerrainColor.RESET, 3, 0, 1, -33, 1, true, false, new ArrayList<>(), new ArrayList<>()),
    RIVER(TerrainColor.RESET, 0, 0, 1, 0, 999999, true, false, new ArrayList<>(), new ArrayList<>());

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

    TerrainEnum(TerrainColor color, int foodCount, int productsCount, int goldCount, int combatModifier, int movementCost, boolean canPass, boolean blocksView, ArrayList<TerrainEnum> possibleTerrainFeatures, ArrayList<ResourceEnum> possibleResources) {
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
    }

    public TerrainColor getColor() {
        return color;
    }

    public boolean canBePassed() {
        return this.canPass;
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

    public int getCombatModifier() {
        return this.combatModifier;
    }

    public int getMovementCost() {
        return this.movementCost;
    }

    public ArrayList<TerrainEnum> getFeatures() {
        return this.possibleTerrainFeatures;
    }

    public ArrayList<ResourceEnum> getResources() {
//        return this.possibleResources;
        return new ArrayList<>();
    }

    public boolean isBlockingView() {
        return this.blocksView;
    }
}