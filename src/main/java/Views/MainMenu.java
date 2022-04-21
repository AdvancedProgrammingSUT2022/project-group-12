package Views;

import Controllers.Command;
import Enums.CommandResponse;
import Enums.REGEX;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;


public class MainMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        String input = command.getType();
        Matcher matcher;
        if (REGEX.getMatcher(input, REGEX.PLAY_GAME_WITH) != null) {
            playGameWith(input);
        } else if (REGEX.getMatcher(input, REGEX.CURRENT_MENU) != null) {
            System.out.println("Main Menu");
        } else if ((matcher = REGEX.getMatcher(input, REGEX.ENTER_MENU)) != null) {
            System.out.println(MenuStack.getInstance().menuController.enterMenu(matcher));
        } else if (REGEX.getMatcher(input, REGEX.EXIT_MENU) != null) {
            MenuStack.getInstance().popMenu();
        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
//        switch (command.getType()) {
//            case "show current menu" -> System.out.println("Main Menu");
//            case "menu exit" -> MenuStack.getInstance().popMenu();
//            case "play game" -> this.playGame(command);
//            case "goto profile menu" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
//        }
    }

    private void playGameWith(String command) {
        StringBuilder input = new StringBuilder(command);
        TreeMap<Integer, String> playersMap = REGEX.playGameWithMatcher(input);
        CommandResponse response = MenuStack.getInstance().menuController.startNewGame(playersMap);
        if (response.isOK()) {
            MenuStack.getInstance().pushMenu(new GameMenu());
        } else {
            System.out.println(response);
        }
    }

    private void playGame(Command command) {
        ArrayList<String> usernames = new ArrayList<>();
        int num = 1;
        String username;
        while ((username = command.getOption("player" + num)) != null) {
            usernames.add(username);
            ++num;
        }
        TreeMap<Integer, String> map = new TreeMap<>();
        CommandResponse response = MenuStack.getInstance().menuController.startNewGame(map);
        System.out.println(!response.isOK() ? response : "user created successfully");
        MenuStack.getInstance().pushMenu(new GameMenu());
    }
}