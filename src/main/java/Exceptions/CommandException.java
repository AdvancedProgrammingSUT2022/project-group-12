package Exceptions;

import Utils.CommandResponse;

public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(CommandResponse commandResponse) {
        super(commandResponse.toString());
    }

    public void print() {
        System.out.println("Error, " + this.getMessage());
    }
}
