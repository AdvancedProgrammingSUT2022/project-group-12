package Project.Enums;

import Project.App;
import javafx.scene.image.Image;

public enum ResourceEnum {
    RESET("rst", ResourceTypeEnum.STRATEGIC, 0, 0, 0, ImprovementEnum.RESET),
    BANANA("bna", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.PLANTATION),

    CATTLE("ctl", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.PASTURE),

    DEER("der", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.CAMP),

    SHEEP("shp", ResourceTypeEnum.BONUS, 2, 0, 0, ImprovementEnum.PASTURE),

    WHEAT("wht", ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.FARM),

    COAL("col", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE),

    HORSE("hrs", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.PASTURE),

    IRON("irn", ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE),

    COTTON("ctn", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.PLANTATION),

    DYES("dys", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.PLANTATION),

    FUR("fur", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP),

    GEMSTONE("gst", ResourceTypeEnum.LUXURY, 0, 0, 3, ImprovementEnum.MINE),

    GOLD("gld", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE),

    INCENSE("inc", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.PLANTATION),

    IVORY("ivr", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP),

    MARBLE("mrb", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.QUARRY),

    SILK("slk", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.PLANTATION),

    SILVER("slv", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE),

    SUGAR("sgr", ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.PLANTATION);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ImprovementEnum improvementNeeded;
    private final String abbreviation;
    private final Image resourceImage;

    ResourceEnum(String abbreviation, ResourceTypeEnum type, int foodCount, int productCount, int goldCount, ImprovementEnum improvementNeeded) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.improvementNeeded = improvementNeeded;
        this.abbreviation = abbreviation;

//        System.out.println("/images/assets/resources/" + this.name().toLowerCase() + ".png");
        if(this.name() != "RESET")
        this.resourceImage = new Image(App.class.getResource("/images/resources/" +  this.name().toLowerCase() + ".png").toExternalForm());
        else this.resourceImage = null;
//        System.out.println("/images/assets/resources/" + this.name());
    }

    public Image getImage() {
        return resourceImage;
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