package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Utils.CommandException;

public class ProfileMenuController {

    public static void changePassword(User user, String oldPassword, String newPassword) throws CommandException {
        if (!user.passwordMatchCheck(oldPassword)) {
            throw new CommandException(CommandResponse.PASSWORD_DOES_NOT_MATCH);
        } else if (oldPassword.equals(newPassword)) {
            throw new CommandException(CommandResponse.REPEATED_PASSWORD);
        } else {
            user.changePassword(newPassword);
        }
    }

    public static void changeNickname(User user, String nickname) throws CommandException {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS, nickname);
        } else {
            user.changeNickname(nickname);
        }
    }
}