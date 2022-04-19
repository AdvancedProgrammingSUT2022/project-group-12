package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegexEnum {
    LOGIN("[\\s]*user[\\s]+login[\\s]+(?<part1>[-]{1,2}.[\\s]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]+(?<part3>[-]{1,2}[.[\\s]]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]*"),
    REGISTER(""),
    PLAY_GAME_WITH(""),
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

    public static Matcher inputMatcher(String input, InputRegexEnum check) {
        Matcher matcher = Pattern.compile(check.regex).matcher(input);
        if (!matcher.find() || !matcher.group().equals(input)) {
            return null;
        }
        return returnOptions(matcher);
    }

    private static Matcher returnOptions(Matcher givenMatcher) {

        return givenMatcher;
    }

    private static Matcher registerMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher loginMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher playGameWithMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher infoMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher exitMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher changePassMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher changeNameMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher logoutMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher currentMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher enterMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }

    private static Matcher exitMenuMatcher(Matcher givenMatcher) {
        return givenMatcher;
    }
}
