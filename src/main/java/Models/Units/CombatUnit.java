package Models.Units;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TechnologyEnum;
import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Terrains.Terrain;

import java.util.ArrayList;

public abstract class CombatUnit extends Unit {
    protected ArrayList<ResourceEnum> requiredResources = new ArrayList<>();
    protected ArrayList<TechnologyEnum> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;
    protected  boolean isSetup;

    public CombatUnit(UnitEnum type, Terrain terrain, Civilization civ) {
        super(type, terrain, civ); isSetup=false;
    }
    public UnitEnum getType() {
        return type;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public void setSetup(boolean setup) {isSetup = setup;}

    public boolean isSetup() {return isSetup; }

}
