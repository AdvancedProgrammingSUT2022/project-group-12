package Controllers;

import Enums.UnitEnum;
import Enums.VisibilityEnum;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MovingController {

    public static void moveUnit(Location location, Tile currentTile, Civilization currentCivilization, Unit unit) throws CommandException {
        // todo: what if not reachable??
        ArrayList<Tile> shortestPath = findTheShortestPath(location, currentTile);
        if (shortestPath == null) {
            throw new CommandException("move is impossible");
        }
        unit.setPathShouldCross(shortestPath);
        moveToNextTile(unit);
    }

    public static void moveToNextTile(Unit unit) {
        while (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross().size() != 0) {
            TileGrid tileGrid = GameController.getGame().getTileGrid();
            Location location = unit.getPathShouldCross().get(0).getLocation();
            calculateMovementCost(unit, location);
            if (unit.getPathShouldCross().size() == 1 && checkForEnemy(location, unit)) break;
            tileGrid.getTile(unit.getLocation()).transferUnitTo(unit, tileGrid.getTile(location));
            GameController.getGame().updateRevealedTileGrid(unit.getCivilization());
            unit.getCivilization().setCurrentSelectedGridLocation(location);
            unit.getPathShouldCross().remove(0);
        }
    }

    private static boolean checkForEnemy(Location nextLocation, Unit unit) {
        Tile currentTile = GameController.getGameTile(nextLocation);
        if (GameController.isEnemyExists(nextLocation, unit.getCivilization())) {
            unit.setAvailableMoveCount(0);
            unit.setPathShouldCross(null);
            return true;
        }
        if (GameController.isNonCombatEnemyExists(nextLocation, unit.getCivilization())) {
            CaptureTheNonCombatUnit(GameController.getGameTile(nextLocation), unit.getCivilization());
            return false;
        }
        return false;
    }

    private static void CaptureTheNonCombatUnit(Tile tile, Civilization civ) {
        NonCombatUnit capturedUnit = tile.getNonCombatUnit();
        if (capturedUnit.getType() == UnitEnum.WORKER || capturedUnit.getType() == UnitEnum.SETTLER) {
            /*
             non combat unit has captured
             */
            capturedUnit.setType(UnitEnum.WORKER);
            capturedUnit.setCiv(civ);
        } else {
            /*
             * nonCombat has killed
             */
            GameController.deleteUnit(tile.getNonCombatUnit());
        }
    }

    private static void calculateMovementCost(Unit unit, Location location) {
        if (unit.getType() == UnitEnum.SCOUT) {
            unit.setAvailableMoveCount(unit.getAvailableMoveCount() - 1);
            return;
        }
        if (checkForZOC(unit.getLocation(), location, unit)) {
            unit.setAvailableMoveCount(0);
            return;
        }
        if (GameController.checkForRivers(GameController.getGameTile(location), GameController.getGameTile(location))) {
            unit.setAvailableMoveCount(0);
            return;
        }
        unit.setAvailableMoveCount(unit.getAvailableMoveCount() - GameController.getGameTile(location).getTerrain().getMovementCost());
    }


    private static boolean checkForZOC(Location location, Location location1, Unit unit) {
        return checkForZOCOnTile(GameController.getGameTile(location), unit) && checkForZOCOnTile(GameController.getGameTile(location1), unit);
    }

    private static boolean checkForZOCOnTile(Tile tile, Unit unit) {
        ArrayList<Tile> tiles = GameController.getGame().getTileGrid().getNeighborsOf(tile);
        Civilization unitCiv = unit.getCivilization();
        for (Tile tempTile : tiles) {
            if (tempTile.getCombatUnit() != null && tempTile.getCombatUnit().getCivilization() != unitCiv) {
                return true;
            }
        }
        return false;
    }

    // todo: move bug found
    protected static ArrayList<Tile> findTheShortestPath(Location location, Tile sourceTile) {
        // Dijkstra algorithm for shortest path
        int targetRow = location.getRow();
        int targetCol = location.getCol();
        TileGrid tileGrid = GameController.getGame().getTileGrid();
        ArrayList<Tile> parent = new ArrayList<>();
        HashMap<Tile, Integer> distance = new HashMap<>();
        ArrayList<Tile> heap = new ArrayList<>(List.of(sourceTile));
        HashMap<Tile, ArrayList<Tile>> shortestPath = new HashMap<>();
        distance.put(sourceTile, 0);
        shortestPath.put(sourceTile, new ArrayList<>(List.of(sourceTile)));
        Tile p;
        Tile first;

        while (true) {
            heap.sort(Comparator.comparing(distance::get));
            first = heap.get(0);
            heap.remove(0);
            if (first.getRow() == targetRow && first.getCol() == targetCol) {
                p = first;
                break;
            }
            for (Tile neighbor : tileGrid.getNeighborsOf(first)) {
                if (validateTile(neighbor)) continue;
                // this is only true if weights are on tiles (graph vertexes)
                int dist = distance.get(first) + neighbor.getTerrain().getMovementCost();
                if (!distance.containsKey(neighbor)) {
                    distance.put(neighbor, dist);
                    heap.add(neighbor);
                    shortestPath.put(neighbor, (ArrayList<Tile>) shortestPath.get(first).clone());
                    shortestPath.get(neighbor).add(neighbor);
                } else if (!parent.contains(neighbor) && dist <= distance.get(neighbor)) {
                    heap.remove(neighbor);
                    distance.put(neighbor, dist);
                    heap.add(neighbor);
                    shortestPath.put(neighbor, (ArrayList<Tile>) shortestPath.get(first).clone());
                    shortestPath.get(neighbor).add(neighbor);
                }
            }
            parent.add(first);
        }
        shortestPath.get(p).remove(0);
        return shortestPath.get(p);
    }

    private static boolean validateTile(Tile tile) {
        return !tile.getTerrain().getTerrainType().canBePassed() || tile.getState() == VisibilityEnum.FOG_OF_WAR;
    }
}
