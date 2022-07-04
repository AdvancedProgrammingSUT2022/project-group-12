package Project.Enums;

import Project.App;
import Project.Models.Terrains.Terrain;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum ImprovementEnum {
    RESET(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "-url-"),
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
    }}, "-url-"),
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
    }}, "-url-"),
    CAMP(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>() {{
        add(FeatureEnum.FOREST);
    }}, "-url-"),
    FARM(new ArrayList<>() {{
        add(TechnologyEnum.AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
    }}, new ArrayList<>(), "-url-"),
    LUMBER_MILL(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>(), new ArrayList<>() {{
        add(FeatureEnum.FOREST);
    }}, "-url-"),
    MINE(new ArrayList<>() {{
        add(TechnologyEnum.MINING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}, new ArrayList<>(), "-url-"),
    PASTURE(new ArrayList<>() {{
        add(TechnologyEnum.ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>(), "-url-"),
    STONE_MINE(new ArrayList<>() {
    }, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.HILL);
    }}, new ArrayList<>(), "-url-"),
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
    }}, "-url-"),
    TRADING_POST(new ArrayList<>() {{
        add(TechnologyEnum.TRAPPING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.DESERT);
        add(TerrainEnum.TUNDRA);
    }}, new ArrayList<>(), "-url-"),
    COMPANY(new ArrayList<>() {{
        add(TechnologyEnum.ENGINEERING);
    }}, new ArrayList<>() {{
        add(TerrainEnum.DESERT);
        add(TerrainEnum.PLAIN);
        add(TerrainEnum.GRASSLAND);
        add(TerrainEnum.TUNDRA);
        add(TerrainEnum.SNOW);
    }}, new ArrayList<>(), "-url-");

    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<TerrainEnum> canBeBuiltOn;
    private final ArrayList<FeatureEnum> features;
    private final String assetUrl;

    ImprovementEnum(ArrayList<TechnologyEnum> requiredTechs, ArrayList<TerrainEnum> canBeBuiltOn, ArrayList<FeatureEnum> features, String assetUrl) {
        this.requiredTechs = requiredTechs;
        this.canBeBuiltOn = canBeBuiltOn;
        this.features = features;
        this.assetUrl = assetUrl;
    }

    public Image getImage() {
        return new Image(App.class.getResource("/images/assets/improvements/" + assetUrl).toExternalForm());
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





