package Models;

import java.util.HashMap;

public class Database {
    private HashMap<String, User> users = new HashMap<>();
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

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void serialize() {

    }

    public void addUserToList(User user) {
        this.users.put(user.getUsername(), user);
    }

    public boolean checkForUsername(String username) {
        return this.users.containsKey(username);
    }

    public void deserialize() {

    }
}