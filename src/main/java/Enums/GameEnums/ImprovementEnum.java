package Enums.GameEnums;

import java.util.List;

public enum ImprovementEnum {
    RESET(List.of(), List.of(), List.of()),
    ROAD(List.of(), List.of(
            TechnologyEnum.THE_WHEEL
    ), List.of(
            TerrainEnum.PLAIN,
            TerrainEnum.JUNGLE,
            TerrainEnum.DESERT,
            TerrainEnum.GRASSLAND,
            TerrainEnum.TUNDRA,
            TerrainEnum.HILL,
            TerrainEnum.MARSH,
            TerrainEnum.FOREST,
            TerrainEnum.RIVER,
            TerrainEnum.SNOW
    )),
    RailRoad(List.of(), List.of(
            TechnologyEnum.RAILROAD
    ), List.of(
            TerrainEnum.PLAIN,
            TerrainEnum.JUNGLE,
            TerrainEnum.DESERT,
            TerrainEnum.GRASSLAND,
            TerrainEnum.TUNDRA,
            TerrainEnum.HILL,
            TerrainEnum.MARSH,
            TerrainEnum.FOREST,
            TerrainEnum.RIVER,
            TerrainEnum.SNOW
    )),
    CAMP(List.of(
            ResourceEnum.IVORY,
            ResourceEnum.FUR,
            ResourceEnum.DEER
    ), List.of(
            TechnologyEnum.TRAPPING
    ), List.of(
            TerrainEnum.FOREST,
            TerrainEnum.TUNDRA,
            TerrainEnum.PLAIN,
            TerrainEnum.HILL
    )),
    FARM(List.of(
            ResourceEnum.WHEAT
    ), List.of(
            TechnologyEnum.AGRICULTURE
    ), List.of(
            TerrainEnum.GRASSLAND,
            TerrainEnum.PLAIN,
            TerrainEnum.DESERT
    )),
    LUMBER_MILL(List.of(), List.of(
            TechnologyEnum.ENGINEERING
    ), List.of(
            TerrainEnum.FOREST
    )),
    MINE(List.of(
            ResourceEnum.IRON,
            ResourceEnum.COAL,
            ResourceEnum.GEMSTONE,
            ResourceEnum.GOLD,
            ResourceEnum.SILVER
    ), List.of(
            TechnologyEnum.MINING
    ), List.of(
            TerrainEnum.GRASSLAND,
            TerrainEnum.PLAIN,
            TerrainEnum.DESERT,
            TerrainEnum.TUNDRA,
            TerrainEnum.SNOW
    )),
    PASTURE(List.of(
            ResourceEnum.HORSE,
            ResourceEnum.CATTLE,
            ResourceEnum.SHEEP
    ), List.of(
            TechnologyEnum.ANIMAL_HUSBANDRY
    ), List.of(
            TerrainEnum.GRASSLAND,
            TerrainEnum.PLAIN,
            TerrainEnum.DESERT,
            TerrainEnum.TUNDRA,
            TerrainEnum.HILL
    )),
    STONE_MINE(List.of(
            ResourceEnum.MARBLE
    ), List.of(
            //TODO : carving stone ?
    ), List.of(
            TerrainEnum.DESERT,
            TerrainEnum.PLAIN,
            TerrainEnum.GRASSLAND,
            TerrainEnum.TUNDRA,
            TerrainEnum.HILL
    )),
    CULTIVATION(List.of(
            ResourceEnum.BANANA,
            ResourceEnum.COTTON,
            ResourceEnum.DYES,
            ResourceEnum.INCENSE,
            ResourceEnum.SILK,
            ResourceEnum.SUGAR
    ), List.of(
            TechnologyEnum.CALENDAR
    ), List.of(
            TerrainEnum.DESERT,
            TerrainEnum.PLAIN,
            TerrainEnum.GRASSLAND,
            TerrainEnum.FOREST,
            TerrainEnum.JUNGLE,
            TerrainEnum.MARSH,
            TerrainEnum.FALLOUT
    )),
    TRADING_POST(List.of(), List.of(TechnologyEnum.TRAPPING), List.of(
            TerrainEnum.GRASSLAND,
            TerrainEnum.PLAIN,
            TerrainEnum.DESERT,
            TerrainEnum.TUNDRA
    )),
    COMPANY(List.of(), List.of(
            TechnologyEnum.ENGINEERING
    ), List.of(
            TerrainEnum.DESERT,
            TerrainEnum.PLAIN,
            TerrainEnum.GRASSLAND,
            TerrainEnum.TUNDRA,
            TerrainEnum.SNOW
    ));

    private final List<ResourceEnum> isRequiredBy;
    private final List<TechnologyEnum> requiredTechs;
    private final List<TerrainEnum> canBeBuiltOn;

    ImprovementEnum(List<ResourceEnum> isRequiredBy, List<TechnologyEnum> requiredTechs, List<TerrainEnum> canBeBuiltOn) {
        this.isRequiredBy = isRequiredBy;
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
    }

    public boolean hasRequiredTechs(List<TechnologyEnum> techs) {
        for (TechnologyEnum list : this.requiredTechs)
            if (!techs.contains(list)) {
                return false;
            }
        return true;
    }

    public List<ResourceEnum> getIsRequiredBy() {
        return isRequiredBy;
    }

    public List<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public List<TerrainEnum> getCanBeBuiltOn() {
        return canBeBuiltOn;
    }

    public boolean canBeBuiltOn(List<TerrainEnum> lands) {
        for (TerrainEnum terrainEnum : lands) {
            if (this.canBeBuiltOn.contains(terrainEnum)) {
                return true;
            }
        }
        return false;
    }

    public List<ResourceEnum> isRequiredBy() {
        return isRequiredBy;
        //TODO : really don't need this func
    }
}






