package Controllers;

import Exceptions.*;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type = '" + type + "'\n");
        for (Map.Entry<String, String> entry : options.entrySet()) {
            stringBuilder.append("key = '" + entry.getKey() + "' / value = '" + entry.getValue() + "'\n");
        }
        return stringBuilder.toString();
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

    public static Command parseCommand(String input) throws InvalidCommandFormat, DuplicateOptionKey {
        int idx = input.indexOf('-');
        if (idx == -1) idx = input.length();
        String cmd = removeWhiteSpaces(input.substring(0, idx)).toLowerCase();
        HashMap<String, String> options = new HashMap<>();
        while (idx < input.length()) {
            String key;
            int idx2 = idx + 1;
            if (idx2 >= input.length()) { // like "move -"
                throw new InvalidCommandFormat(idx2);
            }
            if (input.charAt(idx2) == '-') { // double dash
                idx2 = input.indexOf(' ', idx);
                if (idx2 == -1) { // like "move --amount"
                    throw new InvalidCommandFormat(idx2);
                }
                key = input.substring(idx + 2, idx2);
            } else { // single dash
                key = String.valueOf(input.charAt(idx2));
                ++idx2;
                if (input.charAt(idx2) != ' ') { // like "move -amount"
                    throw new InvalidCommandFormat(idx2);
                }
            }
            idx = idx2 + 1;
            idx2 = input.indexOf('-', idx);
            if (idx2 == -1) idx2 = input.length();
            String value = removeWhiteSpaces(input.substring(idx, idx2));
            idx = idx2;
            if (options.containsKey(key)) {
                throw new DuplicateOptionKey(key);
            }
            options.put(key, value);
        }
        Command command = new Command(cmd, options);
        System.out.println(command);
        return command;
    }

    public String getType() {
        return type;
    }

    private String getPartOfType(int idx) {
        String[] parts = this.getType().split(" ");
        return parts.length > idx ? parts[idx] : null;
    }

    public String getCategory() {
        return getPartOfType(0);
    }

    public String getSubCategory() {
        return getPartOfType(1);
    }

    public String getSubSubCategory() {
        return getPartOfType(2);
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public String getOption(String key) {
        return this.options.get(key);
    }

    public void assertOptions(List<String> requiredKeys) throws MissingRequiredOption, UnrecognizedOption {
        for (String key : requiredKeys) {
            if (this.getOption(key) == null) {
                throw new MissingRequiredOption(key);
            } else if (!requiredKeys.contains(key)) {
                throw new UnrecognizedOption(key);
            }
        }
    }

    public void assertOptionType(String option, String type) throws InvalidOptionType {
        if (type.equals("integer")) {
            if (!option.matches("^-?\\d+$")) {
                throw new InvalidOptionType(option, type);
            }
        }
    }

}
