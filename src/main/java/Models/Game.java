package Models;

import Controllers.GameController;
import Enums.UnitEnum;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;
import Utils.GameException;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Civilization> civs;
    private final TileGrid tileGrid;
    private int gameTurn;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid(Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        for (User user : users) {
            Civilization civ = new Civilization(user);
            civs.add(civ);
            // for easier testing
            Location settlerLocation = new Location(10, 10); // this.tileGrid.getRandomTileLocation();
            NonCombatUnit settler = new NonCombatUnit(UnitEnum.SETTLER, civ, settlerLocation);
            this.tileGrid.getTile(settlerLocation.getRow(), settlerLocation.getCol()).setNonCombatUnit(settler);
            updateRevealedTileGrid(civ);
            civ.setCurrentSelectedGridLocation(settlerLocation);
        }
    }

    public Civilization getCurrentCivilization() {
        return civs.get(this.gameTurn % civs.size());
    }

    public void endCurrentTurn() throws GameException {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        updateRevealedTileGrid(civ);
        for (City city : civ.getCities()) {
            if (city.getCitizens().size() < city.getCitizensCount()) {
                throw new GameException(CommandResponse.UNASSIGNED_CITIZEN, city.getName());
            }
        }
    }

    public void startNextTurn() {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        civ.applyNotes();
        this.gameTurn++;
        updateRevealedTileGrid(civ);
        for (City city : civ.getCities()) {
            city.advanceProductionQueue();
        }
        if (this.gameTurn / civs.size() > 25) {
            //TODO: end game
        }
    }

    public void updateRevealedTileGrid(Civilization civilization) {
        tileGrid.setFogOfWarForAll();
        for (Unit unit : civilization.getUnits()) {
            Tile tile = tileGrid.getTile(unit.getLocation());
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, 1)) {
                TileGrid civTileGrid = civilization.getRevealedTileGrid();
                civTileGrid.setVisible(neighbor.getLocation());
                civTileGrid.setTile(neighbor.getLocation(), neighbor.deepCopy());
            }
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