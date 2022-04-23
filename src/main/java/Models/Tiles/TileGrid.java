package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Terrains.Terrain;
import Models.User;

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

    private ArrayList<Location> usedLocations;
    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    private final Random random = new Random();
    private final int row = 50, col = 50;
    private ArrayList<ArrayList<VisibilityEnum>> terrainState = new ArrayList<>();


    public TileGrid() {
        this.usedLocations = new ArrayList<>();
        int x = random.nextInt(row);
        int y = random.nextInt(col);
        for (int i = 0; i < col; i++) {
            terrains.add(new ArrayList<>());
            for (int j = 0; j < row; j++) {
                terrains.get(i).add(new Terrain(randomAssignment(i, j)));
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

    private TerrainEnum randomAssignment(int x, int y) {
        if (!newTile(x, y)) {
            return TerrainEnum.OCEAN;
        }
        return randomTerrainType();
    }

    private TerrainEnum randomTerrainType() {
        ArrayList<TerrainEnum> values = new ArrayList<>();
        values.add(TerrainEnum.DESERT);
        values.add(TerrainEnum.HILL);
        values.add(TerrainEnum.MOUNTAIN);
        values.add(TerrainEnum.GRASSLAND);
        values.add(TerrainEnum.PLAIN);
        values.add(TerrainEnum.SNOW);
        values.add(TerrainEnum.TUNDRA);
        int randomSelection = random.nextInt(7);
        return values.get(randomSelection);
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

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }
}
