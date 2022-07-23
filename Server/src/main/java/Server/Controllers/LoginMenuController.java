package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Utils.CommandException;
import Server.Utils.InputRegex;

public class LoginMenuController {
    public static User createUser(String username, String nickname, String password) throws CommandException {
        Database database = Database.getInstance();
        if (database.checkForUsername(username)) {
            throw new CommandException(CommandResponse.USERNAME_ALREADY_EXISTS);
        } else if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS);
        } else if (!weakPassword(password)) {
            throw new CommandException(CommandResponse.WEAK_PASSWORD);
        } else {
            User newUser = new User(username, password, nickname);
            Database.getInstance().addUser(newUser);
            return newUser;
        }
    }

    private static boolean weakPassword(String password) {
        return InputRegex.getMatcher(password, InputRegex.CONTAINS_DIGIT) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_UPPERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_LOWERCASE_ALPHABET) != null &&
                InputRegex.getMatcher(password, InputRegex.CONTAINS_SPECIAL_CHAR) != null &&
                password.length() >= 8;
    }

    public static User loginUser(String username, String password) throws CommandException {
        User user;
        if (!Database.getInstance().checkForUsername(username)) {
            throw new CommandException(CommandResponse.USER_DOES_NOT_EXISTS);
        } else if (!(user = Database.getInstance().getUser(username)).passwordMatchCheck(password)) {
            throw new CommandException(CommandResponse.PASSWORD_DOES_NOT_MATCH);
        } else {
            return user;
        }
    }
}