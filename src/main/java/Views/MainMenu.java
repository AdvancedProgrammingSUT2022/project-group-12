package Views;

import Controllers.Command;
import Controllers.GameController;
import Controllers.MainMenuController;
import Enums.CommandResponse;

import java.util.ArrayList;


public class MainMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "play game" -> this.playGame(command);
            case "goto profile menu" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
            case "show current menu" -> System.out.println("Main Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void playGame(Command command) {
        ArrayList<String> usernames = new ArrayList<>();
        int num = 1;
        String username;
        while ((username = command.getOption("player" + num)) != null) { // todo: check player number gap
            usernames.add(username);
            ++num;
        }
        GameController gameController = MainMenuController.startNewGame(usernames);
        System.out.println(/*!response.isOK() ? response : */"user created successfully");
        MenuStack.getInstance().pushMenu(new GameMenu(gameController));
    }
}