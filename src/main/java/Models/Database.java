package Models;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database instance = null;
    private final Gson gson;
    private HashMap<String, User> users;
    private ArrayList<User> usersList;
    private ArrayList<Game> games;

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

    public void deserializeUsers() {
        try {
            File gameFile = new File("users.json");
            if (!gameFile.exists()) {
                gameFile.createNewFile();
            }
            List<User> list = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("users.json"));
            User[] usersArray = gson.fromJson(reader, User[].class);
            if (usersArray != null) {
                list = Arrays.asList(usersArray);
            }
            ArrayList<User> copyingUsers = new ArrayList<>(list);
            reader.close();
            this.usersList.addAll(copyingUsers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!this.usersList.isEmpty()) {
            for (User user : this.usersList) {
                users.put(user.getUsername(), user);
            }
        }
    }

    public void deserializeGames() {
        try {
            File gameFile = new File("games.json");
            if (!gameFile.exists()) {
                gameFile.createNewFile();
            }
            List<Game> games = new ArrayList<>();
            Reader reader = Files.newBufferedReader(Paths.get("games.json"));
            Game[] gamesArray = gson.fromJson(reader, Game[].class);
            if (gamesArray != null) {
                games = Arrays.asList(gamesArray);
            }
            ArrayList<Game> gameList = new ArrayList<>(games);
            reader.close();
            this.games.addAll(gameList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deserialize() {
        deserializeUsers();
        deserializeGames();
    }

    public void serializeUsers() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("users.json"));
            gson.toJson(usersList, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializeGames() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("games.json"));
            gson.toJson(games, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serialize() {
        serializeUsers();
        serializeGames();
    }

    public void printUsers() {
        System.out.println(this.users);
    }
}