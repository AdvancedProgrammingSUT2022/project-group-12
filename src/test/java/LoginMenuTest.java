import Controllers.LoginMenuController;
import Models.Database;
import Models.User;
import Utils.Command;
import Utils.CommandException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static Utils.CommandResponse.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginMenuTest {
    @BeforeEach
    void load() {
        Database.getInstance().deserializeUsers();
        User user = new User("alireza", "Password123!", "nickname");
        Database.getInstance().addUser(user);
    }

    @Test
    public void invalidUsername() throws CommandException {
        User newUser = new User("alireza","password1","nickname");
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user create -u alireza -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
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
        Throwable exception = assertThrows(CommandException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.createUser(username, nickname, password);
            }
        });
        assertEquals(USERNAME_ALREADY_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void weakPassword() throws CommandException {
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user create -u alireza2 -p password -n nickname2";
        Command command = Command.parseCommand(inputString);
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
        Throwable exception = assertThrows(CommandException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.createUser(username, nickname, password);
            }
        });
        assertEquals(WEAK_PASSWORD.toString(), exception.getMessage());
    }

    @Test
    public void invalidNickname() throws CommandException {
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user create -u alireza1 -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
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
        Throwable exception = assertThrows(CommandException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.createUser(username, nickname, password);
            }
        });
        assertEquals(NICKNAME_ALREADY_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void invalidPassword() throws CommandException {
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user login -u alireza -p password";
        Command command = Command.parseCommand(inputString);
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
        Throwable exception = assertThrows(CommandException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.loginUser(username, password);
            }
        });
        assertEquals(PASSWORD_DOES_NOT_MATCH.toString(), exception.getMessage());
    }

    @Test
    public void usernameDoesNotExists() throws CommandException {
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user login -u alireza23 -p Password1!";
        Command command = Command.parseCommand(inputString);
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
        Throwable exception = assertThrows(CommandException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                controller.loginUser(username, password);
            }
        });
        assertEquals(USER_DOES_NOT_EXISTS.toString(), exception.getMessage());
    }
}
