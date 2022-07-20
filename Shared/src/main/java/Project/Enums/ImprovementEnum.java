package Project.Enums;

import Project.Models.Terrains.Terrain;
import Project.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
    ROAD(
            new ArrayList<>(List.of(TechnologyEnum.THE_WHEEL)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW)),
            new ArrayList<>(List.of(FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FOREST, FeatureEnum.RIVER))),
    // todo: rethink about
    RAILROAD(
            new ArrayList<>(List.of(TechnologyEnum.RAILROAD)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW)),
            new ArrayList<>(List.of(FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FOREST, FeatureEnum.RIVER))),
    CAMP(
            new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(List.of(TerrainEnum.TUNDRA, TerrainEnum.PLAIN, TerrainEnum.HILL)),
            new ArrayList<>(List.of(FeatureEnum.FOREST))),
    FARM(
            new ArrayList<>(List.of(TechnologyEnum.AGRICULTURE)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT)),
            new ArrayList<>()),
    LUMBER_MILL(
            new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>(),
            new ArrayList<>(List.of(FeatureEnum.FOREST))),
    MINE(
            new ArrayList<>(List.of(TechnologyEnum.MINING)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.SNOW)),
            new ArrayList<>()),
    PASTURE(new ArrayList<>(List.of(TechnologyEnum.ANIMAL_HUSBANDRY)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.HILL)),
            new ArrayList<>()),
    QUARRY(
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL)),
            new ArrayList<>()),
    PLANTATION(
            new ArrayList<>(List.of(TechnologyEnum.CALENDAR)),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND)),
            new ArrayList<>(List.of(FeatureEnum.FOREST, FeatureEnum.JUNGLE, FeatureEnum.MARSH, FeatureEnum.FALLOUT))),
    TRADING_POST(
            new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA)),
            new ArrayList<>()),

    FORT(
            new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.SNOW)),
            new ArrayList<>());

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
        return Constants.TIME_NEED_TO_BUILD_IMPROVEMENT;
    }
    public synchronized static ImprovementEnum getImprovementEnumByName (String name) {
        for (ImprovementEnum improvementEnum:
             ImprovementEnum.values()) {
            if(improvementEnum.name().toLowerCase().equals(name.toLowerCase())){
                return improvementEnum;
            }
        }
        return null;
    }

}





