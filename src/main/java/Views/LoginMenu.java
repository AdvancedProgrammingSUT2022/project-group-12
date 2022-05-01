package Views;

import Controllers.LoginMenuController;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.List;

public class LoginMenu extends Menu {
    private final LoginMenuController controller = new LoginMenuController();

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "user create" -> createUser(command);
            case "user login" -> loginUser(command);
            case "show current menu" -> System.out.println("Login Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void createUser(Command command) {
        command.abbreviate("username", "u");
        command.abbreviate("nickname", "n");
        command.abbreviate("password", "p");
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
        command.abbreviate("username", "u");
        command.abbreviate("password", "p");
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