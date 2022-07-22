package Server.Utils;

import Project.Models.Tiles.Tile;
import Project.Utils.*;

public class UpdateNotifier implements Observer<Tile> {
    private final Connection connection;

    public UpdateNotifier(Connection connection) {
        this.connection = connection;
    }

    private void sendRequest(Request request) {
        this.connection.send(CustomGson.getInstance().toJson(request));
    }

    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
            Request request = new Request(RequestType.UPDATE_TILE,null);
            request.setRequestTile(tile);
            this.sendRequest(request);
    }
    public void sendAddTechnologyMessage(String techName){
        Request request = new Request(RequestType.SHOW_NEW_TECHNOLOGY_ACHIEVED,null);
        request.addParameter("TechName",techName);
        this.sendRequest(request);
    }

    public void sendAllowToPlay() {
        Request request = new Request(RequestType.ALLOW_PLAY_TURN, null);
        this.sendRequest(request);
    }

    public void sendReloadTileGrid() {
        Request request = new Request(RequestType.RELOAD_TILEGRID, null);
        this.sendRequest(request);
    }
}




