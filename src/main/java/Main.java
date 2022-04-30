import Models.Database;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.util.HashMap.entrySet()" because "this.users" is null
//        Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.runTopMenu();
        }
        Database.getInstance().serialize();
    }
}
/*
user create --username ali --nickname A --password A12345678!
user login -u ali -p A12345678!
play game --player1 ali
 */