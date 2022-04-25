package Views;

import Controllers.Command;
import Controllers.LoginMenuController;
import Exceptions.CommandException;

import java.util.List;

public class LoginMenu extends Menu {
    private LoginMenuController controller = new LoginMenuController();

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "user create" -> createUser(command);
            case "user login" -> loginUser(command);
            case "show current menu" -> System.out.println("Login Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> System.out.println("invalid command");
        }
    }

    private void createUser(Command command) {
        try {
            command.assertOptions(List.of("username", "nickname", "password"));
        } catch (CommandException e) {
            e.print();
            return;
        }
        String username = command.getOption("username");
        String nickname = command.getOption("nickname");
        String password = command.getOption("password");
        try {
            this.controller.createUser(username, nickname, password);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("user created successfully");
    }

    private void loginUser(Command command) {
        try {
            command.assertOptions(List.of("username", "password"));
        } catch (CommandException e) {
            e.print();
            return;
        }
        String username = command.getOption("username");
        String password = command.getOption("password");
        try {
            this.controller.loginUser(username, password);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("user logged in successfully");
        MenuStack.getInstance().pushMenu(new MainMenu());
    }
}