package Exceptions;

public class UnknownUser extends CommandException {
    public UnknownUser(String username) {
        super("unknown username: " + username);
    }
}
