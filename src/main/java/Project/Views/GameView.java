package Project.Views;

import Project.Controllers.GameController;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class GameView implements ViewController {
    private static GameView instance;
    @FXML
    private Pane selectionPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button btn;
    @FXML
    private Menu cheat;
    @FXML
    private Menu info;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pane hexPane;
    @FXML

    public static GameView getInstance() {
        return instance;
    }

    public void initialize() {
        GameController.getGame().SetPage(this);
        instance = this;
        //todo : initialize
    }

    public Game getGame() {
        return GameController.getGame();
    }

    public void showGameMap() {
        btn.setVisible(false);
//        System.out.println(hexPane.getChildren());
//        hexPane.getChildren().addAll(0, GameController.getGame().getTileGrid().getHexes());
        initializeHexPane();
//        System.out.print(new TileGridPrinter(game.getTileGrid(), 10, 10).print(new Location(6, 6)));
    }

    public void initializeHexPane() {
        TileGrid tileGrid = GameController.getGame().getTileGrid();
        for (int i = 0; i < tileGrid.getHeight(); i++) {
            for (int j = 0; j < tileGrid.getWidth(); j++) {
                Hex hex = GameController.getGameTile(new Location(i, j)).getHex();
                hexPane.getChildren().add(hex.getGroup());
                NonCombatUnit nonCombatUnit = tileGrid.getTile(i, j).getNonCombatUnit();
                CombatUnit combatUnit = tileGrid.getTile(i, j).getCombatUnit();
                if (nonCombatUnit != null) {
                    hex.setUnit(nonCombatUnit);
                }
                if (combatUnit != null) {
                    hex.setUnit(combatUnit);
                }
            }
        }
    }


    public void gotoCheatSelectPage() {
        MenuStack.getInstance().pushMenu(Project.Views.Menu.loadFromFXML("CheatSheetPage"));
    }


    public void backToMenu() {
        // todo : go back to menu
    }

    public void exit() {
        System.exit(0);
        // todo : check for thread
    }

}
