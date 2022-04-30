package Utils;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InputRegex {
    CONTAINS_DIGIT(".*[0-9].*"),
    CONTAINS_UPPERCASE_ALPHABET(".*[A-Z].*"),
    CONTAINS_LOWERCASE_ALPHABET(".*[a-z].*"),
    CONTAINS_SPECIAL_CHAR(".*[[*][.]!@$%^&(){}\\[\\]:;<>,?/~_+[-]=|].*"),
    USERNAME("--username|--Username|--USERNAME|--u_name|-U|-u|--user"),
    PASSWORD("--password|--Password|--PASSWORD|--p_word|-P|-p|--pass"),
    NICKNAME("--nickname|--Nickname|--NICKNAME|--n_name|-N|-n|--name"),
    CURRENT("-C|-c|--current|--CURRENT|--Current|--crnt"),
    NEW("-N|-n|--new|--NEW|--New"),
    LOGIN("\\s*user login " +
            "(?<part1>" + USERNAME.selectedRegex + "|" + PASSWORD.selectedRegex + ") ([.\\S]+) " +
            "(?<part2>" + USERNAME.selectedRegex + "|" + PASSWORD.selectedRegex + ") ([.\\S]+)\\s*"),
    LOGIN_DEEPER_USER_FIRST("\\s*user login " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+) " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+)\\s*"),
    LOGIN_DEEPER_PASS_FIRST("\\s*user login " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+) " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+)\\s*"),
    REGISTER("\\s*user create " +
            "(?<part1>(" + USERNAME.selectedRegex + "|" + PASSWORD.selectedRegex + "|" + NICKNAME.selectedRegex + ")) [.\\S]+ " +
            "(?<part2>(" + USERNAME.selectedRegex + "|" + PASSWORD.selectedRegex + "|" + NICKNAME.selectedRegex + ")) [.\\S]+ " +
            "(?<part3>(" + USERNAME.selectedRegex + "|" + PASSWORD.selectedRegex + "|" + NICKNAME.selectedRegex + ")) [.\\S]+\\s*"),
    PLAY_GAME_WITH("[\\s]*play game (?<player>(--player|-p)[0-9]+ [.\\S]+[\\s]*)+"),
    PARSE_PLAY_GAME_WITH("[\\s]*(--player|-p)(?<turn>[0-9]+) (?<username>[.\\S]+)[\\s]*"),
    EXIT("\\s*exit\\s*"),
    MENU_OPTIONS("Login|Main|Play Game|Profile|login|main|play game|profile"),
    BACK("\\s*back|BACK|Back\\s*"),
    LOGOUT("\\s*logout|LOGOUT|Logout\\s*"),
    CURRENT_MENU("\\s*menu show-current\\s*"),
    ENTER_MENU("\\s*menu enter (?<selectedMenu>" + MENU_OPTIONS.selectedRegex + ")\\s*"),
    CHANGE_NICKNAME("\\s*profile change (" + NICKNAME.selectedRegex + ") (?<changeNicknameTo>[.\\S]+)\\s*"),
    CHANGE_PASS("\\s*profile change (" + PASSWORD.selectedRegex + ") " +
            "(?<part1>" + CURRENT + "|" + NEW + ") [.\\S]+ " +
            "(?<part2>" + CURRENT + "|" + NEW + ") [.\\S]+\\s*"),
    CHANGE_PASS_OLD_FIRST("\\s*profile change (" + PASSWORD.selectedRegex + ") " +
            "(" + CURRENT.selectedRegex + ") (?<old>[.\\S]+) " +
            "(" + NEW.selectedRegex + ") (?<new>[.\\S]+)\\s*"),
    CHANGE_PASS_NEW_FIRST("\\s*profile change (" + PASSWORD.selectedRegex + ") " +
            "(" + NEW.selectedRegex + ") (?<new>[.\\S]+) " +
            "(" + CURRENT.selectedRegex + ") (?<old>[.\\S]+)\\s*"),
    USER_PASS_NICK("\\s*user create " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+) " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+) " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+)\\s*"),
    USER_NICK_PASS("\\s*user create " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+) " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+) " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+)\\s*"),
    PASS_USER_NICK("\\s*user create " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+) " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+) " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+)\\s*"),
    PASS_NICK_USER("\\s*user create " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+) " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+) " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+)\\s*"),
    NICK_USER_PASS("\\s*user create " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+) " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+) " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+)\\s*"),
    NICK_PASS_USER("\\s*user create " +
            "(" + NICKNAME.selectedRegex + ") (?<nickname>[.\\S]+) " +
            "(" + PASSWORD.selectedRegex + ") (?<password>[.\\S]+) " +
            "(" + USERNAME.selectedRegex + ") (?<username>[.\\S]+)\\s*"),
    EXIT_MENU("\\s*menu exit\\s*");

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
        Matcher matcher = Pattern.compile(REGISTER.selectedRegex).matcher(input);
        String regex = "nothing";
        if (matcher.find() && matcher.group().equals(input)) {
            String[] usernameArgs = USERNAME.selectedRegex.split("\\|");
            String[] passwordArgs = PASSWORD.selectedRegex.split("\\|");
            String[] nicknameArgs = NICKNAME.selectedRegex.split("\\|");
            if (Arrays.toString(usernameArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(passwordArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(nicknameArgs).contains(matcher.group("part3"))) {
                        regex = USER_PASS_NICK.selectedRegex;
                    }
                } else if (Arrays.toString(nicknameArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(passwordArgs).contains(matcher.group("part3"))) {
                        regex = USER_NICK_PASS.selectedRegex;
                    }
                }
            } else if (Arrays.toString(nicknameArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(passwordArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(usernameArgs).contains(matcher.group("part3"))) {
                        regex = NICK_PASS_USER.selectedRegex;
                    }
                } else if (Arrays.toString(usernameArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(passwordArgs).contains(matcher.group("part3"))) {
                        regex = NICK_USER_PASS.selectedRegex;
                    }
                }
            } else if (Arrays.toString(passwordArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(usernameArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(nicknameArgs).contains(matcher.group("part3"))) {
                        regex = PASS_USER_NICK.selectedRegex;
                    }
                } else if (Arrays.toString(nicknameArgs).contains(matcher.group("part2"))) {
                    if (Arrays.toString(usernameArgs).contains(matcher.group("part3"))) {
                        regex = PASS_NICK_USER.selectedRegex;
                    }
                }
            }
            if (!regex.equals("nothing")) {
                matcher = Pattern.compile(regex).matcher(input);
                if (matcher.find() && matcher.group().equals(input)) {
                    return matcher;
                }
            }
        }
        return null;
    }

    public static Matcher loginMatcher(String input) {
        Matcher matcher = Pattern.compile(LOGIN.selectedRegex).matcher(input);
        String regex = "nothing";
        if (matcher.find() && matcher.group().equals(input)) {
            String[] usernameArgs = USERNAME.selectedRegex.split("\\|");
            String[] passwordArgs = PASSWORD.selectedRegex.split("\\|");
            if (Arrays.toString(usernameArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(passwordArgs).contains(matcher.group("part2"))) {
                    regex = LOGIN_DEEPER_USER_FIRST.selectedRegex;
                }
            } else if (Arrays.toString(passwordArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(usernameArgs).contains(matcher.group("part2"))) {
                    regex = LOGIN_DEEPER_PASS_FIRST.selectedRegex;
                }
            }
            if (!regex.equals("nothing")) {
                matcher = Pattern.compile(regex).matcher(input);
                if (matcher.find() && matcher.group().equals(input)) {
                    return matcher;
                }
            }
        }
        return null;
    }

    private static final TreeMap<Integer, String> map = new TreeMap<>();

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
        Matcher matcher = Pattern.compile(CHANGE_PASS.selectedRegex).matcher(input);
        String regex = "nothing";
        if (matcher.find() && matcher.group().equals(input)) {
            String[] oldArgs = CURRENT.selectedRegex.split("\\|");
            String[] newArgs = NEW.selectedRegex.split("\\|");
            if (Arrays.toString(oldArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(newArgs).contains(matcher.group("part2"))) {
                    regex = CHANGE_PASS_NEW_FIRST.selectedRegex;
                }
            } else if (Arrays.toString(newArgs).contains(matcher.group("part1"))) {
                if (Arrays.toString(oldArgs).contains(matcher.group("part2"))) {
                    regex = CHANGE_PASS_OLD_FIRST.selectedRegex;
                }
            }
            if (!regex.equals("nothing")) {
                matcher = Pattern.compile(regex).matcher(input);
                if (matcher.find() && matcher.group().equals(input)) {
                    return matcher;
                }
            }
        }
        return null;
    }
}