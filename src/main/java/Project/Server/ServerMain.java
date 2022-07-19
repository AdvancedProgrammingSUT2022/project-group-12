package Project.Server;

import Project.Server.Models.Database;
import Project.Server.Views.MenuStack;

import java.util.Scanner;

public class ServerMain {
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
