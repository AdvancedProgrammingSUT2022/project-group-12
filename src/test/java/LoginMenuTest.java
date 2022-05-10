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
    private String username;
    private String nickname;
    private String password;

    @BeforeEach
    void load() {
        Database.getInstance().deserializeUsers();
        User user = new User("alireza", "Password123!", "nickname");
        Database.getInstance().addUser(user);
    }

    @Test
    public void invalidUsername() throws CommandException {
        new User("alireza", "password1", "n");
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user create -u alireza -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
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
        setUserCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> controller.createUser(username, nickname, password));
        assertEquals(WEAK_PASSWORD.toString(), exception.getMessage());
    }

    @Test
    public void invalidNickname() throws CommandException {
        new User("a", "p", "nickname");
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user create -u alireza1 -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> controller.createUser(username, nickname, password));
        assertEquals(NICKNAME_ALREADY_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void invalidPassword() throws CommandException {
        new User("Ap", "P", "nname");
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user login -u Ap -p password";
        Command command = Command.parseCommand(inputString);
        setLoginCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> controller.loginUser(username, password));
        assertEquals(PASSWORD_DOES_NOT_MATCH.toString(), exception.getMessage());
    }

    @Test
    public void usernameDoesNotExists() throws CommandException {
        new User("AP", "T", "nName");
        LoginMenuController controller = new LoginMenuController();
        String inputString = "user login -u alireza23 -p Password1!";
        Command command = Command.parseCommand(inputString);
        setLoginCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> controller.loginUser(username, password));
        assertEquals(USER_DOES_NOT_EXISTS.toString(), exception.getMessage());
    }

    private void setUserCreds(Command command) {
        command.abbreviate("username", "u");
        command.abbreviate("nickname", "n");
        command.abbreviate("password", "p");
        try {
            command.assertOptions(List.of("username", "nickname", "password"));
        } catch (CommandException e) {
            e.print();
            return;
        }
        username = command.getOption("username");
        nickname = command.getOption("nickname");
        password = command.getOption("password");
    }

    private void setLoginCreds(Command command) {
        command.abbreviate("username", "u");
        command.abbreviate("password", "p");
        try {
            command.assertOptions(List.of("username", "password"));
        } catch (CommandException e) {
            e.print();
            return;
        }
        username = command.getOption("username");
        password = command.getOption("password");
    }
}
