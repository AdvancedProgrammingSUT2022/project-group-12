package Exceptions;

public class UnrecognizedOption extends CommandException {
    public UnrecognizedOption(String theOption) {
        super("unrecognized option entered: " + theOption);
    }
}
