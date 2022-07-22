package Server.Utils;

import java.io.*;

import Server.Models.Database;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class DataBaseSerialization {
    public static DataBaseSerialization getInstance(){
        return instance;
    }
    static DataBaseSerialization instance;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public DataBaseSerialization(XStream xStream, String filePath){
        try {
            xStream.addPermission(AnyTypePermission.ANY);
            objectInputStream = xStream.createObjectInputStream(new FileInputStream(filePath));
            objectOutputStream = xStream.createObjectOutputStream(new FileOutputStream(filePath));
            instance = this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void serializeDataBase(){
        try {
            objectOutputStream.writeObject(Database.getInstance());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void deserializeDataBase(){
        try {
         Database.setInstance((Database) objectInputStream.readObject());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
