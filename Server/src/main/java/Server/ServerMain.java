package Server;


import Project.Utils.Constants;
import Server.Models.Database;
import Server.Utils.RequestHandler;
import Server.Views.MenuStack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static Socket socketOfTracker;

    public static void main(String[] args) {

        Database.getInstance().deserialize();

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
             ServerSocket serverSocketOfTracker = new ServerSocket(Constants.SERVER_PORT_TRACKER)) {
            System.out.println("server initialized successfully on ports " + Constants.SERVER_PORT + " and " + Constants.SERVER_PORT_TRACKER);

            MenuStack menuStack = MenuStack.getInstance();
            menuStack.gotoLoginMenu();
            requestHandler.run();

        } catch (IOException e) {
            System.err.println("can't initialize server on port " + Constants.SERVER_PORT + " or " + Constants.SERVER_PORT_TRACKER);
            return;
        }

        System.out.println("waiting for client to connect...");
        Socket socket;
        RequestHandler requestHandler;
        try {
            socket = serverSocket.accept();
            socketOfTracker = serverSocketOfTracker.accept();
        } catch (IOException e) {
            System.err.println("error occurred during wait for client");
            return;
        }
        System.out.println("client connected");

        requestHandler = new RequestHandler(socket);
        menuStack.setCommandReceiver(requestHandler);


        Database.getInstance().serialize();
    }
}
