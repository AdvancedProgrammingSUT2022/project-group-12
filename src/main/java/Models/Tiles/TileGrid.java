package Models.Tiles;

import java.util.ArrayList;

public class TileGrid {
    // TODO: complete
    private ArrayList<ArrayList<Tile>> tiles;
    private int height, width;

    public TileGrid(int height, int width) {
        this.height = height;
        this.width = width;
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
