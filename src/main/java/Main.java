import Controllers.Command;
import Exceptions.InvalidCommand;
import Views.LoginMenu;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InvalidCommand {
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        System.out.println(Command.parseCommand("username jief  jfi -u ali asghar --fjaiee aioefj"));
        menuStack.pushMenu(new LoginMenu());
        while (!menuStack.isEmpty()) {
            menuStack.runTopMenu();
        }
    }
}