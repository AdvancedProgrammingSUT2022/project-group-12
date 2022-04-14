package Controllers;

import java.util.HashMap;

public class Command {
    private HashMap<String, String> options;
    private String type;

    private Command(String type, HashMap<String, String> options) {
        this.options = options;
        this.type = type;
    }

    private static String removeWhiteSpaces(String str) {
        return str;
    }

    private static Command parseCommand(String input) {
        input = removeWhiteSpaces(input);
        int idx = input.indexOf('-');
        if (idx == -1) idx = input.length();
        String cmd = input.substring(0, idx);
        System.out.println("command: " + cmd);
        HashMap<String, String> options = new HashMap<>();
        while (idx < input.length()) {
            String key;
            int idx2 = idx + 1;
            if (idx2 >= input.length()) {
                // invalid command
            }
            if (input.charAt(idx2) == '-') {
                idx2 = input.indexOf(' ', idx);
                if (idx2 == -1) {
                    // invalid command
                }
                key = input.substring(idx + 2, idx2);
                idx = idx2 + 1;
            } else {
                key = String.valueOf(input.charAt(idx2));
                ++idx2;
                if (input.charAt(idx2) != ' ') {
                    // invalid command
                }
                idx = idx2 + 1;
            }
            idx2 = input.indexOf('-', idx);
            if (idx2 == -1) idx2 = input.length();
            String value = input.substring(idx, idx2);
            idx = idx2;
            System.out.println("key = " + key + " / value = " + value);
            options.put(key, value);
        }
        return new Command(cmd, options);
    }

    public String getType() {
        return type;
    }

    public String getOption(String key) {
        return options.get(key);
    }
}
