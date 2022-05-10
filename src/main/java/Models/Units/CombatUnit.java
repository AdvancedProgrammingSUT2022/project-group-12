package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

public class CombatUnit extends Unit {
    protected int combatStrength;
    protected boolean isSetup;

    public CombatUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
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
