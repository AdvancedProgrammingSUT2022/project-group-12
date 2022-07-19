package Project.Server.Controllers;

import Project.Server.Models.Database;
import Project.Server.Models.Game;
import Project.Server.Utils.CommandException;
import Project.SharedModels.User;
import Project.SharedUtils.CommandResponse;

import java.util.ArrayList;

public class MainMenuController {

    public static Game startNewGame(ArrayList<String> usernames) throws CommandException {
//        System.out.println(usernames);
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
