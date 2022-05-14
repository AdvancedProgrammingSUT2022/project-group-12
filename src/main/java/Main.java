import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //     Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.getTopMenu().run();
        }
        //     Database.getInstance().serialize();
    }
}
/*
user create --username alireza --password Password123? --nickname ali
user create --username alireza2 --password Password123? --nickname ali2
user login -p Password123? -u alireza
play game --player1 alireza --player2 alireza2
map show
select unit noncombat -p 49 29
unit found city
select city -p 49 30
cheat spawn unit -p 51 29 -u warrior
cheat increase gold -a 100
city build unit -u warrior
city citizen assign -p 48 29
unit move
unit fortify
 */