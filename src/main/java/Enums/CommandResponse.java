package Enums;

public enum CommandResponse {
    OK("OK"),
    CommandMissingRequiredOption("Required option is missing"),
    USER_DOES_NOT_EXISTS("user doesn't exists"),
    INVALID_POSITION("invalid position"),
    INVALID_CITY("city name is incorrect"),
    INVALID_COMMAND("invalid command"),
    INVALID_NUMBER("invalid number"),
    INVALID_DIRECTION("invalid direction"),
    NOT_HAVING_TILE("this tile is not yours"),
    UNIT_DOES_NOT_EXISTS("unit doesn't exists"),
    WRONG_UNIT("the unit is wrong!"),
    NOT_DAMAGED("the tile is undamaged!"),
    ROUTE_DOES_NOT_EXISTS("there isn't any route here"),
    JUNGLE_DOES_NOT_EXISTS("there isn't any jungle here"),
    ROAD_EXISTS("there is already road"),
    FARM_EXISTS("there is already farm!"),
    YOU_HAVE_NOT_REQUIRED_OPTIONS("you can build here"),
    RAILROAD_EXISTS("there is already railroad"),
    IMPROVEMENT_EXISTS("this improvement is already exists"),
    NOT_HAVING_UNIT("this unit isn't belong to you"),
    IMPOSSIBLE_CITY("building city is impossible here"),
    CITY_DOES_NOT_EXISTS("there isn't any city here"),
    COMMAND_MISSING_REQUIRED_OPTION("Required option is missing"),
    NICKNAME_ALREADY_EXISTS("user with nickname already exists"),
    NICKNAME_CHANGED("user with nickname already exists"),
    INVALID_PASSWORD("current password is invalid"),
    REPEATING_PASSWORD("please enter a new password"),
    PASSWORD_CHANGED("password changed successfully"), TILE_IS_FULL("this tile is full");
    private String message;

    CommandResponse(String message) {
        this.message = message;
    }

    public CommandResponse nicknameExists(String nickname) {
        this.message = "user with nickname " + nickname + " already exists";
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