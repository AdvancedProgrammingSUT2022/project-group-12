package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Terrains.Terrain;

public class Unit {
    protected UnitEnum type;
    protected Civilization civ;
    protected City assignedCity;
    protected Terrain terrain;
    protected int cost;
    protected int movement;
    protected int row;
    protected int column;
    protected int healthBar;
    protected boolean isWorking;

    public Unit(UnitEnum type, Terrain terrain, Civilization civ) {
        this.type = type;
        this.terrain = terrain;
        this.civ = civ;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void keepWorking(boolean work) {
        this.isWorking = work;
    }



    public UnitEnum getType() {
        return this.type;
    }

    public void garrison() {

    }

    public Civilization getCiv() {
        return civ;
    }
}
