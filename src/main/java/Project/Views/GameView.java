package Project.Views;

import Project.CommandlineViews.TileGridPrinter;
import Project.Controllers.GameController;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    public static GameView getInstance() { return instance; }



    public void initialize() {
        this.game = GameController.getGame();
        GameController.getGame().SetPage(this);
        instance = this;
        //todo : initialize
    }
    public void initializeUnits(TileGrid tileGrid){
        for (int i = 0; i < tileGrid.getHeight(); i++) {
            for (int j = 0; j < tileGrid.getWidth(); j++) {
                NonCombatUnit nonCombatUnit = tileGrid.getTile(i, j).getNonCombatUnit();
                if(nonCombatUnit != null) {
                    unitsPane.getChildren().add(nonCombatUnit.getGraphicUnit());
                }
            }
        }
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
    public void addUnit(Unit unit){
        unitsPane.getChildren().add(unit.getGraphicUnit());
    }

    public void click() {
        System.err.println("clicked");
        btn.setVisible(false);
        System.out.println(tilePane.getChildren());
        tilePane.getChildren().addAll(0,game.getTileGrid().getHexes());
        initializeUnits(game.getTileGrid());
        System.out.print(new TileGridPrinter(game.getTileGrid(),10,10).print(new Location(6,6)));
    }
}
