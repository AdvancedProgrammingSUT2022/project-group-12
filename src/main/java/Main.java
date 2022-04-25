import Models.Database;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.runTopMenu();
        }
//        Database.getInstance().serialize();
    }
}