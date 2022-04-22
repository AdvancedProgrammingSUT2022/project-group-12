package Enums.GameEnums;

import Models.Buildings.BuildingNotes;

import java.util.ArrayList;

public enum BuildingEnum {
    BARRACK(80, 1, new ArrayList<>() {{
        add(TechnologyEnum.BRONZE_WORKING);
    }}, null, BuildingNoteEnum.BARRACK_NOTE.getNote()),
    GRANARY(100, 1, new ArrayList<>() {{
        add(TechnologyEnum.POTTERY);
    }}, null, BuildingNoteEnum.GRANARY_NOTE.getNote()),
    LIBRARY(80, 1, new ArrayList<>() {{
        add(TechnologyEnum.WRITING);
    }}, null, BuildingNoteEnum.LIBRARY_NOTE.getNote()),
    MONUMENT(60, 1, null, null, BuildingNoteEnum.MONUMENT_NOTE.getNote()),
    WALLS(100, 1, new ArrayList<>() {{
        add(TechnologyEnum.MASONRY);
    }}, null, BuildingNoteEnum.WALLS_NOTE.getNote()),
    WATER_MILL(120, 2, new ArrayList<>() {{
        add(TechnologyEnum.THE_WHEEL);
    }}, null, BuildingNoteEnum.WALLTERMILLS_NOTE.getNote()),
    ARMORY(130, 3, new ArrayList<>() {{
        add(TechnologyEnum.IRON_WORKING);
    }}, new ArrayList<>() {{
        add(BuildingEnum.BARRACK);
    }}, BuildingNoteEnum.ARMORY_NOTE.getNote()),
    BURIAL_TOMB(120, 0, new ArrayList<>() {{
        add(TechnologyEnum.PHILOSOPHY);
    }}, null, BuildingNoteEnum.BURIAL_TOMB_NOTE.getNote()),
    CIRCUS(150, 3, new ArrayList<>() {{
        add(TechnologyEnum.HORSEBACK_RIDING);
    }}, null, BuildingNoteEnum.CIRCUS_NOTE.getNote()),
    COLOSSEUM(150, 3, new ArrayList<>() {{
        add(TechnologyEnum.CONSTRUCTION);
    }}, null, BuildingNoteEnum.COLOSSEUM_NOTE.getNote()),
    COURT_HOUSE(200, 5, new ArrayList<>() {{
        add(TechnologyEnum.MATHEMATICS);
    }}, null, BuildingNoteEnum.COURTHOUSE_NOTE.getNote()),
    STABLE(100, 1, new ArrayList<>() {{
        add(TechnologyEnum.HORSEBACK_RIDING);
    }}, null, BuildingNoteEnum.STABLE_NOTE.getNote()),
    TEMPLE(120, 2, new ArrayList<>() {{
        add(TechnologyEnum.PHILOSOPHY);
    }}, new ArrayList<>() {{
        add(BuildingEnum.MONUMENT);
    }}, BuildingNoteEnum.TEMPLE_NOTE.getNote()),
    CASTLE(200, 3, new ArrayList<>() {{
        add(TechnologyEnum.CHIVALRY);
    }}, new ArrayList<>() {{
        add(BuildingEnum.WALLS);
    }}, BuildingNoteEnum.CASTLE_NOTE.getNote()),
    FORAGE(150, 2, new ArrayList<>() {{
        add(TechnologyEnum.METAL_CASTING);
    }}, null, BuildingNoteEnum.FORGE_NOTE.getNote()),
    GARDEN(120, 2, new ArrayList<>() {{
        add(TechnologyEnum.THEOLOGY);
    }}, null, BuildingNoteEnum.GARDEN_NOTE.getNote()),
    MARKET(120, 0, new ArrayList<>() {{
        add(TechnologyEnum.CURRENCY);
    }}, null, BuildingNoteEnum.MARKET_NOTE.getNote()),
    MINT(120, 0, new ArrayList<>() {{
        add(TechnologyEnum.CURRENCY);
    }}, null, BuildingNoteEnum.MINT_NOTE.getNote()),
    MONASTERY(120, 2, new ArrayList<>() {{
        add(TechnologyEnum.TECHNO);
    }}, null, BuildingNoteEnum.MONASTERY_NOTE.getNote()),
    UNIVERSITY(200, 3, new ArrayList<>() {{
        add(TechnologyEnum.EDUCATION);
    }}, new ArrayList<>() {{
        add(BuildingEnum.LIBRARY);
    }}, BuildingNoteEnum.UNIVERSITY_NOTE.getNote()),
    WORKSHOP(100, 2, new ArrayList<>() {{
        add(TechnologyEnum.METAL_CASTING);
    }}, null, BuildingNoteEnum.WORKSHOP_NOTE.getNote()),
    BANK(200, 0, new ArrayList<>() {{
        add(TechnologyEnum.BANKING);
    }}, new ArrayList<>() {{
        add(BuildingEnum.MARKET);
    }}, BuildingNoteEnum.BANK.getNote()),
    MILITARY_ACADEMY(350, 3, new ArrayList<>() {{
        add(TechnologyEnum.MILITARY_SCIENCE);
    }}, new ArrayList<>() {{
        add(BuildingEnum.BARRACK);
    }}, BuildingNoteEnum.MILITARY_ACADEMY_NOTE.getNote()),
    MUSEUM(350, 3, new ArrayList<>() {{
        add(TechnologyEnum.ARCHAEOLOGY);
    }}, new ArrayList<>() {{
        add(BuildingEnum.OPERA_HOUSE);
    }}, BuildingNoteEnum.MUSEUM_NOTE.getNote()),
    OPERA_HOUSE(220, 3, new ArrayList<>() {{
        add(TechnologyEnum.ACOUSTICS);
    }}, new ArrayList<>() {{
        add(BuildingEnum.TEMPLE);
        add(BuildingEnum.BURIAL_TOMB);
    }}, BuildingNoteEnum.OPERA_HOUSE_NOTE.getNote()),

