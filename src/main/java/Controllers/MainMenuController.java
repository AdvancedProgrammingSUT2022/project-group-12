package Controllers;

import Models.Database;
import Models.Game;
import Models.User;
import Views.GameMenu;
import Views.LoginMenu;
import Views.MenuStack;
import Views.ProfileMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {

    public static GameController startNewGame(ArrayList<String> usernames) {
        ArrayList<User> users = new ArrayList<>();
        Database database = Database.getInstance();
        for (String username : usernames) {
            User user = database.getUser(username);
            if (user == null) {
//                return CommandResponse.INVALID_COMMAND; // todo: exception
            } else {
                users.add(user);
            }
        }
        Game game = new Game(users);
        database.addGame(game);
        for (User user : users) {
            user.addGame(game);
            user.setRunningGame(game); // todo: is required?
        }
        return new GameController(game);
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