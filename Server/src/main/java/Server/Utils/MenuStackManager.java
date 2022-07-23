package Server.Utils;

import Project.Models.User;
import Server.Views.MenuStack;

import java.util.Collection;
import java.util.HashMap;

public class MenuStackManager {
    private static final MenuStackManager instance = new MenuStackManager();
    private final HashMap<User, MenuStack> menuStacksMap = new HashMap<>();
    private final HashMap<String, User> userMap = new HashMap<>();

    public static MenuStackManager getInstance() {
        return instance;
    }

    public void addMenuStackFor(String token, User user) {
        MenuStack menuStack = new MenuStack(user);
        menuStack.gotoMainMenu();
        this.userMap.put(token, user);
        this.menuStacksMap.put(user, menuStack);
    }

    public MenuStack getMenuStackOfUser(User user) {
        return this.menuStacksMap.get(user);
    }

    public MenuStack getMenuStackByToken(String token) {
        MenuStack menuStack = this.getMenuStackOfUser(this.userMap.get(token));
        if (menuStack == null) return null;
        if (menuStack.isValid()) return menuStack;
        else {
            this.menuStacksMap.remove(userMap.get(token));
            this.userMap.remove(token);
            return null;
        }
    }

    public Collection<MenuStack> getAllMenuStacks() {
        return this.menuStacksMap.values();
    }
}
