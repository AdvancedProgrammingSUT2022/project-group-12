import Views.LoginMenu;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.pushMenu(new LoginMenu());
        while (!menuStack.isEmpty()) {

            menuStack.runTopMenu();
        }
    }
}