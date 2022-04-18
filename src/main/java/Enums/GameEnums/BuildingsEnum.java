package Enums.GameEnums;

import Enums.GameEnums.BuildingNote;
import Models.Buildings.BuildingNotes;

import java.util.ArrayList;

public enum BuildingsEnum {
    barrack(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.bronzeWorking);
    }}, null, BuildingNote.BARRACK_NOTE.getNote()),
    granary(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.pottery);
    }}, null, BuildingNote.GRANARY_NOTE.getNote()),
    library(80, 1, new ArrayList<>() {{
        add(TechnologiesEnum.writing);
    }}, null, BuildingNote.LIBRARY_NOTE.getNote()),
    monument(60, 1, null, null, BuildingNote.MONUMENT_NOTE.getNote()),
    walls(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.masonry);
    }}, null, BuildingNote.WALLS_NOTE.getNote()),
    waterMill(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.theWheel);
    }}, null, BuildingNote.WALLTERMILLS_NOTE.getNote()),
    armory(130, 3, new ArrayList<>() {{
        add(TechnologiesEnum.ironWorking);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.barrack);
    }}, BuildingNote.ARMORY_NOTE.getNote()),
    burialTomb(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.philosophy);
    }}, null, BuildingNote.BURIAL_TOMP_NOTE.getNote()),
    circus(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.horsebackRiding);
    }}, null, BuildingNote.CIRCUS_NOTE.getNote()),
    colosseum(150, 3, new ArrayList<>() {{
        add(TechnologiesEnum.construction);
    }}, null, BuildingNote.COLOSSEUM_NOTE.getNote()),
    courthouse(200, 5, new ArrayList<>() {{
        add(TechnologiesEnum.mathematics);
    }}, null, BuildingNote.COURTHOUSE_NOTE.getNote()),
    stable(100, 1, new ArrayList<>() {{
        add(TechnologiesEnum.horsebackRiding);
    }}, null, BuildingNote.STABLE_NOTE.getNote()),
    temple(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.philosophy);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.monument);
    }}, BuildingNote.TEMPLE_NOTE.getNote()),
    castle(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.chivalry);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.walls);
    }}, BuildingNote.CASTLE_NOTE.getNote()),
    forage(150, 2, new ArrayList<>() {{
        add(TechnologiesEnum.metalCasting);
    }}, null, BuildingNote.FORGE_NOTE.getNote()),
    garden(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.theology);
    }}, null, BuildingNote.GARDEN_NOTE.getNote()),
    market(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.currency);
    }}, null, BuildingNote.MARKET_NOTE.getNote()),
    mint(120, 0, new ArrayList<>() {{
        add(TechnologiesEnum.currency);
    }}, null, BuildingNote.MINT_NOTE.getNote()),
    monastery(120, 2, new ArrayList<>() {{
        add(TechnologiesEnum.techno);
    }}, null, BuildingNote.MONASTERY_NOTE.getNote()),
    university(200, 3, new ArrayList<>() {{
        add(TechnologiesEnum.education);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.library);
    }}, BuildingNote.UNIVERSITY_NOTE.getNote()),
    workshop(100, 2, new ArrayList<>() {{
        add(TechnologiesEnum.metalCasting);
    }}, null, BuildingNote.WORKSHOP_NOTE.getNote()),
    bank(200, 0, new ArrayList<>() {{
        add(TechnologiesEnum.banking);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.market);
    }}, BuildingNote.BANK.getNote()),
    militaryAcademy(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.militaryScience);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.barrack);
    }}, BuildingNote.MILITARY_ACADEMY_NOTE.getNote()),
    museum(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.archaeology);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.operaHouse);
    }}, BuildingNote.MUSEUM_NOTE.getNote()),
    operaHouse(220, 3, new ArrayList<>() {{
        add(TechnologiesEnum.acoustics);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.temple); add(BuildingsEnum.burialTomb);
    }}, BuildingNote.OPERA_HOUSE_NOTE.getNote()),

    publicSchool(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.scientificTheory);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.university);
    }}, BuildingNote.PUBLIC_SCHOOL_NOTE.getNote()),
    satrapsCourt(220, 0, new ArrayList<>() {{
        add(TechnologiesEnum.banking);
    }},new ArrayList<>(){{
        add(BuildingsEnum.market);
    }}, BuildingNote.SATRAPSCOURT_NOTE.getNote()),
    theater(300, 5, new ArrayList<>() {{
        add(TechnologiesEnum.printingPress);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.colosseum);
    }}, BuildingNote.THEATER_NOTE.getNote()),

    windmill(180, 2, new ArrayList<>() {{
        add(TechnologiesEnum.economics);
    }}, null, BuildingNote.WINDMILL_NOTE.getNote()),
    arsenal(350, 3, new ArrayList<>() {{
        add(TechnologiesEnum.railroad);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.militaryAcademy);
    }}, BuildingNote.ARSENAL_NOTE.getNote()),
    broadcastTower(600, 3, new ArrayList<>() {{
        add(TechnologiesEnum.radio);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.museum);
    }}, BuildingNote.BROADCAST_TOWER_NOTE.getNote()),
    factory(300, 3, new ArrayList<>() {{
        add(TechnologiesEnum.steamPower);
    }}, null, BuildingNote.FACTORY_NOTE.getNote()),
    hospital(400, 2, new ArrayList<>() {{
        add(TechnologiesEnum.biology);
    }}, null, BuildingNote.HOSPITAL_NOTE.getNote()),
    militaryBase(450, 4, new ArrayList<>() {{
        add(TechnologiesEnum.telegraph);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.castle);
    }}, BuildingNote.MILITARY_BASE_NOTE.getNote()),
    stockExchange(650, 0, new ArrayList<>() {{
        add(TechnologiesEnum.electricity);
    }}, new ArrayList<>(){{
        add(BuildingsEnum.bank);
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
}
