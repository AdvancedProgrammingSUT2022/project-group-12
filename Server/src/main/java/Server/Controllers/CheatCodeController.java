package Server.Controllers;

import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Server.Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Server.Models.Civilization;
import Server.Utils.BuildingNotesLoader;
import Server.Utils.CommandException;

import java.util.ArrayList;


public class CheatCodeController {
    private static CheatCodeController instance;

    public static CheatCodeController getInstance() {
        if (instance == null) {
            instance = new CheatCodeController();
        }
        return instance;
    }

    public void increaseGold(int amount) {
        GameController.getGame().getCurrentCivilization().addGold(amount);
    }

    public void increaseFood(City city, int amount) {
        city.setFoodFromCheat(city.getFoodFromCheat() + amount);
    }

    public void spawnUnit(UnitEnum unitEnum, Civilization civilization, Location location) throws CommandException {
        Tile tile = GameController.getGameTile(location);
        if (!tile.getTerrain().getTerrainType().isReachable()) {
            throw new CommandException(CommandResponse.CANNOT_SPAWN_ON_TILE, tile.getTerrain().getTerrainType().name());
        }
        Unit newUnit = Unit.constructUnitFromEnum(unitEnum, civilization.getName(), location);
        civilization.addUnit(newUnit);
        GameController.placeUnit(newUnit, tile);
    }

    public void finishProducts(City city) throws CommandException {
        ArrayList<Production> productions = city.finishProductsAndReturnIt();
        Civilization cityCiv = GameController.getGame().getCivByName(city.getCivName());
        addProductsToCity(city, productions, cityCiv);
    }

    private void addProductsToCity(City city, ArrayList<Production> productions, Civilization cityCiv) throws CommandException {
        for (Production production:
                productions) {
            if(production instanceof Unit unit){
                cityCiv.getUnits().add(unit);
                this.spawnUnit(unit.getUnitType(), city.getCivName(), city.getLocation());
            } else if (production instanceof Building building) {
                city.getBuildings().add(building);
            }
        }
    }

    public void spawnUnit(UnitEnum unitEnum, String civName, Location location) throws CommandException {
        //todo how we can spawn ?
        Tile tile = GameController.getGameTile(location);
        if (!tile.getTerrain().getTerrainType().isReachable()) {
            throw new CommandException(CommandResponse.CANNOT_SPAWN_ON_TILE, tile.getTerrain().getTerrainType().name());
        }
        Unit newUnit = Unit.constructUnitFromEnum(unitEnum, civName, location);
        GameController.placeUnit(newUnit, tile);
    }

    public void increaseBeaker(int amount) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        currentCiv.setCheatBeaker(currentCiv.getCheatBeaker() + amount);
    }

    public void teleport(Location location, Unit unit) throws CommandException {
        UnitFuncs.validateTileForMovingUnit(location, unit);
        Tile currentTile = GameController.getGameTile(unit.getLocation());
        Tile destTile = GameController.getGameTile(location);
        currentTile.transferUnitTo(unit, destTile);
    }

    public void increaseProduction(City city, int amount) {
        city.setProductionFromCheat(city.getProductionFromCheat() + amount);
    }

    public void increaseHappiness(int amount) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        currentCiv.setHappinessFromCheat(currentCiv.getHappinessFromCheat() + amount);
    }

    public void revealTile(Location location) {
        GameController.getGame().revealTileFor(GameController.getGame().getCurrentCivilization(), GameController.getGameTile(location));
    }
    public void increaseMovement(Unit unit,int amount){
        unit.setAvailableMoveCount(unit.getAvailableMoveCount() + amount);
    }
    public void healCity(City city){
        city.setHealth(Constants.CITY_FULL_HEALTH);
    }

    public void healUnit(Unit unit) {
        unit.setHealth(Constants.UNIT_FULL_HEALTH);
    }

    public void unlockTechnologies(Civilization civ) {
        for (TechnologyEnum technology : TechnologyEnum.values()) {
            civ.addTechnology(technology);
        }
    }

    public void addBuilding(Building building, City city) {
        city.getBuildings().add(building);
        BuildingNotesLoader.getBuildingNotes().get(building.getType()).note(city);
    }
}
