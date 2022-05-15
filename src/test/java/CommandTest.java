import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static Utils.CommandResponse.DUPLICATE_OPTION_KEY;
import static Utils.CommandResponse.INVALID_COMMAND_FORMAT;

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
}
