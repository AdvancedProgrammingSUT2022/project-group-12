package Utils;

public enum CommandResponse {
    OK("OK"),
    CommandMissingRequiredOption("Required option is missing"),
    USER_DOES_NOT_EXISTS("username cannot be found"),
    USERNAME_ALREADY_EXISTS("there is another user with this username"),
    NO_USER_EXIST_WITH_USERNAME("no user exist with this username"),
    WEAK_PASSWORD("weak password"),
    PASSWORD_DOES_NOT_MATCH("incorrect password"),
    INVALID_POSITION("invalid position"),
    INVALID_CITY("city name is incorrect"),
    INVALID_COMMAND("invalid command"),
    INVALID_SUBCOMMAND("invalid sub command"),
    INVALID_SUBSUBCOMMAND("invalid sub sub command"),
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
    YOU_HAVE_NOT_REQUIRED_OPTIONS("you can't build here"),
    RAILROAD_EXISTS("there is already railroad"),
    IMPROVEMENT_EXISTS("this improvement is already exists"),
    NOT_HAVING_UNIT("this unit isn't belong to you"),
    IMPOSSIBLE_CITY("building city is impossible here"),
    CITY_DOES_NOT_EXISTS("there isn't any city here"),
    COMMAND_MISSING_REQUIRED_OPTION("Required option is missing"),
    NICKNAME_ALREADY_EXISTS("user with nickname already exists"),
    INVALID_PASSWORD("current password is invalid"),
    REPEATING_PASSWORD("please enter a new password"),
    PASSWORD_CHANGED("password changed successfully"),
    TILE_IS_FULL("this tile is full"),
    NOT_HAVING_CITY("this city is not belong to you"),
    UNIT_ISNOT_SIEGE("this unit isn't siege unit"),
    UNIT_HAS_ALREADY_SETTED_UP("this unit has already setted up"),
    NOT_ENOUGH_MOVEMENT_COST("there isn't enough movement cost"),
    DUPLICATE_OPTION_KEY("duplicate option key"),
    INVALID_COMMAND_FORMAT("command format is invalid"),
    MISSING_REQUIRED_OPTION("missing required option"),
    UNRECOGNIZED_OPTION("unrecognized option entered"),
    INVALID_OPTION_TYPE("invalid option type"),
    IMPOSSIBLE_MOVE("this move is impossible"),
    UNASSIGNED_CITIZEN("unassigned citizen in city"),
    CITY_NOT_SELECTED("no city selected"),
    NO_UNASSIGNED_CITIZEN("all of citizens already assigned"),
    CITIZEN_ALREADY_WORKING_ON_TILE("a citizen already working on this tile"),
    EMPTY_PRODUCTION_QUEUE("production queue is empty"),
    NO_CITIZEN_ON_TILE("there is no citizen on the tile");

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