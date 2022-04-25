package Enums.GameEnums;

import Models.Buildings.BuildingNotes;

import java.util.List;

public enum BuildingEnum {
    BARRACK(80, 1, List.of(TechnologyEnum.BRONZE_WORKING), List.of(), BuildingNoteEnum.BARRACK_NOTE.getNote()),
    GRANARY(100, 1, List.of(TechnologyEnum.POTTERY), List.of(), BuildingNoteEnum.GRANARY_NOTE.getNote()),
    LIBRARY(80, 1, List.of(TechnologyEnum.WRITING), List.of(), BuildingNoteEnum.LIBRARY_NOTE.getNote()),
    MONUMENT(60, 1, List.of(), List.of(), BuildingNoteEnum.MONUMENT_NOTE.getNote()),
    WALLS(100, 1, List.of(TechnologyEnum.MASONRY), List.of(), BuildingNoteEnum.WALLS_NOTE.getNote()),
    WATER_MILL(120, 2, List.of(TechnologyEnum.THE_WHEEL), List.of(), BuildingNoteEnum.WATER_MILLS_NOTE.getNote()),
    ARMORY(130, 3, List.of(TechnologyEnum.IRON_WORKING), List.of(BuildingEnum.BARRACK), BuildingNoteEnum.ARMORY_NOTE.getNote()),
    BURIAL_TOMB(120, 0, List.of(TechnologyEnum.PHILOSOPHY), List.of(), BuildingNoteEnum.BURIAL_TOMB_NOTE.getNote()),
    CIRCUS(150, 3, List.of(TechnologyEnum.HORSEBACK_RIDING), List.of(), BuildingNoteEnum.CIRCUS_NOTE.getNote()),
    COLOSSEUM(150, 3, List.of(TechnologyEnum.CONSTRUCTION), List.of(), BuildingNoteEnum.COLOSSEUM_NOTE.getNote()),
    COURT_HOUSE(200, 5, List.of(TechnologyEnum.MATHEMATICS), List.of(), BuildingNoteEnum.COURTHOUSE_NOTE.getNote()),
    STABLE(100, 1, List.of(TechnologyEnum.HORSEBACK_RIDING), List.of(), BuildingNoteEnum.STABLE_NOTE.getNote()),
    TEMPLE(120, 2, List.of(TechnologyEnum.PHILOSOPHY), List.of(BuildingEnum.MONUMENT), BuildingNoteEnum.TEMPLE_NOTE.getNote()),
    CASTLE(200, 3, List.of(TechnologyEnum.CHIVALRY), List.of(BuildingEnum.WALLS), BuildingNoteEnum.CASTLE_NOTE.getNote()),
    FORAGE(150, 2, List.of(TechnologyEnum.METAL_CASTING), List.of(), BuildingNoteEnum.FORGE_NOTE.getNote()),
    GARDEN(120, 2, List.of(TechnologyEnum.THEOLOGY), List.of(), BuildingNoteEnum.GARDEN_NOTE.getNote()),
    MARKET(120, 0, List.of(TechnologyEnum.CURRENCY), List.of(), BuildingNoteEnum.MARKET_NOTE.getNote()),
    MINT(120, 0, List.of(TechnologyEnum.CURRENCY), List.of(), BuildingNoteEnum.MINT_NOTE.getNote()),
    MONASTERY(120, 2, List.of(TechnologyEnum.TECHNO), List.of(), BuildingNoteEnum.MONASTERY_NOTE.getNote()),
    UNIVERSITY(200, 3, List.of(TechnologyEnum.EDUCATION), List.of(BuildingEnum.LIBRARY), BuildingNoteEnum.UNIVERSITY_NOTE.getNote()),
    WORKSHOP(100, 2, List.of(TechnologyEnum.METAL_CASTING), List.of(), BuildingNoteEnum.WORKSHOP_NOTE.getNote()),
    BANK(200, 0, List.of(TechnologyEnum.BANKING), List.of(BuildingEnum.MARKET), BuildingNoteEnum.BANK.getNote()),
    MILITARY_ACADEMY(350, 3, List.of(TechnologyEnum.MILITARY_SCIENCE), List.of(BuildingEnum.BARRACK), BuildingNoteEnum.MILITARY_ACADEMY_NOTE.getNote()),
    OPERA_HOUSE(220, 3, List.of(TechnologyEnum.ACOUSTICS), List.of(BuildingEnum.TEMPLE, BuildingEnum.BURIAL_TOMB), BuildingNoteEnum.OPERA_HOUSE_NOTE.getNote()),
    MUSEUM(350, 3, List.of(TechnologyEnum.ARCHAEOLOGY), List.of(BuildingEnum.OPERA_HOUSE), BuildingNoteEnum.MUSEUM_NOTE.getNote()),
    PUBLIC_SCHOOL(350, 3, List.of(TechnologyEnum.SCIENTIFIC_THEORY), List.of(BuildingEnum.UNIVERSITY), BuildingNoteEnum.PUBLIC_SCHOOL_NOTE.getNote()),
    SATRAPS_COURT(220, 0, List.of(TechnologyEnum.BANKING), List.of(BuildingEnum.MARKET), BuildingNoteEnum.SATRAPS_COURT_NOTE.getNote()),
    THEATER(300, 5, List.of(TechnologyEnum.PRINTING_PRESS), List.of(BuildingEnum.COLOSSEUM), BuildingNoteEnum.THEATER_NOTE.getNote()),
    WINDMILL(180, 2, List.of(TechnologyEnum.ECONOMICS), List.of(), BuildingNoteEnum.WINDMILL_NOTE.getNote()),
    ARSENAL(350, 3, List.of(TechnologyEnum.RAILROAD), List.of(BuildingEnum.MILITARY_ACADEMY), BuildingNoteEnum.ARSENAL_NOTE.getNote()),
    BROADCAST_TOWER(600, 3, List.of(TechnologyEnum.RADIO), List.of(BuildingEnum.MUSEUM), BuildingNoteEnum.BROADCAST_TOWER_NOTE.getNote()),
    FACTORY(300, 3, List.of(TechnologyEnum.STEAM_POWER), List.of(), BuildingNoteEnum.FACTORY_NOTE.getNote()),
    HOSPITAL(400, 2, List.of(TechnologyEnum.BIOLOGY), List.of(), BuildingNoteEnum.HOSPITAL_NOTE.getNote()),
    MILITARY_BASE(450, 4, List.of(TechnologyEnum.TELEGRAPH), List.of(BuildingEnum.CASTLE), BuildingNoteEnum.MILITARY_BASE_NOTE.getNote()),
    STOCK_EXCHANGE(650, 0, List.of(TechnologyEnum.ELECTRICITY), List.of(BuildingEnum.BANK), BuildingNoteEnum.STOCK_EXCHANGE_NOTE.getNote());


    private final int cost;
    private final int maintenance;
    private final List<TechnologyEnum> requiredTechs;
    private final List<BuildingEnum> requiredBuildings;
    private final BuildingNotes note;


    BuildingEnum(int cost, int maintenance, List<TechnologyEnum> requiredTechs, List<BuildingEnum> requiredBuildings, BuildingNotes buildingNotes) {
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

    public List<TechnologyEnum> getRequiredTechs() {
        return this.requiredTechs;
    }

    public boolean checkIfHasRequiredTechs(List<TechnologyEnum> techList) {
        for (TechnologyEnum list : this.requiredTechs) {
            if (!techList.contains(list)) {
                return false;
            }
        }
        return true;
    }
}