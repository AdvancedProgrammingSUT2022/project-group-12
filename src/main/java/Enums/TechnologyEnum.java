package Enums;

import java.util.ArrayList;

public enum TechnologyEnum {
    RESET(0, new ArrayList<>(), new ArrayList<>()),
    AGRICULTURE(20, new ArrayList<>(), new ArrayList<>() {{
        add(POTTERY);
        add(ANIMAL_HUSBANDRY);
        add(ARCHERY);
        add(MINING);
    }}),
    ANIMAL_HUSBANDRY(35, new ArrayList<>() {{
        add(AGRICULTURE);
    }}, new ArrayList<>() {{
        add(TRAPPING);
        add(THE_WHEEL);
    }}),
    ARCHERY(35, new ArrayList<>() {{
        add(AGRICULTURE);
    }}, new ArrayList<>() {{
        add(MATHEMATICS);
    }}),
    BRONZE_WORKING(55, new ArrayList<>() {{
        add(MINING);
    }}, new ArrayList<>() {{
        add(IRON_WORKING);
    }}),
    CALENDAR(70, new ArrayList<>() {{
        add(POTTERY);
    }}, new ArrayList<>() {{
        add(THEOLOGY);
    }}),
    MASONRY(55, new ArrayList<>() {{
        add(MINING);
    }}, new ArrayList<>() {{
        add(CONSTRUCTION);
    }}),
    MINING(35, new ArrayList<>() {{
        add(AGRICULTURE);
    }}, new ArrayList<>() {{
        add(MASONRY);
        add(BRONZE_WORKING);
    }}),
    POTTERY(35, new ArrayList<>() {{
        add(AGRICULTURE);
    }}, new ArrayList<>() {{
        add(CALENDAR);
        add(WRITING);
    }}),
    THE_WHEEL(55, new ArrayList<>() {{
        add(ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(HORSEBACK_RIDING);
        add(MATHEMATICS);
    }}),
    TRAPPING(55, new ArrayList<>() {{
        add(ANIMAL_HUSBANDRY);
    }}, new ArrayList<>() {{
        add(CIVIL_SERVICE);
    }}),
    WRITING(55, new ArrayList<>() {{
        add(POTTERY);
    }}, new ArrayList<>() {{
        add(PHILOSOPHY);
    }}),
    CONSTRUCTION(100, new ArrayList<>() {{
        add(MASONRY);
    }}, new ArrayList<>() {{
        add(ENGINEERING);
    }}),
    HORSEBACK_RIDING(100, new ArrayList<>() {{
        add(THE_WHEEL);
    }}, new ArrayList<>() {{
        add(CHIVALRY);
    }}),
    IRON_WORKING(150, new ArrayList<>() {{
        add(BRONZE_WORKING);
    }}, new ArrayList<>() {{
        add(METAL_CASTING);
    }}),
    MATHEMATICS(100, new ArrayList<>() {{
        add(THE_WHEEL);
    }}, new ArrayList<>() {{
        add(CURRENCY);
        add(ENGINEERING);
    }}),
    PHILOSOPHY(100, new ArrayList<>() {{
        add(WRITING);
    }}, new ArrayList<>() {{
        add(THEOLOGY);
        add(CIVIL_SERVICE);
    }}),
    CHIVALRY(440, new ArrayList<>() {{
        add(CIVIL_SERVICE);
        add(HORSEBACK_RIDING);
        add(CURRENCY);
    }}, new ArrayList<>() {{
        add(BANKING);
    }}),
    CIVIL_SERVICE(400, new ArrayList<>() {{
        add(PHILOSOPHY);
        add(TRAPPING);
    }}, new ArrayList<>() {{
        add(CHIVALRY);
    }}),
    CURRENCY(250, new ArrayList<>() {{
        add(MATHEMATICS);
    }}, new ArrayList<>() {{
        add(CHIVALRY);
    }}),
    EDUCATION(440, new ArrayList<>() {{
        add(THEOLOGY);
    }}, new ArrayList<>() {{
        add(ACOUSTICS);
        add(BANKING);
    }}),
    ENGINEERING(250, new ArrayList<>() {{
        add(MATHEMATICS);
        add(CONSTRUCTION);
    }}, new ArrayList<>() {{
        add(MACHINERY);
        add(PHYSICS);
    }}),
    MACHINERY(440, new ArrayList<>() {{
        add(ENGINEERING);
    }}, new ArrayList<>() {{
        add(PRINTING_PRESS);
    }}),
    METAL_CASTING(240, new ArrayList<>() {{
        add(IRON_WORKING);
    }}, new ArrayList<>() {{
        add(PHYSICS);
        add(STEEL);
    }}),
    PHYSICS(440, new ArrayList<>() {{
        add(IRON_WORKING);
    }}, new ArrayList<>() {{
        add(PRINTING_PRESS);
        add(GUNPOWDER);
    }}),
    STEEL(440, new ArrayList<>() {{
        add(METAL_CASTING);
    }}, new ArrayList<>() {{
        add(GUNPOWDER);
    }}),
    TECHNO(250, new ArrayList<>() {{
        add(CALENDAR);
        add(PHILOSOPHY);
    }}, new ArrayList<>() {{
        add(EDUCATION);
    }}),
    THEOLOGY(250, new ArrayList<>() {{
        add(CALENDAR);
        add(PHILOSOPHY);
    }}, new ArrayList<>() {{
        add(EDUCATION);
    }}),
    ACOUSTICS(650, new ArrayList<>() {{
        add(EDUCATION);
    }}, new ArrayList<>() {{
        add(SCIENTIFIC_THEORY);
    }}),
    ARCHAEOLOGY(1300, new ArrayList<>() {{
        add(ACOUSTICS);
    }}, new ArrayList<>() {{
        add(BIOLOGY);
    }}),
    BANKING(650, new ArrayList<>() {{
        add(EDUCATION);
        add(CHIVALRY);
    }}, new ArrayList<>() {{
        add(ECONOMICS);
    }}),
    CHEMISTRY(900, new ArrayList<>() {{
        add(GUNPOWDER);
    }}, new ArrayList<>() {{
        add(MILITARY_SCIENCE);
        add(FERTILIZER);
    }}),
    ECONOMICS(900, new ArrayList<>() {{
        add(BANKING);
        add(PRINTING_PRESS);
    }}, new ArrayList<>() {{
        add(MILITARY_SCIENCE);
    }}),
    FERTILIZER(1300, new ArrayList<>() {{
        add(CHEMISTRY);
    }}, new ArrayList<>() {{
        add(DYNAMITE);
    }}),
    GUNPOWDER(680, new ArrayList<>() {{
        add(PHYSICS);
        add(STEEL);
    }}, new ArrayList<>() {{
        add(CHEMISTRY);
        add(METALLURGY);
    }}),
    METALLURGY(900, new ArrayList<>() {{
        add(GUNPOWDER);
    }}, new ArrayList<>() {{
        add(RIFLING);
    }}),
    MILITARY_SCIENCE(1300, new ArrayList<>() {{
        add(ECONOMICS);
        add(CHEMISTRY);
    }}, new ArrayList<>() {{
        add(STEAM_POWER);
    }}),
    PRINTING_PRESS(650, new ArrayList<>() {{
        add(MACHINERY);
        add(PHYSICS);
    }}, new ArrayList<>() {{
        add(ECONOMICS);
    }}),
    RIFLING(1425, new ArrayList<>() {{
        add(METALLURGY);
    }}, new ArrayList<>() {{
        add(DYNAMITE);
    }}),
    SCIENTIFIC_THEORY(1300, new ArrayList<>() {{
        add(ACOUSTICS);
    }}, new ArrayList<>() {{
        add(BIOLOGY);
        add(STEAM_POWER);
    }}),
    BIOLOGY(1680, new ArrayList<>() {{
        add(ARCHAEOLOGY);
        add(SCIENTIFIC_THEORY);
    }}, new ArrayList<>() {{
        add(ELECTRICITY);
    }}),
    COMBUSTION(2200, new ArrayList<>() {{
        add(REPLACEABLE_PARTS);
        add(RAILROAD);
        add(DYNAMITE);
    }}, new ArrayList<>()),
    DYNAMITE(1900, new ArrayList<>() {{
        add(FERTILIZER);
        add(RIFLING);
    }}, new ArrayList<>() {{
        add(COMBUSTION);
    }}),
    ELECTRICITY(1900, new ArrayList<>() {{
        add(BIOLOGY);
        add(STEAM_POWER);
    }}, new ArrayList<>() {{
        add(TELEGRAPH);
        add(RADIO);
    }}),
    RADIO(2200, new ArrayList<>() {{
        add(ELECTRICITY);
    }}, new ArrayList<>()),
    RAILROAD(1900, new ArrayList<>() {{
        add(STEAM_POWER);
    }}, new ArrayList<>() {{
        add(COMBUSTION);
    }}),
    REPLACEABLE_PARTS(1900, new ArrayList<>() {{
        add(STEAM_POWER);
    }}, new ArrayList<>() {{
        add(COMBUSTION);
    }}),
    STEAM_POWER(1680, new ArrayList<>() {{
        add(SCIENTIFIC_THEORY);
        add(MILITARY_SCIENCE);
    }}, new ArrayList<>() {{
        add(ELECTRICITY);
        add(REPLACEABLE_PARTS);
        add(RAILROAD);
    }}),
    TELEGRAPH(2200, new ArrayList<>() {{
        add(ELECTRICITY);
    }}, new ArrayList<>());

    private final int cost;
    private final ArrayList<TechnologyEnum> prerequisiteTechs;
    private final ArrayList<TechnologyEnum> leadsToTechs;

    TechnologyEnum(int cost, ArrayList<TechnologyEnum> prerequisiteTechs, ArrayList<TechnologyEnum> leadsToTechs) {
        this.cost = cost;
        this.prerequisiteTechs = prerequisiteTechs;
        this.leadsToTechs = leadsToTechs;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean hasPrerequisiteTechs(ArrayList<TechnologyEnum> techs) {
        for (TechnologyEnum list : this.prerequisiteTechs) {
            if (!techs.contains(list)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<TechnologyEnum> leadsToTech() {
        return this.leadsToTechs;
    }
}