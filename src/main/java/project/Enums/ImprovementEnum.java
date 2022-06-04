package project.Enums;

import project.Models.Terrains.Terrain;

import java.util.ArrayList;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
    ROAD(new ArrayList<>() {{
        add(TechnologyEnum.THE_WHEEL);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
        add(TerrainEnum.SNOW);

    }}, new ArrayList<>() {{
        add(FeatureEnum.JUNGLE);
        add(FeatureEnum.MARSH);
        add(FeatureEnum.FOREST);
        add(FeatureEnum.RIVER);
    }}),
    // todo: rethink about
    RAILROAD(new ArrayList<>() {{
        add(TechnologyEnum.RAILROAD);
    }}, new ArrayList<>() {{
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
        add(TerrainEnum.SNOW);

    }}, new ArrayList<>() {{
        add(FeatureEnum.JUNGLE);
        add(FeatureEnum.MARSH);
        add(FeatureEnum.FOREST);
        add(FeatureEnum.RIVER);
    }}),
    CAMP(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
    }}),
    FARM(new ArrayList<>() {{
        add(TechnologyEnum.AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
    }}, new ArrayList<>()),
    LUMBER_MILL(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>(), new ArrayList<>() {{
        add(FeatureEnum.FOREST);
    }}),
    MINE(new ArrayList<>() {{
        add(TechnologyEnum.MINING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}, new ArrayList<>()),
    PASTURE(new ArrayList<>() {{
        add(TechnologyEnum.ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>()),
    STONE_MINE(new ArrayList<>() {
    }, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>()),
    CULTIVATION(new ArrayList<>() {{
        add(TechnologyEnum.CALENDAR);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);

    }}, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
        add(FeatureEnum.JUNGLE);
        add(FeatureEnum.MARSH);
        add(FeatureEnum.FALLOUT);
    }}),
    TRADING_POST(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
    }}, new ArrayList<>()),
    COMPANY(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}, new ArrayList<>());

    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<TerrainEnum> canBeBuiltOn;
    private final ArrayList<FeatureEnum> features;

    ImprovementEnum(ArrayList<TechnologyEnum> requiredTechs, ArrayList<TerrainEnum> canBeBuiltOn, ArrayList<FeatureEnum> features) {
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
        this.features = features;
    }

    public ArrayList<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public ArrayList<TerrainEnum> getCanBeBuiltOn() {
        return canBeBuiltOn;
    }

    public int getImprovementBuildRequiredTime(Terrain terrain) {
        return 10; // todo
    }

}





