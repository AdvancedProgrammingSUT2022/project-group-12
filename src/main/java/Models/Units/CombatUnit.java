package Models.Units;

import Enums.ResourceEnum;
import Enums.TechnologyEnum;
import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

import java.util.ArrayList;

public abstract class CombatUnit extends Unit {
    protected ArrayList<ResourceEnum> requiredResources = new ArrayList<>();
    protected ArrayList<TechnologyEnum> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;
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
