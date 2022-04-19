package Enums;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegexEnum {
    LOGIN("[\\s]*user[\\s]+login[\\s]+(?<part1>[-]{1,2}.[\\s]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]+(?<part3>[-]{1,2}[.[\\s]]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]*"),
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

    private TreeMap<Integer, String> map = new TreeMap<>();

    public void inputMatcher(String input, InputRegexEnum check) {
        Matcher matcher = Pattern.compile(check.regex).matcher(input);
        if (!matcher.find() || !matcher.group().equals(input)) {
            returnOptions(new StringBuilder().append(input));
        }
    }

    private TreeMap<Integer, String> returnOptions(StringBuilder input) {
        if (regex.startsWith("play game")) {
            return playGameWithMatcher(input);
        }
        return null;
    }

    private Matcher registerMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private Matcher loginMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private TreeMap<Integer, String> playGameWithMatcher(StringBuilder input) {
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

    private Matcher selectUsersMatcher(String input) {
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
