package Controllers;

import Enums.CommandResponse;

public class ProfileMenuController {
    public static CommandResponse changePass(String oldPass, String newPass){
        return CommandResponse.OK;
    }

    public static CommandResponse changeNickname(String newNickname) {
        return CommandResponse.OK;
    }
}
