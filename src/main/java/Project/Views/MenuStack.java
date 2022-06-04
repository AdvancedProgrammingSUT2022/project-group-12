package Project.Views;

import Project.App;
import Project.Models.Database;
import Project.Models.User;

import java.util.ArrayList;

public class MenuStack {
    private static final MenuStack instance = new MenuStack();
    private final ArrayList<Menu> menus = new ArrayList<>();
    private User currentUser;

    public MenuStack() {
        User user1 = new User("uname", "password", "nickname");
        new User("uname2", "password", "nickname1");
        new User("uname3", "password", "nickname2");
        new User("uname4", "password", "nickname3");
        new User("uname5", "password", "nickname4");
        user1.startChat("chat1", Database.getInstance().getUsers());
        user1.startChat("chat2", Database.getInstance().getUsers());
        user1.startChat("chat3", Database.getInstance().getUsers());
        user1.startChat("chat4", Database.getInstance().getUsers());
        this.setUser(user1);
    }

    public static MenuStack getInstance() {
        return instance;
    }

    public User getUser() {
        return this.currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void pushMenu(Menu menu) {
        this.menus.add(menu);
        this.updateScene();
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void updateScene() {
        if (this.getTopMenu() != null) App.setRoot(this.getTopMenu().getRoot());
    }

    public Menu getTopMenu() {
        return this.menus.isEmpty() ? null : this.menus.get(this.menus.size() - 1);
    }

    public void popMenu() {
        this.menus.remove(this.menus.size() - 1);
        if (!this.isEmpty()) this.updateScene();
    }

    public boolean isEmpty() {
        return this.menus.isEmpty();
    }

    public void popToLogin() {
        this.menus.remove(this.menus.size() - 1);
        if (!this.isEmpty()) this.updateScene();
    }
}