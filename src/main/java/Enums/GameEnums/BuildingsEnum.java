package Enums.GameEnums;

import java.util.ArrayList;

public enum BuildingsEnum {
    barrack(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.bronzeWorking);
    }}),
    granary(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.pottery);
    }}),
    library(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.writing);
    }}),
    monument(60, 1, null),
    walls(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.masonry);
    }}),
    waterMill(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.theWheel);
    }}),
    armory(130, 3, new ArrayList<>() {{
        add(TechnologiesEnum.ironWorking);
    }}),
    burialTomb(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.philosophy);
    }}),
    circus(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.horsebackRiding);
    }}),
    colosseum(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.construction);
    }}),
    courthouse(200, 5, new ArrayList<>() {{
        add(TechnologiesEnum.mathematics);
    }}),
    stable(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.horsebackRiding);
    }}),
    temple(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.philosophy);
    }}),
    castle(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.chivalry);
    }}),
    forage(150, 2, new ArrayList<>() {{
        add(TechnologiesEnum.metalCasting);
    }}),
    garden(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.theology);
    }}),
    market(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.currency);
    }}),
    mint(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.currency);
    }}),
    monastery(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.techno);
    }}),
    university(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.education);
    }}),
    workshop(100, 2, new ArrayList<>() {{
        add(TechnologiesEnum.metalCasting);
    }}),
    bank(200, 0, new ArrayList<>() {{
        add(TechnologiesEnum.banking);
    }}),
    militaryAcademy(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.militaryScience);
    }}),
    museum(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.archaeology);
    }}),
    operaHouse(220, 3, new ArrayList<>() {{
        add(TechnologiesEnum.acoustics);
    }}),
    publicSchool(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.scientificTheory);
    }}),
    satrapsCourt(220, 0, new ArrayList<>() {{
        add(TechnologiesEnum.banking);
    }}),
    theater(300, 5, new ArrayList<>() {{
        add(TechnologiesEnum.printingPress);
    }}),
    windmill(180, 2, new ArrayList<>() {{
        add(TechnologiesEnum.economics);
    }}),
    arsenal(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.railroad);
    }}),
    broadcastTower(600, 3, new ArrayList<>() {{
        add(TechnologiesEnum.radio);
    }}),
    factory(300, 3, new ArrayList<>() {{
        add(TechnologiesEnum.steamPower);
    }}),
    hospital(400, 2, new ArrayList<>() {{
        add(TechnologiesEnum.biology);
    }}),
    militaryBase(450, 4, new ArrayList<>() {{
        add(TechnologiesEnum.telegraph);
    }}),
    stockExchange(650, 0, new ArrayList<>() {{
        add(TechnologiesEnum.electricity);
    }});


    private final int cost;
    private final int maintenance;
    private final ArrayList<TechnologiesEnum> requiredTechs;

    BuildingsEnum(int cost, int maintenance, ArrayList<TechnologiesEnum> requiredTechs) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechs = requiredTechs;
    }

    public int getCost() {
        return this.cost;
    }

    public int getMaintenance() {
        return this.maintenance;
    }

    public ArrayList<TechnologiesEnum> getRequiredTechs() {
        return this.requiredTechs;
    }
}
