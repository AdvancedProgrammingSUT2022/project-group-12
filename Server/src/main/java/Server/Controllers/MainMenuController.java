package Server.Controllers;

import Project.Models.User;
import Project.Utils.CommandResponse;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.Command;
import Server.Utils.CommandException;
import Server.Views.MenuStack;

import java.io.*;
import java.util.ArrayList;

public class MainMenuController {

    public static Game startNewGame(ArrayList<String> usernames, int width, int height,User currentUser) throws CommandException {
        ArrayList<User> users = new ArrayList<>();
        for (String username : usernames) {
            User user = Database.getInstance().getUser(username);
            if (user == null) {
                throw new CommandException(CommandResponse.USER_DOES_NOT_EXISTS, username);
            } else {
                users.add(user);
            }
        }
        Game game = new Game(users, height, width);
        Database.getInstance().addGame(game);
        System.out.println("game.getUsers().contains() = " + game.getUsers().contains(currentUser));
        GameController.setGame(game);
        return game;
    }

    public static void enterNewGame(Game game){
        GameController.setGame(game);
    }

    public static void bindUpdateNotifier(Game game, MenuStack userMenuStack) {
        if (userMenuStack.getUpdateNotifier() != null) {
            game.bindUserCivUpdatesTo(userMenuStack.getUser(), userMenuStack.getUpdateNotifier());
        } else {
            System.err.println("WARNING: no UpdateNotifier was bound by the client, updates will not be sent");
        }
    }

}
