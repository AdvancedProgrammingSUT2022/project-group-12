package Controllers;

import Enums.CommandResponse;
import Models.Database;
import Models.Game;
import Models.User;
import Views.GameMenu;
import Views.LoginMenu;
import Views.MenuStack;
import Views.ProfileMenu;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

public class MainMenuController {
    public User user = new User("sth","sth","Sth");
    public String enterMenu(Matcher matcher) {
        String newMenu = matcher.group("selectedMenu").toLowerCase();
        switch (newMenu) {
            case "login" -> MenuStack.getInstance().pushMenu(new LoginMenu());
            case "profile" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
            case "play game" -> MenuStack.getInstance().pushMenu(new GameMenu(user.getRunningGame().getController()));
            case "main" -> {
                return "already in main menu";
            }
        }
        return "menu changed to " + newMenu + " successfully";
    }

    public CommandResponse startNewGame(TreeMap<Integer, String> usernames) {
        ArrayList<User> users = new ArrayList<>();
        Database database = Database.getInstance();
        for (Map.Entry<Integer, String> set : usernames.entrySet()) {
            if (!database.checkForUsername(set.getValue())) {
                return CommandResponse.INVALID_COMMAND;
            } else {
                users.add(database.getUser(set.getValue()));
            }
        }
        Game newGame = new Game(users);
        database.addGame(newGame);
        for (User user : users) {
            user.addGame(newGame);
            user.setRunningGame(newGame);
        }
        MenuStack.getInstance().pushMenu(new GameMenu(new GameController(newGame)));
        return CommandResponse.OK;
    }
}

//        if (!checkUsernameValidation(usernames)) {
//
//        }
//        Database database = Database.getInstance();
//        for (String username : usernames) {
//            User user = database.getUser(username);
//            users.add(user);
//        }
//        Game game = new Game(users);
//        database.addGame(game);
//        for (User user : users) {
//            user.addGame(game);
//            user.setRunningGame(game);
//        }