package Project.Utils;

public enum CommandResponse {
    OK("OK"),
    CommandMissingRequiredOption("Required option is missing"),
    USER_DOES_NOT_EXISTS("username cannot be found"),
    USERNAME_ALREADY_EXISTS("there is another user with this username"),
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
    WRONG_UNIT("wrong unit", ", you need"),
    NOT_DAMAGED("the tile is undamaged!"),
    ROUTE_DOES_NOT_EXISTS("there isn't any route here"),
    JUNGLE_DOES_NOT_EXISTS("there isn't any jungle here"),
    ROAD_EXISTS("there is already road"),
    FARM_EXISTS("there is already farm!"),
    YOU_HAVE_NOT_REQUIRED_OPTIONS("you can't build here"),
    RAILROAD_EXISTS("there is already railroad"),
    IMPROVEMENT_EXISTS("this improvement is already exists"),
    NOT_HAVING_UNIT("this unit isn't belong to you"),
    CLOSE_TO_A_CITY("minimum distance of two cities is 4 tiles"),
    CITY_DOES_NOT_EXISTS("there isn't any city here"),
    COMMAND_MISSING_REQUIRED_OPTION("Required option is missing"),
    NICKNAME_ALREADY_EXISTS("user with nickname already exists"),
    REPEATED_PASSWORD("please enter a new password"),
    PASSWORD_CHANGED("password changed successfully"),
    TILE_IS_FULL("this tile is full"),
    NOT_HAVING_CITY("this city is not belong to you"),
    UNIT_IS_NOT_SIEGE("this unit isn't siege unit"),
    UNIT_HAS_ALREADY_SAT_UP("this unit has already sat up"),
    NOT_ENOUGH_MOVEMENT_COUNT("there isn't enough movement count"),
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
    NO_CITIZEN_ON_TILE("there is no citizen on the tile"),
    UNIT_NOT_SELECTED("no unit selected"),
    NOT_YOUR_TERRITORY("tile isn't in city's territory"),
    UNIT_IS_NOT_SLEEP("unit isn't sleep"),
    UNIT_IS_SLEEP("unit is sleep"),
    UNIT_IS_FORTIFIED("unit is fortified"),
    UNIT_NEED_ORDER("unit need order"),
    NOT_ENOUGH_GOLD("not enough gold for buy"),
    NOT_ADJACENT_TO_CITY_TERRITORY("tile is not adjacent to city territory"),
    ALREADY_FOR_A_CITY("tile is already for a city"),
    INVALID_UNIT_NAME("unit name not recognized"),
    COMBAT_UNIT_ALREADY_ON_TILE("a combat unit is already on tile"),
    NONCOMBAT_UNIT_ALREADY_ON_TILE("a non combat unit is already on tile"),
    UNIT_IS_ALREADY_ON_TILE("unit is already on the tile"),
    ONLY_SETTLERS_CAN_FOUND_CITY("only settlers can found new cities"),
    INVALID_NAME("Invalid input name"),
    ENEMY_DOESNT_EXISTS("enemy doesn't exists"),
    ATTACK_ISNT_POSSIBLE("cannot reach tile with current movement count"),
    YOU_CANT_DESTROY_CITY_BY_RANGED_COMBAT("you can't destroy city by ranged combat unit"),
    YOU_CANT_DESTROY_CITY_BY_CITY("you can't destroy city by ranged combat unit"),
    PLAYER_NUMBER_GAP("there is a gap in number of players"),
    IMPROVEMENT_DOESNT_EXISTS("improvement doesn't exists"),
    ATTACK_UNIT_IS_NOT_COMBAT("cannot attack with non combat units!"),
    TARGET_OUT_OF_UNIT_RANGE("target is out of range for this unit"),
    INVALID_COMMAND_KEY_FORMAT("command key format is invalid"),
    NO_RESOURCE_ON_TILE("there is no resource on the tile"),
    DO_NOT_HAVE_REQUIRED_TECHNOLOGY("you don't have required technology"),
    NO_RESEARCHING_TECHNOLOGY("no technology is being researched!"),
    INVALID_TECHNOLOGY_NAME("technology name not recognized"),
    CANNOT_SPAWN_ON_TILE("can't spawn on this tile"),
    TARGET_NOT_REACHABLE("target location is not reachable"),
    END_OF_GAME("game ended"),
    UNIT_CAN_ATTACK_ONCE("unit can attack just once"),
    DO_NOT_HAVE_REQUIRED_RESOURCE("you don't have required resource"),
    IMPASSABLE_TILE_BETWEEN_UNIT_AND_TARGET("an impassable tile is between this unit and the target"),
    NO_CITY_FOUNDED("you don't have any city"),
    SIEGE_NOT_SETUP("siege is not setup"),
    AMBIGUOUS_UNIT_SELECTION("please select combat or noncombat"),
    WORKER_IS_ALREADY_WORKING("worker is already working"),
    NO_BUILDING_WITH_THIS_NAME("there is no building with this name"), NO_RESOURCE_WITH_THIS_NAME("No resource with this name exists"),
    NO_TRADE_WITH_THIS_NAME("No trade with this name"),
    NO_UNIT_SELECTED("no unit selected");

    private String message;
    private final String itemMessage;

    CommandResponse(String message) {
        this.message = message;
        this.itemMessage = "---";
    }

    CommandResponse(String message, String itemMessage) {
        this.message = message;
        this.itemMessage = itemMessage;
    }
    public void setMessage(String message){
        this.message = message;
    }
    @Override
    public String toString() {
        return this.message;
    }

    public String toStringWithItem() {
        return this.toString() + ": " + this.itemMessage;
    }

    public boolean isOK() {
        return this == CommandResponse.OK;
    }

    public String getMessage() {
        return message;
    }
}