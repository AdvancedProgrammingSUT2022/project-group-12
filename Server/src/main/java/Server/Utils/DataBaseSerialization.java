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
    XStream xStream;
    File dataBaseFile;
    public DataBaseSerialization(XStream xStream, String filePath){
            xStream.addPermission(AnyTypePermission.ANY);
            this.dataBaseFile = new File(filePath);
            this.xStream = xStream;
            instance = this;
    }
    public void serializeDataBase(){
        try {
            ObjectOutputStream objectOutputStream = xStream.createObjectOutputStream(new FileOutputStream(this.dataBaseFile));
            objectOutputStream.writeObject(Database.getInstance());
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void deserializeDataBase(){
        try {
         if(dataBaseFile.length() < 5) return;
         ObjectInputStream objectInputStream = xStream.createObjectInputStream(new FileInputStream(this.dataBaseFile));
         Database.setInstance((Database) objectInputStream.readObject());
         objectInputStream.close();
        } catch (IOException e) {
            Database.setInstance(new Database());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
