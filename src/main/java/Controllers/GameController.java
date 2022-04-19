package Controllers;

import Enums.CommandResponseEnum;
import Models.Civilization;
import Models.Database;
import Models.Game;
import Models.User;

import java.util.ArrayList;

public class GameController {
    private static boolean checkUsernameValidation(ArrayList<String> usernames) {
        Database database = Database.getInstance();
        for (String list : usernames) {
            if (!database.checkForUsername(list)) {
                return false;
            }
        }
        return true;
    }

    public static CommandResponseEnum startNewGame(ArrayList<String> usernames) {
        ArrayList<User> users = new ArrayList<>();
        if (!checkUsernameValidation(usernames)) {
            return CommandResponseEnum.USER_DOESNT_EXISTS;
        }
        Database database = Database.getInstance();
        for (String username : usernames) {
            User user = database.getUser(username);
            users.add(user);
        }
        Game game = new Game(users);
        database.addGame(game);
        for (User user : users) {
            user.addGame(game);
            user.setRunningGame(game);
        }
        return CommandResponseEnum.OK;
    }
}
