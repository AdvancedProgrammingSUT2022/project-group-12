package Models.Tiles;

import Enums.GameEnums.ResourceEnum;
import Enums.GameEnums.VisibilityEnum;
import Models.Cities.City;
import Models.Terrains.Terrain;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Tile {
    protected final int row = 50, col = 50;
    protected ArrayList<ArrayList<VisibilityEnum>> terrainState = new ArrayList<>();

    public Tile() {
        for (int i = 0; i < col; i++) {
            terrainState.add(new ArrayList<>());
            for (int j = 0; j < row; j++) {
                if (this instanceof TileGrid)
                    terrainState.get(i).add(VisibilityEnum.VISIBLE);
                else
                    terrainState.get(i).add(VisibilityEnum.FOG_OF_WAR);
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

    public Tile deepCopy() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), Tile.class);
    }
}