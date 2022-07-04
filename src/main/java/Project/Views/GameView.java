package Project.Views;

import Project.Controllers.GameController;
import Project.Models.Game;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameView implements ViewController {
    private static GameView instance;
    private Game game;
    @FXML
    private GridPane tilePane;
    @FXML
    private GridPane buildingsPane;
    @FXML
    private GridPane unitsPane;

    public GameView() {
        initialize();
    }

    public static GameView getInstance() {
        return instance;
    }

    public void initialize() {
        this.game = GameController.getGame();
        GameController.getGame().SetPage(this);
        instance = this;
        //todo : initialize
    }

    public GridPane getTilePane() {
        return tilePane;
    }

    public GridPane getBuildingsPane() {
        return buildingsPane;
    }

    public GridPane getUnitsPane() {
        return unitsPane;
    }

    public Game getGame() {
        return game;
    }
}
