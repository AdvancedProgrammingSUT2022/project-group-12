package Controllers;

import Models.Database;
import Models.Game;
import Models.User;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;

public class MainMenuController {

    public static void startNewGame(ArrayList<String> usernames) throws CommandException {
        ArrayList<User> users = new ArrayList<>();
        Database database = Database.getInstance();
        for (String username : usernames) {
            User user = database.getUser(username);
            if (user == null) {
                throw new CommandException(CommandResponse.USER_DOES_NOT_EXISTS, username);
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