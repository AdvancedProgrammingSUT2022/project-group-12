package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class StartGameView implements ViewController {
    private final ArrayList<String> users = new ArrayList<>();
    private final String resumeGamesName = null;
    private @FXML MenuButton openGamesList;
    private @FXML MenuButton myGamesList;
    private @FXML MenuButton runningGamesList;
    private @FXML Spinner<Integer> playerCount;
    private @FXML Spinner<Integer> gridSizeHeight;
    private @FXML Spinner<Integer> gridSizeWidth;
    private @FXML TextField gameNameField;
    private @FXML Button createButton;
    private @FXML Button joinButton;
    private @FXML Button resumeButton;
    private @FXML Button showButton;
    private int currentWidthSize;
    private int currentHeightSize;
    private int currentPlayerCount;
    private SpinnerValueFactory<Integer> widthValueFactory;
    private SpinnerValueFactory<Integer> heightValueFactory;
    private SpinnerValueFactory<Integer> countValueFactory;
    private String invitedGamesName = null;
    private String liveGamesName = null;

    public void initialize() {
        initializeSpinners();
        loadInvitedGameItems();
        loadLiveGamesItems();
    }

    public void loadInvitedGameItems() {
        openGamesList.getItems().clear();
        for (String gameName : DatabaseQuerier.getOpenGamesNames()) {
//            System.out.println(gameName);
            MenuItem menuItem = new MenuItem(gameName);
            menuItem.setOnAction(actionEvent -> {
                this.invitedGamesName = gameName;
                openGamesList.setText(gameName);
            });
            openGamesList.getItems().add(menuItem);
        }
    }

    public void loadLiveGamesItems() {
        runningGamesList.getItems().clear();
        for (String gameName : DatabaseQuerier.getOpenGamesNames()) {
//            System.out.println(gameName);
            MenuItem menuItem = new MenuItem(gameName);
            menuItem.setOnAction(actionEvent -> {
                this.liveGamesName = gameName;
                runningGamesList.setText(gameName);
            });
            runningGamesList.getItems().add(menuItem);
        }
    }

    private void initializeSpinners() {
        currentWidthSize = 10;
        currentHeightSize = 10;
        currentPlayerCount = 0;
        widthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        widthValueFactory.setValue(currentWidthSize);
        heightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        heightValueFactory.setValue(currentHeightSize);
        countValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.GAME_MAX_PLAYERS);
        countValueFactory.setValue(currentPlayerCount);
        gridSizeWidth.setValueFactory(widthValueFactory);
        gridSizeWidth.valueProperty().addListener((observableValue, integer, t1) -> {
            currentWidthSize = gridSizeWidth.getValue();
        });
        gridSizeHeight.setValueFactory(heightValueFactory);
        gridSizeHeight.valueProperty().addListener((observableValue, integer, t1) -> {
            currentHeightSize = gridSizeHeight.getValue();
        });
        playerCount.setValueFactory(countValueFactory);
    }

    public void reloadGames() {
        loadInvitedGameItems();
        loadLiveGamesItems();
    }

    public void exitClick() {
        System.exit(0);
    }

    public void createNewGame() {
//        StringBuilder command = new StringBuilder("start game -w " + currentWidthSize + " -h " + currentHeightSize + " ");
//        for (int i = 0; i < selectedUsernames.size(); ++i) {
//            command.append(" --player").append(i + 1).append(" ").append(selectedUsernames.get(i));
//        }
        StringBuilder command = new StringBuilder("start game -w " + currentWidthSize + " -h " + currentHeightSize + " ");
        RequestSender.getInstance().sendCommand(command.toString());
        RequestSender.getInstance().sendCommand("init");
        GameView.setShow(false);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }

    public void startInvitedGame() {
        if (this.invitedGamesName == null) return;
        StringBuilder command = new StringBuilder("enter game -t " + this.invitedGamesName);
        CommandResponse response = RequestSender.getInstance().sendCommand(command.toString());
        GameView.setShow(false);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }


    public void showLiveGame() {
        if (this.liveGamesName == null) return;
        StringBuilder command = new StringBuilder("show game -t " + this.liveGamesName);
        System.out.println("command = " + command);
        CommandResponse response = RequestSender.getInstance().sendCommand(command.toString());
        GameView.setShow(true);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }
}
