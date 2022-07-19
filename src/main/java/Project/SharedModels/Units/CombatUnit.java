package Project.SharedModels.Units;

import Project.Enums.UnitEnum;
import Project.Server.Models.Civilization;
import Project.SharedModels.Location;

public abstract class CombatUnit extends Unit {
    protected int combatStrength;
    protected boolean isSetup;
    protected boolean hasAttack;

    public CombatUnit(UnitEnum type, Civilization civilization, Location location) {
        super(type, civilization, location);
        isSetup = false;
        hasAttack = false;
    }

    public UnitEnum getUnitType() {
        return unitType;
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

    public boolean isHasAttack() {
        return hasAttack;
    }

    public void setHasAttack(boolean hasAttack) {
        this.hasAttack = hasAttack;
    }
}
