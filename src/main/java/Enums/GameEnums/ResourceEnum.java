package Enums.GameEnums;

import java.util.List;

public enum ResourceEnum {
    BANANA(ResourceTypeEnum.BONUS, 1, 0, 0, List.of(), ImprovementEnum.CULTIVATION),

    CATTLE(ResourceTypeEnum.BONUS, 1, 0, 0, List.of(), ImprovementEnum.PASTURE),

    DEER(ResourceTypeEnum.BONUS, 1, 0, 0, List.of(), ImprovementEnum.CAMP),

    SHEEP(ResourceTypeEnum.BONUS, 2, 0, 0, List.of(), ImprovementEnum.PASTURE),

    WHEAT(ResourceTypeEnum.BONUS, 1, 0, 0, List.of(), ImprovementEnum.FARM),

    COAL(ResourceTypeEnum.STRATEGIC, 0, 1, 0, List.of(), ImprovementEnum.MINE),

    HORSE(ResourceTypeEnum.STRATEGIC, 0, 1, 0, List.of(), ImprovementEnum.PASTURE),

    IRON(ResourceTypeEnum.STRATEGIC, 0, 1, 0, List.of(), ImprovementEnum.MINE),

    COTTON(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CULTIVATION),

    DYES(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CULTIVATION),

    FUR(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CAMP),

    GEMSTONE(ResourceTypeEnum.LUXURY, 0, 0, 3, List.of(), ImprovementEnum.MINE),

    GOLD(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.MINE),

    INCENSE(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CULTIVATION),

    IVORY(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CAMP),

    MARBLE(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.STONE_MINE),

    SILK(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CULTIVATION),

    SILVER(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.MINE),

    SUGAR(ResourceTypeEnum.LUXURY, 0, 0, 2, List.of(), ImprovementEnum.CULTIVATION);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final List<TerrainEnum> canBeOn;
    private final ImprovementEnum improvementNeeded;
    private final boolean isLuxury;
    private final boolean isBonus;
    private final boolean isStrategic;

    ResourceEnum(ResourceTypeEnum type, int foodCount, int productCount, int goldCount, List<TerrainEnum> canBeOn, ImprovementEnum improvementNeeded) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.canBeOn = canBeOn;
        this.improvementNeeded = improvementNeeded;
        this.isLuxury = type.equals(ResourceTypeEnum.LUXURY);
        this.isStrategic = type.equals(ResourceTypeEnum.STRATEGIC);
        this.isBonus = type.equals(ResourceTypeEnum.BONUS);
    }

    public int getFoodCount() {
        return this.foodCount;
    }

    public int getProductCount() {
        return this.productCount;
    }

    public int getGoldCount() {
        return this.goldCount;
    }

    public boolean isOn(TerrainEnum terrain) {
        return this.canBeOn.contains(terrain);
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
}