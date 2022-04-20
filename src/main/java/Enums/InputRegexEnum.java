package Enums;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegexEnum {
    LOGIN("\\s*user login -+(?<part1>[.\\S]+) ([.\\S]+) -+(?<part2>[.\\S]+) ([.\\S]+)\\s*"),
    LOGIN_DEEPER_USER_FIRST("\\s*user login -+[.\\S]+ (?<username>[.\\S]+) -+[.\\S]+ (?<password>[.\\S]+)\\s*"),
    LOGIN_DEEPER_PASS_FIRST("\\s*user login -+[.\\S]+ (?<password>[.\\S]+) -+[.\\S]+ (?<username>[.\\S]+)\\s*"),
    REGISTER(""),
    PLAY_GAME_WITH("[\\s]*play game (?<player>(--player|-p)[0-9]+ [.\\S]+[\\s]*)+"),
    PARSE_PLAY_GAME_WITH("[\\s]*(--player|-p)(?<turn>[0-9]+) (?<username>[.\\S]+)[\\s]*"),
    INFO(""),
    EXIT(""),
    BACK(""),
    CHANGE_PASS(""),
    CHANGE_NICKNAME(""),
    LOGOUT(""),
    CURRENT_MENU(""),
    ENTER_MENU("[\\s]*menu[\\s]+enter[\\s]+(?<selectedMenu>[.[\\S]]+)[\\s]*"),
    EXIT_MENU("");

    private final String regex;

    InputRegexEnum(String input) {
        this.regex = input;
    }

    private static TreeMap<Integer, String> map = new TreeMap<>();

    public static void inputMatcher(String input, InputRegexEnum check) {
        Matcher matcher = Pattern.compile(check.regex).matcher(input);
        if (!matcher.find() || !matcher.group().equals(input)) {
            returnOptions(new StringBuilder().append(input));
        }
    }

    private static void returnOptions(StringBuilder input) {
        if (input.toString().startsWith("play game")) {
            playGameWithMatcher(input);
        }
    }

    private Matcher registerMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher loginMatcher(StringBuilder input) {
        Matcher matcher = Pattern.compile(LOGIN.regex).matcher(input.toString());
        boolean canBeUsed = false;
        if (matcher.find() && matcher.group().equals(input.toString())) {
            if ((matcher.group("part1").equals("user") ||
                    matcher.group("part1").equals("username") ||
                    matcher.group("part1").equals("u")) &&
                    (matcher.group("part2").equals("pass") ||
                            matcher.group("part2").equals("password") ||
                            matcher.group("part2").equals("p"))) {
                matcher = Pattern.compile(LOGIN_DEEPER_USER_FIRST.regex).matcher(input.toString());
                if (matcher.find() && matcher.group().equals(input.toString())) {
                    canBeUsed = true;
                }
            } else if ((matcher.group("part2").equals("user") ||
                    matcher.group("part2").equals("username") ||
                    matcher.group("part2").equals("u")) &&
                    (matcher.group("part1").equals("pass") ||
                            matcher.group("part1").equals("password") ||
                            matcher.group("part1").equals("p"))) {
                matcher = Pattern.compile(LOGIN_DEEPER_PASS_FIRST.regex).matcher(input.toString());
                canBeUsed = true;
            }
            if (canBeUsed) {
                return matcher;
            }
        }
        return null;
    }

    public static TreeMap<Integer, String> playGameWithMatcher(StringBuilder input) {
        Matcher matcher = Pattern.compile(PLAY_GAME_WITH.regex).matcher(input.toString());
        if (matcher.find() && matcher.group().equals(input.toString())) {
            int index = input.indexOf(matcher.group("player"));
            input.delete(index, input.length());
            Matcher userCred;
            if ((userCred = selectUsersMatcher(matcher.group("player"))) != null) {
                map.put(Integer.parseInt(userCred.group("turn")), userCred.group("username"));
                return playGameWithMatcher(input);
            }
        }
        return map;
    }

    private static Matcher selectUsersMatcher(String input) {
        Matcher matcher = Pattern.compile(PARSE_PLAY_GAME_WITH.regex).matcher(input);
        if (matcher.find() && matcher.group().equals(input)) {
            return matcher;
        }
        return null;
    }

    private Matcher infoMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher exitMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher changePassMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher changeNameMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher logoutMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher currentMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher enterMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher exitMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }
}
