package Views;

import Utils.Command;
import Utils.CommandException;

import java.util.Scanner;

public abstract class Menu {

    private boolean showName = true;
    private boolean isFirstRun = true;

    public void run() {
        if (showName) {
            this.showName = false;
            String str = "Entered " + this.getName();
            System.out.println(str);
            System.out.println("-".repeat(str.length()));
        }
        if (isFirstRun) {
            this.isFirstRun = false;
            this.firstRun();
        }
        Scanner scanner = MenuStack.getInstance().getScanner();
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) return;
        try {
            Command command = Command.parseCommand(line);
            handleCommand(command);
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void firstRun() {

    }

    protected abstract void handleCommand(Command command);

    public String getName() {
        return this.getClass().getSimpleName();
    }

    protected void resetShowName() {
        this.showName = true;
    }

    protected void answer(Object message) {
        System.out.println(message);
    }
}
