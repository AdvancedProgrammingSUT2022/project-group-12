package project.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import project.App;

import java.io.IOException;

public class Menu {
    private final Pane root;
    private final Object controller;

    public Menu(Pane root, Object controller) {
        this.root = root;
        this.controller = controller;
    }

    public static Menu loadFromFXML(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxmlName + ".fxml"));
        try {
            Pane root = fxmlLoader.load();
            Object controller = fxmlLoader.getController();
            return new Menu(root, controller);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getRoot() {
        return root;
    }

    public Object getController() {
        return controller;
    }
}
