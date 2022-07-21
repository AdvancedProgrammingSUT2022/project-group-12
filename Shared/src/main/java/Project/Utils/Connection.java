package Project.Utils;

import java.io.*;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public Connection(Socket socket) {

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String listen() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        try {
            this.bufferedWriter.write(message + '\n');
            this.bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        try {
            this.bufferedWriter.close();
            this.bufferedReader.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
