package Project.Views;

import Project.Controllers.GameController;
import Project.Models.Game;
import Project.Models.Location;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.ServerViews.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class GameView implements ViewController {
    private static GameView instance;
    public MenuItem nextTurn;
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
        String command = "map show";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        GameController.getGame().setPage(this);
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
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        for (int i = 0; i < tileGrid.getHeight(); i++) {
            for (int j = 0; j < tileGrid.getWidth(); j++) {
                Tile tile = tileGrid.getTile(new Location(i, j));
                Hex hex = tile.getHex();
                hexPane.getChildren().add(hex.getGroup());
                tile.addObserver(tile.getHex());
                hex.updateHex(tile);
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

    public void researchInfo(ActionEvent actionEvent) {
        MenuStack.getInstance().pushMenu(Project.Views.Menu.loadFromFXML("technologyMenu"));
    }

    public void gotoCivPanel() {
        MenuStack.getInstance().pushMenu(Project.Views.Menu.loadFromFXML("CivilizationPanelPage"));
    }


    public void NextTurn(ActionEvent actionEvent) {
        String command = "end turn";
        CommandResponse response = RequestHandler.getInstance().handle(command);
    }
}
