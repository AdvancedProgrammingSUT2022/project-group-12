package Exceptions;

public class DuplicateOptionKey extends CommandException {
    public DuplicateOptionKey(String theOption) {
        super("duplicate option key: " + theOption);
    }
}
