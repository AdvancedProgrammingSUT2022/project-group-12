package Controllers;

import Enums.CommandResponseEnum;

public class ProfileMenuController {
    public static CommandResponseEnum changePass(String oldPass, String newPass){
        return CommandResponseEnum.OK;
    }

    public static CommandResponseEnum changeNickname(String newNickname) {
        return CommandResponseEnum.OK;
    }
}
