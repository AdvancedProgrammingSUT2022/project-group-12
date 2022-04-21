package Views;

import Controllers.Command;
import Controllers.LoginMenuController;
import Enums.CommandResponse;

import java.util.List;

public class LoginMenu extends Menu {
    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "user create" -> createUser(command);
            case "user login" -> loginUser(command);
            case "show current menu" -> System.out.println("Login Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
        }
    }

    private void createUser(Command command) {
        CommandResponse response = command.validateOptions(List.of("username", "nickname", "password"));
        if (!response.isOK()) System.out.println(response);
        else {
            String username = command.getOption("username");
            String nickname = command.getOption("nickname");
            String password = command.getOption("password");
            response = LoginMenuController.createUser(username, nickname, password);
            System.out.println(!response.isOK() ? response : "user created successfully");
        }
    }

    private void loginUser(Command command) {
        CommandResponse response = command.validateOptions(List.of("username", "password"));
        if (!response.isOK()) System.out.println(response);
        else {
            String username = command.getOption("username");
            String password = command.getOption("password");
            response = LoginMenuController.loginUser(username, password);
            System.out.println(!response.isOK() ? response : "user logged in successfully");
            if (response.isOK()) {
                MenuStack.getInstance().pushMenu(new MainMenu());
            }
        }
    }
}