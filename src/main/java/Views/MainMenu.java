package Views;

import Controllers.GameController;
import Controllers.MainMenuController;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;


public class MainMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "play game" -> this.playGame(command);
            case "logout" -> MenuStack.getInstance().gotoLoginMenu();
            case "goto profile menu" -> MenuStack.getInstance().gotoProfileMenu();
            case "show current menu" -> System.out.println("Main Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void playGame(Command command) {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> playerNumbers = new ArrayList<>();
        for (String option : command.getOptions().keySet()) {
            try {
                if (option.startsWith("player")) {
                    playerNumbers.add(Integer.valueOf(option.substring("player".length())));
                }
            } catch (NumberFormatException e) {
                System.out.println(CommandResponse.INVALID_COMMAND_FORMAT);
            }
        }
        for (int i = 1; i <= playerNumbers.size(); ++i) {
            String username = command.getOption("player" + i);
            if (username == null) {
                System.out.println("there is a gap in number of players, player " + i + "not entered");
                return;
            }
            usernames.add(username);
        }
        GameController gameController;
        try {
            gameController = MainMenuController.startNewGame(usernames);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("game started");
        MenuStack.getInstance().pushMenu(new GameMenu(gameController));
    }
}