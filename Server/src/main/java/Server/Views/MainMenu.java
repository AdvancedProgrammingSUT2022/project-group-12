package Server.Views;

import Project.Utils.CommandResponse;
import Server.Controllers.GameController;
import Server.Controllers.MainMenuController;
import Server.Models.Game;
import Server.Utils.Command;
import Server.Utils.CommandException;

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
        answer(CommandResponse.OK);
    }

    private void playGame(Command command) {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> playerNumbers = new ArrayList<>();
        int width , height;
        try {
            command.abbreviate("width",'w');
            command.abbreviate("height",'h');
            width = command.getIntOption("width");
            height = command.getIntOption("height");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
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
            Game game = MainMenuController.startNewGame(usernames,width,height);
            GameController.setGame(game);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        MenuStack.getInstance().pushMenu(new GameMenu());
        answer("game started");
    }

}