package Project.Client.Views;

import Project.Client.App;
import Project.Client.Utils.Cookies;
import Project.Models.User;

import java.util.ArrayList;

public class MenuStack {
    private static final MenuStack instance = new MenuStack();
    private final ArrayList<Menu> menus = new ArrayList<>();
    private User currentUser;
    private final Cookies cookies = new Cookies();

    public MenuStack() {
        new User("1","1","nick1");
        new User("alireza","password","ali");
        new User("username1","password","nickname1");
        new User("username2","password","nickname2");
        new User("username3","password","nickname3");
        new User("username4","password","nickname4");
        new User("username5","password","nickname5");
//        new User("username6","password","nickname6");
//        new User("username7","password","nickname7");
//        new User("username8","password","nickname8");
//        new User("username9","password","nickname9");
//        new User("username10","password","nickname10");
//        new User("username11","password","nickname11");
//        new User("username12","password","nickname12");
//        new User("username13","password","nickname13");
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
        if (!this.isEmpty())
            this.updateScene();
    }

    public boolean isEmpty() {
        return this.menus.isEmpty();
    }

    public void popToLogin() {
        popMenu();
        popMenu();
    }

    public Cookies getCookies() {
        return cookies;
    }
}