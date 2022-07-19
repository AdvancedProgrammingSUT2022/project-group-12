package Project.Server.Utils;

import Project.SharedUtils.CommandResponse;

public class CommandException extends Exception {
    private final CommandResponse response;

    public CommandException(CommandResponse commandResponse) {
        super(commandResponse.toString());
        this.response = commandResponse;
    }

    public CommandException(CommandResponse commandResponse, String item) {
        super(commandResponse.toStringWithItem() + ": " + item);
        this.response = commandResponse;
    }

    @Override
    public String toString() {
        return "Error, " + super.getMessage();
    }

    public CommandResponse getResponse() {
        return response;
    }
}
