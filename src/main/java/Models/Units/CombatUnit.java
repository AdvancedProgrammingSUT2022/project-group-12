package Models.Units;

import Models.Resources.Resource;
import Models.Technologies.Technology;

import java.util.ArrayList;

public class CombatUnit extends Unit {
    protected ArrayList<Resource> requiredResources = new ArrayList<>();
    protected ArrayList<Technology> requiredTechs = new ArrayList<>();
    protected String combatType;
    protected int combatStrength;
    protected int range;
}
