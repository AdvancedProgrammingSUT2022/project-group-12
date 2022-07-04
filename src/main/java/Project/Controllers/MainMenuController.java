package Project.Controllers;

import Project.Models.Database;
import Project.Models.Game;
import Project.Models.User;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;

import java.util.ArrayList;

public class MainMenuController {

    public static Game startNewGame(ArrayList<String> usernames) throws CommandException {
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
        GameController.setGame(game);
        return game;
    }
}
