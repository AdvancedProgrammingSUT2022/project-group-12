package Controllers;

import Enums.CommandResponse;
import Models.Database;
import Models.User;
import Views.GameMenu;
import Views.LoginMenu;
import Views.MainMenu;
import Views.MenuStack;

import java.util.regex.Matcher;

public class ProfileMenuController {

    public CommandResponse changePass(Matcher matcher) {
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

    public CommandResponse changeNickname(Matcher matcher) {
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

    public String enterMenu(Matcher matcher) {
        String newMenu = matcher.group("selectedMenu").toLowerCase();
        switch (newMenu) {
            case "login" -> MenuStack.getInstance().pushMenu(new LoginMenu());
            case "main" -> MenuStack.getInstance().pushMenu(new MainMenu());
            case "play game" -> MenuStack.getInstance().pushMenu(new GameMenu());
            case "profile" -> {
                return "already in profile Menu";
            }
        }
        return "menu changed to " + newMenu + " successfully";
    }
}
