package Project.Utils;

import java.util.HashMap;

public class Response {
    private final CommandResponse commandResponse;
    private final HashMap<String, String> parameters = new HashMap<>();

    public Response(CommandResponse commandResponse) {
        this.commandResponse = commandResponse;
    }

    public CommandResponse getCommandResponse() {
        return commandResponse;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
}
