package Controllers;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import javafx.util.Pair;

import java.util.*;

public class MovingController {

    public static void moveUnit(Location location, Tile currentTile, Unit unit) throws CommandException {
        ArrayList<Tile> shortestPath = findTheShortestPath(location, currentTile);
        System.out.println("shortestPath = ");
        for (Tile tile : shortestPath) System.out.print(tile.getLocation() + " ");
        System.out.println();
        unit.setPathShouldCross(shortestPath);
        moveToNextTile(unit);
    }

    public static int findPathLength(ArrayList<Tile> shortestPath) {
        int pathLength = 0;
        for (Tile tile : shortestPath) {
            pathLength += tile.calculateMovementCost();
        }
        return pathLength;
    }

    public static void moveToNextTile(Unit unit) throws CommandException {
        while (unit.getAvailableMoveCount() > 0 && unit.getPathShouldCross().size() != 0) {
            TileGrid tileGrid = GameController.getGame().getTileGrid();
            Location nextLocation = unit.getPathShouldCross().get(0).getLocation();
            calculateMovementCost(unit, nextLocation);
            assertLastMove(nextLocation, unit);
            if (unit.getPathShouldCross().size() == 1 && checkForEnemy(nextLocation, unit)) break;
            tileGrid.getTile(unit.getLocation()).transferUnitTo(unit, tileGrid.getTile(nextLocation));
            GameController.getGame().updateRevealedTileGrid(unit.getCivilization());
            unit.getCivilization().setCurrentSelectedGridLocation(nextLocation);
            unit.getPathShouldCross().remove(0);
        }
    }

    private static boolean checkForEnemy(Location nextLocation, Unit unit) {
        Tile currentTile = GameController.getGameTile(nextLocation);
        if(GameController.isEnemyCityExists(nextLocation,unit.getCivilization())){
            unit.setAvailableMoveCount(0);
            unit.setPathShouldCross(null);
            return true;
        }
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

    // todo: refactor
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
        int movementCost = GameController.getGameTile(location).getTerrain().getMovementCost();
        unit.setAvailableMoveCount(unit.getAvailableMoveCount() - movementCost);
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

    private static void assertLastMove(Location location, Unit unit) throws CommandException {
        Tile destTile = GameController.getGameTile(location);
        if (unit instanceof CombatUnit && destTile.getCombatUnit() != null) {
            location = findNewTile(destTile, "combatunit", unit);
            unit.setPathShouldCross(findTheShortestPath(location, unit.getPathShouldCross().get(unit.getPathShouldCross().size() - 1)));
        } else if (unit instanceof NonCombatUnit && destTile.getNonCombatUnit() != null) {
            location = findNewTile(destTile, "noncombatunit", unit);
            unit.setPathShouldCross(findTheShortestPath(location, unit.getPathShouldCross().get(unit.getPathShouldCross().size() - 1)));
        }
    }

    private static Location findNewTile(Tile destTile, String combatType, Unit unit) {
        ArrayList<Tile> neighbors = GameController.getGame().getTileGrid().getAllTilesInRadius(destTile, 1);
        for (Tile tile : neighbors) {
            if (combatType.equals("combatunit") && tile.getCombatUnit() == null) {
                return tile.getLocation();
            } else if (combatType.equals("noncombatunit") && tile.getNonCombatUnit() == null) {
                return tile.getLocation();
            }
        }
        return unit.getLocation();
    }


    protected static ArrayList<Tile> findTheShortestPath(Location target, Tile sourceTile) throws CommandException {
        // Dijkstra algorithm for shortest path
        TileGrid tileGrid = GameController.getGame().getTileGrid();
        HashMap<Tile, Tile> parent = new HashMap<>();
        HashMap<Tile, Integer> distance = new HashMap<>();
        Comparator<Pair<Integer, Tile>> comparator = (a, b) -> {
            Integer r1 = a.getValue().getLocation().getRow(), c1 = a.getValue().getLocation().getCol();
            Integer r2 = b.getValue().getLocation().getRow(), c2 = b.getValue().getLocation().getCol();
            int comp1 = a.getKey().compareTo(b.getKey());
            int comp2 = r1.compareTo(r2);
            int comp3 = c1.compareTo(c2);
            return comp1 != 0 ? comp1 : comp2 != 0 ? comp2 : comp3;
        };
        TreeSet<Pair<Integer, Tile>> heap = new TreeSet<>(comparator);
        distance.put(sourceTile, 0);
        heap.add(new Pair<>(0, sourceTile));
        Tile p = null;
        while (!heap.isEmpty()) {
            Tile first = heap.pollFirst().getValue();
            if (first.getLocation().equals(target)) {
                p = first;
                break;
            }
            for (Tile neighbor : tileGrid.getNeighborsOf(first)) {
                if (!neighbor.getTerrain().getTerrainType().isReachable()) continue;
                // this is true because weights are on tiles (on graph vertexes)
                if (!distance.containsKey(neighbor)) {
                    int dist = (int) (distance.get(first) + neighbor.calculateMovementCost());
                    distance.put(neighbor, dist);
                    heap.add(new Pair<>(dist, neighbor));
                    parent.put(neighbor, first);
                }
            }
        }
        if (p == null) {
            throw new CommandException(CommandResponse.TARGET_NOT_REACHABLE);
        }
        ArrayList<Tile> path = new ArrayList<>();
        while (p != sourceTile) {
            path.add(p);
            p = parent.get(p);
        }
        Collections.reverse(path);
        return path;
    }
}
