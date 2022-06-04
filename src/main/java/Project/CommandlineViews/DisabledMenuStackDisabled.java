package Project.CommandlineViews;

import Project.Models.User;

import java.util.ArrayList;
import java.util.Scanner;

public class DisabledMenuStackDisabled {
    private static DisabledMenuStackDisabled instance = null;
    private final ArrayList<DisabledMenuDisabled> menuDisableds = new ArrayList<>();
    private final LoginMenu loginMenu = new LoginMenu();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private Scanner scanner;
    private User currentUser;

    public DisabledMenuStackDisabled() {

    }

    public static DisabledMenuStackDisabled getInstance() {
        if (instance == null) setInstance(new DisabledMenuStackDisabled());
        return instance;
    }

    private static void setInstance(DisabledMenuStackDisabled instance) {
        DisabledMenuStackDisabled.instance = instance;
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
        if (this.menuDisableds.isEmpty()) {
            this.pushMenu(this.loginMenu);
        } else {
            this.popMenu();
        }
    }

    public User getUser() {
        return this.currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void pushMenu(DisabledMenuDisabled menuDisabled) {
        if (this.getTopMenu() != null) this.getTopMenu().resetShowName();
        this.getMenus().add(menuDisabled);
    }

    public DisabledMenuDisabled getTopMenu() {
        return this.getMenus().isEmpty() ? null : this.getMenus().get(this.getMenus().size() - 1);
    }

    private ArrayList<DisabledMenuDisabled> getMenus() {
        return menuDisableds;
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