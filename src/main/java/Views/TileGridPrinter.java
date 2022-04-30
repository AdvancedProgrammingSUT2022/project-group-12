package Views;

import Enums.GameEnums.TerrainColor;
import Enums.GameEnums.VisibilityEnum;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

public class TileGridPrinter {
    private final TileGrid tileGrid;
    private final int height;
    private final int width;
    private final int h = 6;
    private final int w = 5;
    private final String[][] screen;

    public TileGridPrinter(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
        this.height = tileGrid.getHeight();
        this.width = tileGrid.getWidth();
        screen = new String[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                screen[i][j] = " ";
            }
        }
        this.grid = this.tileGrid.getGrid();
        this.state = this.tileGrid.gridState();
    }

    public String print() {
        for (int i = 0; i < this.tileGrid.getHeight(); ++i) {
            for (int j = 0; j < this.tileGrid.getWidth(); ++j) {
                Tile tile = this.tileGrid.getTile(i, j);
                int x = h + w * 2;
                if (i % 2 == 0)
                    this.drawHex(tile, 4 + i * h / 2, 10 + j * x);
                else
                    this.drawHex(tile, 4 + i * h / 2, 18 + j * x);
            }
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                output.append(screen[i][j]);
            }
            if (i < height - 1)
                output.append("\n");
        }
        return output.toString();
    }

    private void drawHex(Tile tile, int row, int col) {
        for (int j = -w / 2; j <= w / 2; ++j) {
            this.screen[row - h / 2][col + j] = "_";
            this.screen[row + h / 2][col + j] = "_";
        }
        for (int i = 0; i < h / 2; ++i) {
            this.screen[row - h / 2 + 1 + i][col - 3 - i] = "/";
            this.screen[row - h / 2 + 1 + i][col + 3 + i] = "\\";
            this.screen[row + h / 2 - i][col - 3 - i] = "\\";
            this.screen[row + h / 2 - i][col + 3 + i] = "/";
        }
    }

    private final Tile[][] grid;
    private final VisibilityEnum[][] state;
    private String[][] tiles;

    private void drawHex(int x, int y) {
        for (int i = 2; i > -1; i--) {
            tiles[x + 2 - i][y + i] = "/";
            tiles[x + 5 - i][y + 8 + i] = "/";
            tiles[x + 2 - i][y + 10 - i] = "\\";
            tiles[x + 5 - i][y + 2 - i] = "\\";
        }
    }

    private void insertTileInfo(int x, int y, int ox, int oy, String color) {
        for (int i = 2; i > -1; i--) {
            for (int j = i + y + 1; j < y + i + 10 - 2 * i; j++) {
                this.tiles[x + 2 - i][j] = color + " " + TerrainColor.RESET;
                this.tiles[x + 3 + i][j] = color + " " + TerrainColor.RESET;
            }
        }
        if (ox / 10 != 0) {
            tiles[x + 2][y + 3] = TerrainColor.BLACK + color + ox / 10 + TerrainColor.RESET;
        }
        tiles[x + 2][y + 4] = TerrainColor.BLACK + color + ox % 10 + TerrainColor.RESET;
        tiles[x + 2][y + 5] = TerrainColor.BLACK + color + "," + TerrainColor.RESET;
        if (oy / 10 != 0) {
            tiles[x + 2][y + 6] = TerrainColor.BLACK + color + oy / 10 + TerrainColor.RESET;
        }
        tiles[x + 2][y + 7] = TerrainColor.BLACK + color + oy % 10 + TerrainColor.RESET;

        for (int i = 0; i < 5; i++) {
            tiles[x + 5][y + 3 + i] = color + "_" + TerrainColor.RESET;
        }
    }

    private void drawGrid(int x, int y) {
        for (int i = 0; i < 5; i++) {
            this.tiles[2][11 + i] = "_";
            this.tiles[2][27 + i] = "_";
            this.tiles[2][43 + i] = "_";
        }

        for (int i = 0; i < 3 && x + i < height; i++) {
            for (int j = 0; j < 6 && y + j < width; j++) {
                String color = grid[x + i][y + j].getTerrain().getColor();
                int xi = i * 6;
                int yj = j * 8;
                xi += (y % 2) * 3;
                drawHex(xi, yj);
                if (this.state[x + i][y + j].equals(VisibilityEnum.FOG_OF_WAR)) {
                    color = TerrainColor.GRAY_BACKGROUND.toString();
                }
                insertTileInfo(xi, yj, x + i + (y % 2), y + j, color);
                if (state[x + i][y + j] == VisibilityEnum.VISIBLE) {
                    if (grid[x + i][y + j].getCity().getCivilization() == null) {
                        tiles[xi + 1][yj + 5] = color + " " + TerrainColor.RESET;
                    } else {
                        tiles[xi + 1][yj + 5] = color + grid[x + i][y + j].getCity().getCivilization().getName() + TerrainColor.RESET;
                    }
                }
            }
        }
    }

    private void setChar(int row, int col, char ch, TerrainColor foreground, TerrainColor background) {
        this.screen[row][col] = foreground.toString() + background.toString() + ch + TerrainColor.RESET;
    }

    public StringBuilder showTileGrid(int x, int y) {
        this.tiles = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i][j] = " ";
            }
        }

        drawGrid(x, y);

        StringBuilder finalPrint = new StringBuilder();
        for (String[] tile : tiles) {
            for (String string : tile) {
                finalPrint.append(string);
            }
            finalPrint.append("\n");
        }
        return finalPrint;
    }

    private StringBuilder showTileInfo;

    private void appendTileInfo(Tile selected) {
        if (selected.getNonCombatUnit() == null)
            showTileInfo.append("\ncontains no non combat units");
        else
            showTileInfo.append("\ncontains ").append(selected.getNonCombatUnit().getType());
        if (selected.getCombatUnit() == null)
            showTileInfo.append("\ncontains no combat units");
        else
            showTileInfo.append("\ncontains ").append(selected.getCombatUnit().getType());
        if (selected.getCity() != null)
            showTileInfo.append("\nhas a city built on it");
        if (selected.isDamaged())
            showTileInfo.append("\nis damaged");
        if (selected.hasRoad())
            showTileInfo.append("\nhas roads");
        if (selected.getResources() != null)
            showTileInfo.append(selected.getTerrain().getResourcesByName());
    }

    public StringBuilder tileInfo(int x, int y) {
        if (!this.tileGrid.isLocationValid(x, y))
            return new StringBuilder("location not valid");
        Tile selectedTile = this.tileGrid.getTile(x, y);
        VisibilityEnum selectedTileState = this.tileGrid.tileState(x, y);
        if (selectedTileState.equals(VisibilityEnum.FOG_OF_WAR))
            return new StringBuilder("you have not explored this tile yet");
        showTileInfo = new StringBuilder("Civilization: ")
                .append(selectedTile.getCivilization().getName())
                .append("\nType: ").append(selectedTile.getTerrain().getTerrainType());
        if (selectedTile.getState().equals(VisibilityEnum.VISIBLE))
            appendTileInfo(selectedTile);
        return showTileInfo;
    }
}
