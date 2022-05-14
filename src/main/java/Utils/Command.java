package Utils;

import Models.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {
    private final HashMap<String, String> options;
    private final String type;

    private Command(String type, HashMap<String, String> options) {
        this.options = options;
        this.type = type;
    }

    public static Command parseCommand(String input) throws CommandException {
        int idx = input.indexOf('-');
        if (idx == -1) idx = input.length();
        String cmd = removeWhiteSpaces(input.substring(0, idx)).toLowerCase();
        HashMap<String, String> options = new HashMap<>();
        while (idx < input.length()) {
            String key;
            int idx2 = idx + 1;
            if (idx2 >= input.length()) { // like "move -"
                throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT, String.valueOf(idx2));
            }
            if (input.charAt(idx2) == '-') { // double dash
                idx2 = input.indexOf(' ', idx);
                if (idx2 == -1) { // like "move --amount"
                    throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT, String.valueOf(idx2));
                }
                key = input.substring(idx + 2, idx2);
            } else { // single dash
                key = String.valueOf(input.charAt(idx2));
                ++idx2;
                if (input.charAt(idx2) != ' ') { // like "move -amount"
                    throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT, String.valueOf(idx2));
                }
            }
            idx = idx2 + 1;
            idx2 = input.indexOf('-', idx);
            if (idx2 == -1) idx2 = input.length();
            String value = removeWhiteSpaces(input.substring(idx, idx2));
            idx = idx2;
            if (options.containsKey(key)) {
                throw new CommandException(CommandResponse.DUPLICATE_OPTION_KEY, key);
            }
            options.put(key, value);
        }
        Command command = new Command(cmd, options);
//        System.out.println(command);
        return command;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type = '").append(type).append("'\n");
        for (Map.Entry<String, String> entry : options.entrySet()) {
            stringBuilder.append("key = '").append(entry.getKey()).append("' / value = '").append(entry.getValue()).append("'\n");
        }
        return stringBuilder.toString();
    }

    public void abbreviate(String key, String abbr) {
        String value = this.getOption(abbr);
        if (value != null) {
            this.options.remove(abbr);
            this.options.put(key, value);
        }
    }

    public String getOption(String key) {
        return this.options.get(key);
    }

    public String getCategory() {
        if (getPartOfType(0) != null) return getPartOfType(0);
        return String.valueOf(CommandResponse.INVALID_COMMAND);
    }

    private String getPartOfType(int idx) {
        String[] parts = this.getType().split(" ");
        return parts.length > idx ? parts[idx] : "";
    }

    public String getType() {
        return type;
    }

    public String getSubCategory() {
        if (getPartOfType(1) != null) return getPartOfType(1);
        return String.valueOf(CommandResponse.INVALID_COMMAND);
    }

    public String getSubSubCategory() {
        if (getPartOfType(2) != null) return getPartOfType(2);
        return String.valueOf(CommandResponse.INVALID_COMMAND);
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void assertOptions(List<String> requiredKeys) throws CommandException {
        for (String key : requiredKeys) {
            if (this.getOption(key) == null) {
                throw new CommandException(CommandResponse.MISSING_REQUIRED_OPTION, key);
            } else if (!requiredKeys.contains(key)) {
                throw new CommandException(CommandResponse.UNRECOGNIZED_OPTION, key);
            }
        }
    }

    public Location getLocationOption(String key) throws CommandException {
        String value = this.getOption(key);
        String[] parts = value.split("\\s+");
        if (parts.length != 2) throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT);
        try {
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            return new Location(row, col);
        } catch (Exception e) {
            throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT);
        }
    }

    public int getIntOption(String key) throws CommandException {
        try {
            return Integer.parseInt(this.getOption(key));
        } catch (Exception e) {
            throw new CommandException(CommandResponse.INVALID_COMMAND_FORMAT);
        }
    }
}
