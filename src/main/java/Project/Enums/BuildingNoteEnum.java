package Project.Enums;

import Project.Models.Buildings.Building;
import Project.Models.Buildings.BuildingNotes;
import Project.Models.Cities.City;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;

public enum BuildingNoteEnum {
    BARRACK_NOTE((BuildingNotes<City>) city -> {
    }),
    GRANARY_NOTE((BuildingNotes<City>) city -> city.setFoodFromBuildings(city.getFoodFromBuildings() + 2)),
    LIBRARY_NOTE((BuildingNotes<City>) city -> city.getCivilization().setBeakerFromBuildings(city.getCitizensCount() / 2 + city.getCivilization().getBeakerFromBuildings())),
    MONUMENT_NOTE((BuildingNotes<City>) city -> {
    }),
    WALLS_NOTE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 5.0f)),
    WATER_MILLS_NOTE((BuildingNotes<City>) city -> {
        if (city.getTile().hasRiver()) {
            city.setFoodFromBuildings(city.getFoodFromBuildings() + 2);
        }
    }),
    ARMORY_NOTE(o -> {
    }),
    BURIAL_TOMB_NOTE((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0)),
    CIRCUS_NOTE((BuildingNotes<City>) city -> {System.out.println("hello note " + city); city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 3.0);
        }),
    COLOSSEUM_NOTE((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    COURTHOUSE_NOTE((BuildingNotes<City>) city -> {

    }),
    STABLE_NOTE((BuildingNotes<City>) city -> {
        Production production = null;
        if ((production instanceof Unit) && ((Unit) production).getType().getCombatType() == CombatTypeEnum.MOUNTED) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 85 / 100);
        }
    }),
    TEMPLE_NOTE((BuildingNotes<City>) city -> {
    }),
    CASTLE_NOTE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 7.5)),
    FORGE_NOTE((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + (city.getProductionFromBuildings() * 15.0 / 100.0))),
    GARDEN_NOTE(o -> {
    }),
    MARKET_NOTE((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4)),
    MINT_NOTE((BuildingNotes<City>) city -> {
        int counter = 0;
        for (ResourceEnum resource : city.getAchievedResources()) {
            if (resource == ResourceEnum.GOLD || resource == ResourceEnum.SILVER) {
                ++counter;
            }
        }
        city.setGoldFromBuildings(city.getGoldFromBuildings() + 3 * counter);
    }),
    MONASTERY_NOTE((BuildingNotes<City>) o -> {
    }),
    UNIVERSITY_NOTE((BuildingNotes<City>) city -> {
        city.getCivilization().setBeakerRatioFromBuildings(city.getCivilization().getBeakerRatioFromBuildings() * 1.5);
        int number = 0;
        for (Tile tile : city.getTiles()) {
            if (tile.getCitizen() != null && tile.getTerrain().getFeatures().contains(FeatureEnum.JUNGLE)) {
                number += 2;
            }
        }
        city.getCivilization().setBeakerFromBuildings(city.getCivilization().getBeakerFromBuildings() + number);
    }),
    WORKSHOP_NOTE(o -> {
        Production production = null;
        if (production instanceof Building) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
    BANK((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4)),
    MILITARY_ACADEMY_NOTE(o -> {
    }),
    MUSEUM_NOTE((BuildingNotes<City>) city -> {
    }),
    OPERA_HOUSE_NOTE((BuildingNotes<City>) city -> {
    }),
    PUBLIC_SCHOOL_NOTE((BuildingNotes<City>) city -> city.getCivilization().setBeakerFromBuildings(city.getCivilization().getBeakerFromBuildings() * (3 / 2))),
    SATRAPS_COURT_NOTE((BuildingNotes<City>) city -> {
        city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4);
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0);
    }),
    THEATER_NOTE((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    //todo : check
    WINDMILL_NOTE((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + city.getProductionFromBuildings() * 15.0 / 100.0)),
    ARSENAL_NOTE((BuildingNotes<City>) city -> {
        Production production = null;
        if (production instanceof Unit) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
    BROADCAST_TOWER_NOTE((BuildingNotes<City>) city -> {
    }),
    FACTORY_NOTE((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + city.getProductionFromBuildings() / 2)),
    HOSPITAL_NOTE((BuildingNotes<City>) city -> city.setFoodFromBuildings(city.getFoodFromBuildings() - city.getFoodFromBuildings() / 2)),
    MILITARY_BASE_NOTE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 12)),
    STOCK_EXCHANGE_NOTE((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 3));
    private final BuildingNotes note;

    BuildingNoteEnum(BuildingNotes note) {
        this.note = note;
    }

    public BuildingNotes getNote() {
        return note;
    }
}
