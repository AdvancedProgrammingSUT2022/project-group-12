package Client.Views;

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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class GameView implements ViewController {
    private static GameView instance;
    public MenuItem goldCount;
    public MenuItem happinessCount;
    public MenuItem beakerCount;
    @FXML
    public Pane hexPaneCover;
    @FXML
    private Text turnNumbers;
    @FXML
    private Pane mainPane;
    @FXML
    private Pane coverPane;
  
    @FXML
    private MenuItem nextTurn;
    @FXML
    private Pane selectionPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pane hexPane;
    private TileGrid tileGrid;
    private HexGrid hexGrid;
    private ArrayList<TechnologyEnum> technologies;
    private static boolean isShow = false;

    public static void setShow(boolean isShow) {
        GameView.isShow = isShow;
    }

    public static ArrayList<TechnologyEnum> getTechnologies() {
        return instance.technologies;
    }

    public void initialize() {
        turnNumbers.setText("Current Year : " + DatabaseQuerier.getCurrentYear());
        Pair<Integer, Integer> tileGridSize = DatabaseQuerier.getTileGridSize();
        this.tileGrid = new TileGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        this.hexGrid = new HexGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        instance = this;
        initializeGameMap();
        if (isShow) {
            instance.hexPaneCover.setVisible(true);
            instance.menuBar.setVisible(false);
            changeCoverVisibility(false);
        } else {
            if (DatabaseQuerier.getIsPlayingAllowedFor(MenuStack.getInstance().getCookies().getLoginToken())) {
                changeCoverVisibility(false);
            }
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

        if (!isShow) {
            Location cameraLocation = DatabaseQuerier.getCivInitialLocation(MenuStack.getInstance().getCookies().getLoginToken());
            this.setFocusOnLocation(cameraLocation);
        }
        scrollPane.setOnKeyPressed(keyEvent -> {
            System.out.println(keyEvent.getCode().getName());
            if (keyEvent.getCode().getName().equals("C"))
                gotoCheatSheetPanel();
        });
    }

    public void setFocusOnLocation(Location location) {
        Hex hex = hexGrid.getHex(location);
        scrollPane.setVvalue(hex.getCenterY() / hexPane.getBoundsInLocal().getHeight());
        scrollPane.setHvalue(hex.getCenterX() / hexPane.getBoundsInLocal().getWidth());
    }

    public void copyTileGridFrom(TileGrid newTileGrid) {
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
        TileGrid newTileGrid = DatabaseQuerier.getTileGridByToken(MenuStack.getInstance().getCookies().getLoginToken());
        instance.copyTileGridFrom(newTileGrid);
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
        if(response == null) {
            System.out.println("response is null be care full");
            return;
        }
        if (!response.isOK()) {
            if(response == CommandResponse.END_OF_GAME) return;
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

    public void gotoUnitsPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("UnitsPanelPage"));
    }

    public void gotoCitiesPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CitiesPanelPage"));
    }

    public void calculateHappiness() {
        happinessCount.setText(String.valueOf(DatabaseQuerier.getHappinessOfCurrentCiv()));
    }

    public void calculateBeaker() {
        beakerCount.setText(String.valueOf(DatabaseQuerier.getScienceOfCurrentCiv()));

    }

    public void endGame(HashMap<String,Integer> civsScore){
        System.out.println("end game");
        EndGameDialog endGameDialog = new EndGameDialog(civsScore);
        endGameDialog.showAndWait();
        backToMenu();
    }

    public void calculateGold() {
        goldCount.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
    }
}
