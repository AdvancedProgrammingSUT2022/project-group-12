package Project.Views;

import Project.App;
import Project.Models.User;

import java.util.ArrayList;

public class MenuStack {
    private static final MenuStack instance = new MenuStack();
    private final ArrayList<Menu> menus = new ArrayList<>();
    private User currentUser;

    public MenuStack() {
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
        if (this.getTopMenu() != null) {
            App.setRoot(this.getTopMenu().getRoot());
            this.getTopMenu().getController().loadEachTime();
        }
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