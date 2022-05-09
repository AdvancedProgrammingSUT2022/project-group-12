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

    private MenuStack() {

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
        this.scanner = scanner;
    }

    private ArrayList<Menu> getMenus() {
        return menus;
    }

    public boolean isEmpty() {
        return this.getMenus().isEmpty();
    }

    public void pushMenu(Menu menu) {
        this.getMenus().add(menu);
    }

    public void popMenu() {
        this.getMenus().remove(this.getMenus().size() - 1);
    }

    public void runTopMenu() {
        this.getMenus().get(this.getMenus().size() - 1).run();
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

    public void gotoMainMenu() {
        this.pushMenu(this.mainMenu);
    }

    public void gotoProfileMenu() {
        this.pushMenu(this.profileMenu);
    }

    public String getOption(String firstMessage, String secondMessage) {
        String message;
        System.out.println(firstMessage);
        message = this.scanner.nextLine();
        System.out.println(secondMessage);
        return message;
    }

    public String getOption(String firstMessage) {
        System.out.println(firstMessage);
        return this.scanner.nextLine();
    }

    public String getOption() {
        return this.scanner.nextLine();
    }
}