package Controllers;

import Enums.TerrainEnum;
import Enums.UnitEnum;
import Enums.VisibilityEnum;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static Controllers.CombatController.AttackUnit;

public class MovingController extends GameController {

    public MovingController(Game newGame) {
        super(newGame);
    }

    public static String moveUnit(int x, int y, Tile currentTile, Civilization currentCivilization, Unit unit) {
        String response = "unit moved successfully";
        ArrayList<Tile> shortestPath = findTheShortestPath(x, y, currentTile, unit);
        if (shortestPath == null) {
            return "move is impossible";
        }
        unit.setPathShouldCross(shortestPath);
        response = moveToNextTile(unit, response);
        return response;
    }

    private static String moveToNextTile(Unit unit, String response) {
        while (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross().size() != 0) {
            TileGrid gameTileGrid = game.getTileGrid();
            int nextRow = unit.getPathShouldCross().get(0).getRow(), nextCol = unit.getPathShouldCross().get(0).getCol();
            calculateMoveMentCost(unit, nextRow, nextCol);
            if (checkForEnemy(nextRow, nextCol, unit, response)) break;
            gameTileGrid.getTile(unit.getRow(), unit.getColumn()).setUnit(unit, null);
            unit.setRow(nextRow);
            unit.setColumn(nextCol);
            checkForFogOfWars(game.getTileGrid(), game.getTileGrid().getTile(nextRow, nextCol));
            gameTileGrid.getTile(unit.getRow(), unit.getColumn()).setUnit(unit, unit);
            unit.getPathShouldCross().remove(0);
        }
        return response;
    }

    private static boolean checkForEnemy(int nextRow, int nextCol, Unit unit, String response) {
        Tile currentTile = game.getTileGrid().getTile(nextRow, nextCol);
        if (isEnemyExists(nextRow, nextCol, unit.getCiv())) {
            response = AttackUnit(nextRow, nextCol, game, currentTile, unit.getCiv());
            unit.setAvailableMoveCount(0);
            unit.setPathShouldCross(null);
            return true;
        }
        if (isNonCombatEnemyExists(nextRow, nextCol, unit.getCiv())) {
            CaptureTheNonCombatUnit(game.getTileGrid().getTile(nextRow, nextCol), unit.getCiv());
            return false;
        }
        return false;
    }

    private static void CaptureTheNonCombatUnit(Tile tile, Civilization civ) {
        NonCombatUnit capturedUnit = tile.getNonCombatUnit();
        if (capturedUnit.getType() == UnitEnum.WORKER || capturedUnit.getType() == UnitEnum.SETTLER) {
            /***
             * non combat unit has captured
             */
            capturedUnit.setType(UnitEnum.WORKER);
            capturedUnit.setCiv(civ);
        } else {
            /***
             * nonCombat has killed
             */
            tile.setNonCombatUnit(null);
        }
    }

    private static void calculateMoveMentCost(Unit unit, int row, int col) {
        if (checkForZOC(unit.getRow(), unit.getColumn(), row, col, unit)) {
            unit.setAvailableMoveCount(0);
            return;
        }
        if (checkForRivers(game.getTileGrid().getTile(row, col), game.getTileGrid().getTile(unit.getRow(), unit.getColumn()))) {
            unit.setAvailableMoveCount(0);
            return;
        }
        unit.setAvailableMoveCount(unit.getAvailableMoveCount() - game.getTileGrid().getTile(unit.getRow(), unit.getColumn()).getTerrain().getMovementCost());
    }

    private static boolean checkForRivers(Tile tile, Tile tile1) {
        //TODO : check if is there a river or not
        return tile.getTerrain().getFeatures().contains(TerrainEnum.RIVER) && tile1.getTerrain().getFeatures().contains(TerrainEnum.RIVER);
    }

    private static boolean checkForZOC(int row, int col, int row1, int col1, Unit unit) {
        return checkForZOCOnTile(game.getTileGrid().getTile(row, col), unit) && checkForZOCOnTile(game.getTileGrid().getTile(row1, col1), unit);
    }

    private static boolean checkForZOCOnTile(Tile tile, Unit unit) {
        ArrayList<Tile> tiles = game.getTileGrid().getNeighborsOf(tile);
        Civilization unitCiv = unit.getCiv();
        for (Tile tempTile : tiles) {
            if (tempTile.getCombatUnit() != null && tempTile.getCombatUnit().getCiv() != unitCiv) {
                return true;
            }
        }
        return false;
    }

    private static void checkForFogOfWars(TileGrid tileGrid, Tile tile) {
        ArrayList<Tile> tiles = tileGrid.getNeighborsOf(tile);
        tiles.forEach(temp_tile -> {
            if (temp_tile.getState() == VisibilityEnum.FOG_OF_WAR) temp_tile.setState(VisibilityEnum.VISIBLE);
        });
    }

    protected static ArrayList<Tile> findTheShortestPath(int targetRow, int targetCol, Tile sourceTile, Unit sourceUnit) { // use Coord/Location
        // Dijkstra algorithm for shortest path
        TileGrid tileGrid = game.getTileGrid();
        HashMap<Tile, Tile> parent = new HashMap<>();
        HashMap<Tile, Integer> distance = new HashMap<>();
        TreeMap<Integer, Tile> heap = new TreeMap<>();
        distance.put(sourceTile, 0);
        heap.put(0, sourceTile);
        Tile p;
        while (true) {
            Tile first = heap.pollFirstEntry().getValue();
            if (first.getRow() == targetRow && first.getCol() == targetCol) {
                p = first;
                break;
            }
            for (Tile neighbor : tileGrid.getNeighborsOf(first)) {
                if (validateTile(neighbor, sourceUnit)) continue;
                // this is only true if weights are on tiles (graph vertexes)
                if (!distance.containsKey(neighbor)) {
                    int dist = distance.get(first) + neighbor.getTerrain().getMovementCost();
                    distance.put(neighbor, dist);
                    heap.put(dist, neighbor);
                    parent.put(neighbor, first);
                }
            }
        }
        ArrayList<Tile> path = new ArrayList<>();
        while (p != sourceTile) {
            path.add(p);
            p = parent.get(p);
        }
        return path;
    }

    private static boolean validateTile(Tile tile, Unit sourceUnit) {
        CombatUnit unknownCombatUnit = tile.getCombatUnit();
        NonCombatUnit unknownNonCombatUnit = tile.getNonCombatUnit();
        if (sourceUnit instanceof CombatUnit) {
            return unknownCombatUnit == null;
        } else {
            return !isEnemyExists(unknownNonCombatUnit.getRow(), unknownNonCombatUnit.getColumn(), sourceUnit.getCiv()) && !isNonCombatEnemyExists(unknownNonCombatUnit.getRow(), unknownNonCombatUnit.getColumn(), sourceUnit.getCiv());
        }
    }


}
