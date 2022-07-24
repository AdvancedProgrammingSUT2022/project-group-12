package Server.Utils;

import Project.Enums.ChatType;
import Project.Models.Chat;
import Project.Models.Tiles.Tile;
import Project.Utils.*;
import com.google.gson.Gson;

import java.util.HashMap;

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
        Request request = new Request(RequestType.UPDATE_TILE, null);
        request.setRequestTile(tile);
        this.sendRequest(request);
    }

    public void sendAddTechnologyMessage(String techName) {
        Request request = new Request(RequestType.SHOW_NEW_TECHNOLOGY_ACHIEVED, null);
        request.addParameter("TechName", techName);
        this.sendRequest(request);
    }

    public void sendUpdateChatRequest(Chat chat, ChatType chatType) {
        Request request;
        switch (chatType) {
            case NORMAL_CHAT -> request = new Request(RequestType.UPDATE_CHAT, null);
            case PUBLIC_CHAT -> request = new Request(RequestType.UPDATE_PUBLIC_CHAT, null);
            case LOBBY_CHAT -> request = new Request(RequestType.UPDATE_LOBBY_CHAT,null);
            default -> throw new IllegalStateException("Unexpected value: " + chatType);
        }
        request.setRequestChat(chat);
        this.sendRequest(request);
    }

    public void sendUpdatePublicChatRequest(Chat chat) {
        Request request = new Request(RequestType.UPDATE_PUBLIC_CHAT, null);
        request.setRequestChat(chat);
        this.sendRequest(request);
    }


    public void sendSimpleRequest(RequestType requestType) {
        Request request = new Request(requestType, null);
        this.sendRequest(request);
    }

    public void sendEndGameMessage(HashMap<String, Integer> civsScore) {
        Request request = new Request(RequestType.END_GAME, null);
        request.addParameter("civsScore", new Gson().toJson(civsScore));
        this.sendRequest(request);
    }

    public void sendUpdateYearMessage(int currentYear) {
        Request request = new Request(RequestType.UPDATE_YEAR, null);
        request.addParameter("currentDate", String.valueOf(currentYear));
        this.sendRequest(request);
    }


}




