package Server;

import Project.Utils.Constants;
import Server.Models.Database;
import Server.Views.MenuStack;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        Database.getInstance().deserialize();

        ServerSocket socket = null;
        try {
            socket = new ServerSocket(Constants.SERVER_PORT);
            System.out.println("server initialized successfully on port " + Constants.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("can't initialize server on port " + Constants.SERVER_PORT);
            System.exit(0);
        }

        MenuStack menuStack = MenuStack.getInstance();
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.getTopMenu().run();
        }
        Database.getInstance().serialize();
    }
}
