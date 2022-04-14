package Models.Improvements;

import Models.Resources.Resource;
import Models.Technologies.Technology;
import Models.Terrains.Terrain;

import java.util.ArrayList;

public class Improvement {
    private String name;
    private String ability;
    private ArrayList<Resource> requiredResources = new ArrayList<>();
    private ArrayList<Technology> requiredTech = new ArrayList<>();
    private ArrayList<Terrain> canBeBuiltOn = new ArrayList<>();
}
