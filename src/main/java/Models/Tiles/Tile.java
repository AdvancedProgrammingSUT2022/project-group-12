package Models.Tiles;

import Enums.ImprovementEnum;
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
import Utils.CommandException;
import Utils.CommandResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
    private boolean hasRailRoad;
    private boolean hasRiver;
    protected ArrayList<ImprovementEnum> improvements;
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
        this.improvements = new ArrayList<>();
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public boolean hasRiver() {
        return this.hasRiver;
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

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    private void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    private void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    // @NotNull reminds not to set tile units to null, directly
    public void setUnit(@NotNull Unit unit) {
        if (unit instanceof CombatUnit combatUnit) {
            setCombatUnit(combatUnit);
        } else if (unit instanceof NonCombatUnit nonCombatUnit) {
            setNonCombatUnit(nonCombatUnit);
        }
    }

    public Civilization getCivilization() {
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

    public double calculateMovementCost() {
        double cost = terrain.getMovementCost();
        if (this.improvements.contains(ImprovementEnum.ROAD)) {
           cost *= (2 / 3);
        }else if(this.improvements.contains(ImprovementEnum.RAILROAD)){
            cost /= 2;
        }
        return cost;
    }

    public int calculateSources(String name) throws CommandException {
        int production = this.terrain.getProductsCount();
        int food = this.terrain.getFoodCount();
        int gold = this.terrain.getGoldCount();
        for (TerrainEnum feature :
                terrain.getFeatures()) {
            production += feature.getProductsCount();
            food += feature.getFoodCount();
            gold += feature.getGoldCount();
        }
        switch (name){
            case "gold" -> {return gold;}
            case "food" -> {return food;}
            case "production" -> {return gold;}
        }
        throw new CommandException(CommandResponse.INVALID_NAME);
    }


    public Location getLocation() {
        return new Location(getRow(), getCol());
    }

    public void deleteUnit(Unit unit) {
        if (unit instanceof NonCombatUnit) {
            this.nonCombatUnit = null;
        } else {
            this.combatUnit = null;
        }
    }
    public ArrayList<ImprovementEnum> getImprovements() {
        return this.improvements;
    }

    public void clearLand() {
        this.terrain.clearLands();
    }
}
