package Project.Utils;

public class ResponseException extends RuntimeException {
    private final CommandResponse response;

    public ResponseException(CommandResponse commandResponse) {
        super(commandResponse.toString());
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
