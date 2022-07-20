package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.CommandException;

import java.util.ArrayList;

public class MainMenuController {

    public static Game startNewGame(ArrayList<String> usernames,int width,int height) throws CommandException {
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
        Game game = new Game(users,width,height);
        database.addGame(game);
        GameController.setGame(game);
        return game;
    }
}
