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

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public HashMap<String, String> getParametersMap() {
        return parameters;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
