package Server.Utils;

import Project.Enums.CombatTypeEnum;
import Project.Enums.FeatureEnum;
import Project.Enums.ResourceEnum;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Server.Controllers.CityHandler;
import Server.Controllers.GameController;

public enum BuildingNoteEnum {
    BARRACK((BuildingNotes<City>) city -> {}),
    GRANARY((BuildingNotes<City>) city -> city.setFoodFromBuildings(city.getFoodFromBuildings() + 2)),
    LIBRARY((BuildingNotes<City>) city -> GameController.getCivByName(city.getCivName()).setBeakerFromBuildings(city.getCitizensCount() / 2 + GameController.getCivByName(city.getCivName()).getBeakerFromBuildings())),
    MONUMENT((BuildingNotes<City>) city -> {
    }),
    WALLS((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 5.0f)),
    WATER_MILL((BuildingNotes<City>) city -> {
        if (GameController.getGameTile(city.getLocation()).hasRiver()) {
            city.setFoodFromBuildings(city.getFoodFromBuildings() + 2);
        }
    }),
    ARMORY(o -> {
    }),
    BURIAL_TOMB((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0)),
    CIRCUS((BuildingNotes<City>) city -> {System.out.println("hello note " + city); city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 3.0);
        }),
    COLOSSEUM((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    COURT_HOUSE((BuildingNotes<City>) city -> {

    }),
    STABLE((BuildingNotes<City>) city -> { // todo: fix
        Production production = null;
        if ((production instanceof Unit) && ((Unit) production).getUnitType().getCombatType() == CombatTypeEnum.MOUNTED) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 85 / 100);
        }
    }),
    TEMPLE((BuildingNotes<City>) city -> {
    }),
    CASTLE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 7.5)),
    FORAGE((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + (city.getProductionFromBuildings() * 15.0 / 100.0))),
    GARDEN(o -> {
    }),
    MARKET((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4)),
    MINT((BuildingNotes<City>) city -> {
        int counter = 0;
        for (ResourceEnum resource : CityHandler.getAchievedResources(city)) {
            if (resource == ResourceEnum.GOLD || resource == ResourceEnum.SILVER) {
                ++counter;
            }
        }
        city.setGoldFromBuildings(city.getGoldFromBuildings() + 3 * counter);
    }),
    MONASTERY((BuildingNotes<City>) o -> {
    }),
    UNIVERSITY((BuildingNotes<City>) city -> {
        GameController.getCivByName(city.getCivName()).setBeakerRatioFromBuildings(GameController.getCivByName(city.getCivName()).getBeakerRatioFromBuildings() * 1.5);
        int number = 0;
        for (Tile tile : CityHandler.getCityTiles(city)) {
            if (tile.getCitizen() != null && tile.getTerrain().getFeatures().contains(FeatureEnum.JUNGLE)) {
                number += 2;
            }
        }
        GameController.getCivByName(city.getCivName()).setBeakerFromBuildings(GameController.getCivByName(city.getCivName()).getBeakerFromBuildings() + number);
    }),
    WORKSHOP(o -> { // todo: fix
        Production production = null;
        if (production instanceof Building) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
    BANK((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4)),
    MILITARY_ACADEMY(o -> {
    }),
    MUSEUM((BuildingNotes<City>) city -> {
    }),
    OPERA_HOUSE((BuildingNotes<City>) city -> {
    }),
    PUBLIC_SCHOOL((BuildingNotes<City>) city -> GameController.getCivByName(city.getCivName()).setBeakerFromBuildings((int) (GameController.getCivByName(city.getCivName()).getBeakerFromBuildings() * 1.5))),
    SATRAPS_COURT((BuildingNotes<City>) city -> {
        city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4);
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0);
    }),
    THEATER((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    //todo : check
    WINDMILL((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + city.getProductionFromBuildings() * 15.0 / 100.0)),
    ARSENAL((BuildingNotes<City>) city -> { // todo: fix
        Production production = null;
        if (production instanceof Unit) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 4 / 5);
        }
    }),
    BROADCAST_TOWER((BuildingNotes<City>) city -> {
    }),
    FACTORY((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + city.getProductionFromBuildings() / 2)),
    HOSPITAL((BuildingNotes<City>) city -> city.setFoodFromBuildings(city.getFoodFromBuildings() - city.getFoodFromBuildings() / 2)),
    MILITARY_BASE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 12)),
    STOCK_EXCHANGE((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 3));
    private final BuildingNotes note;

    BuildingNoteEnum(BuildingNotes note) {
        this.note = note;
    }

    public BuildingNotes getNote() {
        return note;
    }
}
