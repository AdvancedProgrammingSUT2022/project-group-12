package Models;

import Controllers.GameController;
import Controllers.MovingController;
import Enums.TerrainColor;
import Enums.UnitEnum;
import Enums.UnitStates;
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
import java.util.Collections;
import java.util.List;

public class Game {
    private final ArrayList<Civilization> civs;
    private final TileGrid tileGrid;
    private int gameTurn;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid(Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        ArrayList<Tile> availableTiles = this.tileGrid.getFlatTiles();
        Collections.shuffle(availableTiles);
        List<TerrainColor> colors = List.of(TerrainColor.GREEN_BOLD, TerrainColor.RED_BOLD, TerrainColor.YELLOW_BOLD, TerrainColor.CYAN_BOLD, TerrainColor.WHITE_BOLD);
        for (int index = 0; index < users.size(); index++) {
            TerrainColor color = colors.get(index % colors.size());
            Civilization civ = new Civilization(users.get(index), color);
            civs.add(civ);
            // for easier testing
//            Location settlerLocation = new Location(10, 10);

            Tile settlerTile = availableTiles.get(availableTiles.size() - 1);
            for (Tile tile : this.tileGrid.getAllTilesInRadius(settlerTile, Constants.INITIAL_SETTLERS_DISTANCE)) {
                availableTiles.remove(tile);
            }

            NonCombatUnit settler = new NonCombatUnit(UnitEnum.SETTLER, civ, settlerTile.getLocation());
            settlerTile.setUnit(settler);
            updateRevealedTileGrid(civ);
            civ.setCurrentSelectedGridLocation(settlerTile.getLocation());
        }
    }

    public Civilization getCurrentCivilization() {
        return civs.get(this.gameTurn % civs.size());
    }

    public void endCurrentTurn() throws GameException {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        updateRevealedTileGrid(civ);
        //todo : complete
        for (Unit unit : civ.getUnits()) {
            switch (unit.getState()) {
                case ALERT -> {
                    checkForAlertUnit(unit, tileGrid.getTile(unit.getLocation()));
                }
                case AWAKED -> {
                    checkForMultipleMoves(unit);
                    checkForMovementCost(unit);
                }
                case FORTIFY_UNTIL_HEAL -> {
                    checkForFortifyHealUnit(unit, tileGrid.getTile(unit.getLocation()));
                }
                default -> {
                }
            }
        }
        for (City city : civ.getCities()) {
            if (city.getCitizens().size() < city.getCitizensCount()) {
                throw new GameException(CommandResponse.UNASSIGNED_CITIZEN, city.getName());
            } else if (city.getProductionQueue().isEmpty()) {
                throw new GameException(CommandResponse.EMPTY_PRODUCTION_QUEUE, city.getName());
            }
        }
    }

    private void checkForMultipleMoves(Unit unit) {
        if (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross().size() > 0) {
            MovingController.moveToNextTile(unit);
        }
    }

    private void checkForFortifyHealUnit(Unit unit, Tile tile) throws GameException {
        if (unit.getHealthBar() == 100) {
            checkForMovementCost(unit);
        }
    }

    private void checkForMovementCost(Unit unit) throws GameException {
        if (unit.getAvailableMoveCount() > 0) {
            throw new GameException(CommandResponse.UNIT_NEED_ORDER);
        }
    }

    private void checkForAlertUnit(Unit unit, Tile unitTile) throws GameException {
        ArrayList<Tile> tiles = tileGrid.getAllTilesInRadius(unitTile, 2);
        Civilization unitCiv = unit.getCivilization();
        if (checkForEnemy(tiles, unitCiv)) {
            unit.setState(UnitStates.AWAKED);
            checkForMovementCost(unit);
        }
    }

    private boolean checkForEnemy(ArrayList<Tile> tiles, Civilization unitCiv) {
        for (Tile tile : tiles) {
            if (GameController.isEnemyExists(tile.getLocation(), unitCiv) || GameController.isNonCombatEnemyExists(tile.getLocation(), unitCiv)) {
                return true;
            }
        }
        return false;
    }

    public void startNextTurn() {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        civ.applyNotes();
        this.gameTurn++;
        updateRevealedTileGrid(civ);
        for (City city : civ.getCities()) {
            city.advanceProductionQueue();
        }
        civ.resetMoveCount();
        if (this.gameTurn / civs.size() > 25) {
            //TODO: end game
        }
    }

    public void updateRevealedTileGrid(Civilization civilization) {
        tileGrid.setFogOfWarForAll();
        for (Unit unit : civilization.getUnits()) {
            Tile tile = tileGrid.getTile(unit.getLocation());
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, Constants.UNIT_VISION_RADIUS)) {
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
        if (unit == null || unit.getCivilization() != currentCivilization) {
            throw new CommandException(CommandResponse.UNIT_DOES_NOT_EXISTS);
        } else {
            return unit;
        }
    }
}