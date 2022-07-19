package Project.Models;

import Project.Models.Cities.Enums.ResourceEnum;

public class Resource {
    ResourceEnum resourceEnum;
    Location location;

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
