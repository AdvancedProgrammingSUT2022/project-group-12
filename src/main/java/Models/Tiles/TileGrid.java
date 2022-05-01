package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Location;
import Models.Terrains.Terrain;

import java.util.*;

public class TileGrid {
    private final int height = 5;
    private final int width = 5;
    private final ArrayList<Location> usedLocations;
    private final Random random = new Random();
    private final Tile[][] tiles;

    public TileGrid() {
        tiles = new Tile[width][height];
        this.usedLocations = new ArrayList<>();
        int x = random.nextInt(height);
        int y = random.nextInt(width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = this.randomAssignment(i, j);
            }
        }
    }

    public Location getRandomTileLocation() {
        Random random = new Random();
        return new Location(random.nextInt(this.getHeight()), random.nextInt(this.getWidth()));
    }

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        int row = tile.getRow(), col = tile.getCol();
        ArrayList<Tile> tiles = new ArrayList<>();
        List<Location> neighbors;
        if (row % 2 == 0) {
            neighbors = List.of(new Location(-2, 0), new Location(2, 0), new Location(-1, 0), new Location(1, 0), new Location(-1, -1), new Location(1, -1));
        } else {
            neighbors = List.of(new Location(-2, 0), new Location(2, 0), new Location(-1, 1), new Location(1, 1), new Location(-1, 0), new Location(1, 0));
        }
        for (Location location : neighbors) {
            int nrow = row + location.getRow();
            int ncol = row + location.getCol();
            Tile ntile = this.getTile(nrow, ncol);
            if (ntile != null) tiles.add(ntile);
        }
        return tiles;
    }

    public ArrayList<Tile> getNeighborsOfRadius(Tile tile, int rad) { // assume same vision for terrains
        ArrayList<Tile> tiles = new ArrayList<>();
        HashMap<Tile, Integer> dist = new HashMap<>();
        ArrayDeque<Tile> queue = new ArrayDeque<>();
        queue.add(tile);
        dist.put(tile, 0);
        while (!queue.isEmpty()) {
            Tile x = queue.poll();
            if (dist.get(x) >= rad) continue;
            for (Tile y : this.getNeighborsOf(x)) {
                if (!dist.containsKey(y)) {
                    queue.add(y);
                    dist.put(y, dist.get(x) + 1);
                    tiles.add(y);
                }
            }
        }
        return tiles;
    }

    public Tile getTile(int row, int col) {
        if (0 <= row && row < this.getHeight() && 0 <= col && col < this.getWidth()) return tiles[row][col];
        else return null;
    }

    public boolean isLocationValid(int x, int y) {
        return x > -1 && x < width && y > -1 && y < height;
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

    private ArrayList<Location> getUsedLocations() {
        return this.usedLocations;
    }

    public Tile[][] getGrid() {
        return this.tiles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
}