    PUBLIC_SCHOOL(350, 3, new ArrayList<>() {{
        add(TechnologyEnum.SCIENTIFIC_THEORY);
    }}, new ArrayList<>() {{
        add(BuildingEnum.UNIVERSITY);
    }}, BuildingNoteEnum.PUBLIC_SCHOOL_NOTE.getNote()),
    SATRAPS_COURT(220, 0, new ArrayList<>() {{
        add(TechnologyEnum.BANKING);
    }}, new ArrayList<>() {{
        add(BuildingEnum.MARKET);
    }}, BuildingNoteEnum.SATRAPS_COURT_NOTE.getNote()),
    THEATER(300, 5, new ArrayList<>() {{
        add(TechnologyEnum.PRINTING_PRESS);
    }}, new ArrayList<>() {{
        add(BuildingEnum.COLOSSEUM);
    }}, BuildingNoteEnum.THEATER_NOTE.getNote()),

    WINDMILL(180, 2, new ArrayList<>() {{
        add(TechnologyEnum.ECONOMICS);
    }}, null, BuildingNoteEnum.WINDMILL_NOTE.getNote()),
    ARSENAL(350, 3, new ArrayList<>() {{
        add(TechnologyEnum.RAILROAD);
    }}, new ArrayList<>() {{
        add(BuildingEnum.MILITARY_ACADEMY);
    }}, BuildingNoteEnum.ARSENAL_NOTE.getNote()),
    BROADCAST_TOWER(600, 3, new ArrayList<>() {{
        add(TechnologyEnum.RADIO);
    }}, new ArrayList<>() {{
        add(BuildingEnum.MUSEUM);
    }}, BuildingNoteEnum.BROADCAST_TOWER_NOTE.getNote()),
    FACTORY(300, 3, new ArrayList<>() {{
        add(TechnologyEnum.STEAM_POWER);
    }}, null, BuildingNoteEnum.FACTORY_NOTE.getNote()),
    HOSPITAL(400, 2, new ArrayList<>() {{
        add(TechnologyEnum.BIOLOGY);
    }}, null, BuildingNoteEnum.HOSPITAL_NOTE.getNote()),
    MILITARY_BASE(450, 4, new ArrayList<>() {{
        add(TechnologyEnum.TELEGRAPH);
    }}, new ArrayList<>() {{
        add(BuildingEnum.CASTLE);
    }}, BuildingNoteEnum.MILITARY_BASE_NOTE.getNote()),
    STOCK_EXCHANGE(650, 0, new ArrayList<>() {{
        add(TechnologyEnum.ELECTRICITY);
    }}, new ArrayList<>() {{
        add(BuildingEnum.BANK);
    }}, BuildingNoteEnum.STOCK_EXCHANGE_NOTE.getNote());


    private final int cost;
    private final int maintenance;
    private final ArrayList<TechnologyEnum> requiredTechs;
    private final ArrayList<BuildingEnum> requiredBuildings;
    private final BuildingNotes note;


    BuildingEnum(int cost, int maintenance, ArrayList<TechnologyEnum> requiredTechs, ArrayList<BuildingEnum> requiredBuildings, BuildingNotes buildingNotes) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechs = requiredTechs;
        this.requiredBuildings = requiredBuildings;
        this.note = buildingNotes;
    }

    public int getCost() {
        return this.cost;
    }

    public int getMaintenance() {
        return this.maintenance;
    }

    public ArrayList<TechnologyEnum> getRequiredTechs() {
        return this.requiredTechs;
    }

    public boolean checkIfHasRequiredTechs(ArrayList<TechnologyEnum> techList) {
        for (TechnologyEnum list : this.requiredTechs) {
            if (!techList.contains(list)) {
                return false;
            }
        }
        return true;
    }
}
