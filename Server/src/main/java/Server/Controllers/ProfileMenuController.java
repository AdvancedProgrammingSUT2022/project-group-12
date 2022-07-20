package Server.Controllers;

import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Utils.CommandException;
import Server.Views.MenuStack;

public class ProfileMenuController {

    public static void changePassword(String oldPassword, String newPassword) throws CommandException {
        if (!MenuStack.getInstance().getUser().passwordMatchCheck(oldPassword)) {
            throw new CommandException(CommandResponse.PASSWORD_DOES_NOT_MATCH);
        } else if (oldPassword.equals(newPassword)) {
            throw new CommandException(CommandResponse.REPEATED_PASSWORD);
        } else {
            MenuStack.getInstance().getUser().changePassword(newPassword);
        }
    }

    public static void changeNickname(String nickname) throws CommandException {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS, nickname);
        } else {
            MenuStack.getInstance().getUser().changeNickname(nickname);
        }
    }
}