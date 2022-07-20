package Project.Models;

import Project.Enums.ImprovementEnum;
import Project.Enums.ResourceEnum;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Resource {
    ResourceEnum resourceEnum;
    Location location;
    public static final HashMap<ResourceEnum, ImprovementEnum> improvementNeeded = new HashMap<>();

    static {
        improvementNeeded.put(ResourceEnum.RESET, ImprovementEnum.RESET);
        improvementNeeded.put(ResourceEnum.BANANA, ImprovementEnum.PLANTATION);
        improvementNeeded.put(ResourceEnum.CATTLE, ImprovementEnum.PASTURE);
        improvementNeeded.put(ResourceEnum.DEER, ImprovementEnum.CAMP);
        improvementNeeded.put(ResourceEnum.SHEEP, ImprovementEnum.PASTURE);
        improvementNeeded.put(ResourceEnum.WHEAT, ImprovementEnum.FARM);
        improvementNeeded.put(ResourceEnum.COAL, ImprovementEnum.MINE);
        improvementNeeded.put(ResourceEnum.HORSE, ImprovementEnum.PASTURE);
        improvementNeeded.put(ResourceEnum.IRON, ImprovementEnum.MINE);
        improvementNeeded.put(ResourceEnum.COTTON, ImprovementEnum.PASTURE);
        improvementNeeded.put(ResourceEnum.DYES, ImprovementEnum.PLANTATION);
        improvementNeeded.put(ResourceEnum.FUR, ImprovementEnum.CAMP);
        improvementNeeded.put(ResourceEnum.GEMSTONE, ImprovementEnum.MINE);
        improvementNeeded.put(ResourceEnum.GOLD, ImprovementEnum.MINE);
        improvementNeeded.put(ResourceEnum.INCENSE, ImprovementEnum.PLANTATION);
        improvementNeeded.put(ResourceEnum.IVORY, ImprovementEnum.CAMP);
        improvementNeeded.put(ResourceEnum.MARBLE, ImprovementEnum.QUARRY);
        improvementNeeded.put(ResourceEnum.SILK, ImprovementEnum.PLANTATION);
        improvementNeeded.put(ResourceEnum.SILVER, ImprovementEnum.MINE);
        improvementNeeded.put(ResourceEnum.SUGAR, ImprovementEnum.PLANTATION);
    }


    public Resource(ResourceEnum resourceEnum, Location location) {
        this.resourceEnum = resourceEnum;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public ResourceEnum getResourceEnum() {
        return resourceEnum;
    }
}
