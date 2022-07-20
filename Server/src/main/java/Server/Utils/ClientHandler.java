package Server.Utils;

import Project.Models.Tiles.Tile;
import Project.Utils.Connection;
import Project.Utils.CustomGson;
import Project.Utils.Observer;
import Server.ServerMain;

public class ClientHandler implements Observer<Tile> {
    private final Connection connection;

    public ClientHandler() {
        this.connection = new Connection(ServerMain.socketOfTracker);
    }

    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
        this.connection.send(CustomGson.getInstance().toJson(tile));
    }
}
