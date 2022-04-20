package Enums;

public enum CommandResponseEnum {
    OK("OK"), CommandMissingRequiredOption("Required option is missing"), USER_DOESNT_EXISTS("user doesn't exists"),
    INVALID_POSITION("invalid position"), INVALID_CITY("cityname is incorrect"), INVALID_COMMAND("invalid command"),
    INVALID_NUMBER("invalid number"), INVALID_DIRECTION("invalid direction"), NOT_HAVING_TILE("this tile is not yours"),
    UNIT_DOESNT_EXISTS("unit doesn't exists"), WRONG_UNIT("the unit is wrong!"), NOT_DAMAGED("the tile is undamged!"),
    ROUTE_DOESNT_EXISTS("there isn't any route here"), JUNGLE_DOESNT_EXISTS("there isn't any jungle here"), ROAD_EXISTS("there is already road"),
    FARM_EXISTS("there is already farm!"), YOU_HAVE_NOT_REQUIRED_OPTIONS("you can build here"), RAILROAD_EXISTS("there is already railroad"),
    IMPROVMENT_EXISTS("this improvment is already exists"),NOT_HAVING_UNIT("this unit isn't belong to you"),
    IMPOSSIBLE_CITY("building city is impossible here"),CITY_DOESNT_EXISTS("there isn't any city here");
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
