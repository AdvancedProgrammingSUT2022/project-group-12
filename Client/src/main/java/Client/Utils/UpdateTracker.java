package Client.Utils;

import Client.Views.GameView;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Utils.Connection;
import Project.Utils.CustomGson;
import javafx.application.Platform;

import java.net.Socket;

public class UpdateTracker implements Runnable {
    private final Connection connection;

    public UpdateTracker(Socket socket) {
        this.connection = new Connection(socket);
    }

    @Override
    public void run() {
        while (true) {
            String update = this.connection.listen();
            Tile tile = CustomGson.getInstance().fromJson(update, Tile.class);
            Location location = tile.getLocation();
            Platform.runLater(() -> GameView.updateTile(location, tile));
//            System.out.println("update to " + location);
        }
    }
}
