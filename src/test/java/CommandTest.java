import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import Project.Utils.Command;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;

import java.util.List;

import static Project.Utils.CommandResponse.*;

public class CommandTest {

    private Executable getParseExecutable(String input) {
        return () -> Command.parseCommand(input);
    }

    private void assertDoesNotThrowFor(String input) {
        Assertions.assertDoesNotThrow(getParseExecutable(input));
    }

    private void assertThrowFor(CommandResponse response, String input) {
        CommandException exception = Assertions.assertThrows(CommandException.class, getParseExecutable(input));
        Assertions.assertEquals(exception.getResponse(), response);
    }

    private void assertThrowA(CommandResponse response, Executable executable) {
        CommandException exception = Assertions.assertThrows(CommandException.class, executable);
        Assertions.assertEquals(exception.getResponse(), response);
    }

    @Test
    public void blankInput() {
        assertThrowFor(INVALID_COMMAND_FORMAT, "");
    }

    @Test
    public void inputTrimming() {
        assertDoesNotThrowFor("   move        ");
        assertDoesNotThrowFor("  \n move    \t\n    ");
        assertDoesNotThrowFor("  \t\n \tmove");
    }

    @Test
    public void commandFormatWithoutOption() {
        assertDoesNotThrowFor(" move unit   ");
        assertDoesNotThrowFor("move one two three four  ");
    }

    @Test
    public void commandFormatWithOptions() {
        assertThrowFor(INVALID_COMMAND_FORMAT, "-");
        assertThrowFor(INVALID_COMMAND_FORMAT, "--");
        assertThrowFor(INVALID_COMMAND_FORMAT, "---");

        assertThrowFor(INVALID_COMMAND_FORMAT, "move--");
        assertThrowFor(INVALID_COMMAND_FORMAT, "move-");
        assertThrowFor(INVALID_COMMAND_FORMAT, "move -");
        assertThrowFor(INVALID_COMMAND_FORMAT, "move --");

        assertThrowFor(INVALID_COMMAND_FORMAT, "  -move");
        assertThrowFor(INVALID_COMMAND_FORMAT, "--move");
        assertThrowFor(INVALID_COMMAND_FORMAT, " --move");

        assertThrowFor(INVALID_COMMAND_FORMAT, "unit--move");
        assertThrowFor(INVALID_COMMAND_FORMAT, "unit-move");

        assertThrowFor(INVALID_COMMAND_FORMAT, "unit -move");
        assertThrowFor(INVALID_COMMAND_FORMAT, "login --user");
        assertThrowFor(INVALID_COMMAND_FORMAT, "login --user");
        assertThrowFor(INVALID_COMMAND_FORMAT, "login -u");

        assertThrowFor(INVALID_COMMAND_FORMAT, "login -user ali");
        assertThrowFor(INVALID_COMMAND_FORMAT, "login -user ali");

        assertDoesNotThrowFor("login --user ali");
        assertDoesNotThrowFor("login --pass a--l-i");
        assertDoesNotThrowFor("login --pass -");
        assertDoesNotThrowFor("login -p -");
        assertDoesNotThrowFor("login -p - -u ali");
        assertDoesNotThrowFor("login -p -923wefWEFkaAFE --username Ali134");
    }

    @Test
    public void duplicateKey() {
        assertThrowFor(DUPLICATE_OPTION_KEY, "login --user alireza --pass 123 --user alireza");
    }

    @Test
    public void abbreviateOption() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("login -u alireza");
            command.abbreviate("username", 'u');
            Assertions.assertNotNull(command.getOption("username"));
        });
        assertThrowA(DUPLICATE_OPTION_KEY, () -> {
            Command command = Command.parseCommand("login -u ali --username reza");
            command.abbreviate("username", 'u');
        });
    }

    @Test
    public void categorizing() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("one   two three   four  ");
            Assertions.assertEquals("one two three four", command.getType());
            Assertions.assertEquals("one", command.getCategory());
            Assertions.assertEquals("two", command.getSubCategory());
            Assertions.assertEquals("three", command.getSubSubCategory());
        });
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("one  ");
            Assertions.assertEquals("", command.getSubCategory());
            Assertions.assertEquals("", command.getSubSubCategory());
        });
    }

    @Test
    public void assertOptions() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("login --username alireza --password 1234");
            command.assertOptions(List.of("username", "password"));
        });
        assertThrowA(MISSING_REQUIRED_OPTION, () -> {
            Command command = Command.parseCommand("login --username alireza");
            command.assertOptions(List.of("username", "password"));
        });
    }

    @Test
    public void getOption() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("login --number 1234");
            String value = command.getOption("number");
            Assertions.assertEquals(value, "1234");
        });
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("login --value   fe%$123fe-w4 ");
            String value = command.getOption("value");
            Assertions.assertEquals(value, "fe%$123fe-w4");
        });
    }

    @Test
    public void getIntOption() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("login --number 1234");
            command.getIntOption("number");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("login --number ali");
            command.getIntOption("number");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("login --number 123kef3");
            command.getIntOption("number");
        });
    }

    @Test
    public void getLocationOption() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("goto --position 10 10");
            command.getLocationOption("position");
        });
        Assertions.assertDoesNotThrow(() -> {
            Command command = Command.parseCommand("goto --position   10    10  ");
            command.getLocationOption("position");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("goto --position   4afe    10  ");
            command.getLocationOption("position");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("goto --position   10, rg  ");
            command.getLocationOption("position");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("goto --position 5  10, rg  ");
            command.getLocationOption("position");
        });
        assertThrowA(INVALID_COMMAND_FORMAT, () -> {
            Command command = Command.parseCommand("goto --position one  ");
            command.getLocationOption("position");
        });
    }
}
