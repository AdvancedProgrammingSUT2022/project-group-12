package Exceptions;

public class InvalidCommandFormat extends CommandException {
    public InvalidCommandFormat() {
        super("command format is invalid");
    }

    public InvalidCommandFormat(int pos) {
        super("command format is invalid, at pos " + (pos + 1)); // one-base index
    }
}

