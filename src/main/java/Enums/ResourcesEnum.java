package Enums;

import java.util.ArrayList;

public enum ResourcesEnum {
    banana(ResourceTypeEnum.bonus, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    cattle(ResourceTypeEnum.bonus, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    deer(ResourceTypeEnum.bonus, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    sheep(ResourceTypeEnum.bonus, 2, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    wheat(ResourceTypeEnum.bonus, 1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.farm),

    coal(ResourceTypeEnum.strategic, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    horse(ResourceTypeEnum.strategic, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    iron(ResourceTypeEnum.strategic, 0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    cotton(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    dyes(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    fur(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    gemstone(ResourceTypeEnum.luxury, 0, 0, 3, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    gold(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    incense(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    ivory(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    marble(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.stoneMine),

    silk(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    silver(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    sugar(ResourceTypeEnum.luxury, 0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation);

    private final ResourceTypeEnum type;
    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ArrayList<TerrainsEnum> isOn;
    private final ImprovementsEnum improvementNeeded;
    private final boolean isLuxury;
    private final boolean isBonus;
    private final boolean isStrategic;

    ResourcesEnum(ResourceTypeEnum type, int foodCount, int productCount, int goldCount, ArrayList<TerrainsEnum> isOn, ImprovementsEnum improvementNeeded) {
        this.type = type;
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.isOn = isOn;
        this.improvementNeeded = improvementNeeded;
        this.isLuxury = type.equals(ResourceTypeEnum.luxury);
        this.isStrategic = type.equals(ResourceTypeEnum.strategic);
        this.isBonus = type.equals(ResourceTypeEnum.bonus);
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

    public ArrayList<TerrainsEnum> getIsOn() {
        return this.isOn;
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