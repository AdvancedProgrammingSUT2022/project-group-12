package Project.Models.Units;

import Project.Enums.CombatTypeEnum;
import Project.Enums.FeatureEnum;
import Project.Enums.UnitEnum;
import Project.Enums.UnitStates;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Utils.Constants;
import Project.Utils.Notifier;
import Project.Utils.NotifierUtil;
import Project.Utils.Observer;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public abstract class Unit extends Production implements Notifier<Unit> {
    private NotifierUtil<Unit> notifierUtil = null;
    protected UnitEnum unitType;
    protected double availableMoveCount;
    protected Location location;
    protected int health = Constants.UNIT_FULL_HEALTH;
    protected UnitStates state;
    protected String civName;
    protected transient ArrayList<Tile> pathShouldCross; // can be calculated online if needed

    protected Unit(UnitEnum type, String civName, Location location) {
        super(type.getProductionCost());
        this.unitType = type;
        this.civName = civName;
        this.pathShouldCross = new ArrayList<>();
//        this.resetMovementCount();
        this.location = location;
        super.type = this.getClass().getName();
        state = UnitStates.AWAKE;
    }

    // todoLater: integrate unit newing with Civ class
    public static Unit constructUnitFromEnum(UnitEnum unitEnum, String civ, Location location) {
        Unit unit;
        if (unitEnum.isACombatUnit()) {
            if (unitEnum.isRangedUnit()) {
                unit = new RangedUnit(unitEnum, civ, location);
            } else {
                unit = new NonRangedUnit(unitEnum, civ, location);
            }
        } else {
            unit = new NonCombatUnit(unitEnum, civ, location);
        }
        return unit;
    }

    public void initializeNotifier() {
        this.notifierUtil = new NotifierUtil<>(this);
    }

    @Override
    public void addObserver(Observer<Unit> observer) {
        this.notifierUtil.addObserver(observer);
    }

    @Override
    public void notifyObservers() {
        if (notifierUtil != null) this.notifierUtil.notifyObservers();
    }

    protected static double getTerrainFeaturesEffect(Tile tile) {
        double effect = 1;
        effect *= (1.0 + ((double) tile.getTerrain().getTerrainType().getCombatModifier() / 100.0));
        for (FeatureEnum feature : tile.getTerrain().getFeatures()) {
            effect *= (1.0 + ((double) feature.getCombatModifier()) / 100.0);
        }
        return effect;
    }

    public void resetMovementCount() {
        this.setAvailableMoveCount(unitType.getMovement());
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
        this.notifyObservers();
    }

    public double calculateCombatStrength(Unit unit, Tile itsTile, String combatstrengh, Unit enemyUnit) {
        double strength;
        UnitEnum unitType = unit.getUnitType();
        if (combatstrengh.equals("combatstrength")) {
            strength = unitType.getCombatStrength();
        } else {
            strength = unitType.getRangedCombatStrength();
        }
        if (!unitType.hasTerrainDefensiveBonusPenalty()) strength *= getTerrainFeaturesEffect(itsTile);
        if (enemyUnit == null) strength *= 1 + unitType.getBonusVsCity() / 100.0;
        else if (enemyUnit.getUnitType() == UnitEnum.TANK && unitType == UnitEnum.ANTI_TANK_GUN) strength *= 2;
        else if (enemyUnit.getUnitType().getCombatType() == CombatTypeEnum.MOUNTED)
            strength *= 1 + unitType.getBonusVsMounted() / 100.0;
        strength *= unit.getHealth() / 100.0;
        return strength;
    }

    public UnitEnum getUnitType() {
        return this.unitType;
    }

    public void setUnitType(UnitEnum unitType) {
        this.unitType = unitType;
        this.notifyObservers();
    }

    public ArrayList<Tile> getPathShouldCross() {
        return pathShouldCross;
    }

    public void setPathShouldCross(ArrayList<Tile> pathShouldCross) {
        this.pathShouldCross = pathShouldCross;
    }

    public boolean isWorking() {
        return this.getState() == UnitStates.WORKING;
    }

    public double getAvailableMoveCount() {
        return availableMoveCount;
    }

    public void setAvailableMoveCount(double availableMoveCount) {
        this.availableMoveCount = availableMoveCount;
        this.notifyObservers();
    }

    public void decreaseAvailableMoveCount(double value) {
        this.setAvailableMoveCount(this.getAvailableMoveCount() - value);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
//        this.notifyObservers(); not required
    }

    public UnitStates getState() {
        return state;
    }

    public void setState(UnitStates state) {
        this.state = state;
        this.notifyObservers();
    }



    public void setCiv(String civName) {
        this.civName = civName;
        this.notifyObservers();
    }

    @Override
    public void note(City city) {
        // todoLater : complete
    }

    public void decreaseHealth(int value) {
        this.setHealth(this.getHealth() - value);
    }

    public String getInfo() {
        return "Type = " + this.getUnitType().name() + '\n' +
                "Tile = " + this.getLocation() + '\n' +
                "Remaining movement = " + this.getAvailableMoveCount() + '\n' +
                "Health = " + this.getHealth() + '\n' +
                "Has work to do: " + this.isWorking() + '\n';
    }

    public String getCivName() {
        return civName;
    }


}
