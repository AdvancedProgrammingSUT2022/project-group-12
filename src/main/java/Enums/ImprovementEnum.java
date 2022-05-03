package Enums;

import java.util.ArrayList;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
    ROAD(new ArrayList<>(), new ArrayList<>() {{
        add(TechnologyEnum.THE_WHEEL);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.JUNGLE);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
        add(TerrainEnum.MARSH);
        add(TerrainEnum.FOREST);
        add(TerrainEnum.RIVER);
        add(TerrainEnum.SNOW);
    }}),
    RAILROAD(new ArrayList<>(), new ArrayList<>() {{
        add(TechnologyEnum.RAILROAD);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.JUNGLE);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
        add(TerrainEnum.MARSH);
        add(TerrainEnum.FOREST);
        add(TerrainEnum.RIVER);
        add(TerrainEnum.SNOW);

    }}),
    CAMP(new ArrayList<>() {{
        add(ResourceEnum.IVORY);
        add(ResourceEnum.FUR);
        add(ResourceEnum.DEER);
    }}, new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.HILL);
    }}),
    FARM(new ArrayList<>() {{
        add(ResourceEnum.WHEAT);
    }}, new ArrayList<>() {{
        add(TechnologyEnum.AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
    }}),
    LUMBER_MILL(new ArrayList<>(), new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.FOREST);
    }}),
    MINE(new ArrayList<>() {{
        add(ResourceEnum.IRON);
        add(ResourceEnum.COAL);
        add(ResourceEnum.GEMSTONE);
        add(ResourceEnum.GOLD);
        add(ResourceEnum.SILVER);
    }}, new ArrayList<>() {{
        add(TechnologyEnum.MINING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}),
    PASTURE(new ArrayList<>() {{
        add(ResourceEnum.HORSE);
        add(ResourceEnum.CATTLE);
        add(ResourceEnum.SHEEP);
    }}, new ArrayList<>() {{
        add(TechnologyEnum.ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}),
    STONE_MINE(new ArrayList<>() {{
        add(ResourceEnum.MARBLE);
    }}, new ArrayList<>() {{
        //TODO : carving stone ?
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}),
    CULTIVATION(new ArrayList<>() {{
        add(ResourceEnum.BANANA);
        add(ResourceEnum.COTTON);
        add(ResourceEnum.DYES);
        add(ResourceEnum.INCENSE);
        add(ResourceEnum.SILK);
        add(ResourceEnum.SUGAR);
    }}, new ArrayList<>() {{
        add(TechnologyEnum.CALENDAR);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.FOREST);
        add(TerrainEnum.JUNGLE);
        add(TerrainEnum.MARSH);
        add(TerrainEnum.FALLOUT);
    }}),
    TRADING_POST(new ArrayList<>(), new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
    }}),
    COMPANY(new ArrayList<>(), new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }});

    private final ArrayList<ResourceEnum> isRequiredBy;
    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<TerrainEnum> canBeBuiltOn;

    ImprovementEnum(ArrayList<ResourceEnum> isRequiredBy, ArrayList<TechnologyEnum> requiredTechs, ArrayList<TerrainEnum> canBeBuiltOn) {
        this.isRequiredBy = isRequiredBy;
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
    }

    public boolean hasRequiredTechs(ArrayList<TechnologyEnum> techs) {
        for (TechnologyEnum list : this.requiredTechs) {
            if (!techs.contains(list)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ResourceEnum> getIsRequiredBy() {
        return isRequiredBy;
    }

    public ArrayList<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public ArrayList<TerrainEnum> getCanBeBuiltOn() {
        return canBeBuiltOn;
    }

    public boolean canBeBuiltOn(ArrayList<TerrainEnum> lands) {
        for (TerrainEnum terrainEnum : lands) {
            if (this.canBeBuiltOn.contains(terrainEnum)) {
                return true;
            }
        }
        return false;
    }
}






