package Controllers;

import Enums.CommandResponse;
import Models.Database;
import Views.MenuStack;

import java.util.regex.Matcher;

public class ProfileMenuController {

    public static CommandResponse changePass(Matcher matcher) {
        String oldPass = matcher.group("old");
        String newPass = matcher.group("new");
        if (!MenuStack.getInstance().getUser().passwordMatchCheck(oldPass)) {
            return CommandResponse.INVALID_PASSWORD;
        } else if (oldPass.equals(newPass)) {
            return CommandResponse.REPEATING_PASSWORD;
        } else {
            MenuStack.getInstance().getUser().changePassword(newPass);
            return CommandResponse.OK;
        }
    }

    public static CommandResponse changeNickname(Matcher matcher) {
        String nickname = matcher.group("changeNicknameTo");
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            return CommandResponse.NICKNAME_ALREADY_EXISTS.nicknameExists(nickname);
        } else {
            MenuStack.getInstance().getUser().changeNickname(nickname);
            return CommandResponse.NICKNAME_CHANGED;
        }
    }
}