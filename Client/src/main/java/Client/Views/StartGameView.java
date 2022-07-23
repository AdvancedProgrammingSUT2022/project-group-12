package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Project.Models.OpenGame;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class StartGameView implements ViewController {
    private final ArrayList<String> users = new ArrayList<>();
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
    private String openGamesNames = null;
    private String resumeGamesName = null;
    private String liveGamesName = null;

    public void initialize() {
        initializeSpinners();
        reloadGamesLists();
        joinButton.setDisable(true);
        resumeButton.setDisable(true);
        showButton.setDisable(true);
    }

    public void loadOpenGameItems() {
        openGamesList.getItems().clear();
        for (String gameName : DatabaseQuerier.getOpenGamesNames()) {
//            System.out.println(gameName);
            MenuItem menuItem = new MenuItem(gameName);
            menuItem.setOnAction(actionEvent -> {
                this.openGamesNames = gameName;
                this.joinButton.setDisable(false);
                openGamesList.setText(gameName);
            });
            openGamesList.getItems().add(menuItem);
        }
    }

    public void loadResumeGameItems() {
        runningGamesList.getItems().clear();
        for (String gameName : DatabaseQuerier.getRunningGamesNames()) {
//            System.out.println(gameName);
            MenuItem menuItem = new MenuItem(gameName);
            menuItem.setOnAction(actionEvent -> {
                this.openGamesNames = gameName;
                this.joinButton.setDisable(false);
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
                this.showButton.setDisable(false);
                runningGamesList.setText(gameName);
            });
            runningGamesList.getItems().add(menuItem);
        }
    }

    private void initializeSpinners() {
        currentWidthSize = 20;
        currentHeightSize = 20;
        currentPlayerCount = 2;
        widthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        widthValueFactory.setValue(currentWidthSize);
        heightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        heightValueFactory.setValue(currentHeightSize);
        countValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, Constants.GAME_MAX_PLAYERS);
        countValueFactory.setValue(currentPlayerCount);
        countValueFactory.valueProperty().addListener((observableValue, integer, t1) -> {
            currentHeightSize = observableValue.getValue() * 10;
            currentWidthSize = observableValue.getValue() * 10;
            widthValueFactory.setValue(currentWidthSize);
            heightValueFactory.setValue(currentHeightSize);
        });
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

    public void reloadGamesLists() {
        loadOpenGameItems();
        loadResumeGameItems();
        loadLiveGamesItems();
    }

    public void exitClick() {
        System.exit(0);
    }

    public void createNewGame() {
        String name = gameNameField.getText();
        int height = heightValueFactory.getValue();
        int width = widthValueFactory.getValue();
        int playerLimit = countValueFactory.getValue();
        String token = DatabaseQuerier.createGame(MenuStack.getInstance().getCookies().getLoginToken(), name, height, width, playerLimit);
        this.gotoRoom(token);
    }

    private void gotoRoom(String token) {
        MenuStack.getInstance().getCookies().setOpenRoom(DatabaseQuerier.getOpenGameByToken(token));
        GameView.setShow(false);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GameRoomPage"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }

    public void joinGame() {
        if (this.openGamesNames == null) return;
        OpenGame selectedOpenGame = DatabaseQuerier.getOpenGameByToken(this.openGamesNames);
        if (selectedOpenGame.getPlayers().size() + 1 > selectedOpenGame.getPlayerLimit()) return;
        DatabaseQuerier.joinToGame(MenuStack.getInstance().getCookies().getLoginToken(), openGamesNames);
        gotoRoom(openGamesNames);
    }

    public void resumeGame() {
        if (this.resumeGamesName == null) return;
        StringBuilder command = new StringBuilder("resume game -t " + this.openGamesNames);
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
