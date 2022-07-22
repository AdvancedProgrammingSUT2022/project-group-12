package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.Command;
import Server.Utils.CommandException;
import Server.Views.Menu;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.*;
import java.util.ArrayList;

public class MainMenuController extends Menu {

    public static Game startNewGame(ArrayList<String> usernames, int width, int height,User currentUser) throws CommandException {
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
        Game game = new Game(users, height, width);
        database.addGame(game);
        System.out.println("game.getUsers().contains() = " + game.getUsers().contains(currentUser));
        GameController.setGame(game);
        return game;
    }
    public static void enterNewGame(Game game){
        GameController.setGame(game);
    }

    @Override
    protected void handleCommand(Command command) {

    }

}
