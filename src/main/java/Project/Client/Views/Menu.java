package Project.Client.Views;

import Project.Client.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Menu {
    private final Pane root;
    private final ViewController controller;

    private Menu(Pane root, ViewController controller) {
        this.root = root;
        this.controller = controller;
    }

    public static Menu loadFromFXML(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Project/fxml/" + fxmlName + ".fxml"));
        try {
            Pane root = fxmlLoader.load();
            ViewController controller = fxmlLoader.getController();
            controller.loadEachTime();
            return new Menu(root, controller);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Pane getRoot() {
        return root;
    }

    public ViewController getController() {
        return controller;
    }
}
