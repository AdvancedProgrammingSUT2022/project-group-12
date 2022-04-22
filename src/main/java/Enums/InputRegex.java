package Enums;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegex {
    LOGIN("\\s*user login -+(?<part1>[.\\S]+) ([.\\S]+) -+(?<part2>[.\\S]+) ([.\\S]+)\\s*"),
    LOGIN_DEEPER_USER_FIRST("\\s*user login -+[.\\S]+ (?<username>[.\\S]+) -+[.\\S]+ (?<password>[.\\S]+)\\s*"),
    LOGIN_DEEPER_PASS_FIRST("\\s*user login -+[.\\S]+ (?<password>[.\\S]+) -+[.\\S]+ (?<username>[.\\S]+)\\s*"),
    REGISTER("\\s*user create -+(?<part1>[.\\S]+) [.\\S]+ -+(?<part2>[.\\S]+) [.\\S]+ -+(?<part3>[.\\S]+) [.\\S]+\\s*"),
    PLAY_GAME_WITH("[\\s]*play game (?<player>(--player|-p)[0-9]+ [.\\S]+[\\s]*)+"),
    PARSE_PLAY_GAME_WITH("[\\s]*(--player|-p)(?<turn>[0-9]+) (?<username>[.\\S]+)[\\s]*"),
    EXIT("\\s*exit\\s*"),
    BACK("\\s*back|BACK|Back\\s*"),
    LOGOUT("\\s*logout|LOGOUT|Logout\\s*"),
    CURRENT_MENU("\\s*menu show-current\\s*"),
    ENTER_MENU("\\s*menu enter (?<selectedMenu>Login|Main|(Play Game)|Profile|login|main|(play game)|profile)\\s*"),
    USERNAME("(username|Username|USERNAME|u_name|U|u|user)"),
    PASSWORD("(password|Password|PASSWORD|p_word|P|p|pass)"),
    NICKNAME("(nickname|Nickname|NICKNAME|n_name|N|n|name)"),
    CURRENT("(C|c|current|CURRENT|Current|crnt)"),
    NEW("(N|n|new|NEW|New)"),
    CHANGE_NICKNAME("\\s*profile change -+" + NICKNAME.selectedRegex + " (?<changeNicknameTo>[.\\S]+)\\s*"),
    CHANGE_PASS("\\s*profile change -+" + PASSWORD.selectedRegex + " -+(?<part1>[.\\S]+) [.\\S]+ -+(?<part2>[.\\S]+) [.\\S]+\\s*"),
    CHANGE_PASS_OLD_FIRST("\\s*profile change -+" + PASSWORD.selectedRegex + " -+" + CURRENT.selectedRegex + " (?<old>[.\\S]+) -+" + NEW.selectedRegex + " (?<new>[.\\S]+)\\s*"),
    CHANGE_PASS_NEW_FIRST("\\s*profile change -+" + PASSWORD.selectedRegex + " -+" + NEW.selectedRegex + " (?<new>[.\\S]+) -+" + CURRENT.selectedRegex + " (?<old>[.\\S]+)\\s*"),
    USER_PASS_NICK("\\s*user create -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+) -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+) -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+)\\s*"),
    USER_NICK_PASS("\\s*user create -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+) -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+) -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+)\\s*"),
    PASS_USER_NICK("\\s*user create -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+) -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+) -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+)\\s*"),
    PASS_NICK_USER("\\s*user create -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+) -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+) -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+)\\s*"),
    NICK_USER_PASS("\\s*user create -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+) -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+) -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+)\\s*"),
    NICK_PASS_USER("\\s*user create -+" + NICKNAME.selectedRegex + " (?<nickname>[.\\S]+) -+" + PASSWORD.selectedRegex + " (?<password>[.\\S]+) -+" + USERNAME.selectedRegex + " (?<username>[.\\S]+)\\s*"),
    EXIT_MENU("\\s*menu exit\\s*");

    private static final TreeMap<Integer, String> map = new TreeMap<>();
    private final String selectedRegex;

    InputRegex(String input) {
        this.selectedRegex = input;
    }

    public static Matcher getMatcher(String input, InputRegex inputRegex) {
        Matcher matcher = Pattern.compile(inputRegex.selectedRegex).matcher(input);
        if (matcher.find() && matcher.group().equals(input)) {
            return matcher;
        }
        return null;
    }

    public static Matcher registerMatcher(String input) {
        String regex = REGISTER.selectedRegex;
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find() && matcher.group().equals(input)) {
            String parts = matcher.group("part1") + " " + matcher.group("part2") + " " + matcher.group("part3");
            String regex_u1 = USERNAME.selectedRegex + " " + PASSWORD.selectedRegex + " " + NICKNAME.selectedRegex;
            Matcher m_u1 = Pattern.compile(regex_u1).matcher(parts);
            String regex_u2 = USERNAME.selectedRegex + " " + NICKNAME.selectedRegex + " " + PASSWORD.selectedRegex;
            Matcher m_u2 = Pattern.compile(regex_u2).matcher(parts);
            String regex_p1 = PASSWORD.selectedRegex + " " + USERNAME.selectedRegex + " " + NICKNAME.selectedRegex;
            Matcher m_p1 = Pattern.compile(regex_p1).matcher(parts);
            String regex_p2 = PASSWORD.selectedRegex + " " + NICKNAME.selectedRegex + " " + USERNAME.selectedRegex;
            Matcher m_p2 = Pattern.compile(regex_p2).matcher(parts);
            String regex_n1 = NICKNAME.selectedRegex + " " + USERNAME.selectedRegex + " " + PASSWORD.selectedRegex;
            Matcher m_n1 = Pattern.compile(regex_n1).matcher(parts);
            String regex_n2 = NICKNAME.selectedRegex + " " + PASSWORD.selectedRegex + " " + USERNAME.selectedRegex;
            Matcher m_n2 = Pattern.compile(regex_n2).matcher(parts);
            if (m_u1.find() && m_u1.group().equals(parts)) {
                regex = USER_PASS_NICK.selectedRegex;
            } else if (m_u2.find() && m_u2.group().equals(parts)) {
                regex = USER_NICK_PASS.selectedRegex;
            } else if (m_p1.find() && m_p1.group().equals(parts)) {
                regex = PASS_USER_NICK.selectedRegex;
            } else if (m_p2.find() && m_p2.group().equals(parts)) {
                regex = PASS_NICK_USER.selectedRegex;
            } else if (m_n1.find() && m_n1.group().equals(parts)) {
                regex = NICK_USER_PASS.selectedRegex;
            } else if (m_n2.find() && m_n2.group().equals(parts)) {
                regex = NICK_PASS_USER.selectedRegex;
            } else {
                return null;
            }
            matcher = Pattern.compile(regex).matcher(input);
            if (matcher.find() && matcher.group().equals(input)) {
                return matcher;
            }
        }
        return null;
    }

    public static Matcher loginMatcher(StringBuilder input) {
        Matcher matcher = Pattern.compile(LOGIN.selectedRegex).matcher(input.toString());
        boolean canBeUsed = false;
        if (matcher.find() && matcher.group().equals(input.toString())) {
            Matcher part1MatcherUser = Pattern.compile(USERNAME.selectedRegex).matcher(matcher.group("part1"));
            Matcher part2MatcherPass = Pattern.compile(PASSWORD.selectedRegex).matcher(matcher.group("part2"));
            Matcher part1MatcherPass = Pattern.compile(PASSWORD.selectedRegex).matcher(matcher.group("part1"));
            Matcher part2MatcherUser = Pattern.compile(PASSWORD.selectedRegex).matcher(matcher.group("part2"));
            if (part1MatcherUser.find() && part1MatcherUser.group().equals(matcher.group("part1")) && part2MatcherPass.find() && part2MatcherPass.group().equals(matcher.group("part2"))) {
                matcher = Pattern.compile(LOGIN_DEEPER_USER_FIRST.selectedRegex).matcher(input.toString());
                if (matcher.find() && matcher.group().equals(input.toString())) {
                    canBeUsed = true;
                }
            } else if (part1MatcherPass.find() && part1MatcherPass.group().equals(matcher.group("part1")) && part2MatcherUser.find() && part2MatcherUser.group().equals(matcher.group("part2"))) {
                matcher = Pattern.compile(LOGIN_DEEPER_PASS_FIRST.selectedRegex).matcher(input.toString());
                canBeUsed = true;
            }
            if (canBeUsed) {
                return matcher;
            }
        }
        return null;
    }

    public static TreeMap<Integer, String> playGameWithMatcher(StringBuilder input) {
        Matcher matcher = Pattern.compile(PLAY_GAME_WITH.selectedRegex).matcher(input.toString());
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
        Matcher matcher = Pattern.compile(PARSE_PLAY_GAME_WITH.selectedRegex).matcher(input);
        if (matcher.find() && matcher.group().equals(input)) {
            return matcher;
        }
        return null;
    }

    public static Matcher changePasswordMatcher(String input) {
        String regex = CHANGE_PASS.selectedRegex;
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find() && matcher.group().equals(input)) {
            Matcher checkPart1Current = Pattern.compile(CURRENT.selectedRegex).matcher(matcher.group("part1"));
            Matcher checkPart1New = Pattern.compile(NEW.selectedRegex).matcher(matcher.group("part1"));
            Matcher checkPart2Current = Pattern.compile(CURRENT.selectedRegex).matcher(matcher.group("part2"));
            Matcher checkPart2New = Pattern.compile(NEW.selectedRegex).matcher(matcher.group("part2"));

            if (checkPart1Current.find() && checkPart1Current.group().equals(matcher.group("part1")) && checkPart2New.find() && checkPart2New.group().equals(matcher.group("part2"))) {
                regex = CHANGE_PASS_OLD_FIRST.selectedRegex;
            } else if (checkPart2Current.find() && checkPart2Current.group().equals(matcher.group("part2")) && checkPart1New.find() && checkPart1New.group().equals(matcher.group("part1"))) {
                regex = CHANGE_PASS_NEW_FIRST.selectedRegex;
            } else {
                return null;
            }
            matcher = Pattern.compile(regex).matcher(input);
            if (matcher.find() && matcher.group().equals(input)) {
                return matcher;
            }
        }
        return null;
    }
}