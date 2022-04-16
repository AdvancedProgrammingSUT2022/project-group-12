package Models;

import java.util.HashMap;

public class UserDatebase {
    private HashMap<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void serialize() {

    }

    public static UserDatebase deserialize() {
        return null;
    }
}
