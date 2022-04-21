package Views;

import Controllers.Command;
import Controllers.ProfileMenuController;
import Enums.CommandResponse;
import Enums.InputRegex;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {


    @Override
    protected void handleCommand(Command command) {
        String input = command.getType();
        Matcher matcher;
        if ((matcher = InputRegex.getMatcher(input, InputRegex.CHANGE_NICKNAME)) != null) {
            System.out.println(MenuStack.getInstance().profileController.changeNickname(matcher));
        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.CHANGE_PASS)) != null) {
            System.out.println(MenuStack.getInstance().profileController.changePass(matcher));
        } else if (InputRegex.getMatcher(input, InputRegex.BACK) != null) {
            MenuStack.getInstance().popMenu();
        } else if (InputRegex.getMatcher(input, InputRegex.CURRENT_MENU) != null) {
            System.out.println("Profile Menu");
        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.ENTER_MENU)) != null) {
            System.out.println(MenuStack.getInstance().profileController.enterMenu(matcher));
        } else if (InputRegex.getMatcher(input, InputRegex.LOGOUT) != null) {
            MenuStack.getInstance().logout();
            System.out.println("logged out successfully");
        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }
}