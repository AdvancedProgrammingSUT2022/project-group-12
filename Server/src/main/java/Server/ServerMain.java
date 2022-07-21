package Server;


import Project.Utils.Constants;
import Server.Models.Database;
import Server.Utils.RequestHandler;

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

//            Thread thread = new Thread(() -> {
            System.out.println("server is running (clients can start game) ...");
            while (true) {
                    Socket socket;
                    try {
                        socket = serverSocket.accept();
//                        socketOfTracker = serverSocketOfTracker.accept();
                    } catch (IOException e) {
                        System.err.println("error occurred during wait for clients");
                        e.printStackTrace();
                        return;
                    }
                    System.out.println("client connected");
                    RequestHandler requestHandler = new RequestHandler(socket);
                    requestHandler.run();
                }
//            });

//            thread.start();
//            System.out.println("server is running (clients can start game) ...");

//            MenuStack menuStack = MenuStack.getInstance();
//            menuStack.gotoLoginMenu();

        } catch (IOException e) {
            System.err.println("can't initialize server on port " + Constants.SERVER_PORT + " or " + Constants.SERVER_PORT_TRACKER);
            return;
        } finally {
            Database.getInstance().serialize();
        }

//        menuStack.setCommandReceiver(requestHandler);
    }
}
