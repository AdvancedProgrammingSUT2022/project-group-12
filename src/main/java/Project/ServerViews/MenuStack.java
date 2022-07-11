package Project.ServerViews;

import Project.Models.User;
import Project.Utils.CommandResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MenuStack {
    private static MenuStack instance = null;
    private final ArrayList<Menu> menus = new ArrayList<>();
    private final LoginMenu loginMenu = new LoginMenu();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private Scanner scanner;
    private User currentUser;
    private HashMap<String, String> responseParameters = new HashMap<>();

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

    public void gotoLoginMenu() {
        this.currentUser = null;
        if (this.menus.isEmpty()) {
            this.pushMenu(this.loginMenu);
        } else {
            this.popMenu();
        }
    }

    public CommandResponse runCommand(String line) {
        return this.getTopMenu().runCommand(line);
    }

    public User getUser() {
        return this.currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void pushMenu(Menu menu) {
        if (this.getTopMenu() != null) this.getTopMenu().resetShowName();
        this.getMenus().add(menu);
    }

    public Menu getTopMenu() {
        return this.getMenus().isEmpty() ? null : this.getMenus().get(this.getMenus().size() - 1);
    }

    private ArrayList<Menu> getMenus() {
        return menus;
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

    public HashMap<String, String> getResponseParameters() {
        return responseParameters;
    }

    public void setResponseParameters(HashMap<String, String> responseParameters) {
        this.responseParameters = responseParameters;
    }
}