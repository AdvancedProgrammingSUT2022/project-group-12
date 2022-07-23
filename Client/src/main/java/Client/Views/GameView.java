package Client.Views;

import Client.App;
import Client.Models.Hex;
import Client.Models.HexGrid;
import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Project.Enums.TechnologyEnum;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Utils.CommandResponse;
import Project.Utils.Pair;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView implements ViewController {
    @FXML
    private Text turnNumbers;
    @FXML
    private Rectangle myRect;
    @FXML
    private Pane mainPane;
    @FXML
    private Pane coverPane;

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
    private ArrayList<TechnologyEnum> technologies;
    private static GameView instance;

    public void initialize() {
        turnNumbers.setText("Current Year : " + DatabaseQuerier.getCurrentYear());
        gold.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
        goldImg.setImage(new Image(App.class.getResource("/images/emojis/gold.png").toExternalForm()));
        happiness.setText(String.valueOf(DatabaseQuerier.getHappinessOfCurrentCiv()));
        happinessImg.setImage(new Image(App.class.getResource("/images/emojis/happiness.png").toExternalForm()));
        beaker.setText(String.valueOf(DatabaseQuerier.getScienceOfCurrentCiv()));
        beakerImg.setImage(new Image(App.class.getResource("/images/emojis/beaker.png").toExternalForm()));
        Pair<Integer, Integer> tileGridSize = DatabaseQuerier.getTileGridSize();
        this.tileGrid = new TileGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        this.hexGrid = new HexGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        instance = this;
        initializeGameMap();
        if (DatabaseQuerier.getIsPlayingAllowedFor(MenuStack.getInstance().getCookies().getLoginToken())) {
            changeCoverVisibility(false);
        }
    }

    public static void changeCoverVisibility(boolean visible) {
        if (instance != null) instance.coverPane.setVisible(visible);
    }

    public void initializeGameMap() {
        for (int i = 0; i < this.tileGrid.getHeight(); i++) {
            for (int j = 0; j < this.tileGrid.getWidth(); j++) {
                Hex hex = this.hexGrid.getHex(new Location(i, j));
                hexPane.getChildren().add(hex.getGroup());
                Tile tile = this.tileGrid.getTile(i, j);
                tile.initializeNotifier();
                tile.addObserver(hex);
            }
        }
        reloadTechnologies();
        reloadHexGrid();

        Location cameraLocation = DatabaseQuerier.getCivInitialLocation(MenuStack.getInstance().getCookies().getLoginToken());
        this.setFocusOnLocation(cameraLocation);
    }

    public void setFocusOnLocation(Location location) {
        Hex hex = hexGrid.getHex(location);
        scrollPane.setVvalue(hex.getCenterY() / hexPane.getBoundsInLocal().getHeight());
        scrollPane.setHvalue(hex.getCenterX() / hexPane.getBoundsInLocal().getWidth());
    }

    public void reloadTileGridFromServer() {
        TileGrid newTileGrid = DatabaseQuerier.getTileGridByToken(MenuStack.getInstance().getCookies().getLoginToken());
        for (int i = 0; i < newTileGrid.getHeight(); i++) {
            for (int j = 0; j < newTileGrid.getWidth(); j++) {
                this.tileGrid.getTile(i, j).copyPropertiesFrom(newTileGrid.getTile(i, j));
            }
        }
    }


    public void gotoCheatSheetPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CheatSheetPage"));
    }

    public void gotoTradePanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("DemandAndTradePanel"));
    }


    public void backToMenu() {
        String command = "end game";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
        // todo : check for thread
    }

    public void gotoReseachPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("technologyMenu"));
    }

    public void gotoCivPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CivilizationPanelPage"));
    }

    public static void reloadHexGrid() {
        instance.reloadTileGridFromServer();
    }

    public static void reloadTechnologies() {
        instance.technologies = DatabaseQuerier.getCurrentTechnologies();
    }
    public void reloadDate(String currentDate){
        turnNumbers.setText("Current Year : " + DatabaseQuerier.getCurrentYear());
    }

    public void NextTurn() {
        String command = "end turn";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            if(response == CommandResponse.END_OF_GAME){
                System.out.println("hello");
                HashMap<String,Integer> civsScore = new HashMap<>(DatabaseQuerier.getCivsByName());
                EndGameDialog dialog = new EndGameDialog(civsScore);
                dialog.showAndWait();
                backToMenu();
                return;
            }
            MenuStack.getInstance().showError(response.toString());
        } else {
            changeCoverVisibility(true);
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
    }

    public static void updateTile(Location location, Tile newTile) {
        if (instance != null) instance.tileGrid.getTile(location).copyPropertiesFrom(newTile);
    }

    public void gotoNotifications() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("NotificationPanel"));
    }

    public void gotoSetting() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GameSettingPage"));
    }

    public static ArrayList<TechnologyEnum> getTechnologies() {
        return instance.technologies;
    }

    public void endGame(HashMap<String,Integer> civsScore){
        System.out.println("end game");
        EndGameDialog endGameDialog = new EndGameDialog(civsScore);
        endGameDialog.showAndWait();
        backToMenu();
    }

}
