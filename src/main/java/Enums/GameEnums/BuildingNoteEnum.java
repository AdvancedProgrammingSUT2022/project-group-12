package Enums.GameEnums;

import Models.Buildings.BuildingNotes;
import Models.Cities.City;

public enum BuildingNoteEnum {
    BARRACK_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
    }),
    GRANARY_NOTE((BuildingNotes<City>) city -> city.setFood(city.getFood() + 2)),
    LIBRARY_NOTE((BuildingNotes<City>) city -> city.setBeaker(city.getCitizensCount() / 2 + city.getBeaker())),
    MONUMENT_NOTE((BuildingNotes<City>) city -> {
    }),
    WALLS_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
    }),
    WATER_MILLS_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
        //if border a river --> city.setFood(city.getFood()+2);
    }),
    ARMORY_NOTE(o -> {
        //TODO : complete
    }),
    BURIAL_TOMB_NOTE((BuildingNotes<City>) city -> {
        city.setHappiness(city.getHappiness() + 2);
        //TODO : complete
    }),
    CIRCUS_NOTE((BuildingNotes<City>) city -> {
        city.setHappiness(city.getHappiness() + 3);
        //TODO : complete
    }),
    COLOSSEUM_NOTE((BuildingNotes<City>) city -> city.setHappiness(city.getHappiness() + 4)),
    COURTHOUSE_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
    }),
    STABLE_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
    }),
    TEMPLE_NOTE((BuildingNotes<City>) city -> {
    }),
    CASTLE_NOTE((BuildingNotes<City>) city -> city.setCombatStrength(city.getCombatStrength() + 7.5)),
    FORGE_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
        city.setProduction(city.getProduction() + (city.getProduction() * 15.0 / 100.0));
    }),
    GARDEN_NOTE(o -> {
        //TODO : complete
    }),
    MARKET_NOTE((BuildingNotes<City>) city -> city.setGold(city.getGold() + city.getGold() / 4)),
    MINT_NOTE((BuildingNotes<City>) o -> {
        //TODO : complete
    }),
    MONASTERY_NOTE((BuildingNotes<City>) city -> {
    }),
    UNIVERSITY_NOTE((BuildingNotes<City>) city -> {
        //TODO : complete
        city.setBeaker(city.getBeaker() + city.getBeaker() / 2);
    }),
    WORKSHOP_NOTE(o -> {
        //TODO : complete
    }),
    BANK((BuildingNotes<City>) city -> city.setGold(city.getGold() + city.getGold() / 2)),
    MILITARY_ACADEMY_NOTE(o -> {
        //TODO : complete
    }),
    MUSEUM_NOTE((BuildingNotes<City>) city -> {
    }),
    OPERA_HOUSE_NOTE((BuildingNotes<City>) city -> {
    }),
    PUBLIC_SCHOOL_NOTE((BuildingNotes<City>) city -> city.setBeaker(city.getBeaker() + city.getBeaker() / 2)),
    SATRAPS_COURT_NOTE((BuildingNotes<City>) city -> {
        city.setGold(city.getGold() + city.getGold() / 4);
        city.setHappiness(city.getHappiness() + 2);
    }),
    THEATER_NOTE((BuildingNotes<City>) city -> city.setHappiness(city.getHappiness() + 4)),
    WINDMILL_NOTE((BuildingNotes<City>) city -> city.setProduction(city.getProduction() + city.getProduction() * 15.0 / 100.0)),
    ARSENAL_NOTE((BuildingNotes<City>) city -> city.setProduction(city.getProduction() + city.getProduction() * 20.0 / 100.0)),
    BROADCAST_TOWER_NOTE((BuildingNotes<City>) city -> {
    }),
    FACTORY_NOTE((BuildingNotes<City>) city -> city.setProduction(city.getProduction() + city.getProduction() / 2)),
    HOSPITAL_NOTE((BuildingNotes<City>) city -> city.setFood(city.getFood() - city.getFood() / 2)),
    MILITARY_BASE_NOTE((BuildingNotes<City>) city -> city.setCombatStrength(city.getCombatStrength() + 12)),
    STOCK_EXCHANGE_NOTE((BuildingNotes<City>) city -> city.setGold(city.getGold() + city.getGold() / 3));


    private final BuildingNotes note;

    BuildingNoteEnum(BuildingNotes note) {
        this.note = note;
    }

    public BuildingNotes getNote() {
        return note;
    }
}
