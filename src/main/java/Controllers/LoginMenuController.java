package Controllers;

import Enums.CommandResponse;
import Enums.InputRegex;
import Exceptions.CommandException;
import Models.Database;
import Models.User;
import Views.MenuStack;

public class LoginMenuController {
    public void createUser(String username, String nickname, String password) throws CommandException {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(username)) {
            throw new CommandException(CommandResponse.USERNAME_ALREADY_EXISTS.toString());
        } else if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS.toString());
        } else if (!weakPassword(password)) {
            throw new CommandException(CommandResponse.WEAK_PASSWORD.toString());
        } else {
            User newUser = new User(username, password, nickname);
            database.addUserToList(newUser);
        }
    }

    public void loginUser(String username, String password) throws CommandException {
        Database database = Database.getInstance();
        User user = database.getUser(username);
        if (user == null || !user.passwordMatchCheck(password)) {
            throw new CommandException("no user exists with this username or password doesn't match");
        } else {
            MenuStack.getInstance().setUser(user);
        }
    }

    private boolean weakPassword(String password) {
        return InputRegex.getMatcher(password, InputRegex.CONTAINS_DIGIT) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_UPPERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_LOWERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_SPECIAL_CHAR) != null &&
                password.length() >= 8;
    }
}