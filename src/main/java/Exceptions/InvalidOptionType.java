package Exceptions;

public class InvalidOptionType extends CommandException {
    public InvalidOptionType(String theOption, String theType) {
        super("invalid option type: " + theOption + " must be " + theType);
    }
}
