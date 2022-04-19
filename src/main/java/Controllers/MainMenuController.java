package Controllers;

import Enums.CommandResponseEnum;
import Enums.InputRegexEnum;

import java.util.TreeMap;

public class MainMenuController {
    public CommandResponseEnum playGameWithOthers(String input){
        TreeMap<Integer,String> selectedUsers;
        StringBuilder command = new StringBuilder(input);
        if((selectedUsers = InputRegexEnum.playGameWithMatcher(command)) == null){
            return CommandResponseEnum.INVALID_COMMAND;
        }
        //TODO : playing game assignments
        return CommandResponseEnum.OK;
    }
}
