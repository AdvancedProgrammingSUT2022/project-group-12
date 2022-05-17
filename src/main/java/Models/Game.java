package Models;

import Controllers.GameController;
import Controllers.MovingController;
import Enums.TerrainColor;
import Enums.UnitEnum;
import Enums.UnitStates;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
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
    private final ArrayList<Civilization> civilizations;
    private final TileGrid tileGrid;
    private int gameTurn = -1;

    public Game(ArrayList<User> users) {
        this.civilizations = new ArrayList<>();
        this.tileGrid = TileGrid.generateRandomTileGrid(Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);

        ArrayList<Tile> availableTiles = new ArrayList<>();
        for (Tile tile : this.tileGrid.getFlatTiles()) {
            if (tile.getTerrain().getTerrainType().isReachable()) {
                availableTiles.add(tile);
            }
        }
        Collections.shuffle(availableTiles);

        List<TerrainColor> colors = List.of(TerrainColor.GREEN_BOLD, TerrainColor.RED_BOLD, TerrainColor.YELLOW_BOLD, TerrainColor.CYAN_BOLD, TerrainColor.WHITE_BOLD);
        for (int index = 0; index < users.size(); index++) {
            TerrainColor color = colors.get(index % colors.size());
            Civilization civ = new Civilization(users.get(index), color);
            civilizations.add(civ);

            // for easier testing
            Tile settlerTile;
            if (users.size() == 1) settlerTile = this.getTileGrid().getTile(new Location(10, 10));
            else settlerTile = availableTiles.get(availableTiles.size() - 1);

            for (Tile tile : this.tileGrid.getAllTilesInRadius(settlerTile, Constants.INITIAL_SETTLERS_DISTANCE)) {
                availableTiles.remove(tile);
            }

            NonCombatUnit settler = new NonCombatUnit(UnitEnum.SETTLER, civ, settlerTile.getLocation());
            try {
                settlerTile.placeUnit(settler);
            } catch (CommandException e) { // never
                throw new RuntimeException(e);
            }
            updateRevealedTileGrid(civ);
            civ.setCurrentSelectedGridLocation(settlerTile.getLocation());
        }
    }

    public void updateRevealedTileGrid(Civilization civilization) {
        tileGrid.setFogOfWarForAll();
        for (Unit unit : civilization.getUnits()) {
            Tile tile = tileGrid.getTile(unit.getLocation());
            int visibilityRange = unit.getType().hasLimitedVisibility() ? Constants.UNIT_LIMITED_VISION_RADIUS : Constants.UNIT_VISION_RADIUS;
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, visibilityRange)) {
                revealTileFor(civilization, neighbor);
            }
        }
        for (City city : civilization.getCities()) {
            Tile tile = tileGrid.getTile(city.getLocation());
            for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, Constants.UNIT_VISION_RADIUS)) {
                revealTileFor(civilization, neighbor);
            }
        }
    }

    public void revealTileFor(Civilization civilization, Tile tile) {
        TileGrid civTileGrid = civilization.getRevealedTileGrid();
        civTileGrid.setVisible(tile.getLocation());
        civTileGrid.setTile(tile.getLocation(), tile.deepCopy());
    }

    public void endCurrentTurn() throws GameException, CommandException {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        updateRevealedTileGrid(civ);

        for (Unit unit : civ.getUnits()) {
            if (unit.getState() == UnitStates.AWAKE) {
                checkForMultipleMoves(unit);
                checkForMovementCost(unit);
            }
        }
        for (City city : civ.getCities()) {
            if (city.getCitizens().size() < city.getCitizensCount()) {
                throw new GameException(CommandResponse.UNASSIGNED_CITIZEN, city.getName());
            } else if (city.getProductionQueue().isEmpty()) {
                throw new GameException(CommandResponse.EMPTY_PRODUCTION_QUEUE, city.getName());
            }
        }
        if (civ.getResearchingTechnology() == null) {
            throw new GameException(CommandResponse.NO_RESEARCHING_TECHNOLOGY);
        }
        checkForKillingCiziten(civ);
        /***
         * add gold
         */
        civ.addGold(civ.calculateCivilizationGold());

    }


    private void checkForKillingCiziten(Civilization civ) {
        for (City city :
                civ.getCities()) {
            if (city.calculateFood() < 0) {
                city.killCitizen();
            }
        }
    }

    public Civilization getCurrentCivilization() {
        return civilizations.get(this.gameTurn % civilizations.size());
    }

    public void startNewTurn() throws GameException {
        this.gameTurn++;
        Civilization civ = this.getCurrentCivilization();
        civ.applyNotes();
        updateRevealedTileGrid(civ);
        civ.advanceResearchTech();
        for (City city : civ.getCities()) {
            city.checkCitizenBirth();
            city.advanceProductionQueue();
        }
        for (Unit unit : civ.getUnits()) {
            checkForAlertUnit(unit);
            checkForFortifyHealUnit(unit);
            System.out.println("unit.getType().name() = " + unit.getType().name());
            System.out.println("unit.getLocation() = " + unit.getLocation());
        }
        civ.resetMoveCount();
        /***
         * set has attack false for units which they can move after attack
         */
        civ.getUnits().forEach((unit) -> {
            if (unit instanceof CombatUnit) ((CombatUnit) unit).setHasAttack(false);
        });
        if (this.gameTurn / civilizations.size() > 25) {
            throw new GameException(CommandResponse.END_OF_GAME);
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

    private void checkForMovementCost(Unit unit) throws GameException {
        if (unit.getAvailableMoveCount() > 0) {
            throw new GameException(CommandResponse.UNIT_NEED_ORDER);
        }
    }

    private void checkForMultipleMoves(Unit unit) throws CommandException {
        System.out.println("reached here");
        System.out.println("unit.getPathShouldCross().size() = " + unit.getPathShouldCross().size());
        if (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross().size() > 0) {
            MovingController.moveToNextTile(unit);
        }
    }

    private void checkForAlertUnit(Unit unit) {
        Tile unitTile = GameController.getGameTile(unit.getLocation());
        ArrayList<Tile> tiles = tileGrid.getAllTilesInRadius(unitTile, 2);
        Civilization unitCiv = unit.getCivilization();
        if (checkForEnemy(tiles, unitCiv)) {
            unit.setState(UnitStates.AWAKE);
        }
    }

    private void setPlayersScores() {
        for (Civilization civilization : this.civilizations) {
            civilization.civUser().setScore(civilization.calculateSuccess() / 100);
        }
    }

    private void checkForFortifyHealUnit(Unit unit) {
        if (unit.getHealth() == Constants.UNIT_FULL_HEALTH) {
            unit.setState(UnitStates.AWAKE);
        }
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
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

    public int getGameTurnNumber() {
        return this.gameTurn;
    }
}