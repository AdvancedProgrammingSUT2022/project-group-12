package Server;


import Project.Utils.Constants;
import Server.Models.Database;
import Server.Utils.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) {

        Database.getInstance().deserialize();

        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println("server initialized successfully on port " + Constants.SERVER_PORT);

            System.out.println("server is running (client sockets can connect) ...");
            while (true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("error occurred during wait for clients");
                    e.printStackTrace();
                    return;
                }
                System.out.println("a client socket connected");
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();
            }

        } catch (IOException e) {
            System.err.println("can't initialize server on port " + Constants.SERVER_PORT);
        } finally {
            Database.getInstance().serialize();
        }
    }
}
