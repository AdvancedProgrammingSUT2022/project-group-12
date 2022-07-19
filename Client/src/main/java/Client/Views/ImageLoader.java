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
        for (TechnologyEnum resourceEnum:
                TechnologyEnum.values()) {
            researchImages.put(resourceEnum,new Image(App.class.getResource("/images/technologies/" + resourceEnum.name().toLowerCase() + ".png").toExternalForm()));
        }
        for (TerrainEnum te:
                TerrainEnum.values()) {
            if (!te.name().equals("UNKNOWN")) {
                terrainImages.put(te,new Image(App.class.getResource("/images/resources/" + te.name().toLowerCase() + ".png").toExternalForm()));
            } else {
                terrainImages.put(te , new Image(App.class.getResource("/images/resources/fogOfWarIcon.png").toExternalForm()));
            }
        }
        for (ResourceEnum re:
                ResourceEnum.values()) {
            resourceImages.put(re,new Image(App.class.getResource("/images/resources/" + re.name().toLowerCase() + ".png").toExternalForm()));
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
