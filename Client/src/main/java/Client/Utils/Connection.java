package Client.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;

    public Connection(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String listen() {
        try {
            return this.dataInputStream.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        try {
            this.dataOutputStream.writeUTF(message);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        try {
            this.dataOutputStream.close();
            this.dataInputStream.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
