package Applications;

import Models.Database;
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

    public static void setScene(String address) {
        App.scene.setRoot(loadFXML(address));
    }

    private static Pane loadFXML(String address) {
        try {
            URL url = new URL(Objects.requireNonNull(App.class.getResource("/fxml/" + address + ".fxml")).toExternalForm());
            return FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Pane root = loadFXML("LoginPage");
        assert root != null;
        Database.getInstance().deserialize();
        App.scene = new Scene(root);
        stage.setScene(App.scene);
        stage.setResizable(false);
        stage.show();
    }
}
