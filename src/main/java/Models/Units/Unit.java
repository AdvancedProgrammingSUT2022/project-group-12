package Models.Units;

import Enums.UnitEnum;
import Enums.UnitStates;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Tiles.Tile;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public abstract class Unit extends Production {
    protected UnitEnum type;
    protected Civilization civ;
    protected double availableMoveCount;
    protected Location location;
    protected int healthBar;
    protected ArrayList<Tile> pathShouldCross;
    protected boolean isWorking;
    protected UnitStates state;

    public Unit(UnitEnum type, Civilization civ, Location location) {
        this.type = type;
        this.civ = civ;
        this.pathShouldCross = new ArrayList<>();
        this.resetMovementCount();
        this.location = location;
        civ.addUnit(this);
        state = UnitStates.AWAKE;
    }

    public Unit(UnitEnum type, Civilization civ, Location location, int productionCost) {
        super(productionCost);
        this.type = type;
        this.civ = civ;
        this.pathShouldCross = new ArrayList<>();
        this.resetMovementCount();
        this.location = location;
        state = UnitStates.AWAKE;
    }

    public static void calculateDamage(Unit unit, int strengthDiff, Random random) {
        double random_number = (random.nextInt(50) + 75) / 100;
        unit.setHealthBar(unit.getHealthBar() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
    }

    public static int calculateCombatStrength(Unit unit, Tile itsTile) {
        int strength = unit.getType().getCombatStrength();
        strength = AffectTerrainFeatures(strength, itsTile);
        strength = HealthBarAffect(strength, unit);
        return strength;
    }

    protected static int HealthBarAffect(int strength, Unit unit) {
        return (unit.getHealthBar() / 100) * strength;
    }

    protected static int AffectTerrainFeatures(int strength, Tile itsTile) {
        //Todo : affect the terrain features
        return 0;
    }

    public ArrayList<Tile> getPathShouldCross() {
        return pathShouldCross;
    }

    public void setPathShouldCross(ArrayList<Tile> pathShouldCross) {
        this.pathShouldCross = pathShouldCross;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void keepWorking(boolean work) {
        this.isWorking = work;
    }

    public double getAvailableMoveCount() {
        return availableMoveCount;
    }

    public void setAvailableMoveCount(double availableMoveCount) {
        this.availableMoveCount = availableMoveCount;
    }

    public UnitEnum getType() {
        return this.type;
    }

    public void setType(UnitEnum type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(int healthBar) {
        this.healthBar = healthBar;
    }

    public UnitStates getState() {
        return state;
    }

    public void setState(UnitStates state) {
        this.state = state;
    }

    public Civilization getCivilization() {
        return civ;
    }

    public void setCiv(Civilization civ) {
        this.civ = civ;
    }

    public void resetMovementCount() {
        this.availableMoveCount = type.getMovement();
    }

    @Override
    public void note(City city) {
        //todo : complete
    }
}
