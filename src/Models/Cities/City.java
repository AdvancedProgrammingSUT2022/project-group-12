package Models.Cities;

import Models.Resources.Resource;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class City {
    private CombatUnit combatUnit = new CombatUnit();
    private NonCombatUnit nonCombatUnit = new NonCombatUnit();
    private int goldCount;
    private ArrayList<Resource> resources = new ArrayList<>();
    private int hitPoint;
    private int combatStrength;
}
