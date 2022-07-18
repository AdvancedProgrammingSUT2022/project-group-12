package Project.Client.Views;

import Project.Client.App;
import Project.Client.Utils.DatabaseQuerier;
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
import javafx.scene.text.Text;

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
    private TileGrid tileGrid;
    private HexGrid hexGrid;
    public static GameView instance; // temporary

    public void initialize() {
        String command = "map show"; // dummy command to initialize logic GameMenu
        CommandResponse response = RequestHandler.getInstance().handle(command);
        gold.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
        goldImg.setImage(new Image(App.class.getResource("/images/emojis/gold.png").toExternalForm()));
        happiness.setText(String.valueOf(DatabaseQuerier.getHappinessOfCurrentCiv()));
        happinessImg.setImage(new Image(App.class.getResource("/images/emojis/happiness.png").toExternalForm()));
        beaker.setText(String.valueOf(DatabaseQuerier.getScienceOfCurrentCiv()));
        beakerImg.setImage(new Image(App.class.getResource("/images/emojis/beaker.png").toExternalForm()));
        this.tileGrid = DatabaseQuerier.getTileGrid();
        this.hexGrid = new HexGrid(tileGrid.getHeight(), tileGrid.getWidth());
        instance = this;
    }

    public void initializeGameMap() {
        btn.setVisible(false);
        for (int i = 0; i < this.tileGrid.getHeight(); i++) {
            for (int j = 0; j < this.tileGrid.getWidth(); j++) {
                Hex hex = this.hexGrid.getHex(new Location(i, j));
                hexPane.getChildren().add(hex.getGroup());
                Tile tile = this.tileGrid.getTile(i, j);
                tile.initializeNotifier();
                tile.addObserver(hex);
            }
        }
        this.reloadTileGridFromServer();
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

    public void reloadTileGridFromServer() {
        TileGrid newTileGrid = GameController.getGame().getCurrentCivilization().getRevealedTileGrid();
        for (int i = 0; i < newTileGrid.getHeight(); i++) {
            for (int j = 0; j < newTileGrid.getWidth(); j++) {
                this.tileGrid.getTile(i, j).copyPropertiesFrom(newTileGrid.getTile(i, j));
            }
        }
    }


    public void gotoCheatSelectPage() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("CheatSheetPage"));
    }

    public void gotoTradePanel() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("DemandAndTradePanel"));
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

    public static void reloadHexGrid() {
        instance.reloadTileGridFromServer();
    }

    public void NextTurn() {

        String command = "end turn";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
            return;
        } else MenuStack.getInstance().showSuccess(response.getMessage());
        this.reloadTileGridFromServer();
        setCameraOnCivSelectedLocation();
    }

    public void gotoNotificationPanel() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("NotificationPanel"));
    }

    public void gotoSetting() {
        MenuStack.getInstance().pushMenu(Project.Client.Views.Menu.loadFromFXML("GameSettingPage"));
    }
}
