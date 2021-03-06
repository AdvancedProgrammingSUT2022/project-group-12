package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Utils.CommandException;
import Server.Utils.InputRegex;

public class LoginMenuController {
    public static void createUser(String username, String nickname, String password) throws CommandException {
        Database database = Database.getInstance();
        if (database.checkForUsername(username)) {
            throw new CommandException(CommandResponse.USERNAME_ALREADY_EXISTS);
        } else if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS);
        } else if (!weakPassword(password)) {
            throw new CommandException(CommandResponse.WEAK_PASSWORD);
        } else {
            Database.getInstance().addUser(new User(username, password, nickname));
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
        Database database = Database.getInstance();
        User user;
        if (!database.checkForUsername(username)) {
            throw new CommandException(CommandResponse.USER_DOES_NOT_EXISTS);
        } else if (!(user = database.getUser(username)).passwordMatchCheck(password)) {
            throw new CommandException(CommandResponse.PASSWORD_DOES_NOT_MATCH);
        } else {
            return user;
        }
    }
}