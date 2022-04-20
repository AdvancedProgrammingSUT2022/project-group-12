//TODO : decide to keep or don't
package Controllers;

import Enums.CommandResponse;
import Exceptions.InvalidCommand;

import java.util.HashMap;
import java.util.List;

public class Command {
    private final HashMap<String, String> options;
    private final String type;

    private Command(String type, HashMap<String, String> options) {
        this.options = options;
        this.type = type;
    }

    private static String removeWhiteSpaces(String str) {
        str = str.trim();
        StringBuilder stringBuilder = new StringBuilder();
        boolean space = false;
        for (int i = 0; i < str.length(); ++i) {
            boolean isSpace = Character.isWhitespace(str.charAt(i));
            if (!space || !isSpace) stringBuilder.append(str.charAt(i));
            space = isSpace;
        }
        return stringBuilder.toString();
    }

    public static Command parseCommand(String input) throws InvalidCommand {
        input = removeWhiteSpaces(input);
        int idx = input.indexOf('-');
        if (idx == -1) idx = input.length();
        String cmd = input.substring(0, idx);
        HashMap<String, String> options = new HashMap<>();
        while (idx < input.length()) {
            String key;
            int idx2 = idx + 1;
            if (idx2 >= input.length()) {
//                throw new InvalidCommand();
            }
            if (input.charAt(idx2) == '-') {
                idx2 = input.indexOf(' ', idx);
                if (idx2 == -1) {
//                    throw new InvalidCommand();
                }
                key = input.substring(idx + 2, idx2);
            } else {
                key = String.valueOf(input.charAt(idx2));
                ++idx2;
                if (input.charAt(idx2) != ' ') {
//                    throw new InvalidCommand();
                }
            }
            idx = idx2 + 1;
            idx2 = input.indexOf('-', idx);
            if (idx2 == -1) idx2 = input.length();
            String value = input.substring(idx, idx2);
            idx = idx2;
            options.put(key, value);
        }
        return new Command(cmd, options);
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return this.getType().split(" ")[0];
    }

    public String getOption(String key) {
        return this.options.get(key);
    }

    public CommandResponse validateOptions(List<String> requiredKeys) {
        // todo: should validate more: unrecognized options, difference of dash and double dash, ...
        for (String key : requiredKeys) {
            if (this.getOption(key) == null) {
                return CommandResponse.COMMAND_MISSING_REQUIRED_OPTION; // todo: should contain the missing option's key
            }
        }
        return CommandResponse.OK;
    }
}