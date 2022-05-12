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
            menuStack.getTopMenu().run();
        }

//        Database.getInstance().serialize();
    }
}
/*
user create --username alireza --password Password123? --nickname ali
user login -p Password123? -u alireza
play game --player1 alireza
map show
select unit noncombat -p 10 10
unit fortify
 */