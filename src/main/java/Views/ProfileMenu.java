package Views;

import Controllers.ProfileMenuController;
import Exceptions.CommandException;
import Utils.Command;
import Utils.CommandResponse;

public class ProfileMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "change nickname" -> this.changeNickname(command);
            case "change password" -> this.changePassword(command);
            case "show current menu" -> System.out.println("Profile Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void changeNickname(Command command) {
        command.abbreviate("nickname", "n");
        String nickname = command.getOption("nickname");
        try {
            ProfileMenuController.changeNickname(nickname);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("nickname changed successfully");
    }

    private void changePassword(Command command) {
        command.abbreviate("oldPassword", "o");
        command.abbreviate("newPassword", "n");
        String oldPassword = command.getOption("oldPassword");
        String newPassword = command.getOption("newPassword");
        try {
            ProfileMenuController.changePassword(oldPassword, newPassword);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("password changed successfully");
    }
}