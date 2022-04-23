package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Cities.City;
import Models.Location;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import com.google.gson.Gson;

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
    private boolean hasACombatUnit;
    private boolean hasANonCombatUnit;

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
        this.hasACombatUnit = false;
        this.hasANonCombatUnit = false;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
        this.hasACombatUnit = true;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
        this.hasANonCombatUnit = true;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public boolean isHasACombatUnit() {
        return hasACombatUnit;
    }

    public boolean isHasANonCombatUnit() {
        return hasANonCombatUnit;
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

    public Tile deepCopy() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Tile.class);
    }
}