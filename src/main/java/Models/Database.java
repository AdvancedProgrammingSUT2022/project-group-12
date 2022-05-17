package Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database instance = null;
    private final Gson gson;
    private final HashMap<String, User> users;
    private final ArrayList<User> usersList;
    private final ArrayList<Game> games;

    private Database() {
        this.gson = new Gson();
        this.users = new HashMap<>();
        this.games = new ArrayList<>();
        this.usersList = new ArrayList<>();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        usersList.add(user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public boolean nicknameAlreadyExists(String nickname) {
        for (User account : this.usersList) {
            if (account.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public void deserialize() {
        deserializeUsers();
    }

    public void deserializeUsers() {
        ArrayList<User> jsonUsers = new ArrayList<>();
        try {
            File file = new File("users.json");
            if (!file.exists()) {
                file.createNewFile();
            }
            String jsonFile = new String(Files.readAllBytes(Paths.get("users.json")));
            jsonUsers = new Gson().fromJson(jsonFile, new TypeToken<List<User>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonUsers != null) {
            this.usersList.addAll(jsonUsers);
        }
        for (User user : this.usersList) {
            this.users.put(user.getUsername(), user);
        }
    }

    public void serialize() {
        try {
            File file = new File("users.json");
            file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter("users.json");
            writer.write(this.gson.toJson(usersList));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}