import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.runTopMenu();
        }
    }
}
/*
user create --username ali --nickname A --password A12345678!
user login -u ali -p A12345678!
play game --player1 ali
 */