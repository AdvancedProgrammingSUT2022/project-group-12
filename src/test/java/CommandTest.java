import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    public void inputTrimming() {
        Assertions.assertDoesNotThrow(() -> Command.parseCommand("   move        "));
        Assertions.assertDoesNotThrow(() -> Command.parseCommand("  \n move    \t\n    "));
        Assertions.assertDoesNotThrow(() -> Command.parseCommand("  \t\n \tmove"));
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> Command.parseCommand(""));
        Assertions.assertEquals(exception.getResponse(), CommandResponse.INVALID_COMMAND_FORMAT);
    }

    @Test
    public void invalidCommandFormat() {
        Assertions.assertDoesNotThrow(() -> Command.parseCommand("move"));
    }
}
