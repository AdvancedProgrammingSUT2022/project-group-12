package Exceptions;

public class MissingRequiredOption extends CommandException {
    public MissingRequiredOption(String theOption) {
        super("missing required option: " + theOption);
    }
}
