import Models.Database;
import Models.User;
import Utils.Command;
import Utils.CommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static Utils.CommandResponse.*;
import static org.junit.jupiter.api.Assertions.*;

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
    public void successfulRegister() throws CommandException {
        String inputString = "user create -u a1 -p Password1! -n n1";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
        assertDoesNotThrow(() -> Controllers.LoginMenuController.createUser(username, nickname, password));
    }

    private void setUserCreds(Command command) {
        assertDoesNotThrow(() -> {
            command.abbreviate("username", 'u');
            command.abbreviate("nickname", 'n');
            command.abbreviate("password", 'p');
            command.assertOptions(List.of("username", "nickname", "password"));
        });
        username = command.getOption("username");
        nickname = command.getOption("nickname");
        password = command.getOption("password");
    }

    @Test
    public void invalidUsername() throws CommandException {
        new User("alireza", "password1", "n");
        String inputString = "user create -u alireza -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> Controllers.LoginMenuController.createUser(username, nickname, password));
        assertEquals(USERNAME_ALREADY_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void weakPassword() throws CommandException {
        String inputString = "user create -u alireza2 -p Pass1! -n nickname2";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> Controllers.LoginMenuController.createUser(username, nickname, password));
        assertEquals(WEAK_PASSWORD.toString(), exception.getMessage());
    }

    @Test
    public void invalidNickname() throws CommandException {
        new User("a", "p", "nickname");
        String inputString = "user create -u alireza1 -p Password1! -n nickname";
        Command command = Command.parseCommand(inputString);
        setUserCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> Controllers.LoginMenuController.createUser(username, nickname, password));
        assertEquals(NICKNAME_ALREADY_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void invalidPassword() throws CommandException {
        new User("Ap", "P", "nname");
        String inputString = "user login -u Ap -p password";
        Command command = Command.parseCommand(inputString);
        setLoginCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> Controllers.LoginMenuController.loginUser(username, password));
        assertEquals(PASSWORD_DOES_NOT_MATCH.toString(), exception.getMessage());
    }

    private void setLoginCreds(Command command) {
        assertDoesNotThrow(() -> {
            command.abbreviate("username", 'u');
            command.abbreviate("password", 'p');
            command.assertOptions(List.of("username", "password"));
        });
        username = command.getOption("username");
        password = command.getOption("password");
    }

    @Test
    public void usernameDoesNotExists() throws CommandException {
        new User("AP", "T", "nName");
        String inputString = "user login -u alireza23 -p Password1!";
        Command command = Command.parseCommand(inputString);
        setLoginCreds(command);
        Throwable exception = assertThrows(CommandException.class, () -> Controllers.LoginMenuController.loginUser(username, password));
        assertEquals(USER_DOES_NOT_EXISTS.toString(), exception.getMessage());
    }

    @Test
    public void successfulLogin() throws CommandException {
        new User("Ap", "P", "nname");
        String inputString = "user login -u Ap -p P";
        Command command = Command.parseCommand(inputString);
        setLoginCreds(command);
        assertDoesNotThrow(() -> Controllers.LoginMenuController.loginUser(username, password));
    }
}
