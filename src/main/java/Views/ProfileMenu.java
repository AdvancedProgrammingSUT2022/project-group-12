package Views;

import Controllers.Command;
import Enums.CommandResponse;
import Enums.REGEX;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {


    @Override
    protected void handleCommand(Command command) {
        String input = command.getType();
        Matcher matcher;
        if ((matcher = REGEX.getMatcher(input, REGEX.CHANGE_NICKNAME)) != null) {
            System.out.println(MenuStack.getInstance().profileController.changeNickname(matcher));
        } else if ((matcher = REGEX.getMatcher(input, REGEX.CHANGE_PASS)) != null) {
            System.out.println(MenuStack.getInstance().profileController.changePass(matcher));
        } else if (REGEX.getMatcher(input, REGEX.BACK) != null) {
            MenuStack.getInstance().popMenu();
        } else if (REGEX.getMatcher(input, REGEX.CURRENT_MENU) != null) {
            System.out.println("Profile Menu");
        } else if ((matcher = REGEX.getMatcher(input, REGEX.ENTER_MENU)) != null) {
            System.out.println(MenuStack.getInstance().profileController.enterMenu(matcher));
        } else if (REGEX.getMatcher(input, REGEX.LOGOUT) != null) {
            MenuStack.getInstance().logout();
            System.out.println("logged out successfully");
        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }
}