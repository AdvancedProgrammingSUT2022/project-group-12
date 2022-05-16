package Enums;

import java.util.ArrayList;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>()),
    ROAD(new ArrayList<>() {{
        add(TechnologyEnum.THE_WHEEL);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
//        add(TerrainEnum.JUNGLE);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
//        add(TerrainEnum.MARSH);
//        add(TerrainEnum.FOREST);
//        add(TerrainEnum.RIVER);
        add(TerrainEnum.SNOW);
    }}),
    // todo: rethink about
    RAILROAD(new ArrayList<>() {{
        add(TechnologyEnum.RAILROAD);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
//        add(TerrainEnum.JUNGLE);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
//        add(TerrainEnum.MARSH);
//        add(TerrainEnum.FOREST);
//        add(TerrainEnum.RIVER);
        add(TerrainEnum.SNOW);

    }}),
    CAMP(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
//        add(TerrainEnum.FOREST);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.HILL);
    }}),
    FARM(new ArrayList<>() {{
        add(TechnologyEnum.AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
    }}),
    LUMBER_MILL(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
//        add(TerrainEnum.FOREST);
    }}),
    MINE(new ArrayList<>() {{
        add(TechnologyEnum.MINING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}),
    PASTURE(new ArrayList<>() {{
        add(TechnologyEnum.ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}),
    STONE_MINE(new ArrayList<>() {
    }, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}),
    CULTIVATION(new ArrayList<>() {{
        add(TechnologyEnum.CALENDAR);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
//        add(TerrainEnum.FOREST);
//        add(TerrainEnum.JUNGLE);
//        add(TerrainEnum.MARSH);
//        add(TerrainEnum.FALLOUT);
    }}),
    TRADING_POST(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
    }}),
    COMPANY(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }});

    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<TerrainEnum> canBeBuiltOn;

    ImprovementEnum(ArrayList<TechnologyEnum> requiredTechs, ArrayList<TerrainEnum> canBeBuiltOn) {
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
    }

    public ArrayList<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public ArrayList<TerrainEnum> getCanBeBuiltOn() {
        return canBeBuiltOn;
    }

}






