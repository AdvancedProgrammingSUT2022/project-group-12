package Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private HashMap<String, User> users = new HashMap<>();
    private ArrayList<Game> games = new ArrayList<>();
    private static Database instance = null;

    private static void setInstance(Database instance) {
        Database.instance = instance;
    }

    public static Database getInstance() {
        if (instance == null) {
            setInstance(new Database());
        }
        return instance;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void serialize() {
        try {
            FileWriter writer = new FileWriter("users.json");
            writer.write(new Gson().toJson(this.users));
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("games.json");
            writer.write(new Gson().toJson(this.games));
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addUserToList(User user) {
        this.users.put(user.getUsername(), user);
    }

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public void deserialize() {
        try {
            String jsonFile = new String(Files.readAllBytes(Paths.get("users.json")));
            this.users = new Gson().fromJson(jsonFile,new TypeToken<HashMap<String,User>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            String jsonFile = new String(Files.readAllBytes(Paths.get("games.json")));
            this.users = new Gson().fromJson(jsonFile,new TypeToken<HashMap<String,Game>>(){}.getType());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}