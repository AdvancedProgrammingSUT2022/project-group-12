package Project.SharedModels.Tiles;

import Project.SharedModels.Location;

public class HexGrid {
    private final int height;
    private final int width;
    private final Hex[][] hexes;

    public HexGrid(int height, int width) {
        this.height = height;
        this.width = width;
        this.hexes = new Hex[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                hexes[i][j] = new Hex(i, j);
            }
        }
    }

    public Hex getHex(Location location) {
        return hexes[location.getRow()][location.getCol()];
    }
}
