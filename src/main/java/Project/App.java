package Project;

import Project.Models.Database;
import Project.Views.Menu;
import Project.Views.MenuStack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(Pane root) {
        App.scene.setRoot(root);
    }

    @Override
    public void start(Stage stage) {
        Database.getInstance().deserialize();
        App.scene = new Scene(new Pane());
        stage.setScene(App.scene);
        stage.setResizable(false);

        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("MainPage"));

        stage.show();
    }
}
