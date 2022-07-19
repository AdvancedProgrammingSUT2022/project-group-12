package Project.Enums;


import java.util.ArrayList;

public enum TerrainEnum {

    UNKNOWN("?", TerrainColor.GRAY_BACKGROUND, 0, 0, 0, 0, 0, true, false, new ArrayList<>(), new ArrayList<>(), "-url-"),

    GRASSLAND("GRSLD", TerrainColor.LIGHTGREEN_BACKGROUND, 2, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
        add(FeatureEnum.MARSH);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.COAL);
        add(ResourceEnum.CATTLE);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.SHEEP);
    }}, "-url-"),
    HILL("HIL", TerrainColor.DARKGREEN_BACKGROUND, 0, 2, 0, 25, 2, true, true, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
        add(FeatureEnum.JUNGLE);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.COAL);
        add(ResourceEnum.DEER);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.SHEEP);
    }}, "-url-"),
    MOUNTAIN("MOUNT", TerrainColor.DARKBROWN_BACKGROUND, 0, 0, 0, 25, 0, false, true, new ArrayList<>(), new ArrayList<>(), "-url-"),
    OCEAN("OCEAN", TerrainColor.BLUE_BACKGROUND, 1, 0, 1, 0, 1, false, false, new ArrayList<>() {{
        add(FeatureEnum.ICE);
    }}, new ArrayList<>(), "-url-"),
    PLAIN("PLAIN", TerrainColor.GREEN_BACKGROUND, 1, 1, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
        add(FeatureEnum.JUNGLE);
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
    }}, "-url-"),
    DESERT("DSERT", TerrainColor.BROWN_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FeatureEnum.OASIS);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.COTTON);
        add(ResourceEnum.INCENSE);
        add(ResourceEnum.SHEEP);
    }}, "-url-"),
    SNOW("SNW", TerrainColor.WHITE_BACKGROUND, 0, 0, 0, -33, 1, true, false, new ArrayList<>(), new ArrayList<>() {{
        add(ResourceEnum.IRON);
    }}, "-url-"),
    TUNDRA("TNDRA", TerrainColor.DARKRED_BACKGROUND, 1, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
    }}, new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.HORSE);
        add(ResourceEnum.DEER);
        add(ResourceEnum.SILVER);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.MARBLE);
        add(ResourceEnum.FUR);
    }}, "-url-");

    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<FeatureEnum> possibleTerrainFeatures;
    private final ArrayList<ResourceEnum> possibleResources;
    private final boolean canPass;
    private final boolean blocksView;
    private final TerrainColor color;

    private final String abbreviation;
    private final String assetUrl;

    TerrainEnum(String abbreviation, TerrainColor color, int foodCount, int productsCount, int goldCount, int combatModifier, int movementCost, boolean canPass, boolean blocksView, ArrayList<FeatureEnum> possibleTerrainFeatures, ArrayList<ResourceEnum> possibleResources, String assetUrl) {
        this.foodCount = foodCount;
        this.productsCount = productsCount;
        this.goldCount = goldCount;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleResources = possibleResources;
        this.canPass = canPass;
        this.blocksView = blocksView;
        this.color = color;
        this.abbreviation = abbreviation;
        this.possibleTerrainFeatures = possibleTerrainFeatures;
        this.assetUrl = assetUrl;
//        System.out.println("/images/assets/resources/" + this.name());

//        System.out.println("/images/assets/resources/" + this.name());
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
        return movementCost;
    }

    public ArrayList<ResourceEnum> getPossibleResources() {
        return possibleResources;
    }

    public boolean isReachable() {
        return !this.getFeatures().contains(FeatureEnum.ICE) &&
                this != MOUNTAIN && this != OCEAN;
    }


    public boolean isRough() {
        return this == HILL;
    }

    public boolean isBlockingView() {
        return this.blocksView;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public ArrayList<FeatureEnum> getFeatures() {
        return possibleTerrainFeatures;
    }

    public boolean isRangeAttackable() {
        return this != HILL && this != MOUNTAIN;
    }
}