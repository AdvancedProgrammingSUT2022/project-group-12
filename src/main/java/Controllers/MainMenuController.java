package Controllers;

import Models.Database;
import Models.Game;
import Models.User;
import Views.LoginMenu;
import Views.MenuStack;
import Views.ProfileMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {

    public static String enterMenu(Matcher matcher) {
        String newMenu = matcher.group("selectedMenu").toLowerCase();
        switch (newMenu) {
            case "login" -> MenuStack.getInstance().pushMenu(new LoginMenu());
            case "profile" -> MenuStack.getInstance().pushMenu(new ProfileMenu());
            // todo: should get user
//            case "play game" -> MenuStack.getInstance().pushMenu(new GameMenu(user.getRunningGame().getController()));
            case "main" -> {
                return "already in main menu";
            }
        }
        return "menu changed to " + newMenu + " successfully";
    }

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