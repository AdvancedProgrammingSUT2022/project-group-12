package Controllers;

import Exceptions.CommandException;
import Models.Database;
import Views.MenuStack;

public class ProfileMenuController {

    public static void changePassword(String oldPassword, String newPassword) throws CommandException {
        if (!MenuStack.getInstance().getUser().passwordMatchCheck(oldPassword)) {
            throw new CommandException("password is incorrect");
        } else if (oldPassword.equals(newPassword)) {
            throw new CommandException("new password is the same as old one");
        } else {
            MenuStack.getInstance().getUser().changePassword(newPassword);
        }
    }

    public static void changeNickname(String nickname) throws CommandException {
        Database database = Database.getInstance();
        if (database.nicknameAlreadyExists(nickname)) {
            throw new CommandException("nickname already used: " + nickname);
        } else {
            MenuStack.getInstance().getUser().changeNickname(nickname);
        }
    }
}