package Models;

import Models.Cities.City;

public class Citizen {
    private final City city;
    private boolean locked = false;

    public Citizen(City city) {
        this.city = city;
    }


    public City getCity() {
        return city;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
