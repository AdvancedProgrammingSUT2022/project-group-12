package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private ChoiceBox<String> userSelect;
    private SpinnerValueFactory<Integer> widthValueFactory;
    private SpinnerValueFactory<Integer> heightValueFactory;

    public void initialize() {
        initializeSpinners();
        selectedUsernames = new ArrayList<>();
        selectedUsernames.add(MenuStack.getInstance().getUser().getUsername());
        ArrayList<String> usernames = DatabaseQuerier.getAllUsernames();
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
        StringBuilder command = new StringBuilder("play game -w " + currentWidthSize + " -h " + currentHeightSize + " ");
        for (int i = 0; i < selectedUsernames.size(); ++i) {
            command.append(" --player").append(i + 1).append(" ").append(selectedUsernames.get(i));
        }
        System.out.println("command = " + command);
        CommandResponse response = RequestSender.getInstance().send(command.toString());
//        MainMenuController.startNewGame(selectedUsernames);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }
}
