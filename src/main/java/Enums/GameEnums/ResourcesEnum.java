package Enums.GameEnums;

import java.util.ArrayList;

public enum ResourcesEnum {
    BANANA(ResourceTypeEnum.BONUS, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION),

    CATTLE(ResourceTypeEnum.BONUS, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.PASTURE),

    DEER(ResourceTypeEnum.BONUS, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.CAMP),

    SHEEP(ResourceTypeEnum.BONUS, 2, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.PASTURE),

    WHEAT(ResourceTypeEnum.BONUS, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.FARM),

    COAL(ResourceTypeEnum.STRATEGIC, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.MINE),

    HORSE(ResourceTypeEnum.STRATEGIC, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.PASTURE),

    IRON(ResourceTypeEnum.STRATEGIC, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.MINE),

    COTTON(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION),

    DYES(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION),

    FUR(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CAMP),

    GEMSTONE(ResourceTypeEnum.LUXURY, 0, 0, 3, new ArrayList<>() {{
    }}, ImprovementsEnum.MINE),

    GOLD(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.MINE),

    INCENSE(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION),

    IVORY(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CAMP),

    MARBLE(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.STONE_MINE),

    SILK(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION),

    SILVER(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.MINE),

    SUGAR(ResourceTypeEnum.LUXURY, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.CULTIVATION);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ArrayList<TerrainsEnum> canBeOn;
    private final ImprovementsEnum improvementNeeded;
    private final boolean isLuxury;
    private final boolean isBonus;
    private final boolean isStrategic;

    ResourcesEnum(ResourceTypeEnum type, int foodCount,
                  int productCount, int goldCount, ArrayList<TerrainsEnum> canBeOn,
                  ImprovementsEnum improvementNeeded) {
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

    public boolean isOn(TerrainsEnum terrain) {
        return this.canBeOn.contains(terrain);
    }

    public ImprovementsEnum getImprovementNeeded() {
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