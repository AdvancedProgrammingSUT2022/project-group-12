package Server.Utils;

import Project.Models.Chat;
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

    public void sendUpdateChatRequest(Chat chat){
        Request request = new Request(RequestType.UPDATE_CHAT,null);
        request.setRequestChat(chat);
        this.sendRequest(request);
    }

    public void sendSimpleRequest(RequestType requestType) {
        Request request = new Request(requestType, null);
        this.sendRequest(request);
    }
}




