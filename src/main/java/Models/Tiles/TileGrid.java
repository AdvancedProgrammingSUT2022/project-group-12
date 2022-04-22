package Models.Tiles;

import Enums.GameEnums.TerrainEnum;
import Models.Civilization;
import Models.Location;
import Models.Terrains.Terrain;

import java.util.ArrayList;
import java.util.Random;

public class TileGrid extends Tile{
    private ArrayList<Location> usedLocations;
    private final ArrayList<ArrayList<Terrain>> terrains = new ArrayList<>();
    private final Random random = new Random();

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

    public Terrain getTerrain(int x, int y) {
        return terrains.get(x).get(y);
    }
}
