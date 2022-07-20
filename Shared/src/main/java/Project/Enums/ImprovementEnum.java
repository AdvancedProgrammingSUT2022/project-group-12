package Project.Enums;

import Project.Models.Terrains.Terrain;
import Project.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>()  ),
    ROAD(
            new ArrayList<>(List.of(TechnologyEnum.THE_WHEEL)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW))),
    // todo: rethink about
    RAILROAD(
            new ArrayList<>(List.of(TechnologyEnum.RAILROAD)),
            new ArrayList<>(List.of(TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL, TerrainEnum.SNOW))),
    CAMP(
            new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(List.of(TerrainEnum.TUNDRA, TerrainEnum.PLAIN, TerrainEnum.HILL))
            ),
    FARM(
            new ArrayList<>(List.of(TechnologyEnum.AGRICULTURE)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT))
             ),
    LUMBER_MILL(
            new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>()),
    MINE(
            new ArrayList<>(List.of(TechnologyEnum.MINING)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.SNOW))
             ),
    PASTURE(new ArrayList<>(List.of(TechnologyEnum.ANIMAL_HUSBANDRY)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA, TerrainEnum.HILL))
             ),
    QUARRY(
            new ArrayList<>(),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.HILL))
             ),
    PLANTATION(
            new ArrayList<>(List.of(TechnologyEnum.CALENDAR)),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND))),
    TRADING_POST(
            new ArrayList<>(List.of(TechnologyEnum.TRAPPING)),
            new ArrayList<>(List.of(TerrainEnum.GRASSLAND, TerrainEnum.PLAIN, TerrainEnum.DESERT, TerrainEnum.TUNDRA))),

    FORT(
            new ArrayList<>(List.of(TechnologyEnum.ENGINEERING)),
            new ArrayList<>(List.of(TerrainEnum.DESERT, TerrainEnum.PLAIN, TerrainEnum.GRASSLAND, TerrainEnum.TUNDRA, TerrainEnum.SNOW)));

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





