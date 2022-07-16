package Project.Server.Controllers;

import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Server.Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;


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
        Unit newUnit = Unit.constructUnitFromEnum(unitEnum, civilization, location);
        civilization.addUnit(newUnit);
        tile.placeUnit(newUnit);
    }

    public void finishProducts(City city) throws CommandException {
        city.finishProducts();
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
        building.note(city);
    }
}