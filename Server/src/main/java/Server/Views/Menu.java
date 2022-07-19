package Server.Views;

import Project.Utils.CommandResponse;
import Project.Utils.ResponseException;
import Server.Utils.Command;
import Server.Utils.CommandException;

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

    public CommandResponse runCommand(String line) {
        if (isFirstRun) {
            this.isFirstRun = false;
            this.firstRun();
        }
        try {
            Command command = Command.parseCommand(line);
            handleCommand(command);
        } catch (CommandException e) {
            answer(e);
        }
        return CommandResponse.OK;
    }

    public void firstRun() {

    }

    protected abstract void handleCommand(Command command);

    public String getName() {
        return this.getClass().getSimpleName();
    }

    protected void answer(Object message) {
        System.out.println(message);
        if (message instanceof CommandException commandException) {
            throw new ResponseException(commandException.getResponse());
        } else if (message instanceof String string){
            throw new ResponseException(CommandResponse.OK,string);
        }
    }

    protected void resetShowName() {
        this.showName = true;
    }
}
