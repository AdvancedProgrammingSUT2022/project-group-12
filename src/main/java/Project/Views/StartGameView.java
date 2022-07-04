package Project.Views;

import Project.Controllers.MainMenuController;
import Project.Models.Database;
import Project.Models.Game;
import Project.Models.User;
import Project.Utils.CommandException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class StartGameView implements ViewController {
    private final ArrayList<String> users = new ArrayList<>();
    @FXML
    private Button acceptButton;
    private ArrayList<String> selectedUsernames;
    @FXML
    private VBox userBox1;
    private boolean userSelected;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    @FXML
    private ChoiceBox<String> userSelect;
    private ArrayList<String> usernames = new ArrayList<>();

    public void initialize() {
        selectedUsernames = new ArrayList<>();
        usernames = Database.getInstance().getAllUsers();
        usernames.remove(MenuStack.getInstance().getUser().getUsername());
        userSelect.getItems().removeAll(userSelect.getItems());
        userSelect.getItems().addAll(usernames);
        this.userSelected = false;
        userSelect.setOnAction(this::getUser);
    }

    private void getUser(ActionEvent event) {
        if (users.contains(userSelect.getValue()))
            return;
        users.add(userSelect.getValue());
        selectedUsers.add(Database.getInstance().getUser(userSelect.getValue()));
        selectedUsernames.add(userSelect.getValue());
        userBox1.getChildren().add(returnText(userSelect.getValue()));
        userSelect.getItems().remove(userSelect.getValue());
    }

    private Text returnText(String value) {
        Text newText = new Text(value);
        newText.setOnMouseClicked(event -> {
            userBox1.getChildren().remove(newText);
            userSelect.getItems().add(value);
            users.remove(value);
        });
        return newText;
    }


    public void exitClick() {
        System.exit(0);
    }

    public void acceptClick() {
        try {
            MainMenuController.startNewGame(selectedUsernames);
        } catch (CommandException e) {
            System.err.println("error in start game/ accept click");
            System.err.println(e.getMessage());
            return;
        }
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }
}
