package Enums.GameEnums;

import java.util.ArrayList;

public enum TerrainsEnum {

    DESERT(0, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(OASIS);
        add(PLAIN);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.GOLD);
        add(ResourcesEnum.SILVER);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.MARBLE);
        add(ResourcesEnum.COTTON);
        add(ResourcesEnum.INCENSE);
        add(ResourcesEnum.SHEEP);
    }}),
    GRASSLAND(2, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
        add(MARSH);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.HORSE);
        add(ResourcesEnum.COAL);
        add(ResourcesEnum.CATTLE);
        add(ResourcesEnum.GOLD);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.MARBLE);
        add(ResourcesEnum.SHEEP);
    }}),
    HILL(0, 2, 0, 25, 2, true, true, new ArrayList<>() {{
        add(FOREST);
        add(JUNGLE);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.COAL);
        add(ResourcesEnum.DEER);
        add(ResourcesEnum.GOLD);
        add(ResourcesEnum.SILVER);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.MARBLE);
        add(ResourcesEnum.SHEEP);
    }}),
    MOUNTAIN(0, 0, 0, 25, 0, false, true, null, null),
    OCEAN(1, 0, 1, 0, 1, false, false, new ArrayList<>() {{
        add(ICE);
    }}, null),
    PLAIN(1, 1, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
        add(JUNGLE);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.HORSE);
        add(ResourcesEnum.COAL);
        add(ResourcesEnum.WHEAT);
        add(ResourcesEnum.GOLD);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.MARBLE);
        add(ResourcesEnum.IVORY);
        add(ResourcesEnum.COTTON);
        add(ResourcesEnum.INCENSE);
        add(ResourcesEnum.SHEEP);
    }}),
    SNOW(0, 0, 0, -33, 1, true, false, null, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
    }}),
    TUNDRA(1, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FOREST);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.IRON);
        add(ResourcesEnum.HORSE);
        add(ResourcesEnum.DEER);
        add(ResourcesEnum.SILVER);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.MARBLE);
        add(ResourcesEnum.FUR);
    }}),
    FALLOUT(-3, -3, -3, -33, 2, true, false, null, null),
    FOREST(1, 1, 0, 25, 2, true, true, null, new ArrayList<>() {{
        add(ResourcesEnum.DEER);
        add(ResourcesEnum.FUR);
        add(ResourcesEnum.DYES);
        add(ResourcesEnum.SILK);
    }}),
    ICE(0, 0, 0, 0, 0, false, false, null, null),
    JUNGLE(1, -1, 0, 25, 2, true, true, null, new ArrayList<>() {{
        add(ResourcesEnum.BANANA);
        add(ResourcesEnum.GEMSTONE);
        add(ResourcesEnum.DYES);
    }}),
    MARSH(-1, 0, 0, -33, 2, true, false, null, new ArrayList<>() {{
        add(ResourcesEnum.SUGAR);
    }}),
    OASIS(3, 0, 1, -33, 1, true, false, null, null),
    RIVER(0, 0, 1, 0, 999999, true, false, null, null);

    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<TerrainsEnum> possibleTerrainFeatures;
    private final ArrayList<ResourcesEnum> possibleResources;
    private final boolean canPass;
    private final boolean blocksView;

    TerrainsEnum(int foodCount, int productsCount, int goldCount, int combatModifier
            , int movementCost, boolean canPass, boolean blocksView, ArrayList<TerrainsEnum> possibleTerrainFeatures, ArrayList<ResourcesEnum> possibleResources) {
        this.foodCount = foodCount;
        this.productsCount = productsCount;
        this.goldCount = goldCount;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleTerrainFeatures = possibleTerrainFeatures;
        this.possibleResources = possibleResources;
        this.canPass = canPass;
        this.blocksView = blocksView;
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

    public ArrayList<TerrainsEnum> getFeatures() {
        return this.possibleTerrainFeatures;
    }

    public ArrayList<ResourcesEnum> getResources() {
        return this.possibleResources;
    }

    public boolean isBlockingView() {
        return this.blocksView;
    }
}