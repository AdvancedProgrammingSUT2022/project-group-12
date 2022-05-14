package Models.Tiles;

import Enums.TerrainEnum;
import Enums.VisibilityEnum;
import Models.Civilization;
import Models.Location;
import Models.Terrains.Terrain;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.*;

public class TileGrid {
    private final ArrayList<Location> usedLocations;
    private final Random random = new Random();
    private final int height;
    private final int width;
    private final Tile[][] tiles;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
        tiles = new Tile[height][width];
        this.usedLocations = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = this.randomAssignment(i, j);
                tiles[i][j].setState(VisibilityEnum.VISIBLE);
            }
        }
    }

    private Tile randomAssignment(int x, int y) {
        if (!newTile(x, y)) {
            return new Tile(new Terrain(TerrainEnum.OCEAN), x, y);
        }
        return randomTerrainType(x, y);
    }

    private Tile randomTerrainType(int x, int y) {
        ArrayList<TerrainEnum> values = new ArrayList<>();
        values.add(TerrainEnum.DESERT);
        values.add(TerrainEnum.HILL);
        values.add(TerrainEnum.MOUNTAIN);
        values.add(TerrainEnum.GRASSLAND);
        values.add(TerrainEnum.PLAIN);
        values.add(TerrainEnum.SNOW);
        values.add(TerrainEnum.TUNDRA);
        int randomSelection = random.nextInt(7);
        return new Tile(new Terrain(values.get(randomSelection)), x, y);
    }

    private boolean newTile(int x, int y) {
        for (Location usedLocation : this.usedLocations) {
            if (usedLocation.getRow() == x && usedLocation.getCol() == y) {
                return false;
            }
        }
        return true;
    }

    public TileGrid(Civilization civilization, int height, int width) {
        this.height = height;
        this.width = width;
        tiles = new Tile[height][width];
        this.usedLocations = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = new Tile(new Terrain(TerrainEnum.UNKNOWN), i, j);
            }
        }
    }

    public void setVisible(Location location) {
        this.tiles[location.getRow()][location.getCol()].setState(VisibilityEnum.VISIBLE);
    }

    public Location getRandomTileLocation() {
        Random random = new Random();
        return new Location(random.nextInt(this.getHeight()), random.nextInt(this.getWidth()));
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

    public ArrayList<Tile> getAllTilesInRadius(Tile sourceTile, int rad) {
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

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        int row = tile.getRow(), col = tile.getCol();
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

    public boolean isLocationValid(Location location) {
        return isLocationValid(location.getRow(), location.getCol());
    }

    public boolean isLocationValid(int r, int c) {
        return 0 <= r && r < this.getHeight() && 0 <= c && c < this.getWidth();
    }

    private ArrayList<Location> getUsedLocations() {
        return this.usedLocations;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void replaceTile(Tile replacement) {
        this.tiles[replacement.getRow()][replacement.getCol()] = replacement;
    }

    public VisibilityEnum[][] gridState() {
        VisibilityEnum[][] state = new VisibilityEnum[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                state[i][j] = tiles[i][j].getState();
            }
        }
        return state;
    }

    public VisibilityEnum tileState(int x, int y) {
        return this.tiles[x][y].getState();
    }

    public void assertLocationValid(Location location) throws CommandException {
        if (!isLocationValid(location.getRow(), location.getCol())) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
    }

    public Tile getTile(Location location) {
        return getTile(location.getRow(), location.getCol());
    }

    public void setFogOfWarForAll() {
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                tiles[i][j].setState(tiles[i][j].getState() == VisibilityEnum.FOG_OF_WAR ? VisibilityEnum.FOG_OF_WAR : VisibilityEnum.REVEALED);
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