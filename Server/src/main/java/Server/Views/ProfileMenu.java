package Server.Views;

import Project.Utils.CommandResponse;
import Server.Controllers.ProfileMenuController;
import Server.Utils.Command;
import Server.Utils.CommandException;

import java.util.List;

public class ProfileMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "change nickname" -> this.changeNickname(command);
            case "change password" -> this.changePassword(command);
            case "show current menu" -> answer(this.getName());
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void changeNickname(Command command) {
        try {
            command.abbreviate("nickname", 'n');
            command.assertOptions(List.of("nickname"));
            String nickname = command.getOption("nickname");
            ProfileMenuController.changeNickname(nickname);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("nickname changed successfully");
    }

    private void changePassword(Command command) {
        try {
            command.abbreviate("oldPassword", 'o');
            command.abbreviate("newPassword", 'n');
            command.assertOptions(List.of("oldPassword", "newPassword"));
            String oldPassword = command.getOption("oldPassword");
            String newPassword = command.getOption("newPassword");
            ProfileMenuController.changePassword(oldPassword, newPassword);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("password changed successfully");
    }
}