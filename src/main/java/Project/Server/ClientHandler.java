package Project.Server;

import Project.Client.Views.GameView;
import Project.Models.Tiles.Tile;
import Project.Utils.TileObserver;

public class ClientHandler implements TileObserver {
    @Override
    public void getNotified(Tile tile) {
//        System.out.println("sending " + tile.getLocation() + " to client");
        GameView.updateTile(tile.getLocation(), tile);
    }
}
