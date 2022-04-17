package Enums.GameEnums;

import java.util.ArrayList;

public enum TechnologiesEnum {
    agriculture(20, null, new ArrayList<>() {{
        add(pottery);
        add(animalHusbandry);
        add(archery);
        add(mining);
    }}),
    animalHusbandry(35, new ArrayList<>() {{
        add(agriculture);
    }}, new ArrayList<>() {{
        add(trapping);
        add(theWheel);
    }}),
    archery(35, new ArrayList<>() {{
        add(agriculture);
    }}, new ArrayList<>() {{
        add(mathematics);
    }}),
    bronzeWorking(55, new ArrayList<>() {{
        add(mining);
    }}, new ArrayList<>() {{
        add(ironWorking);
    }}),
    calendar(70, new ArrayList<>() {{
        add(pottery);
    }}, new ArrayList<>() {{
        add(theology);
    }}),
    masonry(55, new ArrayList<>() {{
        add(mining);
    }}, new ArrayList<>() {{
        add(construction);
    }}),
    mining(35, new ArrayList<>() {{
        add(agriculture);
    }}, new ArrayList<>() {{
        add(masonry);
        add(bronzeWorking);
    }}),
    pottery(35, new ArrayList<>() {{
        add(agriculture);
    }}, new ArrayList<>() {{
        add(calendar);
        add(writing);
    }}),
    theWheel(55, new ArrayList<>() {{
        add(animalHusbandry);
    }}, new ArrayList<>() {{
        add(horsebackRiding);
        add(mathematics);
    }}),
    trapping(55, new ArrayList<>() {{
        add(animalHusbandry);
    }}, new ArrayList<>() {{
        add(civilService);
    }}),
    writing(55, new ArrayList<>() {{
        add(pottery);
    }}, new ArrayList<>() {{
        add(philosophy);
    }}),
    construction(100, new ArrayList<>() {{
        add(masonry);
    }}, new ArrayList<>() {{
        add(engineering);
    }}),
    horsebackRiding(100, new ArrayList<>() {{
        add(theWheel);
    }}, new ArrayList<>() {{
        add(chivalry);
    }}),
    ironWorking(150, new ArrayList<>() {{
        add(bronzeWorking);
    }}, new ArrayList<>() {{
        add(metalCasting);
    }}),
    mathematics(100, new ArrayList<>() {{
        add(theWheel);
    }}, new ArrayList<>() {{
        add(currency);
        add(engineering);
    }}),
    philosophy(100, new ArrayList<>() {{
        add(writing);
    }}, new ArrayList<>() {{
        add(theology);
        add(civilService);
    }}),
    chivalry(440, new ArrayList<>() {{
        add(civilService);
        add(horsebackRiding);
        add(currency);
    }}, new ArrayList<>() {{
        add(banking);
    }}),
    civilService(400, new ArrayList<>() {{
        add(philosophy);
        add(trapping);
    }}, new ArrayList<>() {{
        add(chivalry);
    }}),
    currency(250, new ArrayList<>() {{
        add(mathematics);
    }}, new ArrayList<>() {{
        add(chivalry);
    }}),
    education(440, new ArrayList<>() {{
        add(theology);
    }}, new ArrayList<>() {{
        add(acoustics);
        add(banking);
    }}),
    engineering(250, new ArrayList<>() {{
        add(mathematics);
        add(construction);
    }}, new ArrayList<>() {{
        add(machinery);
        add(physics);
    }}),
    machinery(440, new ArrayList<>() {{
        add(engineering);
    }}, new ArrayList<>() {{
        add(printingPress);
    }}),
    metalCasting(240, new ArrayList<>() {{
        add(ironWorking);
    }}, new ArrayList<>() {{
        add(physics);
        add(steel);
    }}),
    physics(440, new ArrayList<>() {{
        add(ironWorking);
    }}, new ArrayList<>() {{
        add(printingPress);
        add(gunpowder);
    }}),
    steel(440, new ArrayList<>() {{
        add(metalCasting);
    }}, new ArrayList<>() {{
        add(gunpowder);
    }}),
    techno(250, new ArrayList<>() {{
        add(calendar);
        add(philosophy);
    }}, new ArrayList<>() {{
        add(education);
    }}),
    theology(250, new ArrayList<>() {{
        add(calendar);
        add(philosophy);
    }}, new ArrayList<>() {{
        add(education);
    }}),
    acoustics(650, new ArrayList<>() {{
        add(education);
    }}, new ArrayList<>() {{
        add(scientificTheory);
    }}),
    archaeology(1300, new ArrayList<>() {{
        add(acoustics);
    }}, new ArrayList<>() {{
        add(biology);
    }}),
    banking(650, new ArrayList<>() {{
        add(education);
        add(chivalry);
    }}, new ArrayList<>() {{
        add(economics);
    }}),
    chemistry(900, new ArrayList<>() {{
        add(gunpowder);
    }}, new ArrayList<>() {{
        add(militaryScience);
        add(fertilizer);
    }}),
    economics(900, new ArrayList<>() {{
        add(banking);
        add(printingPress);
    }}, new ArrayList<>() {{
        add(militaryScience);
    }}),
    fertilizer(1300, new ArrayList<>() {{
        add(chemistry);
    }}, new ArrayList<>() {{
        add(dynamite);
    }}),
    gunpowder(680, new ArrayList<>() {{
        add(physics);
        add(steel);
    }}, new ArrayList<>() {{
        add(chemistry);
        add(metallurgy);
    }}),
    metallurgy(900, new ArrayList<>() {{
        add(gunpowder);
    }}, new ArrayList<>() {{
        add(rifling);
    }}),
    militaryScience(1300, new ArrayList<>() {{
        add(economics);
        add(chemistry);
    }}, new ArrayList<>() {{
        add(steamPower);
    }}),
    printingPress(650, new ArrayList<>() {{
        add(machinery);
        add(physics);
    }}, new ArrayList<>() {{
        add(economics);
    }}),
    rifling(1425, new ArrayList<>() {{
        add(metallurgy);
    }}, new ArrayList<>() {{
        add(dynamite);
    }}),
    scientificTheory(1300, new ArrayList<>() {{
        add(acoustics);
    }}, new ArrayList<>() {{
        add(biology);
        add(steamPower);
    }}),
    biology(1680, new ArrayList<>() {{
        add(archaeology);
        add(scientificTheory);
    }}, new ArrayList<>() {{
        add(electricity);
    }}),
    combustion(2200, new ArrayList<>() {{
        add(replaceableParts);
        add(railroad);
        add(dynamite);
    }}, null),
    dynamite(1900, new ArrayList<>() {{
        add(fertilizer);
        add(rifling);
    }}, new ArrayList<>() {{
        add(combustion);
    }}),
    electricity(1900, new ArrayList<>() {{
        add(biology);
        add(steamPower);
    }}, new ArrayList<>() {{
        add(telegraph);
        add(radio);
    }}),
    radio(2200, new ArrayList<>() {{
        add(electricity);
    }}, null),
    railroad(1900, new ArrayList<>() {{
        add(steamPower);
    }}, new ArrayList<>() {{
        add(combustion);
    }}),
    replaceableParts(1900, new ArrayList<>() {{
        add(steamPower);
    }}, new ArrayList<>() {{
        add(combustion);
    }}),
    steamPower(1680, new ArrayList<>() {{
        add(scientificTheory);
        add(militaryScience);
    }}, new ArrayList<>() {{
        add(electricity);
        add(replaceableParts);
        add(railroad);
    }}),
    telegraph(2200, new ArrayList<>() {{
        add(electricity);
    }}, null);

    private final int cost;
    private final ArrayList<TechnologiesEnum> prerequisiteTechs;
    private final ArrayList<TechnologiesEnum> leadsToTechs;

    TechnologiesEnum(int cost, ArrayList<TechnologiesEnum> prerequisiteTechs, ArrayList<TechnologiesEnum> leadsToTechs) {
        this.cost = cost;
        this.prerequisiteTechs = prerequisiteTechs;
        this.leadsToTechs = leadsToTechs;
    }

    public int getCost() {
        return this.cost;
    }

    public ArrayList<TechnologiesEnum> getPrerequisiteTechs() {
        return this.prerequisiteTechs;
    }

    public ArrayList<TechnologiesEnum> getLeadsToTechs() {
        return this.leadsToTechs;
    }
}