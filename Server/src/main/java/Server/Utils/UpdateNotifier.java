package Server.Utils;

import Project.Enums.TechnologyEnum;
import Project.Models.Tiles.Tile;
import Project.Utils.*;
import com.google.gson.Gson;

public class UpdateNotifier implements Observer<Tile> {
    private final Connection connection;

    public UpdateNotifier(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void getNotified(Tile tile) {
//        System.err.println("sending " + tile.getLocation() + " to client");
            Request request = new Request(RequestType.UPDATE_TILE,null);
            request.setRequestTile(tile);
            this.connection.send(CustomGson.getInstance().toJson(request));
    }
    public void sendAddTechnologyMessage(String techName){
        Request request = new Request(RequestType.SHOW_NEW_TECHNOLOGY_ACHIEVED,null);
        request.addParameter("TechName",techName);
        this.connection.send(CustomGson.getInstance().toJson(request));
    }
}




