package Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Database {
    private static Database instance = null;
    private HashMap<String, User> users;
    private ArrayList<Game> games;

    private final Gson gson;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Database() {
        this.gson = new Gson();
        this.users = new HashMap<>();
        this.games = new ArrayList<>();
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

    public void addUserToList(User user) {
        this.users.put(user.getUsername(), user);
    }

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public boolean nicknameAlreadyExists(String nickname) {
        for (Map.Entry<String, User> account : this.users.entrySet()) {
            if (account.getValue().getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public void deserializeUsers() {
        try {
            File usersFile = new File("users.json");
            if (!usersFile.exists())
                usersFile.createNewFile();
            String fileReader = new String(Files.readAllBytes(Paths.get("users.json")));
            this.users = new Gson().fromJson(fileReader, new TypeToken<HashMap<String, User>>() {
            }.getType());
            if (this.users == null)
                this.users = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deserializeGames() {
        try {
            File gamesFile = new File("games.json");
            if (!gamesFile.exists())
                gamesFile.createNewFile();
            Reader readGames = Files.newBufferedReader(Paths.get("games.json"));
            List<Game> list = new ArrayList<>();
            Game[] copyingGames = this.gson.fromJson(readGames, Game[].class);
            readGames.close();
            if (copyingGames != null) {
                list = Arrays.asList(copyingGames);
            }
            ArrayList<Game> gamesList = new ArrayList<>(list);
            this.games.addAll(gamesList);
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
            gson.toJson(this.users, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeGames() {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("games.json"));
            gson.toJson(this.games, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
        serializeUsers();
        serializeGames();
    }
}