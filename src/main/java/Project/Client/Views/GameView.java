package Project.Client.Views;

import Project.Models.Location;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class GameView implements ViewController {
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

    public void initialize() {
        String command = "map show"; // dummy command to initialize logic GameMenu
        CommandResponse response = RequestHandler.getInstance().handle(command);
    }

    public void showGameMap() {
        btn.setVisible(false);
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        fillHexPaneFromTilesOf(tileGrid);
        setCameraOnCivSelectedLocation();
    }

    private void setCameraOnCivSelectedLocation() {
        String command = "api get camera";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        int cameraRow = Integer.parseInt(RequestHandler.getInstance().getParameter("camera_row"));
        int cameraCol = Integer.parseInt(RequestHandler.getInstance().getParameter("camera_col"));
//        System.out.println("Resp: " + cameraRow + ' ' + cameraCol);
        this.setFocusOnLocation(new Location(cameraRow, cameraCol));
    }

    public void setFocusOnLocation(Location location) {
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        Hex hex = tileGrid.getTile(location).getHex();
        scrollPane.setVvalue(hex.getCenterY() / hexPane.getBoundsInLocal().getHeight());
        scrollPane.setHvalue(hex.getCenterX() / hexPane.getBoundsInLocal().getWidth());
    }

    public void fillHexPaneFromTilesOf(TileGrid tileGrid) {
        hexPane.getChildren().clear();
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
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("CheatSheetPage"));
    }
    public void gotoTradePanel(){
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("TradeMenu"));
    }


    public void backToMenu() {
        String command = "end game";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
        // todo : check for thread
    }

    public void researchInfo(ActionEvent actionEvent) {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("technologyMenu"));
    }

    public void gotoCivPanel() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("CivilizationPanelPage"));
    }


    public void NextTurn(ActionEvent actionEvent) {
        String command = "end turn";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        TileGrid tileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        fillHexPaneFromTilesOf(tileGrid);
        setCameraOnCivSelectedLocation();
    }

    public void gotoNotificationPanel(ActionEvent actionEvent) {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("NotificationPanel"));
    }
}
