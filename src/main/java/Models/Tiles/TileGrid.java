package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Location;
import Models.Terrains.Terrain;


import java.util.ArrayList;
import java.util.Random;

public class TileGrid {
    private static TileGrid instance = null;

    private static void setInstance(TileGrid instance) {
        TileGrid.instance = instance;
    }

    public static TileGrid getInstance() {
        if (instance == null) {
            setInstance(new TileGrid());
        }
        return instance;
    }

    private final ArrayList<Location> usedLocations;
    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    private final Random random = new Random();
    private final int row = 50, col = 50;

    public TileGrid() {
        this.usedLocations = new ArrayList<>();
        int x = random.nextInt(row);
        int y = random.nextInt(col);
        for (int i = 0; i < col; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < row; j++) {
                tiles.get(i).add(randomAssignment(i, j));
            }
        }
    }


    public boolean isLocationValid(int x, int y) {
        return x > -1 && x < col && y > -1 && y < row;
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
}
