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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameView implements ViewController {
    private static GameView instance;
    public MenuItem goldCount;
    public MenuItem happinessCount;
    public MenuItem beakerCount;
    @FXML
    private Rectangle myRect;
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

    public static void changeCoverVisibility(boolean visible) {
        if (instance != null) instance.coverPane.setVisible(visible);
    }

    public static void reloadHexGrid() {
        instance.reloadTileGridFromServer();
    }

    public static void reloadTechnologies() {
        instance.technologies = DatabaseQuerier.getCurrentTechnologies();
    }

    public static void updateTile(Location location, Tile newTile) {
        if (instance != null) instance.tileGrid.getTile(location).copyPropertiesFrom(newTile);
    }

    public static ArrayList<TechnologyEnum> getTechnologies() {
        return instance.technologies;
    }

    public void initialize() {
        Pair<Integer, Integer> tileGridSize = DatabaseQuerier.getTileGridSize();
        this.tileGrid = new TileGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        this.hexGrid = new HexGrid(tileGridSize.getFirst(), tileGridSize.getSecond());
        instance = this;
        initializeGameMap();
        if (DatabaseQuerier.getIsPlayingAllowedFor(MenuStack.getInstance().getCookies().getLoginToken())) {
            changeCoverVisibility(false);
        }
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
        scrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCode().getName());
                if (keyEvent.getCode().getName().equals("C"))
                    gotoCheatSheetPanel();
            }
        });
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
    }

    public void gotoReseachPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("technologyMenu"));
    }

    public void gotoCivPanel() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CivilizationPanelPage"));
    }

    public void NextTurn() {
        String command = "end turn";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (!response.isOK()) {
            MenuStack.getInstance().showError(response.toString());
        } else {
            changeCoverVisibility(true);
            MenuStack.getInstance().showSuccess(response.getMessage());
        }
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

    public void calculateGold() {
        goldCount.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
    }
}
