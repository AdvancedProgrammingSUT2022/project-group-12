package Enums;

import java.util.ArrayList;

public enum ResourcesEnum {
    banana(1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    cattle(1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    deer(1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    sheep(2, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    wheat(1, 0, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.farm),

    coal(0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    horse(0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.pasture),

    iron(0, 1, 0, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    cotton(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    dyes(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    fur(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    gemstone(0, 0, 3, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    gold(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    incense(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    ivory(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.camp),

    marble(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.stoneMine),

    silk(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation),

    silver(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.mine),

    sugar(0, 0, 2, new ArrayList<>() {{
    }}, ImprovementsEnum.cultivation);


    private final int foodCount;
    private final int productCount;
    private final int goldCount;
    private final ArrayList<TerrainsEnum> isOn;
    private final ImprovementsEnum improvementNeeded;

    ResourcesEnum(int foodCount, int productCount, int goldCount, ArrayList<TerrainsEnum> isOn, ImprovementsEnum improvementNeeded) {
        this.foodCount = foodCount;
        this.productCount = productCount;
        this.goldCount = goldCount;
        this.isOn = isOn;
        this.improvementNeeded = improvementNeeded;
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
}
