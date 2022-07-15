package Project.Enums;

import Project.Client.App;
import Project.Models.Terrains.Terrain;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
    ROADS(new ArrayList<>() {{
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
    QUARRY(new ArrayList<>() {
    }, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>()),
    PLANTATION(new ArrayList<>() {{
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
    FORT(new ArrayList<>() {{
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
    private final Image improvementImage;

    ImprovementEnum(ArrayList<TechnologyEnum> requiredTechs, ArrayList<TerrainEnum> canBeBuiltOn, ArrayList<FeatureEnum> features) {
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
        this.features = features;
        this.improvementImage = new Image(App.class.getResource("/images/improvements/" + this.name().toLowerCase() + ".png").toExternalForm());
    }

    public Image getImage() {
        return improvementImage;
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
    public synchronized static ImprovementEnum getImprovementEnumByName (String name) throws CommandException {
        for (ImprovementEnum improvementEnum:
             ImprovementEnum.values()) {
            if(improvementEnum.name().toLowerCase().equals(name.toLowerCase())){
                return improvementEnum;
            }
        }
        throw  new CommandException(CommandResponse.INVALID_NAME);
    }

}





