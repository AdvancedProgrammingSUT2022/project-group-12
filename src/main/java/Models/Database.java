package Models;

import java.util.HashMap;

public class Database {
    private final HashMap<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void serialize() {

    }

    public static Database deserialize() {
        return null;
    }
}
