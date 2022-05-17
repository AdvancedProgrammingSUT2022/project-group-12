package Views;

import Models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuStack {
    private static MenuStack instance = null;
    private final ArrayList<Menu> menus = new ArrayList<>();
    private final LoginMenu loginMenu = new LoginMenu();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private Scanner scanner;
    private User currentUser;

    public MenuStack() {

    }

    public static MenuStack getInstance() {
        if (instance == null) setInstance(new MenuStack());
        return instance;
    }

    private static void setInstance(MenuStack instance) {
        MenuStack.instance = instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        if (this.scanner == null) this.scanner = scanner;
    }

    public void setNullScanner() {
        this.scanner = null;
    }

    public boolean isEmpty() {
        return this.getMenus().isEmpty();
    }

    private ArrayList<Menu> getMenus() {
        return menus;
    }

    public User getUser() {
        return this.currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void gotoLoginMenu() {
        this.currentUser = null;
        if (this.menus.isEmpty()) {
            this.pushMenu(this.loginMenu);
        } else {
            this.popMenu();
        }
    }

    public void pushMenu(Menu menu) {
        if (this.getTopMenu() != null) this.getTopMenu().resetShowName();
        this.getMenus().add(menu);
    }

    public Menu getTopMenu() {
        return this.getMenus().isEmpty() ? null : this.getMenus().get(this.getMenus().size() - 1);
    }

    public void popMenu() {
        this.getMenus().remove(this.getMenus().size() - 1);
    }

    public void gotoMainMenu() {
        this.pushMenu(this.mainMenu);
    }

    public void gotoProfileMenu() {
        this.pushMenu(this.profileMenu);
    }

    public String getOption(String firstMessage) {
        System.out.println(firstMessage);
        return this.scanner.nextLine();
    }

    public String getOption() {
        return this.scanner.nextLine();
    }
}