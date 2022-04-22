package Models.Units;

import Models.Cities.City;
import Models.Civilization;

public abstract class Unit {
    protected String name;
    protected Civilization civ;
    protected City assignedCity;
    protected int cost;
    protected int movement;
    protected int row;
    protected int column;
    protected int healthBar;

    public void garrison() {

    }

    public Civilization getCiv() {
        return civ;
    }
}
