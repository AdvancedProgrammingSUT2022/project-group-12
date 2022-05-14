package Utils;

public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(CommandResponse commandResponse) {
        super(commandResponse.toString());
    }

    public CommandException(CommandResponse commandResponse, String item) {
        super(commandResponse.toString() + ": " + item);
    }

    @Override
    public String toString() {
        return "Error, " + super.getMessage();
    }
}
