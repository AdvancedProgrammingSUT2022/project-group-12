package Client;

import Client.Utils.*;
import Client.Views.*;
import Project.Utils.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        Socket socket;
        Socket socketOfTracker;
        try {
            socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            socketOfTracker = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT_TRACKER);
            System.out.println("connected to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("can't connect to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
            return;
        }

        UpdateTracker updateTracker = new UpdateTracker(socketOfTracker);
        Thread thread = new Thread(updateTracker);
        thread.setDaemon(true);
        thread.start();
        RequestSender.initialize(socket);
        launch(args);
    }

    public static void setRoot(Pane root) {
        App.scene.setRoot(root);
    }

    @Override
    public void start(Stage stage) {
        App.scene = new Scene(new Pane());
//        App.scene.getStylesheets().add(Objects.requireNonNull(App.getResourcePath("css/styles.css")));
        stage.setScene(App.scene);
        stage.setResizable(false);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("LoginPage"));
        stage.show();
    }

    public static String getResourcePath(String path) {
        return Objects.requireNonNull(App.class.getResource(path)).toExternalForm();
    }
}
