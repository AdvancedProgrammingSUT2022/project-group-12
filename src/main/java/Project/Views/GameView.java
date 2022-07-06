package Project.Views;

import Project.CommandlineViews.TileGridPrinter;
import Project.Controllers.GameController;
import Project.Models.Game;
import Project.Models.Location;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class GameView implements ViewController {
    private static GameView instance;
    public Button btn;
    @FXML
    private ScrollPane scrollPane;
    private Game game;
    @FXML
    private Pane tilePane;
    @FXML
    private Pane buildingsPane;
    @FXML
    private Pane unitsPane;

    public static GameView getInstance() {
        return instance;
    }



    public void initialize() {
        this.game = GameController.getGame();
        GameController.getGame().SetPage(this);
        instance = this;
        //todo : initialize
    }

    public Pane getTilePane() {
        return tilePane;
    }

    public Pane getBuildingsPane() {
        return buildingsPane;
    }

    public Pane getUnitsPane() {
        return unitsPane;
    }

    public Game getGame() {
        return game;
    }

    public void click() {
        System.err.println("clicked");
        btn.setVisible(false);
        tilePane.getChildren().addAll(game.getTileGrid().getHexes());
        System.out.print(new TileGridPrinter(game.getTileGrid(),10,10).print(new Location(6,6)));
    }
}
