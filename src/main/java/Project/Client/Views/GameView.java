package Project.Client.Views;

import Project.Client.App;
import Project.Client.Utils.DatabaseQuerier;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Tiles.Hex;
import Project.Models.Tiles.HexGrid;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;

public class GameView implements ViewController {
    @FXML
    private ImageView happinessImg;
    @FXML
    private ImageView goldImg;
    @FXML
    private ImageView beakerImg;
    @FXML
    private MenuItem nextTurn;
    @FXML
    private Text happiness;
    @FXML
    private Text gold;
    @FXML
    private Text beaker;
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
    private HexGrid hexGrid;

    public void initialize() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        gold.setText(String.valueOf(civilization.getGold()));

        goldImg.setImage(new Image(App.class.getResource("/images/emojis/gold.png").toExternalForm()));

        happiness.setText(String.valueOf(civilization.getHappiness()));

        happinessImg.setImage(new Image(App.class.getResource("/images/emojis/happiness.png").toExternalForm()));

        beaker.setText(String.valueOf(civilization.getBeaker()));
        beaker.setFill(Color.PINK);
        beakerImg.setImage(new Image(App.class.getResource("/images/emojis/beaker.png").toExternalForm()));

        String command = "map show"; // dummy command to initialize logic GameMenu
        CommandResponse response = RequestHandler.getInstance().handle(command);
        HashMap<String, Integer> tileGridSize = DatabaseQuerier.getTileGridSize();
        hexGrid = new HexGrid(tileGridSize.get("Height"), tileGridSize.get("Width"));
    }

    public void showGameMap() {
        btn.setVisible(false);
        // TileGrid tileGrid = DatabaseQuerier.getTileGrid();
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
        Hex hex = hexGrid.getHex(location);
        scrollPane.setVvalue(hex.getCenterY() / hexPane.getBoundsInLocal().getHeight());
        scrollPane.setHvalue(hex.getCenterX() / hexPane.getBoundsInLocal().getWidth());
    }

    public void fillHexPaneFromTilesOf(TileGrid tileGrid) {
        hexPane.getChildren().clear();
        for (int i = 0; i < tileGrid.getHeight(); i++) {
            for (int j = 0; j < tileGrid.getWidth(); j++) {
                Tile tile = tileGrid.getTile(new Location(i, j));
                Hex hex = hexGrid.getHex(new Location(i, j));
                hexPane.getChildren().add(hex.getGroup());
                tile.addObserver(hex);
                hex.updateHex(tile);
            }
        }
    }


    public void gotoCheatSelectPage() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("CheatSheetPage"));
    }

    public void gotoTradePanel() {
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

    public void researchInfo() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("technologyMenu"));
    }

    public void gotoCivPanel() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("CivilizationPanelPage"));
    }


    public void NextTurn() {
        String command = "end turn";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else MenuStack.getInstance().showSuccess(response.getMessage());
        TileGrid tileGrid = DatabaseQuerier.getTileGrid();
        fillHexPaneFromTilesOf(tileGrid);
        setCameraOnCivSelectedLocation();
    }

    public void gotoNotificationPanel() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("NotificationPanel"));
    }

    public void gotoSetting() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("GameSettingPage"));
    }
}
