package Project.Enums;

import Project.Client.App;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum FeatureEnum {
    FALLOUT("FLOUT", TerrainColor.RESET, -3, -3, -3, -33, 2, new ArrayList<>(), "-url-"),
    FOREST("FORST", TerrainColor.RESET, 1, 1, 0, 25, 2, new ArrayList<>() {{
        add(ResourceEnum.DEER);
        add(ResourceEnum.FUR);
        add(ResourceEnum.DYES);
        add(ResourceEnum.SILK);
    }}, "-url-"),
    ICE("ICE", TerrainColor.RESET, 0, 0, 0, 0, 0, new ArrayList<>(), "-url-"),
    JUNGLE("JNGLE", TerrainColor.RESET, 1, -1, 0, 25, 2, new ArrayList<>() {{
        add(ResourceEnum.BANANA);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.DYES);
    }}, "-url-"),
    MARSH("MARSH", TerrainColor.RESET, -1, 0, 0, -33, 2, new ArrayList<>() {{
        add(ResourceEnum.SUGAR);
    }}, "-url-"),
    OASIS("OASIS", TerrainColor.RESET, 3, 0, 1, -33, 1, new ArrayList<>(), "-url-"),
    RIVER("RIVER", TerrainColor.RESET, 0, 0, 1, 0, 999999, new ArrayList<>(), "-url-"),
    FLOODPLAIN("FDPLN", TerrainColor.RESET, 2, 0, 0, -33, 1, new ArrayList<>(), "-url-");

    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<ResourceEnum> possibleResources;
    private final String abbreviation;
    private final String assetUrl;

    FeatureEnum(String abbreviation, TerrainColor color, int foodCount, int productsCount, int goldCount, int combatModifier, int movementCost, ArrayList<ResourceEnum> possibleResources, String assetUrl) {
        this.foodCount = foodCount;
        this.productsCount = productsCount;
        this.goldCount = goldCount;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
//        this.color = color;
        this.abbreviation = abbreviation;
        this.possibleResources = possibleResources;
        this.assetUrl = assetUrl;

    }

    public Image getImage() {
        return new Image(App.class.getResource("/images/assets/features/" + assetUrl).toExternalForm());
    }

//    public TerrainColor getColor() {
//        return color;
//    }

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public ArrayList<ResourceEnum> getPossibleResources() {
        return possibleResources;
    }

    public boolean isRough() {
        return this == FeatureEnum.FOREST || this == FeatureEnum.JUNGLE;
    }
}