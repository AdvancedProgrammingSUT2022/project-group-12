package Models.Cities;

import Models.Resources.Resource;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class City {
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int goldCount;
    private ArrayList<Resource> resources;
    private int hitPoint;
    private int combatStrength;
    protected boolean isCapital;
}
