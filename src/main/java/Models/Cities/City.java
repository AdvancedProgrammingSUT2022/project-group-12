package Models.Cities;

import Models.Resources.Resource;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;

public class City {
    private String name;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int goldCount;
    private ArrayList<Resource> resources;
    private int hitPoint;
    private int combatStrength;
    protected boolean isCapital;

    public City(String name, boolean isCapital) {
        this.name = name;
        this.isCapital = isCapital;
    }
}
