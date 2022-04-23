package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;

import java.util.ArrayList;

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
    protected ArrayList<Tile> pathShouldCross;
    protected boolean isWorking;

    public Unit(UnitEnum type, Terrain terrain, Civilization civ) {
        this.type = type;
        this.terrain = terrain;
        this.civ = civ;
        this.pathShouldCross=null;
    }

    public void setPathShouldCross(ArrayList<Tile> pathShouldCross) {
        this.pathShouldCross = pathShouldCross;
    }

    public ArrayList<Tile> getPathShouldCross() {
        return pathShouldCross;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void keepWorking(boolean work) {
        this.isWorking = work;
    }

    public int getMovement() {
        return movement;
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
