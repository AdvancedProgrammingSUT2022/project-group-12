package Models.Units;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TechnologyEnum;
import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Terrains.Terrain;

import java.util.ArrayList;

public class CombatUnit extends Unit {
    protected ArrayList<ResourceEnum> requiredResources = new ArrayList<>();
    protected ArrayList<TechnologyEnum> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;

    public CombatUnit(UnitEnum type, Terrain terrain, Civilization civ) {
        super(type, terrain, civ);
    }

    public UnitEnum getType() {
        return type;
    }
}
