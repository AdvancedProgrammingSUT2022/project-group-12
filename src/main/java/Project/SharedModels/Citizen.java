package Project.SharedModels;

import Project.SharedModels.Cities.City;

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
    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder("(" + this.getLocation().getRow() + " " + this.getLocation().getCol() + ")");
        if(isLocked()){
            toString.append(" locked");
        }else {
            toString.append(" unlocked");
        }
        return String.valueOf(toString);
    }

    public void setLock(boolean locked) {
        this.locked = locked;
    }
}
