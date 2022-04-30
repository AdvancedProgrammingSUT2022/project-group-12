package Views;

import Exceptions.CommandException;
import Utils.Command;

import java.util.Scanner;

public abstract class Menu {

    public void run() {
        Scanner scanner = MenuStack.getInstance().getScanner();
        String line = scanner.nextLine().trim();
        try {
            Command command = Command.parseCommand(line);
            handleCommand(command);
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void handleCommand(Command command);
}
