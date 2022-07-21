package Project.Utils;


public enum DatabaseQueryType {
    GET_ALL_USERNAMES,/*("SELECT username FROM users")*/
    GET_USER_BY_USERNAME,
    GET_ALL_USERS,
    GET_SELECTED_CITY_PRODUCTION_QUEUE,
    GET_CIV_TILES_LOCATIONS,
    GET_CIV_RESOURCES,/*"SELECT * FROM users WHERE username=$0"*/
    GET_CIV_RESOURCES_BY_NAME,
    GET_TILEGRID_SIZE,
    GET_CURRENTCIV_FOOD,
    GET_CURRENTCIV_GOLD,
    GET_CURRENTCIV_SCIENCE,
    GET_NEIGHBORS_CURRENTCIV_NAMES,
    GET_CURRENTCIV_UNITS_NAMES,
    GET_CURRENTCIV_UNITS_LOCATIONS,
    GET_CURRENTCIV_CITIES_NAMES,
    GET_CURRENTCIV_CITIES_LOCATION_BY_NAME,
    GET_SELECTED_UNIT,
    GET_CIV_UNITS,
    GET_ALL_UNITS_ENUMS,
    GET_CURRENTCIV_INPEACEWITH,
    GET_ALL_BUILDING_ENUMS,
    GET_CURRENTCIV_HAPPINESS,
    GET_CURRENTCIV_INWARWITH,
    GET_CIV_GOLD_BY_NAME,
    GET_SELECTED_CITY,
    GET_TILE_BY_LOCATION,
    GET_CURRENT_CIV_TECHNOLOGIES,
    GET_CURRENTCIV_NOTIFICATIONS,
    GET_TECHNOLOGIES,
    GET_RESEARCHING_TECHNOLOGIES,
    GET_RESEARCHING_TECHNOLOGY,
    GET_CURRENTCIV_NAME,
    GET_TILE_GRID,
     GET_SELECTED_CITY_GOLD,
             GET_SELECTED_CITY_PRODUCTION,
            GET_SELECTED_CITY_FOOD,
    GET_AVAILABLE_BUILDINGS_NAME,
    GET_AVAILABLE_UNITS_NAME,
    GET_CIV_CAMERA_LOCATION,
    GET_SELECTED_CITY_UNASSIGNED_CITIZEN,
    GET_SELECTED_CITY_ASSIGNED_CITIZEN, GET_INVITED_GAMES_NAMES,
    SEND_FRIEND_REQUEST,
    ACCEPT_FRIEND_REQUEST,
    DENY_FRIEND_REQUEST,
}
