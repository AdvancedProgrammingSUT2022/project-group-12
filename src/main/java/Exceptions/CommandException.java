package Exceptions;

public abstract class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public void print() {
        System.out.println("Error, " + this.getMessage());
    }
}