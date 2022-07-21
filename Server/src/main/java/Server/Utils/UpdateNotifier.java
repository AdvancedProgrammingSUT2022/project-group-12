package Server.Utils;

import Project.Models.Tiles.Tile;
import Project.Utils.Connection;
import Project.Utils.CustomGson;
import Project.Utils.Observer;

public class UpdateNotifier implements Observer<Tile> {
    private final Connection connection;

    public UpdateNotifier(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
        this.connection.send(CustomGson.getInstance().toJson(tile));
    }
}
