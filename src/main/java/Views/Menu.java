package Views;

import Controllers.Command;
import Exceptions.InvalidCommand;

import java.util.Scanner;

public abstract class Menu {

    public void run() {
        Scanner scanner = MenuStack.getInstance().getScanner();
        String line = scanner.nextLine().trim();
        try {
            Command command = Command.parseCommand(line);
            handleCommand(command);
        } catch (InvalidCommand e) {
            e.printStackTrace();
        }
    }

    protected abstract void handleCommand(Command command);
}
