package Views;

import Controllers.Command;
import Controllers.ProfileMenuController;
import Enums.CommandResponseEnum;

import java.util.List;

public class ProfileMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "profile change" -> profileChange(command);
            case "show current menu" -> System.out.println("Profile Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
        }
    }

    private void profileChange(Command command){
        switch (command.getType()) {
            case "profile change nickname" -> changeNickname(command);
            case "profile change password" -> changePassword(command);
        }
    }

    private void changePassword(Command command) {
        CommandResponseEnum response = command.validateOptions(List.of("currentPass", "newPass"));
        if (!response.isOK()) System.out.println(response);
        else {
            String currentPass = command.getOption("currentPass");
            String newPass = command.getOption("newPass");
            response = ProfileMenuController.changePass(currentPass,newPass);
            System.out.println(!response.isOK() ? response : "Password changed successfully");
        }
    }

    private void changeNickname(Command command) {
        CommandResponseEnum response = command.validateOptions(List.of("nickname"));
        if (!response.isOK()) System.out.println(response);
        else {
            String newNickname = command.getOption("nickname");
            response = ProfileMenuController.changeNickname(newNickname);
            System.out.println(!response.isOK() ? response : "Password changed successfully");
        }
    }
}
