package Client.Utils;

import Client.Views.GameView;
import Client.Views.MenuStack;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Utils.*;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

public class UpdateTracker implements Runnable {
    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            System.out.println("updater socket connected to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("updater socket can't connect to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
            return;
        }
        Connection connection = new Connection(socket);

        Request request = new Request(RequestType.SET_SOCKET_FOR_UPDATE_TRACKER, MenuStack.getInstance().getCookies().getLoginToken());
        new Connection(socket).send(CustomGson.getInstance().toJson(request));

        System.out.println("updater bound with server successfully");
        while (true) {
            String update = connection.listen();
            Tile tile = CustomGson.getInstance().fromJson(update, Tile.class);
            Location location = tile.getLocation();
            Platform.runLater(() -> GameView.updateTile(location, tile));
        }
    }
}
