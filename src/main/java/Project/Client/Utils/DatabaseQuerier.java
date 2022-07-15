package Project.Client.Utils;

import Project.Models.User;
import Project.Server.Views.RequestHandler;
import Project.Utils.DatabaseQueryType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DatabaseQuerier {

    public static ArrayList<User> getAllUsers() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERS);
        TypeToken<ArrayList<User>> arrayListTypeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, arrayListTypeToken.getType());
    }

    public static ArrayList<String> getAllUsernames() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERNAMES);
        TypeToken<ArrayList<String>> arrayListTypeToken = new TypeToken<>() {};
        return new Gson().fromJson(json, arrayListTypeToken.getType());
    }

    public static User getUser(String username) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_USER_BY_USERNAME, username);
        return new Gson().fromJson(json, User.class);
    }
}
