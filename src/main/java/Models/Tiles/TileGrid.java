package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Models.Location;
import Models.Terrains.Terrain;

import java.util.ArrayList;
import java.util.Random;

public class TileGrid {
    private static TileGrid instance = null;

    public static TileGrid getInstance() {
        if (instance == null) {
//            instance = new TileGrid(); // todo: change to non-singleton
        }
        return instance;
    }

    private final ArrayList<Location> usedLocations;
    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    private final Random random = new Random();
    private int height;
    private int width;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
        this.usedLocations = new ArrayList<>();
        int x = random.nextInt(height);
        int y = random.nextInt(width);
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                tiles.get(i).add(randomAssignment(i, j));
            }
        }
    }

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        // todo
        return new ArrayList<>();
    }

    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }

    public boolean isLocationValid(int x, int y) {
        return x > -1 && x < width && y > -1 && y < height;
    }

    public StringBuilder showGrid(int x, int y) {
        if (isLocationValid(x, y)) {
            //TODO : draw map
        }
        return null;
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

    public Tile getTerrain(int x, int y) {
        return tiles.get(x).get(y);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}