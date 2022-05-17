package Models.Units;

import Enums.FeatureEnum;
import Enums.UnitEnum;
import Enums.UnitStates;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Tiles.Tile;
import Utils.Constants;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public abstract class Unit extends Production {
    protected UnitEnum type;
    protected Civilization civ;
    protected double availableMoveCount;
    protected Location location;
    protected int health = Constants.UNIT_FULL_HEALTH;
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

    // todo: integrate unit newing with Civ class
    public static Unit constructUnitFromEnum(UnitEnum unitEnum, Civilization civ, Location location) {
        if (unitEnum.isACombatUnit()) {
            if (unitEnum.isRangedUnit()) {
                return new RangedUnit(unitEnum, civ, location);
            } else {
                return new NonRangedUnit(unitEnum, civ, location);
            }
        } else {
            return new NonCombatUnit(unitEnum, civ, location);
        }
    }

    public void resetMovementCount() {
        this.availableMoveCount = type.getMovement();
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

    public int calculateDamage(double strengthDiff) {
        Random random = new Random();
        double random_number = (double) random.nextInt(75, 125) / 100;
        //debug for test
//        random_number = 1.25;
        return (int) Math.ceil(25 * exp(strengthDiff / (25.0 * random_number)));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double calculateCombatStrength(Unit unit, Tile itsTile, String combatstrengh) {
        double strength;
        if (combatstrengh.equals("combatstrength")) {
            strength = unit.getType().getCombatStrength();
        } else {
            strength = unit.getType().getRangedCombatStrength();
        }
//        if (!unit.getType().hasTerrainDefensiveBonusPenalty()) strength *= getTerrainFeaturesEffect(itsTile);
        strength *= getHealthBarEffect(unit);
        return strength;
    }

    protected static double getHealthBarEffect(Unit unit) {
        return (double) unit.getHealth() / 100;
    }

    protected static double getTerrainFeaturesEffect(Tile tile) {
        double effect = 1;
        effect *= (1.0 + ((double) tile.getTerrain().getTerrainType().getCombatModifier() / 100.0));
        for (FeatureEnum feature : tile.getTerrain().getFeatures()) {
            effect *= (1.0 + ((double) feature.getCombatModifier()) / 100.0);
        }
        return effect;
    }

    public UnitEnum getType() {
        return this.type;
    }

    public void setType(UnitEnum type) {
        this.type = type;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    @Override
    public void note(City city) {
        // todoLater : complete
    }

    public void decreaseHealth(int value) {
        this.health -= value;
    }

    // todo
    public String getInfo() {
        return "Tile = " + this.getLocation() + '\n' +
                "Remaining movement = " + this.getAvailableMoveCount() + '\n' +
                "Health = " + this.getHealth() + '\n' +
                "Has work to do: " + this.isWorking() + '\n';
    }
}
