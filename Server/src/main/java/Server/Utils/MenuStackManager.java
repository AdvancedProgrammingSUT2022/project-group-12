package Server.Utils;

import Project.Models.User;
import Server.Views.MenuStack;

import java.util.HashMap;

public class MenuStackManager {
    private static final MenuStackManager instance = new MenuStackManager();
    private final HashMap<String, MenuStack> menuStacksMap = new HashMap<>();

    public static MenuStackManager getInstance() {
        return instance;
    }

    public void addMenuStackFor(String token, User user) {
        this.menuStacksMap.put(token, new MenuStack(user));
    }

    public MenuStack getMenuStackOf(String token) {
        MenuStack menuStack = this.menuStacksMap.get(token);
        if (menuStack.isValid()) return menuStack;
        else {
            this.menuStacksMap.remove(token);
            return null;
        }
    }
}
