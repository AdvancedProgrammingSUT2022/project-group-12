import Models.Database;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Database.getInstance().deserialize();
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
/*
user create --username alireza --password Password123? --nickname ali
user login -p Password123? -u alireza
play game --player1 alireza
map show
unit move noncombat -p 9 2
 */