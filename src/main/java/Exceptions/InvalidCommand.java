package Exceptions;

public class InvalidCommand extends Exception {
    public InvalidCommand(String message) {
        super(message);
    }
}