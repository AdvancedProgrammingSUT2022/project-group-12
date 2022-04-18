package Models.Tiles;

import java.util.ArrayList;

public class TileGrid {
    // TODO: complete
    private ArrayList<ArrayList<Tile>> tiles;
    private int height, width;

    private TileGrid() {
    }

    public static TileGrid GenerateRandom(int height, int width, int seed) {
        TileGrid tileGrid = new TileGrid();
        return tileGrid;
    }

    public ArrayList<Tile> getNeighborsOf(Tile tile) {
        return new ArrayList<>();
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
