package Views;

import Controllers.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuStack {
    public final ProfileMenuController profileController = new ProfileMenuController();
    public final MainMenuController menuController = new MainMenuController();
    public final LoginMenuController loginController = new LoginMenuController();
    public final CivilizationController civController = new CivilizationController();


    private static MenuStack instance = null;
    private final ArrayList<Menu> menus = new ArrayList<>();
    private Scanner scanner;

    private MenuStack() {
    }

    private static void setInstance(MenuStack instance) {
        MenuStack.instance = instance;
    }

    public static MenuStack getInstance() {
        if (instance == null) setInstance(new MenuStack());
        return instance;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
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

    public void logout() {
        while (!this.getMenus().isEmpty()) {
            this.getMenus().remove(this.getMenus().size() - 1);
        }
        this.getMenus().add(new LoginMenu());
    }
}