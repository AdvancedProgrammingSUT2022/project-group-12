package Project;

import Project.Models.Database;
import Project.Views.Menu;
import Project.Views.MenuStack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(Pane root) {
        App.scene.setRoot(root);
    }

    private static Pane loadFXML(String address) {
        System.out.println(address);
        try {
            URL url = new URL(Objects.requireNonNull(App.class.getResource("fxml/" + "ProfilePage" + ".fxml")).toExternalForm());
            return FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start(Stage stage) {
        Database.getInstance().deserialize();
        App.scene = new Scene(new Pane());
        stage.setScene(App.scene);
        stage.setResizable(false);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("LoginPage"));
        stage.show();
    }
}
