package Exceptions;

public class InvalidCommandFormat extends CommandException {
    public InvalidCommandFormat() {
        super("command format is invalid");
    }
}

