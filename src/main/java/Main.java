import Models.Database;
import Views.LoginMenu;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Database data = Database.getInstance();
        data.deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.pushMenu(new LoginMenu());
        while (!menuStack.isEmpty()) {
            menuStack.runTopMenu();
        }
        data.serialize();
    }
}