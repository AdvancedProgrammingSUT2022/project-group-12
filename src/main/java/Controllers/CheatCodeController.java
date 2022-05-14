package Controllers;

import Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Enums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.Unit;
import Utils.CommandException;


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
        Unit newUnit = Unit.constructUnitFromEnum(unitEnum, civilization, location);
        tile.placeUnit(newUnit);
    }

    public void finishProducts(City city) throws CommandException {
        city.finishProducts();
    }

    public void increaseBeaker(int amount) {
        Civilization currnetCiv = GameController.getGame().getCurrentCivilization();
        currnetCiv.setCheatBeaker(currnetCiv.getCheatBeaker() + amount);
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
        Civilization currnetCiv = GameController.getGame().getCurrentCivilization();
        currnetCiv.setHappinessFromCheat(currnetCiv.getHappinessFromCheat() + amount);
    }

    public void revealTile(Location location) {
        GameController.getGame().revealTileFor(GameController.getGame().getCurrentCivilization(), GameController.getGameTile(location));
    }
}
