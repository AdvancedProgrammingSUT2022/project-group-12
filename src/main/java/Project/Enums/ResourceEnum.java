package Project.Enums;

import Project.App;
import javafx.scene.image.Image;

public enum ResourceEnum {
    RESET("rst", ResourceTypeEnum.STRATEGIC, 0, 0, 0, ImprovementEnum.RESET, "-url-"),
    BANANA("bna", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.CULTIVATION, "-url-"),

    CATTLE("ctl", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.PASTURE, "-url-"),

    DEER("der", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.CAMP, "-url-"),

    SHEEP("shp", ResourceTypeEnum.BONUS, 2, 0, 0, ImprovementEnum.PASTURE, "-url-"),

    WHEAT("wht", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.FARM, "-url-"),

    COAL("col", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE, "-url-"),

    HORSE("hrs", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.PASTURE, "-url-"),

    IRON("irn", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE, "-url-"),

    COTTON("ctn", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION, "-url-"),

    DYES("dys", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION, "-url-"),

    FUR("fur", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP, "-url-"),

    GEMSTONE("gst", ResourceTypeEnum.LUXURY, 0, 0, 3, ImprovementEnum.MINE, "-url-"),

    GOLD("gld", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE, "-url-"),

    INCENSE("inc", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION, "-url-"),

    IVORY("ivr", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP, "-url-"),

    MARBLE("mrb", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.STONE_MINE, "-url-"),

    SILK("slk", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION, "-url-"),

    SILVER("slv", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE, "-url-"),

    SUGAR("sgr", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION, "-url-");

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ImprovementEnum improvementNeeded;
    private final String abbreviation;
    private final String assetUrl;

    ResourceEnum(String abbreviation, ResourceTypeEnum type, int foodCount, int productCount, int goldCount, ImprovementEnum improvementNeeded, String assetUrl) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.improvementNeeded = improvementNeeded;
        this.abbreviation = abbreviation;
        this.assetUrl = assetUrl;
    }

    public Image getImage() {
        return new Image(App.class.getResource("/images/assets/resources/" + assetUrl).toExternalForm());
    }

    public ResourceTypeEnum getType() {
        return type;
    }

    public int getFoodCount() {
        return this.foodCount;
    }

    public int getProductsCount() {
        return this.productCount;
    }

    public int getGoldCount() {
        return this.goldCount;
    }

    public ImprovementEnum getImprovementNeeded() {
        return improvementNeeded;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}