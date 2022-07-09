package Project.ServerViews;

import Project.Controllers.GameController;
import Project.Controllers.MainMenuController;
import Project.Models.Game;
import Project.Utils.Command;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;

import java.util.ArrayList;


public class MainMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "play game" -> this.playGame(command);
            case "logout" -> MenuStack.getInstance().gotoLoginMenu();
            case "goto profile menu" -> MenuStack.getInstance().gotoProfileMenu();
            case "show current menu" -> answer(this.getName());
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> answer(CommandResponse.INVALID_COMMAND);
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
                answer(new CommandException(CommandResponse.INVALID_COMMAND_FORMAT));
            }
        }
        for (int i = 1; i <= playerNumbers.size(); ++i) {
            String username = command.getOption("player" + i);
            if (username == null) {
                answer(new CommandException(CommandResponse.PLAYER_NUMBER_GAP, String.valueOf(i)));
                return;
            }
            usernames.add(username);
        }
        try {
            Game game = MainMenuController.startNewGame(usernames);
            GameController.setGame(game);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("game started");
        MenuStack.getInstance().pushMenu(new GameMenu());
    }

}