package Server.Utils;

import Project.Client.Views.GameView;
import Project.Models.Tiles.Tile;
import Project.Utils.Observer;

public class ClientHandler implements Observer<Tile> {
    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
        GameView.updateTile(tile.getLocation(), tile);
    }
}
