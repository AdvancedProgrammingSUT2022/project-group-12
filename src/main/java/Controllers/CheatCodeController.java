package Controllers;

import Enums.UnitEnum;
import Models.Cities.City;
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
        city.setFood(city.getFood() + amount);
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

    public void increaseProduction(City city, int amount) {
        city.setProduction(city.getProduction() + amount);
    }

    public void increaseHappiness(int amount) {
        GameController.getGame().getCurrentCivilization().setHappiness(amount);
    }

    public void revealTile(Location location) {
        GameController.getGame().getCurrentCivilization().getRevealedTileGrid().setVisible(location);
    }
}
