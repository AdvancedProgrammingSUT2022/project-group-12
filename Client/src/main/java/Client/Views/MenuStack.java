package Client.Views;

import Client.App;
import Client.Models.*;
import Project.Models.*;
import Project.Utils.*;

import java.util.ArrayList;

public class MenuStack {
    private static final MenuStack instance = new MenuStack();
    private final ArrayList<Menu> menus = new ArrayList<>();
    private User currentUser;
    private final Cookies cookies = new Cookies();

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
    public void showResponse(CommandResponse commandResponse){
        if(!commandResponse.isOK()){
            this.showError(commandResponse.toString());
        } else {
            this.showSuccess(commandResponse.getMessage());
        }
    }

    public void showError(String message){
        new ErrorDialog(message,"error").showAndWait();
    }

    public void showSuccess(String message){
        new ErrorDialog(message,"success").showAndWait();
    }

    public Cookies getCookies() {
        return cookies;
    }
}