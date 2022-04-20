package Enums;

public enum CommandResponse {
    OK("OK"),
    INVALID_COMMAND("invalid command!!"),
    COMMAND_MISSING_REQUIRED_OPTION("Required option is missing"),
    NICKNAME_ALREADY_EXISTS("user with nickname already exists"),
    NICKNAME_CHANGED("user with nickname already exists"),
    INVALID_PASSWORD("current password is invalid"),
    REPEATING_PASSWORD("please enter a new password"),
    PASSWORD_CHANGED("password changed successfully");


    private String message;

    CommandResponse(String message) {
        this.message = message;
    }

    public CommandResponse nicknameExists(String nickname){
        this.message = "user with nickname "+nickname+" already exists";
        return null;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public boolean isOK() {
        return this == CommandResponse.OK;
    }
}
