package Project.Client.Views;

import Project.Client.App;
import Project.Models.User;

import java.util.ArrayList;

public class MenuStack {
    private static final MenuStack instance = new MenuStack();
    private final ArrayList<Menu> menus = new ArrayList<>();
    private User currentUser;

    public MenuStack() {
        new User("1","1","nick1");
        new User("alireza","password","ali");
        new User("username1","password","nickname1");
        new User("username2","password","nickname2");
        new User("username3","password","nickname3");
        new User("username4","password","nickname4");
        new User("username5","password","nickname5");
        new User("username6","password","nickname6");
        new User("username7","password","nickname7");
        new User("username8","password","nickname8");
        new User("username9","password","nickname9");
        new User("username10","password","nickname10");
        new User("username11","password","nickname11");
        new User("username12","password","nickname12");
        new User("username13","password","nickname13");
//        new User("username14","password","nickname14");
//        new User("username15","password","nickname15");
//        new User("username16","password","nickname16");
//        new User("username17","password","nickname17");
//        new User("username18","password","nickname18");
//        new User("username19","password","nickname19");
//        new User("username20","password","nickname20");
//        new User("username21","password","nickname21");
//        new User("username22","password","nickname22");
//        new User("username23","password","nickname23");
//        new User("username24","password","nickname24");
//        new User("username25","password","nickname25");
//        new User("username26","password","nickname26");
//        new User("username27","password","nickname27");
//        new User("username28","password","nickname28");
//        new User("username29","password","nickname29");
//        new User("username30","password","nickname30");
//        new User("username31","password","nickname31");
//        new User("username99","password","nickname99");
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
}