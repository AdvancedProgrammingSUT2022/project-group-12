package Models;

import Enums.GameEnums.UnitEnum;
import Exceptions.CommandException;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandResponse;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Civilization> civs;
    private final TileGrid tileGrid;
    private int gameTurn;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid();
        for (User user : users) {
            Civilization civ = new Civilization(user);
            civs.add(civ);
            Location settlerLocation = tileGrid.getRandomTileLocation();
            tileGrid.getTile(settlerLocation.getRow(), settlerLocation.getCol()).setNonCombatUnit(new NonCombatUnit(UnitEnum.SETTLER, civ));
            civ.setCurrentGridLocation(settlerLocation);
        }
    }

//    public void fullPlayersMove() {
//        for (int i = this.civs.size() - 1; i > -1; i--) {
//            this.civTurn.add(this.civs.get(i));
//        }
//    }

//    public ArrayList<Civilization> getCivTurn() {
//        return civTurn;
//    }

//    public void nextPlayerMove() {
//        this.civTurn.remove(this.civTurn.size() - 1);
//        if (this.civTurn.isEmpty()) {
//            fullPlayersMove();
//        }
//    }

    public Civilization getCurrentCivilization() {
        return civs.get(this.gameTurn % civs.size());
    }

    public void startNextTurn() {
        this.gameTurn++;
        if (this.gameTurn > 25) {
            //TODO : end game
        }
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }

    public ArrayList<Civilization> getCivs() {
        return civs;
    }

    public Unit getSelectedUnit(Civilization currentCivilization, Location location, boolean isCombatUnit) throws CommandException {
        Tile tile = this.tileGrid.getTile(location);
        Unit unit = isCombatUnit ? tile.getCombatUnit() : tile.getNonCombatUnit();
        if (unit == null || unit.getCiv() != currentCivilization) {
            throw new CommandException(CommandResponse.UNIT_DOES_NOT_EXISTS);
        } else {
            return unit;
        }
    }
}