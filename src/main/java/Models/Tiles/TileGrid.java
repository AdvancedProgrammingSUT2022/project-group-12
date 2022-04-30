package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Location;
import Models.Terrains.Terrain;
import Views.TileGridPrinter;

import java.util.ArrayList;
import java.util.Random;

public class TileGrid {
    private final int height = 30;
    private final int width = 30;
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

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        return new ArrayList<>();
    }

    public Tile getTile(int row, int col) {
        return tiles[row][col];
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
            if (usedLocation.getX() == x && usedLocation.getY() == y) {
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