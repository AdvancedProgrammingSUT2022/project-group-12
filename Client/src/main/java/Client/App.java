package Client;

import Client.Utils.RequestHandler;
import Client.Views.Menu;
import Client.Views.MenuStack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            System.out.println("can't connect to server at " + SERVER_HOST + ":" + SERVER_PORT);
            System.exit(0);
        }
        RequestHandler.initialize(socket);
//        MenuStack.getInstance().gotoLoginMenu();
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
