package Project.Server.Utils;

import Project.Client.Views.GameView;
import Project.SharedModels.Tiles.Tile;
import Project.SharedUtils.TileObserver;

public class ClientHandler implements TileObserver {
    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
        GameView.updateTile(tile.getLocation(), tile);
    }
}
