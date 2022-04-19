package Enums;

public enum CommandResponseEnum {
    OK("OK"), CommandMissingRequiredOption("Required option is missing");

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
