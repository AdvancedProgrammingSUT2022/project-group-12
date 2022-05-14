package Views;

import Controllers.ProfileMenuController;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

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
        command.abbreviate("nickname", "n");
        String nickname = command.getOption("nickname");
        try {
            ProfileMenuController.changeNickname(nickname);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("nickname changed successfully");
    }

    private void changePassword(Command command) {
        command.abbreviate("oldPassword", "o");
        command.abbreviate("newPassword", "n");
        String oldPassword = command.getOption("oldPassword");
        String newPassword = command.getOption("newPassword");
        try {
            ProfileMenuController.changePassword(oldPassword, newPassword);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("password changed successfully");
    }
}