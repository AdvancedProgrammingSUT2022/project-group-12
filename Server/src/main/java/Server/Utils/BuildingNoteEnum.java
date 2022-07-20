package Server.Utils;

import Project.Models.Cities.Enums.CombatTypeEnum;
import Project.Models.Cities.Enums.FeatureEnum;
import Project.Models.Cities.Enums.ResourceEnum;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Server.Controllers.GameController;

public enum BuildingNoteEnum {
    BARRACK((BuildingNotes<City>) city -> {}),
    GRANARY((BuildingNotes<City>) city -> city.setFoodFromBuildings(city.getFoodFromBuildings() + 2)),
    LIBRARY((BuildingNotes<City>) city -> GameController.getCivByName(city.getCivilization()).setBeakerFromBuildings(city.getCitizensCount() / 2 + GameController.getCivByName(city.getCivilization()).getBeakerFromBuildings())),
    MONUMENT((BuildingNotes<City>) city -> {
    }),
    WALLS((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 5.0f)),
    WATER_MILLS((BuildingNotes<City>) city -> {
        if (city.getTile().hasRiver()) {
            city.setFoodFromBuildings(city.getFoodFromBuildings() + 2);
        }
    }),
    ARMORY(o -> {
    }),
    BURIAL_TOMB((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0)),
    CIRCUS((BuildingNotes<City>) city -> {System.out.println("hello note " + city); city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 3.0);
        }),
    COLOSSEUM((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    COURTHOUSE((BuildingNotes<City>) city -> {

    }),
    STABLE((BuildingNotes<City>) city -> {
        Production production = null;
        if ((production instanceof Unit) && ((Unit) production).getUnitType().getCombatType() == CombatTypeEnum.MOUNTED) {
            production.decreaseRemainedProduction(production.getRemainedProduction() * 85 / 100);
        }
    }),
    TEMPLE((BuildingNotes<City>) city -> {
    }),
    CASTLE((BuildingNotes<City>) city -> city.setCombatStrengthFromBuildings(city.getCombatStrengthFromBuildings() + 7.5)),
    FORGE((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + (city.getProductionFromBuildings() * 15.0 / 100.0))),
    GARDEN(o -> {
    }),
    MARKET((BuildingNotes<City>) city -> city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4)),
    MINT((BuildingNotes<City>) city -> {
        int counter = 0;
        for (ResourceEnum resource : city.getAchievedResources()) {
            if (resource == ResourceEnum.GOLD || resource == ResourceEnum.SILVER) {
                ++counter;
            }
        }
        city.setGoldFromBuildings(city.getGoldFromBuildings() + 3 * counter);
    }),
    MONASTERY((BuildingNotes<City>) o -> {
    }),
    UNIVERSITY((BuildingNotes<City>) city -> {
        GameController.getCivByName(city.getCivilization()).setBeakerRatioFromBuildings(GameController.getCivByName(city.getCivilization()).getBeakerRatioFromBuildings() * 1.5);
        int number = 0;
        for (Tile tile : city.getTiles()) {
            if (tile.getCitizen() != null && tile.getTerrain().getFeatures().contains(FeatureEnum.JUNGLE)) {
                number += 2;
            }
        }
        GameController.getCivByName(city.getCivilization()).setBeakerFromBuildings(GameController.getCivByName(city.getCivilization()).getBeakerFromBuildings() + number);
    }),
    WORKSHOP(o -> {
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
    PUBLIC_SCHOOL((BuildingNotes<City>) city -> GameController.getCivByName(city.getCivilization()).setBeakerFromBuildings(GameController.getCivByName(city.getCivilization()).getBeakerFromBuildings() * (3 / 2))),
    SATRAPS_COURT((BuildingNotes<City>) city -> {
        city.setGoldRatioFromBuildings(city.getGoldRatioFromBuildings() + city.getGoldRatioFromBuildings() / 4);
        city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 2.0);
    }),
    THEATER((BuildingNotes<City>) city -> city.setHappinessFromBuildings(city.getHappinessFromBuildings() + 4.0)),
    //todo : check
    WINDMILL((BuildingNotes<City>) city -> city.setProductionFromBuildings(city.getProductionFromBuildings() + city.getProductionFromBuildings() * 15.0 / 100.0)),
    ARSENAL((BuildingNotes<City>) city -> {
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
