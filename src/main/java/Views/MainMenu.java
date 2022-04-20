package Views;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;

import java.util.ArrayList;


public class MainMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "show current menu" -> System.out.println("Main Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            case "play game" -> this.playGame(command);
            case "goto profile menu" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
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
        CommandResponse response = GameController.startNewGame(usernames);
        System.out.println(!response.isOK() ? response : "user created successfully");
        MenuStack.getInstance().pushMenu(new GameMenu());
    }
}