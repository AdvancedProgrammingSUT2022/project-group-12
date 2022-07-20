package Server;

import Project.Utils.Constants;
import Server.Models.Database;
import Server.Utils.RequestHandler;
import Server.Views.MenuStack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        Database.getInstance().deserialize();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constants.SERVER_PORT);
            System.out.println("server initialized successfully on port " + Constants.SERVER_PORT);
        } catch (IOException e) {
            System.err.println("can't initialize server on port " + Constants.SERVER_PORT);
            System.exit(0);
        }

        MenuStack menuStack = MenuStack.getInstance();

        System.out.println("waiting for client to connect...");
        Socket socket;
        RequestHandler requestHandler;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("error occurred during wait for client");
            return;
        }
        System.out.println("client connected");

        requestHandler = new RequestHandler(socket);
        menuStack.setCommandReceiver(requestHandler);
        menuStack.gotoLoginMenu();
        requestHandler.run();

        Database.getInstance().serialize();
    }
}
