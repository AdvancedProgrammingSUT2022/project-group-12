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
        System.out.println("game.getUsers().contains() = " + game.getUsers().contains(currentUser));
        File file = new File("temp.txt");
        XStream xStream = new XStream(new StaxDriver());
        xStream.addPermission(AnyTypePermission.ANY);
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        Game game1;
        try {
            objectOutputStream = xStream.createObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(game);
            objectInputStream = xStream.createObjectInputStream(new FileInputStream(file));
            game1 = (Game) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(game1.toString());
        database.addGame(game1);
        System.out.println("game1.getUsers().contains() = " + game1.getUsers().contains(currentUser));
        GameController.setGame(game1);
        return game1;
    }

    @Override
    protected void handleCommand(Command command) {

    }
}
