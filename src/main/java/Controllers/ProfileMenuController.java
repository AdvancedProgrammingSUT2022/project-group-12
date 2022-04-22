package Controllers;

import Enums.CommandResponse;
import Models.Database;
import Views.LoginMenu;
import Views.MainMenu;
import Views.MenuStack;

import java.util.regex.Matcher;

public class ProfileMenuController {

    public static CommandResponse changePass(Matcher matcher) { // todo
        String oldPass = matcher.group("old");
        String newPass = matcher.group("new");
//        if(!selectedUser.passwordMatchCheck(oldPass)){
//            return CommandResponse.INVALID_PASSWORD;
//        }else if(oldPass.equals(newPass)){
//            return CommandResponse.REPEATING_PASSWORD;
//        }else{
//            selectedUser.changePassword(newPass);
//            return CommandResponse.PASSWORD_CHANGED;
//        }
        return CommandResponse.OK;
    }

    public static CommandResponse changeNickname(Matcher matcher) { // todo
        String nickname = matcher.group("changeNicknameTo");
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            return CommandResponse.NICKNAME_ALREADY_EXISTS.nicknameExists(nickname);
        } else {
            //TODO : SELECT USER IN CONTROL
            //selectedUser.changeNickname(nickname);
            return CommandResponse.NICKNAME_CHANGED;
        }
    }
}