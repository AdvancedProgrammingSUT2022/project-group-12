package Enums;

public enum ResourceEnum {
    RESET(ResourceTypeEnum.STRATEGIC, 0, 0, 0, ImprovementEnum.RESET),
    BANANA(ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.CULTIVATION),

    CATTLE(ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.PASTURE),

    DEER(ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.CAMP),

    SHEEP(ResourceTypeEnum.BONUS, 2, 0, 0, ImprovementEnum.PASTURE),

    WHEAT(ResourceTypeEnum.BONUS, 1, 0, 0, ImprovementEnum.FARM),

    COAL(ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE),

    HORSE(ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.PASTURE),

    IRON(ResourceTypeEnum.STRATEGIC, 0, 1, 0, ImprovementEnum.MINE),

    COTTON(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION),

    DYES(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION),

    FUR(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP),

    GEMSTONE(ResourceTypeEnum.LUXURY, 0, 0, 3, ImprovementEnum.MINE),

    GOLD(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE),

    INCENSE(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION),

    IVORY(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CAMP),

    MARBLE(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.STONE_MINE),

    SILK(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION),

    SILVER(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.MINE),

    SUGAR(ResourceTypeEnum.LUXURY, 0, 0, 2, ImprovementEnum.CULTIVATION);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ImprovementEnum improvementNeeded;
    private final boolean isLuxury;
    private final boolean isBonus;
    private final boolean isStrategic;

    ResourceEnum(ResourceTypeEnum type, int foodCount, int productCount, int goldCount, ImprovementEnum improvementNeeded) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.improvementNeeded = improvementNeeded;
        this.isLuxury = type.equals(ResourceTypeEnum.LUXURY);
        this.isStrategic = type.equals(ResourceTypeEnum.STRATEGIC);
        this.isBonus = type.equals(ResourceTypeEnum.BONUS);
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

    public ImprovementEnum improvementNeeded() {
        return this.improvementNeeded;
    }

    public boolean isLuxury() {
        return this.isLuxury;
    }

    public boolean isBonus() {
        return this.isBonus;
    }

    public boolean isStrategic() {
        return this.isStrategic;
    }

    public ImprovementEnum getImprovementNeeded() {
        return improvementNeeded;
    }

    public String getAbbreviation() {
        // todo
        return this.name();
    }
}