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
import Utils.CommandResponse;


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

    public void spawnUnit(UnitEnum unit, Location location) throws CommandException {
        Unit newUnit;
        if (unit.isACombatUnit())
            newUnit = new CombatUnit(unit, GameController.getGame().getCurrentCivilization(), location);
        else
            newUnit = new NonCombatUnit(unit, GameController.getGame().getCurrentCivilization(), location);
        GameController.getGame().getCurrentCivilization().addUnit(newUnit);
        Tile tile = GameController.getGame().getTileGrid().getTile(location);
        if (newUnit instanceof CombatUnit) {
            if (tile.getCombatUnit() == null)
                tile.setUnit(newUnit);
            else throw new CommandException(CommandResponse.COMBAT_UNIT_ALREADY_ON_TILE, tile.getCombatUnit().getType().name());
        } else {
            if (tile.getNonCombatUnit() == null)
                tile.setUnit(newUnit);
            else throw new CommandException(CommandResponse.NONCOMBAT_UNIT_ALREADY_ON_TILE, tile.getNonCombatUnit().getType().name());
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
