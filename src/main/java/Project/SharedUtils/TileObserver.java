package Project.SharedUtils;

import Project.SharedModels.Tiles.Tile;

public interface TileObserver {
    void getNotified(Tile subject);
}
