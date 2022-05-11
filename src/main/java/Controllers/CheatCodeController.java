package Controllers;

import Enums.UnitEnum;
import Models.Cities.City;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;


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

    public void increaseFood(int amount, String name) throws CommandException {
        City city = GameController.getGame().getCurrentCivilization().getCityByName(name);
        city.setFood(city.getFood() + amount);
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

    public void finishProducts(City city) {
        city.finishProducts();
    }

    public void increaseProduction(int amount, String name) throws CommandException {
        City city = GameController.getGame().getCurrentCivilization().getCityByName(name);
        city.setProduction(city.getProduction() + amount);
    }

    public void increaseHappiness(int amount) {
        GameController.getGame().getCurrentCivilization().setHappiness(amount);
    }

    public void revealTile(Location location) {
        GameController.getGame().getCurrentCivilization().getRevealedTileGrid().setVisible(location);
    }
}
