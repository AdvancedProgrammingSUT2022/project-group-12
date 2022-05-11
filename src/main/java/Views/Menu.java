package Views;

import Utils.Command;
import Utils.CommandException;

import java.util.Scanner;

public abstract class Menu {

    private boolean showName = true;

    public void run() {
        if (showName) {
            this.showName = false;
            String str = "Entered " + this.getName();
            System.out.println(str);
            System.out.println("-".repeat(str.length()));
        }
        Scanner scanner = MenuStack.getInstance().getScanner();
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return;
        try {
            Command command = Command.parseCommand(line);
            handleCommand(command);
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void handleCommand(Command command);

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void resetShowName() {
        this.showName = true;
    }
}
