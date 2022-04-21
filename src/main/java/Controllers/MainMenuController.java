package Controllers;

import Enums.CommandResponse;
import Enums.InputRegex;

import java.util.TreeMap;

public class MainMenuController {
    public CommandResponse playGameWithOthers(String input){
        TreeMap<Integer,String> selectedUsers;
        StringBuilder command = new StringBuilder(input);
        if((selectedUsers = InputRegex.playGameWithMatcher(command)) == null){
            return CommandResponse.INVALID_COMMAND;
        }
        //TODO : playing game assignments
        return CommandResponse.OK;
    }
}
