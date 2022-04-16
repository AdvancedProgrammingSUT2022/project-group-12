package Enums;

import java.util.ArrayList;

public enum TerrainsEnum {

    desert(0, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(oasis);
        add(plain);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.gold);
        add(ResourcesEnum.silver);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.marble);
        add(ResourcesEnum.cotton);
        add(ResourcesEnum.incense);
        add(ResourcesEnum.sheep);
    }}),
    grassland(2, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(forest);
        add(marsh);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.horse);
        add(ResourcesEnum.coal);
        add(ResourcesEnum.cattle);
        add(ResourcesEnum.gold);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.marble);
        add(ResourcesEnum.sheep);
    }}),
    hill(0, 2, 0, 25, 2, true, true, new ArrayList<>() {{
        add(forest);
        add(jungle);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.coal);
        add(ResourcesEnum.deer);
        add(ResourcesEnum.gold);
        add(ResourcesEnum.silver);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.marble);
        add(ResourcesEnum.sheep);
    }}),
    mountain(0, 0, 0, 25, 0, false, true, null, null),
    ocean(1, 0, 1, 0, 1, false, false, new ArrayList<>() {{
        add(ice);
    }}, null),
    plain(1, 1, 0, -33, 1, true, false, new ArrayList<>() {{
        add(forest);
        add(jungle);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.horse);
        add(ResourcesEnum.coal);
        add(ResourcesEnum.wheat);
        add(ResourcesEnum.gold);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.marble);
        add(ResourcesEnum.ivory);
        add(ResourcesEnum.cotton);
        add(ResourcesEnum.incense);
        add(ResourcesEnum.sheep);
    }}),
    snow(0, 0, 0, -33, 1, true, false, null, new ArrayList<>() {{
        add(ResourcesEnum.iron);
    }}),
    tundra(1, 0, 0, -33, 1, true, false, new ArrayList<>() {{
        add(forest);
    }}, new ArrayList<>() {{
        add(ResourcesEnum.iron);
        add(ResourcesEnum.horse);
        add(ResourcesEnum.deer);
        add(ResourcesEnum.silver);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.marble);
        add(ResourcesEnum.fur);
    }}),
    fallout(-3, -3, -3, -33, 2, true, false, null, null),
    forest(1, 1, 0, 25, 2, true, true, null, new ArrayList<>() {{
        add(ResourcesEnum.deer);
        add(ResourcesEnum.fur);
        add(ResourcesEnum.dyes);
        add(ResourcesEnum.silk);
    }}),
    ice(0, 0, 0, 0, 0, false, false, null, null),
    jungle(1, -1, 0, 25, 2, true, true, null, new ArrayList<>() {{
        add(ResourcesEnum.banana);
        add(ResourcesEnum.gemstone);
        add(ResourcesEnum.dyes);
    }}),
    marsh(-1, 0, 0, -33, 2, true, false, null, new ArrayList<>() {{
        add(ResourcesEnum.sugar);
    }}),
    oasis(3, 0, 1, -33, 1, true, false, null, null),
    river(0, 0, 1, 0, 9999, true, false, null, null);

    private final int foodCount;
    private final int productsCount;
    private final int goldCount;
    private final int combatModifier;
    private final int movementCost;
    private final ArrayList<TerrainsEnum> possibleTerrainFeatures;
    private final ArrayList<ResourcesEnum> possibleResources;
    private final boolean canPass;
    private final boolean blocksView;

    TerrainsEnum(int foodCount, int productsCount, int goldCount, int combatModifier
            , int movementCost, boolean canPass, boolean blocksView, ArrayList<TerrainsEnum> possibleTerrainFeatures, ArrayList<ResourcesEnum> possibleResources) {
        this.foodCount = foodCount;
        this.productsCount = productsCount;
        this.goldCount = goldCount;
        this.combatModifier = combatModifier;
        this.movementCost = movementCost;
        this.possibleTerrainFeatures = possibleTerrainFeatures;
        this.possibleResources = possibleResources;
        this.canPass = canPass;
        this.blocksView = blocksView;
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
        return this.movementCost;
    }

    public ArrayList<TerrainsEnum> getFeatures() {
        return this.possibleTerrainFeatures;
    }

    public ArrayList<ResourcesEnum> getResources() {
        return this.possibleResources;
    }

    public boolean isBlockingView() {
        return this.blocksView;
    }

}
