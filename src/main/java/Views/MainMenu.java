package Views;

import Controllers.Command;
import Controllers.LoginMenuController;
import Enums.CommandResponseEnum;

import java.util.List;


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
        CommandResponseEnum response = command.validateOptions(List.of("username"));
        if (!response.isOK()) System.out.println(response);
        else {
            String username = command.getOption("username");
            System.out.println(!response.isOK() ? response : "user created successfully");
        }
        MenuStack.getInstance().pushMenu(new GameMenu());
    }
}
