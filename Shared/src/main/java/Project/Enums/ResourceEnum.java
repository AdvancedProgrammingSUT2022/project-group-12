package Project.Enums;

import Project.Models.Resource;

public enum ResourceEnum {
    RESET("rst", ResourceTypeEnum.STRATEGIC, 0, 0, 0),
    BANANA("bna", ResourceTypeEnum.BONUS, 1, 0, 0),

    CATTLE("ctl", ResourceTypeEnum.BONUS, 1, 0, 0),

    DEER("der", ResourceTypeEnum.BONUS, 1, 0, 0),

    SHEEP("shp", ResourceTypeEnum.BONUS, 2, 0, 0),

    WHEAT("wht", ResourceTypeEnum.BONUS, 1, 0, 0),

    COAL("col", ResourceTypeEnum.STRATEGIC, 0, 1, 0),

    HORSE("hrs", ResourceTypeEnum.STRATEGIC, 0, 1, 0),

    IRON("irn", ResourceTypeEnum.STRATEGIC, 0, 1, 0),

    COTTON("ctn", ResourceTypeEnum.LUXURY, 0, 0, 2),

    DYES("dys", ResourceTypeEnum.LUXURY, 0, 0, 2),

    FUR("fur", ResourceTypeEnum.LUXURY, 0, 0, 2),

    GEMSTONE("gst", ResourceTypeEnum.LUXURY, 0, 0, 3),

    GOLD("gld", ResourceTypeEnum.LUXURY, 0, 0, 2),

    INCENSE("inc", ResourceTypeEnum.LUXURY, 0, 0, 2),

    IVORY("ivr", ResourceTypeEnum.LUXURY, 0, 0, 2),

    MARBLE("mrb", ResourceTypeEnum.LUXURY, 0, 0, 2),

    SILK("slk", ResourceTypeEnum.LUXURY, 0, 0, 2),

    SILVER("slv", ResourceTypeEnum.LUXURY, 0, 0, 2),

    SUGAR("sgr", ResourceTypeEnum.LUXURY, 0, 0, 2);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;

    private final String abbreviation;

    ResourceEnum(String abbreviation, ResourceTypeEnum type, int foodCount, int productCount, int goldCount) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.abbreviation = abbreviation;
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
        return Resource.improvementNeeded.get(this);
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
    public static ResourceEnum getResourceEnumByName(String resourceName) {
        for (ResourceEnum re:
             ResourceEnum.values()) {
            if(re.name().toLowerCase().equals(resourceName.toLowerCase())){
                return  re;
            }
        }
        return null;
    }
}