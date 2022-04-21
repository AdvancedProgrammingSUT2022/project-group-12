package Controllers;

import Enums.CommandResponse;

public class LoginMenuController {
    public static CommandResponse createUser(String username, String nickname, String password) {
        return CommandResponse.OK;
    }

    public static CommandResponse loginUser(String username, String password) {
        return CommandResponse.OK;
    }

    public CommandResponse logout() {
        return CommandResponse.OK;
    }

    public CommandResponse startNewGame() {
        return CommandResponse.OK;
    }

    public CommandResponse enterMenu() {
        return CommandResponse.OK;
    }

    public CommandResponse exitMenu() {
        return CommandResponse.OK;
    }

    public CommandResponse playGame() {
        return CommandResponse.OK;
    }
}