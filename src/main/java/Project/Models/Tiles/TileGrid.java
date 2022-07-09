package Project.Models.Tiles;

import Project.Enums.TerrainEnum;
import Project.Enums.VisibilityEnum;
import Project.Models.Location;
import Project.Models.Terrains.Terrain;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TileGrid {
    private final int height;
    private final int width;
    private final Tile[][] tiles;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
        this.tiles = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Terrain terrain = new Terrain(TerrainEnum.UNKNOWN);
                tiles[i][j] = new Tile(terrain, new Location(i, j), getTileColor(terrain));
            }
        }
    }

    public static TileGrid generateRandomTileGrid(int height, int width) {
        TileGrid tileGrid = new TileGrid(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Terrain terrain = generateRandomTerrain();
                tileGrid.tiles[i][j] = new Tile(terrain, new Location(i, j), getTileColor(terrain));
            }
        }
        return tileGrid;
    }

    private static String getTileColor(Terrain terrainType) {
        return switch (terrainType.getColor().name()) {
            case "GREEN_BACKGROUND" -> "GREEN";
            case "BLUE_BACKGROUND" -> "DARKBLUE";
            case "GRAY_BACKGROUND" -> "DARKGRAY";
            case "BROWN_BACKGROUND" -> "SANDYBROWN";
            case "DARKRED_BACKGROUND" -> "DARKRED";
            case "DARKGREEN_BACKGROUND" -> "DARKGREEN";
            case "LIGHTGREEN_BACKGROUND" -> "LIGHTGREEN";
            case "DARKBROWN_BACKGROUND" -> "BROWN";
            case "WHITE_BACKGROUND" -> "WHITE";
            default -> "CORAL";
        };
    }

    private static Terrain generateRandomTerrain() {
        ArrayList<TerrainEnum> values = new ArrayList<>();
        values.add(TerrainEnum.DESERT);
        values.add(TerrainEnum.HILL);
        values.add(TerrainEnum.MOUNTAIN);
        values.add(TerrainEnum.GRASSLAND);
        values.add(TerrainEnum.PLAIN);
        values.add(TerrainEnum.SNOW);
        values.add(TerrainEnum.TUNDRA);
        return new Terrain(values.get(new Random().nextInt(values.size())));
    }

    public void setVisible(Location location) {
        this.tiles[location.getRow()][location.getCol()].setState(VisibilityEnum.VISIBLE);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setTile(Location location, Tile tile) {
        this.tiles[location.getRow()][location.getCol()] = tile;
    }

    public ArrayList<Tile> getAllTilesInRadius(@NotNull Tile sourceTile, int rad) {
        Map<Tile, Integer> dist = new HashMap<>();
        Deque<Tile> queue = new ArrayDeque<>();
        queue.add(sourceTile);
        dist.put(sourceTile, 0);
        while (!queue.isEmpty()) {
            Tile x = queue.poll();
            if (dist.get(x) < rad && (!x.getTerrain().getTerrainType().isBlockingView() ||
                    x == sourceTile || sourceTile.getTerrain().getTerrainType() == TerrainEnum.HILL)) {
                for (Tile y : this.getNeighborsOf(x)) {
                    if (!dist.containsKey(y)) {
                        queue.add(y);
                        dist.put(y, dist.get(x) + 1);
                    }
                }
            }
        }
        return new ArrayList<>(dist.keySet());
    }

    public int calculateStraightDistanceWith(Tile sourceTile, Tile targetTile) {
        Map<Tile, Integer> dist = new HashMap<>();
        Deque<Tile> queue = new ArrayDeque<>();
        queue.add(sourceTile);
        dist.put(sourceTile, 0);
        while (!queue.isEmpty()) {
            Tile x = queue.poll();
            for (Tile y : this.getNeighborsOf(x)) {
                if (!dist.containsKey(y)) {
                    queue.add(y);
                    dist.put(y, dist.get(x) + 1);
                    if (y == targetTile) return dist.get(y);
                }
            }
        }
        throw new RuntimeException("target tile not found on source's tileGrid");
    }

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        int row = tile.getLocation().getRow(), col = tile.getLocation().getCol();
        ArrayList<Tile> tiles = new ArrayList<>();
        List<Location> neighbors;
        if (row % 2 == 0) {
            neighbors = List.of(new Location(-2, 0), new Location(2, 0), new Location(-1, -1), new Location(1, -1), new Location(-1, 0), new Location(1, 0));
        } else {
            neighbors = List.of(new Location(-2, 0), new Location(2, 0), new Location(-1, 0), new Location(1, 0), new Location(-1, 1), new Location(1, 1));
        }
        for (Location adj : neighbors) {
            Tile neighbor = this.getTile(row + adj.getRow(), col + adj.getCol());
            if (neighbor != null) tiles.add(neighbor);
        }
        return tiles;
    }

    public Tile getTile(int row, int col) {
        if (0 <= row && row < this.getHeight() && 0 <= col && col < this.getWidth()) {
            return tiles[row][col];
        } else {
            return null;
        }
    }

    public void assertLocationValid(Location location) throws CommandException {
        if (!isLocationValid(location)) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public boolean isLocationValid(Location location) {
        int r = location.getRow(), c = location.getCol();
        return 0 <= r && r < this.getHeight() && 0 <= c && c < this.getWidth();
    }

    public Tile getTile(Location location) {
        return getTile(location.getRow(), location.getCol());
    }

    public void setFogOfWarForAll() {
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                tiles[i][j].setState(tiles[i][j].getState() == VisibilityEnum.FOG_OF_WAR ? VisibilityEnum.FOG_OF_WAR : VisibilityEnum.VISIBLE);
            }
        }
    }

    public ArrayList<Tile> getFlatTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                tiles.add(this.getGrid()[i][j]);
            }
        }
        return tiles;
    }

    public Tile[][] getGrid() {
        return this.tiles;
    }

    public ArrayList<Location> getFlatTileLocations() {
        ArrayList<Location> tiles = new ArrayList<>();
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                tiles.add(new Location(i, j));
            }
        }
        return tiles;
    }

}