package Models.Units;

import Enums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Tiles.Tile;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public class Unit extends Production {
    protected UnitEnum type;
    protected Civilization civ;
    protected City assignedCity;
    protected int cost;
    protected double availableMoveCount;
    protected Location location;
    protected int healthBar;
    protected ArrayList<Tile> pathShouldCross;
    protected boolean isWorking;

    public Unit(UnitEnum type, Civilization civ, Location location) {
        this.type = type;
        this.civ = civ;
        this.pathShouldCross=null;
        this.resetMovementCount();
        this.location = location;
        civ.addUnit(this);
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

    public void keepWorking(boolean work) {
        this.isWorking = work;
    }

    public double getAvailableMoveCount() {
        return availableMoveCount;
    }

    public UnitEnum getType() {
        return this.type;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public void setAvailableMoveCount(double availableMoveCount) {
        this.availableMoveCount = availableMoveCount;
    }


    public Location getLocation() {
        return location;
    }

    public void setHealthBar(int healthBar) {
        this.healthBar = healthBar;
    }

    public void setType(UnitEnum type) {
        this.type = type;
    }

    public int getHealthBar() {
        return healthBar;
    }

    public void garrison() {

    }
    public static void calculateDamage(Unit unit, int strengthDiff, Random random) {
        double random_number=(random.nextInt(50)+75)/100;
        unit.setHealthBar(unit.getHealthBar()-(int) (25*exp(strengthDiff /(25.0*random_number))));
    }
    public static int calculateCombatStrength(Unit unit, Tile itsTile){
        int strength=unit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,unit);
        return strength;
    }
    protected static int HealthBarAffect(int strength, Unit unit) {
        return (unit.getHealthBar()/100)*strength;
    }
    protected static int AffectTerrainFeatures(int strength, Tile itsTile) {
        //Todo : affect the terrain features
        return 0;
    }


    public Civilization getCiv() {
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
