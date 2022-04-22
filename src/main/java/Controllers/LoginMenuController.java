package Controllers;

import Enums.CommandResponse;

public class LoginMenuController { // todo
    public static CommandResponse createUser(String username, String nickname, String password) {
        return CommandResponse.OK;
    }

    public static CommandResponse loginUser(String username, String password) {
        return CommandResponse.OK;
    }
}