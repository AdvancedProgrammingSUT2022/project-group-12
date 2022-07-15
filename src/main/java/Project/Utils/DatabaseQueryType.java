package Project.Utils;

public enum DatabaseQueryType {
    GET_ALL_USERNAMES,/*("SELECT username FROM users")*/
    GET_USER_BY_USERNAME,
    GET_ALL_USERS;/*"SELECT * FROM users WHERE username=$0"*/;
}
