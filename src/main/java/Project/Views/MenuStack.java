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
        this.getMenus().add(menu);
        this.updateScene();
    }

    private ArrayList<Menu> getMenus() {
        return menus;
    }

    public void updateScene() {
        if (this.getTopMenu() != null) App.setRoot(this.getTopMenu().getRoot());
    }

    public Menu getTopMenu() {
        return this.getMenus().isEmpty() ? null : this.getMenus().get(this.getMenus().size() - 1);
    }

    public void popMenu() {
        this.getMenus().remove(this.getMenus().size() - 1);
        if (!this.isEmpty()) updateScene();
    }

    public boolean isEmpty() {
        return this.getMenus().isEmpty();
    }
}