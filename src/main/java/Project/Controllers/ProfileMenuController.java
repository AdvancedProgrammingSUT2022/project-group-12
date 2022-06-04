package Project.Controllers;

import Project.CommandlineViews.DisabledMenuStackDisabled;
import Project.Models.Database;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;

public class ProfileMenuController {

    public static void changePassword(String oldPassword, String newPassword) throws CommandException {
        if (!DisabledMenuStackDisabled.getInstance().getUser().passwordMatchCheck(oldPassword)) {
            throw new CommandException(CommandResponse.INVALID_PASSWORD);
        } else if (oldPassword.equals(newPassword)) {
            throw new CommandException(CommandResponse.REPEATED_PASSWORD);
        } else {
            DisabledMenuStackDisabled.getInstance().getUser().changePassword(newPassword);
        }
    }

    public static void changeNickname(String nickname) throws CommandException {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException(CommandResponse.NICKNAME_ALREADY_EXISTS, nickname);
        } else {
            DisabledMenuStackDisabled.getInstance().getUser().changeNickname(nickname);
        }
    }
}