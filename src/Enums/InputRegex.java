package Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegex {
    login("[\\s]*user[\\s]+login[\\s]+(?<part1>[-]+.[\\s]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]+(?<part3>[-]+[.[\\s]]+)[\\s]+(?<part2>[.[\\S]]+)[\\s]*"),
    register(""),
    playGameWith(""),
    info(""),
    exit(""),
    changePassword(""),
    changeNickname(""),
    logout(""),
    currentMenu(""),
    enterMenu("[\\s]*menu[\\s]+enter[\\s]+(?<selectedMenu>[.[\\S]]+)[\\s]*"),
    exitMenu("");

    private final String regex;

    InputRegex(String input) {
        this.regex = input;
    }

    public static Matcher inputMatcher(String input, InputRegex check) {
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
