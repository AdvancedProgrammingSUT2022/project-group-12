package Models.Tiles;

import Enums.TerrainEnum;
import Enums.VisibilityEnum;
import Models.Cities.City;
import Models.Citizen;
import Models.Civilization;
import Models.Location;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

public class Tile {
    private final int row;
    private final int col;
    private final Terrain terrain;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int HP;
    private boolean isDamaged;
    private Civilization civilization;
    private City city;
    private boolean hasRoad;
    private VisibilityEnum state;
    private Citizen citizen = null;

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

    public Tile(Civilization civilization, Terrain terrain, int x, int y) {
        this.civilization = civilization;
        this.row = x;
        this.col = y;
        this.terrain = terrain;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.HP = 0;
        this.isDamaged = false;
        this.city = null;
        this.hasRoad = false;
        this.state = VisibilityEnum.VISIBLE;
    }

    private Tile(Tile that) {
        this.row = that.row;
        this.col = that.col;
        this.terrain = that.terrain;
        this.combatUnit = that.combatUnit;
        this.nonCombatUnit = that.nonCombatUnit;
        this.HP = that.HP;
        this.isDamaged = that.isDamaged;
        this.city = that.city;
        this.hasRoad = that.hasRoad;
        this.state = that.state;
        this.civilization = that.civilization;
        this.citizen = that.citizen;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public void buildRoad() {
        this.hasRoad = true;
    }


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public VisibilityEnum getState() {
        return state;
    }

    public void setState(VisibilityEnum state) {
        this.state = state;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
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

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;

    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public Civilization getCiv() {
        return this.civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public Tile deepCopy() {
        return new Tile(this);
    }

    public void setNullUnit(Unit unit) {
        if (unit instanceof CombatUnit) {
            setCombatUnit(null);
        } else {
            setNonCombatUnit(null);
        }
    }

    public void setUnit(Unit unit) {
        if (unit instanceof CombatUnit) {
            setCombatUnit((CombatUnit) unit);
        } else {
            setNonCombatUnit((NonCombatUnit) unit);
        }
    }

    public int calculateMovementCost() {
        int cost = terrain.getMovementCost();
        if (hasRoad) {
            cost /= 2;
        }
        return cost;
    }

    public int calculateProductionCount() {
        int production = this.terrain.getProductsCount();
        for (TerrainEnum feature :
                terrain.getFeatures()) {
            production += feature.getProductsCount();
        }
        return production;
    }

    public int calculateFoodCount() {
        int food = this.terrain.getFoodCount();
        for (TerrainEnum feature :
                terrain.getFeatures()) {
            food += feature.getFoodCount();
        }
        return food;
    }

    public Location getLocation() {
        return new Location(getRow(), getCol());
    }

    public void deleteUnit(Unit unit){
        this.getCiv().unitDelete(unit);
        if(unit instanceof NonCombatUnit)
            this.nonCombatUnit = null;
        else
            this.combatUnit = null;
    }

    public void clearLand(){
        this.terrain.clearLands();
    }
}
