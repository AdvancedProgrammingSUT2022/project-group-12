package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

public abstract class CombatUnit extends Unit {
    protected int combatStrength;
    protected boolean isSetup;

    public CombatUnit(UnitEnum type, Civilization civilization, Location location) {
        super(type, civilization, location);
        isSetup = false;
    }

    public CombatUnit(UnitEnum type, Civilization civilization, Location location, int productionCost) {
        super(type, civilization, location, productionCost);
        isSetup = false;
    }

    public UnitEnum getType() {
        return type;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
    }

}
