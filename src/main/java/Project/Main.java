package Project;

import Project.Models.Database;
import Project.ServerViews.MenuStack;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.getTopMenu().run();
        }
        Database.getInstance().serialize();
    }
}
