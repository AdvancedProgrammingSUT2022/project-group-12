package Server.Views;

import Project.Utils.CommandResponse;
import Server.Controllers.LoginMenuController;
import Server.Utils.Command;
import Server.Utils.CommandException;

import java.util.List;

public class LoginMenu extends Menu {

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "user create" -> createUser(command);
            case "user login" -> loginUser(command);
            case "show current menu" -> answer(this.getName());
            case "menu exit" -> this.menuStack.popMenu();
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void createUser(Command command) {
        try {
            command.abbreviate("username", 'u');
            command.abbreviate("nickname", 'n');
            command.abbreviate("password", 'p');
            command.assertOptions(List.of("username", "nickname", "password"));
        } catch (CommandException e) {
            answer(e);
            return;
        }
        String username = command.getOption("username");
        String nickname = command.getOption("nickname");
        String password = command.getOption("password");
        try {
            LoginMenuController.createUser(username, nickname, password);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("user created successfully");
    }

    private void loginUser(Command command) {
        try {
            command.abbreviate("password", 'p');
            command.abbreviate("username", 'u');
            command.assertOptions(List.of("username", "password"));
        } catch (CommandException e) {
            answer(e);
            return;
        }
        String username = command.getOption("username");
        String password = command.getOption("password");
        try {
            LoginMenuController.loginUser(username, password);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        this.menuStack.pushMenu(new MainMenu());
        answer("user logged in successfully");
    }
}