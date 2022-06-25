package Project.Views;

import Project.Models.Tiles.Hex;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameView implements ViewController{
    @FXML
    public GridPane tilePane;
    public Pane tempPane;
    @FXML
    private GridPane buildingsPane;
    @FXML
    private GridPane unitsPane;

    public void initialize() {

    }
}
