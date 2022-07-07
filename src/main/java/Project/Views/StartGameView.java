package Project.Views;

import Project.Controllers.MainMenuController;
import Project.Models.Database;
import Project.Models.User;
import Project.Utils.CommandException;
import Project.Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class StartGameView implements ViewController {
    private final ArrayList<String> users = new ArrayList<>();
    @FXML
    private Spinner<Integer> gridSizeHeight;
    @FXML
    private Spinner<Integer> gridSizeWidth;
    @FXML
    private Button acceptButton;
    private int currentWidthSize;
    private int currentHeightSize;
    private ArrayList<String> selectedUsernames;
    @FXML
    private VBox userBox1;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    @FXML
    private ChoiceBox<String> userSelect;
    private ArrayList<String> usernames = new ArrayList<>();
    private SpinnerValueFactory<Integer> widthValueFactory;
    private SpinnerValueFactory<Integer> heightValueFactory;

    public void initialize() {
        initializeSpinners();
        selectedUsernames = new ArrayList<>();
        selectedUsernames.add(MenuStack.getInstance().getUser().getUsername());
        usernames = Database.getInstance().getAllUsers();
        usernames.remove(MenuStack.getInstance().getUser().getUsername());
        userSelect.getItems().removeAll(userSelect.getItems());
        userSelect.getItems().addAll(usernames);
        userSelect.setOnAction(this::getUser);
    }

    private void initializeSpinners() {
        currentWidthSize = 10;
        currentHeightSize = 10;
        widthValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        widthValueFactory.setValue(currentWidthSize);
        heightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 999);
        heightValueFactory.setValue(currentHeightSize);
        gridSizeWidth.setValueFactory(widthValueFactory);
        gridSizeWidth.valueProperty().addListener((observableValue, integer, t1) -> {
            currentWidthSize = gridSizeWidth.getValue();
        });
        gridSizeHeight.setValueFactory(heightValueFactory);
        gridSizeHeight.valueProperty().addListener((observableValue, integer, t1) -> {
            currentHeightSize = gridSizeHeight.getValue();
        });

    }

    private void getUser(ActionEvent event) {
        if (users.contains(userSelect.getValue()))
            return;
        users.add(userSelect.getValue());
        selectedUsers.add(Database.getInstance().getUser(userSelect.getValue()));
        selectedUsernames.add(userSelect.getValue());
        userBox1.getChildren().add(returnText(userSelect.getValue()));
        userSelect.getItems().remove(userSelect.getValue());
        currentWidthSize += 10;
        currentHeightSize += 10;
        widthValueFactory.setValue(currentWidthSize);
        heightValueFactory.setValue(currentHeightSize);
        gridSizeWidth.setValueFactory(widthValueFactory);
        gridSizeHeight.setValueFactory(heightValueFactory);
    }

    private Text returnText(String value) {
        Text newText = new Text(value);
        newText.setOnMouseClicked(event -> {
            userBox1.getChildren().remove(newText);
            userSelect.getItems().add(value);
            users.remove(value);
            currentWidthSize -= 10;
            currentHeightSize -= 10;
            widthValueFactory.setValue(currentWidthSize);
            heightValueFactory.setValue(currentHeightSize);
            gridSizeWidth.setValueFactory(widthValueFactory);
            gridSizeHeight.setValueFactory(heightValueFactory);
        });
        return newText;
    }


    public void exitClick() {
        System.exit(0);
    }

    public void acceptClick() {
        try {
            Constants.TILEGRID_WIDTH = currentWidthSize;
            Constants.TILEGRID_HEIGHT = currentHeightSize;
            MainMenuController.startNewGame(selectedUsernames);
        } catch (CommandException e) {
            System.err.println("error in start game / accept click");
            System.err.println(e.getMessage());
            return;
        }
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }
}
