package Controllers;

import Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Enums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;


public class CheatCodeController {
    private static CheatCodeController instance;

    public static CheatCodeController getInstance() {
        if (instance == null)
            instance = new CheatCodeController();
        return instance;
    }

    public void increaseGold(int amount) {
        GameController.getGame().getCurrentCivilization().addGold(amount);
    }

    public void increaseFood(City city, int amount) {
        city.setFoodFromCheat(city.getFoodFromCheat() + amount);
    }

    public void spawnUnit(UnitEnum unit, Location location) {
        Unit newUnit;
        if (unit.isACombatUnit())
            newUnit = new CombatUnit(unit, GameController.getGame().getCurrentCivilization(), location);
        else
            newUnit = new NonCombatUnit(unit, GameController.getGame().getCurrentCivilization(), location);
        GameController.getGame().getCurrentCivilization().addUnit(newUnit);
        if (newUnit instanceof CombatUnit) {
            if (GameController.getGame().getTileGrid().getTile(location).getCombatUnit() != null)
                GameController.getGame().getTileGrid().getTile(location).setUnit(newUnit);
        } else {
            if (GameController.getGame().getTileGrid().getTile(location).getNonCombatUnit() != null)
                GameController.getGame().getTileGrid().getTile(location).setUnit(newUnit);
        }
    }

    public void finishProducts(City city) throws CommandException {
        city.finishProducts();
    }
    public void increaseBeaker(int amount){
        Civilization currnetCiv =  GameController.getGame().getCurrentCivilization();
        currnetCiv.setCheatBeaker(currnetCiv.getCheatBeaker() + amount);
    }
    public void teleport(Location location,Unit unit) throws CommandException {
        UnitFuncs.validateTileForMovingUnit(location, unit);
        Tile currentTile = GameController.getGame().getTileGrid().getTile(unit.getLocation());
        Tile destTile = GameController.getGame().getTileGrid().getTile(location);
        currentTile.setNullUnit(unit);
        destTile.setUnit(unit);
    }

    public void increaseProduction(City city, int amount) {
        city.setProductionFromCheat(city.getProductionFromCheat() + amount);
    }

    public void increaseHappiness(int amount) {
        Civilization currnetCiv =  GameController.getGame().getCurrentCivilization();
        currnetCiv.setHappinessFromCheat(currnetCiv.getHappinessFromCheat() + amount);
    }

    public void revealTile(Location location) {
        GameController.getGame().revealTileFor(GameController.getGame().getCurrentCivilization(), GameController.getGame().getTileGrid().getTile(location));
    }
}
