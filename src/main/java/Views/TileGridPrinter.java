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

    private StringBuilder showTileInfo;

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
            this.setChar(row, col - (text.length() - 1) / 2 + i, text.charAt(i), foreground, background);
        }
    }

    public String print(Location upleft) {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                screen[i][j] = " ";
            }
        }

        for (int i = tileGrid.getHeight(); i >= -1; --i) {
            for (int j = -1; j <= tileGrid.getWidth(); ++j) {
                int ii = i + upleft.getRow();
                int jj = j + upleft.getCol();
                if (0 <= ii && ii < tileGrid.getHeight() && 0 <= jj && jj < tileGrid.getWidth()) {
                    Tile tile = tileGrid.getTile(ii, jj);
                    int x = hexH + hexW * 2;
                    if (i % 2 == 0) this.drawHex(tile, hexH / 2 + i * hexH / 2, hexW + j * x);
                    else this.drawHex(tile, hexH / 2 + i * hexH / 2, hexW * 2 + hexH / 2 + j * x);
                }
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
        TerrainColor tileColor = tile.getTerrain().getColor();
        for (int i = 0; i < hexH / 2; ++i) {
            int lt = col - 3 - i;
            int rt = col + 3 + i;
            this.setChar(row - hexH / 2 + 1 + i, lt, '/');
            this.setChar(row - hexH / 2 + 1 + i, rt, '\\');
            this.setChar(row + hexH / 2 - i, lt, '\\');
            this.setChar(row + hexH / 2 - i, rt, '/');
            for (int k = lt + 1; k < rt; ++k) {
                this.setChar(row - hexH / 2 + 1 + i, k, ' ', TerrainColor.BLACK, tileColor);
                this.setChar(row + hexH / 2 - i, k, ' ', TerrainColor.BLACK, tileColor);
            }
            for (int j = -hexW / 2; j <= hexW / 2; ++j) {
                this.setChar(row - hexH / 2, col + j, '_');
                this.setChar(row + hexH / 2, col + j, '_', TerrainColor.WHITE_BRIGHT, tileColor);
            }
        }
        this.writeCentered(row - 1, col, tile.getRow() + "," + tile.getCol(), TerrainColor.BLACK, tileColor);
        if (tile.getState() == VisibilityEnum.FOG_OF_WAR) {
            this.writeCentered(row, col, tile.getTerrain().getTerrainType().getAbbreviation(), TerrainColor.BLACK, tileColor);
//            if (tile.getCivilization() != null) this.writeCentered(row + 1, col, tile.getCivilization().getAbbreviation(), TerrainColor.BLACK, tileColor);
            if (tile.getNonCombatUnit() != null) this.writeCentered(row + 1, col, tile.getNonCombatUnit().getType().name(), TerrainColor.BLACK, tileColor);
        } else {
            this.writeCentered(row, col, "", TerrainColor.GRAY_BACKGROUND, TerrainColor.RESET);
        }
    }

    private void appendTileInfo(Tile selected) {
        if (selected.getNonCombatUnit() == null) showTileInfo.append("\ncontains no non combat units");
        else showTileInfo.append("\ncontains ").append(selected.getNonCombatUnit().getType());
        if (selected.getCombatUnit() == null) showTileInfo.append("\ncontains no combat units");
        else showTileInfo.append("\ncontains ").append(selected.getCombatUnit().getType());
        if (selected.getCity() != null) showTileInfo.append("\nhas a city built on it");
        if (selected.isDamaged()) showTileInfo.append("\nis damaged");
        if (selected.hasRoad()) showTileInfo.append("\nhas roads");
        if (selected.getResources() != null) showTileInfo.append(selected.getTerrain().getResourcesByName());
    }

    public StringBuilder tileInfo(int x, int y) {
        if (!this.tileGrid.isLocationValid(x, y)) return new StringBuilder("location not valid");
        Tile selectedTile = this.tileGrid.getTile(x, y);
        VisibilityEnum selectedTileState = this.tileGrid.tileState(x, y);
        if (selectedTileState.equals(VisibilityEnum.FOG_OF_WAR))
            return new StringBuilder("you have not explored this tile yet");
        showTileInfo = new StringBuilder("Civilization: ").append(selectedTile.getCivilization().getName()).append("\nType: ").append(selectedTile.getTerrain().getTerrainType());
        if (selectedTile.getState().equals(VisibilityEnum.VISIBLE)) appendTileInfo(selectedTile);
        return showTileInfo;
    }
}
