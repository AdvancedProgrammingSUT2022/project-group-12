package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

import java.util.ArrayList;

public class Tile {
    private final int row;
    private final int col;
    private final Terrain terrain;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int HP;
    private boolean isDamaged;
    private City city;
    private boolean hasRoad;
    private VisibilityEnum state;

    public Tile(Terrain terrain, int x, int y) {
        this.row = x;
        this.col = y;
        this.terrain = terrain;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.HP = 0;
        this.isDamaged = false;
        this.city = null;
        this.hasRoad = false;
        this.state = VisibilityEnum.FOG_OF_WAR;
    }

    private Tile(Tile anotherTile) {
        this.row = anotherTile.row;
        this.col = anotherTile.col;
        this.terrain = anotherTile.terrain;
        this.combatUnit = anotherTile.combatUnit;
        this.nonCombatUnit = anotherTile.nonCombatUnit;
        this.HP = anotherTile.HP;
        this.isDamaged = anotherTile.isDamaged;
        this.city = anotherTile.city;
        this.hasRoad = anotherTile.hasRoad;
        this.state = anotherTile.state;
    }


    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void buildRoad() {
        this.hasRoad = true;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public void setState(VisibilityEnum state) {
        this.state = state;
    }

    public ArrayList<ResourceEnum> getResources() {
        return terrain.getResources();
    }

    public int getHP() {
        return HP;
    }

    public VisibilityEnum getState() {
        return state;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public Civilization getCivilization() {
        return this.city.getCivilization();
    }

    public Tile deepCopy(Tile tileToCopy) {
        return new Tile(tileToCopy);
    }

    public void setUnit(Unit unit, Unit toSet) {
        if (unit instanceof CombatUnit) {
            setCombatUnit((CombatUnit) toSet);
        } else {
            setNonCombatUnit((NonCombatUnit) toSet);
        }
    }

    public int calculateMovementCost() {
        int cost = terrain.getMovementCost();
        if (hasRoad)
            cost /= 2;
        return cost;
    }
}