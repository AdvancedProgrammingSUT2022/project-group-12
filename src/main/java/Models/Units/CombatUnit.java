package Models.Units;

import Enums.GameEnums.ResourcesEnum;
import Enums.GameEnums.TechnologiesEnum;

import java.util.ArrayList;

public class CombatUnit extends Unit {
    protected ArrayList<ResourcesEnum> requiredResources = new ArrayList<>();
    protected ArrayList<TechnologiesEnum> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;
}
