package Enums;

import Models.Buildings.Building;
import Models.Buildings.BuildingNotes;
import Models.Cities.City;
import Models.Production;
import Models.Tiles.Tile;
import Models.Units.Unit;

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
        //TODO : for buildings we have to check if they can build or not
//        Location location=city.getCityLocation();
//        Tile cityTile=GameController.getGame().getTileGrid().getTile(location);
//        TileGrid gameTileGrid=GameController.getGame().getTileGrid();
//        for (Tile neighborTile:
//        gameTileGrid.getNeighborsOf(cityTile)) {
//          if(GameController.checkForRivers(cityTile,neighborTile)){
//              return true;
//          }
//        }
//        return false;
        city.setFood(city.getFood() + 2);
    }),
    ARMORY_NOTE(o -> {
        //TODO : complete
    }),
    BURIAL_TOMB_NOTE((BuildingNotes<City>) city -> {
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0);
        /***
         * it used in when city is capture;
         */
    }),
    CIRCUS_NOTE((BuildingNotes<City>) city -> {
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 3.0);
    }),
    COLOSSEUM_NOTE((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() +  4.0)),
    COURTHOUSE_NOTE((BuildingNotes<City>) city -> {
        /***
         * eliminates happiness from city
         */
    }),
    STABLE_NOTE((BuildingNotes<City>) city -> {
        Production production = null; //todo : getCurrent production
        if (production instanceof Unit && ((Unit) production).getType().getCombatType() == CombatTypeEnum.MOUNTED) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 85 / 100);
        }
    }),
    TEMPLE_NOTE((BuildingNotes<City>) city -> {
    }),
    CASTLE_NOTE((BuildingNotes<City>) city -> city.setCombatStrength(city.getCombatStrength() + 7.5)),
    FORGE_NOTE((BuildingNotes<City>) city -> {
        city.setProduction(city.getProduction() + (city.getProduction() * 15.0 / 100.0));
    }),
    GARDEN_NOTE(o -> {
    }),
    MARKET_NOTE((BuildingNotes<City>) city -> city.setGold(city.getGold() + city.getGold() / 4)),
    MINT_NOTE((BuildingNotes<City>) city -> {
        int counter = 0;
        for (ResourceEnum resource :
                city.getResources()) {
            if (resource == ResourceEnum.GOLD || resource == ResourceEnum.SILVER) {
                ++counter;
            }
        }
        city.setGold(city.getGold() + 3 * counter);
    }),
    MONASTERY_NOTE((BuildingNotes<City>) o -> {
    }),
    UNIVERSITY_NOTE((BuildingNotes<City>) city -> {
        city.setBeaker(city.getBeaker() + city.getBeaker() / 2);
        int number = 0;
        for (Tile tile : city.getTiles()) {
            if (tile.isCitizen() && tile.getTerrain().getTerrainType() == TerrainEnum.JUNGLE) {
                ++number;
            }
        }
        city.setBeaker(city.getBeaker() + 2 * number);
    }),
    WORKSHOP_NOTE(o -> {
        Production production = null; //todo : getCurrent production
        if (production instanceof Building) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
    BANK((BuildingNotes<City>) city -> city.setGold(city.getGold() + city.getGold() / 4)),
    MILITARY_ACADEMY_NOTE(o -> {
    }),
    MUSEUM_NOTE((BuildingNotes<City>) city -> {
    }),
    OPERA_HOUSE_NOTE((BuildingNotes<City>) city -> {
    }),
    PUBLIC_SCHOOL_NOTE((BuildingNotes<City>) city -> city.setBeaker(city.getBeaker() + city.getBeaker() / 2)),
    SATRAPS_COURT_NOTE((BuildingNotes<City>) city -> {
        city.setGold(city.getGold() + city.getGold() / 4);
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0);
    }),
    THEATER_NOTE((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings()  + 4.0)),
    WINDMILL_NOTE((BuildingNotes<City>) city -> city.setProduction(city.getProduction() + city.getProduction() * 15.0 / 100.0)),
    ARSENAL_NOTE((BuildingNotes<City>) city -> {
        Production production = null; //todo : getCurrent production
        if (production instanceof Unit) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
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
