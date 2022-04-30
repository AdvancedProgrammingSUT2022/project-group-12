package Views;

import Enums.GameEnums.TerrainColor;
import Enums.GameEnums.VisibilityEnum;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

public class TileGridPrinter {
    private final TileGrid tileGrid;
    private final String[][] screen;
    private final int hexH = 6;
    private final int hexW = 5;
    private final int height;
    private final int width;

    private String[][] tiles;

    public TileGridPrinter(TileGrid tileGrid, int height, int width) {
        this.tileGrid = tileGrid;
        this.height = height;
        this.width = width;
        screen = new String[height][width];
    }

    private void setChar(int row, int col, char ch, TerrainColor foreground, TerrainColor background) {
        if (0 <= row && row < this.height && 0 <= col && col < this.width)
            screen[row][col] = foreground.toString() + background.toString() + ch + TerrainColor.RESET;
    }

    private void setChar(int row, int col, char ch) {
        if (0 <= row && row < this.height && 0 <= col && col < this.width) screen[row][col] = String.valueOf(ch);
    }

    private void writeCentered(int row, int col, String text, TerrainColor foreground, TerrainColor background) {
        for (int i = 0; i < text.length(); ++i) {
            this.setChar(row, col - text.length() / 2 + i, text.charAt(i), foreground, background);
        }
    }

    public String print(Location upleft) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                screen[i][j] = " ";
            }
        }

        for (int i = -1; i + upleft.getX() < tileGrid.getHeight(); ++i) {
            for (int j = -1; j + upleft.getY() < tileGrid.getWidth(); ++j) {
                Tile tile = tileGrid.getTile(i + upleft.getX(), j + upleft.getY());
                int x = hexH + hexW * 2;
                if (i % 2 == 0) this.drawHex(tile, hexH / 2 + i * hexH / 2, hexW + j * x);
                else this.drawHex(tile, hexH / 2 + i * hexH / 2, hexW * 2 + hexH / 2 + j * x);
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
        for (int j = -hexW / 2; j <= hexW / 2; ++j) {
            this.setChar(row - hexH / 2, col + j, '_');
            this.setChar(row + hexH / 2, col + j, '_');
        }
        for (int i = 0; i < hexH / 2; ++i) {
            this.setChar(row - hexH / 2 + 1 + i, col - 3 - i, '/');
            this.setChar(row - hexH / 2 + 1 + i, col + 3 + i, '\\');
            this.setChar(row + hexH / 2 - i, col - 3 - i, '\\');
            this.setChar(row + hexH / 2 - i, col + 3 + i, '/');
        }
        this.writeCentered(row - 1, col, tile.getRow() + "," + tile.getCol(), TerrainColor.BLACK, tile.getTerrain().getColor());
        this.writeCentered(row, col, tile.getTerrain().getTerrainType().toString(), tile.getTerrain().getColor(), TerrainColor.RESET);
    }

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

        Tile[][] grid = this.tileGrid.getGrid();
        VisibilityEnum[][] state = this.tileGrid.gridState();

        for (int i = 0; i < 3 && x + i < height; i++) {
            for (int j = 0; j < 6 && y + j < width; j++) {
                String color = grid[x + i][y + j].getTerrain().getColor().toString();
                int xi = i * 6;
                int yj = j * 8;
                xi += (y % 2) * 3;
                drawHex(xi, yj);
                if (state[x + i][y + j].equals(VisibilityEnum.FOG_OF_WAR)) {
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
