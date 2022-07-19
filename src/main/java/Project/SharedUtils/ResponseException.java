package Project.SharedUtils;

public class ResponseException extends RuntimeException {
    private final CommandResponse response;
    private String successMessage;

    public ResponseException(CommandResponse commandResponse) {
        super(commandResponse.toString());
        this.response = commandResponse;
    }
    public ResponseException(CommandResponse commandResponse,String successMessage) {
        super(commandResponse.toString());
        this.response = commandResponse;
        this.successMessage = successMessage;
    }

    @Override
    public String toString() {
        return "Error, " + super.getMessage();
    }

    public CommandResponse getResponse() {
        return response;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}
