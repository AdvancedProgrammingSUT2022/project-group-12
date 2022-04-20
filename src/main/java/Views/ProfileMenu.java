package Views;

import Controllers.Command;
import Enums.CommandResponse;
import Enums.InputRegex;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        String input = command.getType();
        Matcher matcher;
        if ((matcher = InputRegex.getMatcher(input, InputRegex.CHANGE_NICKNAME)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.CHANGE_PASS)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.BACK)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.CURRENT_MENU)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.ENTER_MENU)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.LOGOUT)) != null) {

        } else if ((matcher = InputRegex.getMatcher(input, InputRegex.LOGOUT)) != null) {

        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }
}