package Client.Views;

import Client.App;
import Project.Enums.ResourceEnum;
import Project.Enums.TechnologyEnum;
import Project.Enums.TerrainEnum;
import Project.Enums.UnitEnum;
import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageLoader {


    private static final HashMap<UnitEnum, Image> unitImages = new HashMap<>();
    private static final HashMap<ResourceEnum,Image> resourceImages = new HashMap<>();
    private static final HashMap<TerrainEnum,Image> terrainImages = new HashMap<>();
    static HashMap<TechnologyEnum, Image> researchImages = new HashMap<>();

    static {
        for (TechnologyEnum technologyEnum:
                TechnologyEnum.values()) {
            if (technologyEnum != TechnologyEnum.RESET) researchImages.put(technologyEnum,new Image(App.class.getResource("/images/technologies/" + technologyEnum.name().toLowerCase() + ".png").toExternalForm()));
        }
        for (TerrainEnum terrainEnum:
                TerrainEnum.values()) {
            if (!terrainEnum.name().equals("UNKNOWN")) {
                terrainImages.put(terrainEnum,new Image(App.class.getResource("/images/resources/" + terrainEnum.name().toLowerCase() + ".png").toExternalForm()));
            } else {
                terrainImages.put(terrainEnum , new Image(App.class.getResource("/images/resources/fogOfWarIcon.png").toExternalForm()));
            }
        }
        for (ResourceEnum resourceEnum:
                ResourceEnum.values()) {
            if (resourceEnum != ResourceEnum.RESET) resourceImages.put(resourceEnum,new Image(App.class.getResource("/images/resources/" + resourceEnum.name().toLowerCase() + ".png").toExternalForm()));
        }
        for (UnitEnum unitEnum : UnitEnum.values()) {
            unitImages.put(unitEnum,new Image(App.class.getResource("/images/units/Units/" + unitEnum.name().toLowerCase() + ".png").toExternalForm()));
        }
    }

    public static HashMap<ResourceEnum, Image> getResourceImages() {
        return resourceImages;
    }

    public static HashMap<TerrainEnum, Image> getTerrainImages() {
        return terrainImages;
    }

    public static HashMap<UnitEnum, Image> getUnitImages() {
        return unitImages;
    }

    public static HashMap<TechnologyEnum, Image> getResearchImages() {
        return researchImages;
    }
}
