package Server.Utils;

import Project.Models.User;
import Server.Views.MenuStack;

import java.util.ArrayList;

public class MenuStackManager {
    private static final MenuStackManager instance = new MenuStackManager();
    private ArrayList<MenuStack> menuStacks = new ArrayList<>();

    public static MenuStackManager getInstance() {
        return instance;
    }

    public void addMenuStackFor(String token, User user) {

    }
}
