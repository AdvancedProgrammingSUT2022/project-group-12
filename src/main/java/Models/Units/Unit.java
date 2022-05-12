package Models.Units;

import Enums.TerrainEnum;
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

    public static void calculateDamage(Unit unit, double strengthDiff, Random random) {
        double random_number = (double) random.nextInt(75, 125) / 100;
        unit.setHealthBar(unit.getHealthBar() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
    }

    public static double calculateCombatStrength(Unit unit, Tile itsTile) {
        double strength = unit.getType().getCombatStrength();
        strength *= getTerrainFeaturesEffect(itsTile);
        strength *= getHealthBarEffect(unit);
        return strength;
    }

    protected static double getHealthBarEffect(Unit unit) {
        return (double) unit.getHealthBar() / 100;
    }

    protected static double getTerrainFeaturesEffect(Tile tile) {
        double effect = 1;
        effect *= 1 - (double) tile.getTerrain().getCombatModifier() / 100;
        for (TerrainEnum terrainEnum : tile.getTerrain().getFeatures()) {
            effect *= terrainEnum.getCombatModifier();
        }
        return effect;
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
