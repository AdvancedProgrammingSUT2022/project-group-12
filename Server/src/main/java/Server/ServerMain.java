package Server;


import Project.Utils.Constants;
import Server.Models.Database;
import Server.Utils.DataBaseSerialization;
import Server.Utils.RequestHandler;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) throws IOException {

        createDabaseSerialization();
        DataBaseSerialization.getInstance().deserializeDataBase();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
              DataBaseSerialization.getInstance().serializeDataBase();
        }));
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println("server initialized successfully on port " + Constants.SERVER_PORT);

//            Thread thread = new Thread(() -> {
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
//            });

//            thread.start();
//            System.out.println("server is running (clients can start game) ...");

        } catch (IOException e) {
            System.err.println("can't initialize server on port " + Constants.SERVER_PORT);
        } finally {
            System.out.println("hello");
            //    Database.getInstance().serialize();
        }
    }

    private static void createDabaseSerialization() {
        new DataBaseSerialization(new XStream(),"temp.txt");
    }


}
