package Views;

import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

import java.util.ArrayList;

public class TileGridPrinter {
    private ArrayList<ArrayList<Character>> screen;
    private TileGrid tileGrid;
    private int height = 20;
    private int width = 70;
    private final int h = 6;
    private final int w = 5;

    public TileGridPrinter(TileGrid tileGrid) {

        this.tileGrid = tileGrid;
        screen = new ArrayList<>();
        for (int i = 0; i < height; ++i) {
            screen.add(new ArrayList<>());
            for (int j = 0; j < width; ++j) {
                screen.get(i).add(' ');
            }
        }
    }

    public String print() {
        for (int i = 0; i < this.tileGrid.getHeight(); ++i) {
            for (int j = 0; j < this.tileGrid.getWidth(); ++j) {
                Tile tile = this.tileGrid.getTile(i, j);
                int x = h + w * 2;
                if (i % 2 == 0) this.drawHex(tile, 4 + i * h / 2, 10 + j * x);
                else this.drawHex(tile, 4 + i * h / 2, 18 + j * x);
            }
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                output.append(screen.get(i).get(j));
            }
            output.append('\n');
        }
        return output.toString();
    }

    private void drawHex(Tile tile, int row, int col) {
        for (int j = -w / 2; j <= w / 2; ++j) {
            this.screen.get(row - h / 2).set(col + j, '_');
            this.screen.get(row + h / 2).set(col + j, '_');
        }
        for (int i = 0; i < h / 2; ++i) {
            this.screen.get(row - h / 2 + 1 + i).set(col - 3 - i, '/');
            this.screen.get(row - h / 2 + 1 + i).set(col + 3 + i, '\\');
            this.screen.get(row + h / 2 - i).set(col - 3 - i, '\\');
            this.screen.get(row + h / 2 - i).set(col + 3 + i, '/');
        }
    }
}
