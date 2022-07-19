package Project.Utils;

public class Response {
    private final CommandResponse commandResponse;

    public Response(CommandResponse commandResponse) {
        this.commandResponse = commandResponse;
    }

    public CommandResponse getCommandResponse() {
        return commandResponse;
    }
}
