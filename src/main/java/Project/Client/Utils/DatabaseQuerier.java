package Project.Client.Utils;

import Project.Models.Location;
import Project.Models.Resource;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Server.Views.RequestHandler;
import Project.Utils.DatabaseQueryType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DatabaseQuerier {

    public static ArrayList<User> getAllUsers() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERS);
        TypeToken<ArrayList<User>> typeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getAllUsernames() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERNAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static User getUser(String username) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_USER_BY_USERNAME, username);
        TypeToken<User> typeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Location> getCivTilesLocations() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_TILES_LOCATIONS);
        TypeToken<ArrayList<Location>> typeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, typeToken.getType());
    }
    public static ArrayList<Unit> getCurrentCivilizationUnits(){
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_UNITS);
        return new Gson().fromJson(json,new TypeToken<ArrayList<Unit>>(){}.getType());
    }

    public static ArrayList<Resource> getCivResources() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_RESOURCES);
        TypeToken<ArrayList<Resource>> typeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, typeToken.getType());
    }
//    public static int getGoldCivilization(){
//        String json = RequestHandler.
//    }
}
