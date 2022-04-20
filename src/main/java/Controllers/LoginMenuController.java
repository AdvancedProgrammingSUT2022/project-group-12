package Controllers;

import Enums.CommandResponseEnum;

public class LoginMenuController {
    public static CommandResponseEnum createUser(String username, String nickname, String password) {
        return CommandResponseEnum.OK;
    }

    public static CommandResponseEnum loginUser(String username, String password) {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum logout() {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum startNewGame() {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum enterMenu() {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum exitMenu() {
        return CommandResponseEnum.OK;
    }

    public CommandResponseEnum playGame() {
        return CommandResponseEnum.OK;
    }
}
