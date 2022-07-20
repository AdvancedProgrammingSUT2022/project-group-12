package Server.Models;

import Project.Models.User;
import Server.Controllers.GameController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database instance = null;
    private final Gson gson;
    private final HashMap<String, User> users;
    private final ArrayList<Game> games;

    private Database() {
        this.gson = new Gson();
        this.users = new HashMap<>();
        this.games = new ArrayList<>();
        for (int i = 1; i < 10; ++i) {
            this.addUser(new User(String.valueOf(i), String.valueOf(i), "nick" + i));
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(this.users.values());
    }

    public ArrayList<String> getAllUsernames() {
        return new ArrayList<>(this.users.values().stream().map(User::getUsername).toList());
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

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public boolean nicknameAlreadyExists(String nickname) {
        return this.users.values().stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    public void deserialize() {
//        deserializeUsers();
    }

    public void deserializeUsers() {
        ArrayList<User> copiedUsers = new ArrayList<>();
        try {
            File file = new File(Game.class.getResource("/database/users.xml").toExternalForm());
            if (!file.exists())
                return;
            String filePath = new String(Files.readAllBytes(Paths.get(Game.class.getResource("/database/users.xml").toExternalForm())));
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY);
            if (filePath.length() != 0) {
                Object selectedUsers = new XStream().fromXML(filePath, new TypeToken<List<User>>() {
                }.getType());
                User[] myUsers = new User[Array.getLength(selectedUsers)];
                for (int i = 0; i < Array.getLength(selectedUsers); i++) {
                    Array.set(myUsers, i, Array.get(selectedUsers, i));
                }
                copiedUsers.addAll(List.of(myUsers));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!copiedUsers.isEmpty()) {
            for (User user : copiedUsers) {
                this.users.put(user.getUsername(), user);
            }
        }
    }

    private void serializeUsers() {
        try {
            FileWriter writer;
            if (!Files.exists(Paths.get(Game.class.getResource("/database/users.xml").toExternalForm())))
                new File(Game.class.getResource("/database").toExternalForm()).mkdir();
            writer = new FileWriter(Game.class.getResource("/database/game.xml").toExternalForm(), false);
            XStream xStream = new XStream();
            writer.write(xStream.toXML(this.getAllUsers()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
//        serializeUsers();
    }

    public void saveGame() {
        Game game = GameController.getGame();
        try {
            FileWriter writer;
            if (!Files.exists(Paths.get(Game.class.getResource("/database/game.xml").toExternalForm())))
                new File(Game.class.getResource("/database").toExternalForm()).mkdir();
            writer = new FileWriter(Game.class.getResource("/database/game.xml").toExternalForm(), false);
            XStream xStream = new XStream();
            writer.write(xStream.toXML(game));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Game openLastGame() {
        try {
            String filePath = new String(Files.readAllBytes(Paths.get(Game.class.getResource("/database/game.xml").toExternalForm())));
            XStream file = new XStream();
            file.addPermission(AnyTypePermission.ANY);
            if (filePath.length() != 0) {
                return (Game) file.fromXML(filePath);
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }


    public boolean checkPassword(String username, String password) {
        return this.users.get(username).passwordMatchCheck(password);
    }
}