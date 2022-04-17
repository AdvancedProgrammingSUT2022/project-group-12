package Models.Buildings;

import Models.Technologies.Technology;

import java.util.ArrayList;

public class Building {
    protected String name;
    protected int cost;
    protected int maintenance;
    protected ArrayList<Technology> requiredTechs = new ArrayList<>();

}
