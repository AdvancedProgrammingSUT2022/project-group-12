package Views;

import Enums.GameEnums.TerrainColor;
import Enums.GameEnums.VisibilityEnum;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

public class TileGridPrinter {
    private final TileGrid tileGrid;
    private final int height = 20;
    private final int width = 70;
    private final int h = 6;
    private final int w = 5;
    private final String[][] screen;

    public TileGridPrinter(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
        screen = new String[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                screen[i][j] = " ";
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
                output.append(screen[i][j]);
            }
            output.append('\n');
        }
        return output.toString();
    }

    private void drawHex(Tile tile, int row, int col) {
        for (int j = -w / 2; j <= w / 2; ++j) {
            // todo: need setChar
//            this.screen[row - h / 2][col + j] = '_';
//            this.screen[row + h / 2][col + j] = '_';
        }
        for (int i = 0; i < h / 2; ++i) {
//            this.screen[row - h / 2 + 1 + i][col - 3 - i] = '/';
//            this.screen[row - h / 2 + 1 + i][col + 3 + i] = '\\';
//            this.screen[row + h / 2 - i][col - 3 - i] = '\\';
//            this.screen[row + h / 2 - i][col + 3 + i] = '/';
        }
    }

    private Tile[][] grid;
    private VisibilityEnum[][] state;
    private StringBuilder[][] tiles; // todo: not initialized

    private void drawHex(int x, int y) {
        for (int i = 2; i > -1; i--) {
            tiles[x + 2 - i][y + i].replace(0, tiles.length - 1, "/");
            tiles[x + 5 - i][y + 8 + i].replace(0, tiles.length - 1, "/");
            tiles[x + 2 - i][y + 10 - i].replace(0, tiles.length - 1, "\\");
            tiles[x + 5 - i][y + 2 - i].replace(0, tiles.length - 1, "\\");
        }
    }

    private void insertTileInfo(int x, int y, int ox, int oy, String color) {
        for (int i = 2; i > -1; i--) {
            for (int j = i + y + 1; j < y + i + 1 + 5 + 4 - 2; j++) {
                this.tiles[x + 2 - i][j].replace(0, tiles.length - 1, color + " " + TerrainColor.RESET);
                this.tiles[x + 3 + i][j].replace(0, tiles.length - 1, color + " " + TerrainColor.RESET);
            }
        }
        if (ox / 10 != 0) {
            tiles[x + 2][y + 3].replace(0, tiles.length - 1, TerrainColor.BLACK + color + ox / 10 + TerrainColor.RESET);
        }
        tiles[x + 2][y + 4].replace(0, tiles.length - 1, TerrainColor.BLACK + color + ox % 10 + TerrainColor.RESET);
        tiles[x + 2][y + 5].replace(0, tiles.length - 1, TerrainColor.BLACK + color + "," + TerrainColor.RESET);
        if (oy / 10 != 0) {
            tiles[x + 2][y + 6].replace(0, tiles.length - 1, TerrainColor.BLACK + color + oy / 10
                    + TerrainColor.RESET);
        }
        tiles[x + 2][y + 7].replace(0, tiles.length - 1, TerrainColor.BLACK + color + oy % 10 + TerrainColor.RESET);

        for (int i = 0; i < 5; i++) {
            tiles[x + 5][y + 3 + i].replace(0, tiles.length - 1, color + "_" + TerrainColor.RESET);
        }
    }

    private void drawGrid(int x, int y) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                this.tiles[2][11 + j].replace(0, tiles.length - 1, "_");
                this.tiles[2][27 + j].replace(0, tiles.length - 1, "_");
                this.tiles[2][43 + j].replace(0, tiles.length - 1, "_");
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                String color = grid[x + i][y + j].getTerrain().getColor();
                int xi = i * 6;
                int yj = j * 8;
                if (j % 2 == 1) {
                    xi += 3;
                }

                drawHex(xi, yj);

                if (this.state[x + i][y + j] == VisibilityEnum.FOG_OF_WAR) {
                    color = TerrainColor.GRAY_BACKGROUND.toString();
                }

                insertTileInfo(xi, yj, x + i, y + j, color);

                if (state[x + i][y + j] == VisibilityEnum.VISIBLE) {
                    if (grid[x + i][y + j].getCity().getCivilization() == null) {
                        tiles[xi + 1][yj + 5].replace(0, tiles.length - 1, color + " " + TerrainColor.RESET);
                    } else {
                        tiles[xi + 1][yj + 5].replace(0, tiles.length - 1, color
                                + grid[x + i][y + j].getCity().getCivilization().getName().charAt(0) + TerrainColor.RESET);
                    }
                }
            }
        }
    }

    private void setChar(int row, int col, char ch, TerrainColor foreground, TerrainColor background) {
        this.screen[row][col] = foreground.toString() + background.toString() + ch + TerrainColor.RESET;
    }

    public StringBuilder showTileGrid(int x, int y) {
        this.grid = this.tileGrid.getGrid();
        this.state = this.tileGrid.gridState();

        tiles = new StringBuilder[21][51];

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 51; j++) {
                tiles[i][j].replace(0, tiles.length - 1, " ");
            }
        }

        drawGrid(x, y);

        StringBuilder finalPrint = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                finalPrint.append(tiles[i][j]);
            }
            finalPrint.append("\n");
        }
        return finalPrint;
    }
}
