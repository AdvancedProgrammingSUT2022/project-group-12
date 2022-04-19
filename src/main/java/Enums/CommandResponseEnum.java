package Enums;

public enum CommandResponseEnum {
    OK("OK"),
    INVALID_COMMAND("invalid command!!"),
    CommandMissingRequiredOption("Required option is missing"),
    USER_DOESNT_EXISTS("user doesn't exists");

    private final String message;

    CommandResponseEnum(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public boolean isOK() {
        return this == CommandResponseEnum.OK;
    }
}
