package Controllers;

import Enums.CommandResponse;
import Enums.InputRegex;
import Models.Database;
import Models.User;
import Views.MenuStack;

public class LoginMenuController {
    public CommandResponse createUser(String username, String nickname, String password) {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(username)) {
            return CommandResponse.USERNAME_ALREADY_EXISTS;
        } else if (database.nicknameAlreadyExists(nickname)) {
            return CommandResponse.NICKNAME_ALREADY_EXISTS;
        } else if (!weakPassword(password)) {
            return CommandResponse.WEAK_PASSWORD;
        } else {
            User newUser = new User(username, password, nickname);
            database.addUserToList(newUser);
            return CommandResponse.OK;
        }
    }

    public CommandResponse loginUser(String username, String password) {
        Database database = Database.getInstance();
        if (!database.checkForUsername(username)) {
            return CommandResponse.NO_USER_EXIST_WITH_USERNAME;
        } else if (!database.getUser(username).passwordMatchCheck(password)) {
            return CommandResponse.INVALID_PASSWORD;
        } else {
            MenuStack.getInstance().setUser(database.getUser(username));
            return CommandResponse.OK;
        }
    }

    private boolean weakPassword(String password) {
        return InputRegex.getMatcher(password, InputRegex.CONTAINS_DIGIT) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_UPPERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_LOWERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_SPECIAL_CHAR) != null &&
                password.length() > 8;
    }
}