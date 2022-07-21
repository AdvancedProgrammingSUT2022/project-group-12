package Project.Enums;


import Project.Utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public enum TechnologyEnum {
    RESET(0, new ArrayList<>()),
    AGRICULTURE(20, new ArrayList<>()),
    ANIMAL_HUSBANDRY(35, new ArrayList<>(List.of(AGRICULTURE))),
    ARCHERY(35, new ArrayList<>(List.of(AGRICULTURE))),
    MINING(35, new ArrayList<>(List.of(AGRICULTURE))),
    BRONZE_WORKING(55, new ArrayList<>(List.of(MINING))),
    POTTERY(35, new ArrayList<>(List.of(AGRICULTURE))),
    CALENDAR(70, new ArrayList<>(List.of(POTTERY))),
    MASONRY(55, new ArrayList<>(List.of(MINING))),
    THE_WHEEL(55, new ArrayList<>(List.of(ANIMAL_HUSBANDRY))),
    TRAPPING(55, new ArrayList<>(List.of(ANIMAL_HUSBANDRY))),
    WRITING(55, new ArrayList<>(List.of(POTTERY))),
    CONSTRUCTION(100, new ArrayList<>(List.of(MASONRY))),
    HORSEBACK_RIDING(100, new ArrayList<>(List.of(THE_WHEEL))),
    IRON_WORKING(150, new ArrayList<>(List.of(BRONZE_WORKING))),
    MATHEMATICS(100, new ArrayList<>(List.of(THE_WHEEL))),
    PHILOSOPHY(100, new ArrayList<>(List.of(WRITING))),
    CIVIL_SERVICE(400, new ArrayList<>(List.of(PHILOSOPHY, TRAPPING))),
    CURRENCY(250, new ArrayList<>(List.of(MATHEMATICS))),
    CHIVALRY(440, new ArrayList<>(List.of(CIVIL_SERVICE, HORSEBACK_RIDING, CURRENCY))),
    THEOLOGY(250, new ArrayList<>(List.of(CALENDAR, PHILOSOPHY))),
    EDUCATION(440, new ArrayList<>(List.of(THEOLOGY))),
    ENGINEERING(250, new ArrayList<>(List.of(MATHEMATICS, CONSTRUCTION))),
    MACHINERY(440, new ArrayList<>(List.of(ENGINEERING))),
    METAL_CASTING(240, new ArrayList<>(List.of(IRON_WORKING))),
    PHYSICS(440, new ArrayList<>(List.of(IRON_WORKING))),
    STEEL(440, new ArrayList<>(List.of(METAL_CASTING))),
    TECHNO(250, new ArrayList<>(List.of(CALENDAR, PHILOSOPHY))),
    ACOUSTICS(650, new ArrayList<>(List.of(EDUCATION))),
    ARCHAEOLOGY(1300, new ArrayList<>(List.of(ACOUSTICS))),
    BANKING(650, new ArrayList<>(List.of(EDUCATION, CHIVALRY))),
    GUNPOWDER(680, new ArrayList<>(List.of(PHYSICS, STEEL))),
    CHEMISTRY(900, new ArrayList<>(List.of(GUNPOWDER))),
    PRINTING_PRESS(650, new ArrayList<>(List.of(MACHINERY, PHYSICS))),
    ECONOMICS(900, new ArrayList<>(List.of(BANKING, PRINTING_PRESS))),
    FERTILIZER(1300, new ArrayList<>(List.of(CHEMISTRY))),
    METALLURGY(900, new ArrayList<>(List.of(GUNPOWDER))),
    MILITARY_SCIENCE(1300, new ArrayList<>(List.of(ECONOMICS, CHEMISTRY))),
    RIFLING(1425, new ArrayList<>(List.of(METALLURGY))),
    SCIENTIFIC_THEORY(1300, new ArrayList<>(List.of(ACOUSTICS))),
    BIOLOGY(1680, new ArrayList<>(List.of(ARCHAEOLOGY, SCIENTIFIC_THEORY))),
    STEAM_POWER(1680, new ArrayList<>(List.of(SCIENTIFIC_THEORY, MILITARY_SCIENCE))),
    REPLACEABLE_PARTS(1900, new ArrayList<>(List.of(STEAM_POWER))),
    RAILROAD(1900, new ArrayList<>(List.of(STEAM_POWER))),
    DYNAMITE(1900, new ArrayList<>(List.of(FERTILIZER, RIFLING))),
    COMBUSTION(2200, new ArrayList<>(List.of(REPLACEABLE_PARTS, RAILROAD, DYNAMITE))),
    ELECTRICITY(1900, new ArrayList<>(List.of(BIOLOGY, STEAM_POWER))),
    RADIO(2200, new ArrayList<>(List.of(ELECTRICITY))),
    TELEGRAPH(2200, new ArrayList<>(List.of(ELECTRICITY)));

    private final int cost;
    private final ArrayList<TechnologyEnum> prerequisiteTechs;
    private  Observer<Object> updateNotifier;

    TechnologyEnum(int cost, ArrayList<TechnologyEnum> prerequisiteTechs) {
        this.cost = cost;
        this.prerequisiteTechs = prerequisiteTechs;
        String name = "/images/technologies/" + this.name().toLowerCase() + ".png";
    }

    public ArrayList<TechnologyEnum> getPrerequisiteTechs() {
        return prerequisiteTechs;
    }


    public int getCost() {
        return this.cost;
    }

    public boolean containsAllPreRequisite(ArrayList<TechnologyEnum> civTechs) {
        for (TechnologyEnum tech : prerequisiteTechs)
            if (!civTechs.contains(tech))
                return false;
        return true;
    }

    public ArrayList<TechnologyEnum> leadsToTech() {
        ArrayList<TechnologyEnum> techs = new ArrayList<>();
        for (TechnologyEnum tech : TechnologyEnum.values()) {
            if (tech.prerequisiteTechs.contains(this)) {
                techs.add(tech);
            }
        }
        return techs;
    }

    public String info() {
        return this.name() + " " + this.cost;
    }

    public TreeMap<String, TechnologyEnum> getAllTech() {
        TreeMap<String, TechnologyEnum> techMap = new TreeMap<>();
        for (var tech : TechnologyEnum.values())
            if (!tech.name().equals("RESET"))
                techMap.put(tech.name(), tech);
        return techMap;
    }

    public void setUpdateNotifier(Observer<Object> updateNotifier) {
        this.updateNotifier = updateNotifier;
    }

    public Observer<Object> getUpdateNotifier() {
        return updateNotifier;
    }
    public void notifyObservers(){
        this.updateNotifier.getNotified(this);
    }
}