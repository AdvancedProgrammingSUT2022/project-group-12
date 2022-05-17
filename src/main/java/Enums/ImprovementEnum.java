package Enums;

import Models.Terrains.Terrain;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
    ROAD(new ArrayList<>(List.of(TechnologyEnum.THE_WHEEL)),
            new ArrayList<>(List.of(FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FOREST, FeatureEnum.RIVER)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW))),
    RAILROAD(new ArrayList<>(List.of(TechnologyEnum.RAILROAD)),
            new ArrayList<>(List.of(FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FOREST, FeatureEnum.RIVER)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW))),
    CAMP(new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(List.of(FeatureEnum.FOREST)),
            new ArrayList<>(List.of(TerrainEnum.TUNDRA, TerrainEnum.PLAIN, TerrainEnum.HILL))),
    FARM(new ArrayList<>(List.of(TechnologyEnum.AGRICULTURE)),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT))),
    LUMBER_MILL(new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>(List.of(FeatureEnum.FOREST)),
            new ArrayList<>()),
    MINE(new ArrayList<>(List.of(TechnologyEnum.MINING)),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.SNOW))
    ),
    PASTURE(new ArrayList<>(List.of(TechnologyEnum.ANIMAL_HUSBANDRY)),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.HILL))),
    STONE_MINE(new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL))),
    CULTIVATION(new ArrayList<>(List.of(TechnologyEnum.CALENDAR)),
            new ArrayList<>(List.of(FeatureEnum.FOREST, FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FALLOUT)),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND))),
    TRADING_POST(new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA))),
    COMPANY(new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.SNOW)));

    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<TerrainEnum> canBeBuiltOn;
    private final ArrayList<FeatureEnum> requiredFeatures;

    ImprovementEnum(ArrayList<TechnologyEnum> requiredTechs, ArrayList<FeatureEnum> requiredFeatures, ArrayList<TerrainEnum> canBeBuiltOn) {
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
        this.requiredFeatures = requiredFeatures;
    }

    public ArrayList<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public boolean canBeBuiltOn(Terrain terrain) {
        if (!canBeBuiltOn.contains(terrain.getTerrainType()))
            return false;
        for (FeatureEnum feature : requiredFeatures)
            if (terrain.getFeatures().contains(feature))
                return true;
        return false;
    }

}






