package Server.Views;

import Project.Utils.CommandResponse;
import Server.Controllers.GameController;
import Server.Controllers.MainMenuController;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.Command;
import Server.Utils.CommandException;

import java.util.ArrayList;


public class MainMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "start game" -> this.playGame(command);
            case "resume game" -> this.enterGame(command);
            case "show game" -> this.showGame(command);
            case "logout" -> this.logout();
            case "goto profile menu" -> this.menuStack.gotoProfileMenu();
            case "show current menu" -> answer(this.getName());
            case "menu exit" -> this.menuStack.popMenu();
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void showGame(Command command) {
        try {
            command.abbreviate("token",'t');
            String gameToken = command.getOption("token");
            Game game = Database.getInstance().getGameByToken(gameToken);
            MainMenuController.bindUpdateNotifier(game, this.menuStack, true);
            this.enterNewGameMenu(game.getToken());
            answer("entered the show");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void logout() {
        MainMenuController.logout(this.menuStack);
    }

    private void enterNewGameMenu(String gameToken) {
        Game game = Database.getInstance().getGameByToken(gameToken);
        this.menuStack.setCurrentGame(game);
        this.menuStack.pushMenu(new GameMenu(game));
    }

    private void enterGame(Command command) {
        try {
            command.abbreviate("token",'t');
            String gameToken = command.getOption("token");
            Game game = Database.getInstance().getGameByToken(gameToken);
            MainMenuController.bindUpdateNotifier(game, this.menuStack, false);
            this.enterNewGameMenu(game.getToken());
            answer("entered the game");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
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
            Game game = MainMenuController.startNewGame(usernames,width,height,this.menuStack.getUser());
            MainMenuController.bindUpdateNotifier(game, this.menuStack, false);
            this.enterNewGameMenu(game.getToken());
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("new game started");
    }

    public void bindAndEnterNewGame(Game game) {
        MainMenuController.bindUpdateNotifier(game, this.menuStack, false);
        this.enterNewGameMenu(game.getToken());
        GameController.setGame(game);
    }

}