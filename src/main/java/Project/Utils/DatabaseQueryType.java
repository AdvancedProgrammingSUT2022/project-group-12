package Project.Utils;

public enum DatabaseQueryType {
    GET_ALL_USERNAMES,/*("SELECT username FROM users")*/
    GET_USER_BY_USERNAME,
    GET_ALL_USERS,
    GET_CIV_TILES_LOCATIONS,
    GET_CIV_RESOURCES,/*"SELECT * FROM users WHERE username=$0"*/
    GET_CIV_RESOURCES_BY_NAME,
    GET_TILEGRID_SIZE,
    GET_CURRENTCIV_FOOD,
    GET_CURRENTCIV_GOLD,
    GET_CURRENTCIV_SCIENCE,
    GET_NEIGHBORS_CURRENTCIV_NAMES,
    GET_CIV_UNITS, GET_ALL_UNITS_ENUMS, GET_ALL_BUILDING_ENUMS, GET_CURRENTCIV_HAPPINESS, GET_CURRENTCIV_INWARWITH, GET_CIV_GOLD_BY_NAME;
}
