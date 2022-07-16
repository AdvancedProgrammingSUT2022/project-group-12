package Project.Utils;

public enum DatabaseQueryType {
    GET_ALL_USERNAMES,/*("SELECT username FROM users")*/
    GET_USER_BY_USERNAME,
    GET_ALL_USERS,
    GET_CIV_TILES_LOCATIONS,
    GET_CIV_RESOURCES,/*"SELECT * FROM users WHERE username=$0"*/
    GET_TILEGRID_SIZE,
    GET_CIV_UNITS, GET_ALL_UNITS_ENUMS, GET_ALL_BUILDING_ENUMS;
}
