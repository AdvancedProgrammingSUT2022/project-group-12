package Enums.GameEnums;

import Enums.GameEnums.BuildingNote;
import Models.Buildings.BuildingNotes;

import java.util.ArrayList;

public enum BuildingsEnum {
    BARRAK(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.BRONZE_WORKING);
    }}, null, BuildingNote.BARRACK_NOTE.getNote()),
    GRANARY(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.POTTERY);
    }}, null, BuildingNote.GRANARY_NOTE.getNote()),
    LIBRARY(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.WRITING);
    }}, null, BuildingNote.LIBRARY_NOTE.getNote()),
    MONUMENT(60, 1, null, null, BuildingNote.MONUMENT_NOTE.getNote()),
    WALLS(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.MASONRY);
    }}, null, BuildingNote.WALLS_NOTE.getNote()),
    WATER_MILL(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.THE_WHEEL);
    }}, null, BuildingNote.WALLTERMILLS_NOTE.getNote()),
    ARMORY(130, 3, new ArrayList<>() {{
        add(TechnologiesEnum.IRON_WORKING);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.BARRAK);
    }}, BuildingNote.ARMORY_NOTE.getNote()),
    BURIAL_TOMB(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.PHILOSOPHY);
    }}, null, BuildingNote.BURIAL_TOMP_NOTE.getNote()),
    CIRCUS(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.HORSEBACK_RIDING);
    }}, null, BuildingNote.CIRCUS_NOTE.getNote()),
    COLOSSEUM(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.CONSTRUCTION);
    }}, null, BuildingNote.COLOSSEUM_NOTE.getNote()),
    COURT_HOUSE(200, 5, new ArrayList<>() {{
        add(TechnologiesEnum.MATHEMATICS);
    }}, null, BuildingNote.COURTHOUSE_NOTE.getNote()),
    STABLE(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.HORSEBACK_RIDING);
    }}, null, BuildingNote.STABLE_NOTE.getNote()),
    TEMPLE(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.PHILOSOPHY);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.MONUMENT);
    }}, BuildingNote.TEMPLE_NOTE.getNote()),
    CASTLE(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.CHIVALRY);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.WALLS);
    }}, BuildingNote.CASTLE_NOTE.getNote()),
    FORAGE(150, 2, new ArrayList<>() {{
        add(TechnologiesEnum.METAL_CASTING);
    }}, null, BuildingNote.FORGE_NOTE.getNote()),
    GARDEN(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.THEOLOGY);
    }}, null, BuildingNote.GARDEN_NOTE.getNote()),
    MARKET(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.CURRENCY);
    }}, null, BuildingNote.MARKET_NOTE.getNote()),
    MINT(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.CURRENCY);
    }}, null, BuildingNote.MINT_NOTE.getNote()),
    MONASTERY(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.TECHNO);
    }}, null, BuildingNote.MONASTERY_NOTE.getNote()),
    UNIVERSITY(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.EDUCATION);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.LIBRARY);
    }}, BuildingNote.UNIVERSITY_NOTE.getNote()),
    WORKSHOP(100, 2, new ArrayList<>() {{
        add(TechnologiesEnum.METAL_CASTING);
    }}, null, BuildingNote.WORKSHOP_NOTE.getNote()),
    BANK(200, 0, new ArrayList<>() {{
        add(TechnologiesEnum.BANKING);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.MARKET);
    }}, BuildingNote.BANK.getNote()),
    MILITARY_ACADEMY(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.MILITARY_SCIENCE);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.BARRAK);
    }}, BuildingNote.MILITARY_ACADEMY_NOTE.getNote()),
    MUSEUM(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.ARCHAEOLOGY);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.OPERA_HOUSE);
    }}, BuildingNote.MUSEUM_NOTE.getNote()),
    OPERA_HOUSE(220, 3, new ArrayList<>() {{
        add(TechnologiesEnum.ACOUSTICS);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.TEMPLE);
        add(BuildingsEnum.BURIAL_TOMB);
    }}, BuildingNote.OPERA_HOUSE_NOTE.getNote()),

    PUBLIC_SCHOOL(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.SCIENTIFIC_THEORY);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.UNIVERSITY);
    }}, BuildingNote.PUBLIC_SCHOOL_NOTE.getNote()),
    SATRAPS_COURT(220, 0, new ArrayList<>() {{
        add(TechnologiesEnum.BANKING);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.MARKET);
    }}, BuildingNote.SATRAPSCOURT_NOTE.getNote()),
    THEATER(300, 5, new ArrayList<>() {{
        add(TechnologiesEnum.PRINTING_PRESS);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.COLOSSEUM);
    }}, BuildingNote.THEATER_NOTE.getNote()),

    WINDMILL(180, 2, new ArrayList<>() {{
        add(TechnologiesEnum.ECONOMICS);
    }}, null, BuildingNote.WINDMILL_NOTE.getNote()),
    ARSENAL(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.RAILROAD);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.MILITARY_ACADEMY);
    }}, BuildingNote.ARSENAL_NOTE.getNote()),
    BROADCAST_TOWER(600, 3, new ArrayList<>() {{
        add(TechnologiesEnum.RADIO);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.MUSEUM);
    }}, BuildingNote.BROADCAST_TOWER_NOTE.getNote()),
    FACTORY(300, 3, new ArrayList<>() {{
        add(TechnologiesEnum.STEAM_POWER);
    }}, null, BuildingNote.FACTORY_NOTE.getNote()),
    HOSPITAL(400, 2, new ArrayList<>() {{
        add(TechnologiesEnum.BIOLOGY);
    }}, null, BuildingNote.HOSPITAL_NOTE.getNote()),
    MILITARY_BASE(450, 4, new ArrayList<>() {{
        add(TechnologiesEnum.TELEGRAPH);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.CASTLE);
    }}, BuildingNote.MILITARY_BASE_NOTE.getNote()),
    STOCK_EXCHANGE(650, 0, new ArrayList<>() {{
        add(TechnologiesEnum.ELECTRICITY);
    }}, new ArrayList<>() {{
        add(BuildingsEnum.BANK);
    }}, BuildingNote.STOCK_EXCHANGE_NOTE.getNote());


    private final int cost;
    private final int maintenance;
    private final ArrayList<TechnologiesEnum> requiredTechs;
    private final ArrayList<BuildingsEnum> requiredBuildings;
    private final BuildingNotes note;


    BuildingsEnum(int cost, int maintenance, ArrayList<TechnologiesEnum> requiredTechs, ArrayList<BuildingsEnum> requiredBuildings, BuildingNotes buildingNotes) {
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

    public ArrayList<TechnologiesEnum> getRequiredTechs() {
        return this.requiredTechs;
    }

    public boolean checkIfHasRequiredTechs(ArrayList<TechnologiesEnum> techList) {
        for (TechnologiesEnum list : this.requiredTechs) {
            if (!techList.contains(list)) {
                return false;
            }
        }
        return true;
    }
}
