package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;

public class Unit {
    protected UnitEnum type;
    protected Civilization civ;
    protected City assignedCity;
    protected Tile isSetOnTile;
    protected int healthBar;
    protected boolean isWorking;

    public Unit(UnitEnum type, Terrain terrain, Civilization civ) {
        this.type = type;
        this.civ = civ;
        this.isSetOnTile = null;
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
