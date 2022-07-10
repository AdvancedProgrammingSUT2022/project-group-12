package Project.Models;

import Project.Models.Cities.City;

public class Citizen {
    private final City city;
    private boolean locked = false;
    private Location location;

    public Citizen(City city,Location location)
    {
        this.city = city;
        this.location = location;
    }

    public City getCity() {
        return city;
    }

    public boolean isLocked() {
        return locked;
    }

    public Location getLocation() {
        return location;
    }

    public void setLock(boolean locked) {
        this.locked = locked;
    }
}
