package Models.Units;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.TechnologyEnum;
import Enums.GameEnums.UnitEnum;

import java.util.ArrayList;

public class CombatUnit extends Unit {
    private UnitEnum type;


    public UnitEnum getType() {
        return type;
    }
    CombatUnit(UnitEnum type){
        this.type=type;
    }

    protected ArrayList<ResourceEnum> requiredResources = new ArrayList<>();
    protected ArrayList<TechnologyEnum> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;
}
