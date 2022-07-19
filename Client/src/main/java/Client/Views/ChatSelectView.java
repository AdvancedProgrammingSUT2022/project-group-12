package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.Chat;
import Project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatSelectView implements ViewController {
    private final ArrayList<String> users = new ArrayList<>();
    @FXML
    private Button acceptButton;
    @FXML
    private Text chatGroup;
    @FXML
    private TextField groupName;
    @FXML
    private ChoiceBox<String> chatSelect;
    @FXML
    private VBox userBox1;
    private boolean chatSelected;
    private boolean userSelected;
    private final ArrayList<User> selectedUsers = new ArrayList<>();
    @FXML
    private ChoiceBox<String> userSelect;
    private ArrayList<String> usernames = new ArrayList<>();

    public void initialize() {
        usernames = DatabaseQuerier.getAllUsernames();
        usernames.remove(MenuStack.getInstance().getUser().getUsername());
        chatSelect.getItems().addAll(MenuStack.getInstance().getUser().previousChats());
        userSelect.getItems().removeAll(userSelect.getItems());
        userSelect.getItems().addAll(usernames);
        this.chatSelected = false;
        this.userSelected = false;
        chatSelect.setOnAction(this::getChat);
        userSelect.setOnAction(this::getUser);
    }

    private void getChat(ActionEvent event) {
        clearEverything();
        userSelected = true;
        chatSelected = false;
        userSelect.getItems().removeAll(userSelect.getItems());
        userSelect.getItems().addAll(usernames);
        groupName.setText(chatSelect.getValue());
        MenuStack.getInstance().getUser().setCurrentChat(chatSelect.getValue());
        for (String username : MenuStack.getInstance().getUser().getChat().getUsernames()) {
            userBox1.getChildren().add(new Text(username));
        }
    }

    private void clearEverything() {
        groupName.setStyle("-fx-border-color: none;");
        chatGroup.setText("");
        userBox1.getChildren().removeAll(userBox1.getChildren());
    }

    private void getUser(ActionEvent event) {
        if (userSelected) {
            clearEverything();
            chatSelected = true;
            userSelected = false;
        }
        if (users.contains(userSelect.getValue()))
            return;
        users.add(userSelect.getValue());
        selectedUsers.add(DatabaseQuerier.getUser(userSelect.getValue()));
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
        if (groupName.getText().length() == 0) {
            groupName.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            acceptButton.setDisable(true);
            return;
        }
        User user = MenuStack.getInstance().getUser();
        if (user.previousChats().contains(groupName.getText())) {
            user.setCurrentChat(groupName.getText());
        } else {
            selectedUsers.add(user);
            Chat newChat = new Chat(selectedUsers, groupName.getText());
            for (User eachUser : selectedUsers) {
                eachUser.startChat(newChat);
            }
            user.setCurrentChat(groupName.getText());
        }
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("MainChat"));
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }

    public void groupNameEntered() {
        for (User user : selectedUsers) {
            if (user.previousChats().contains(groupName.getText())) {
                groupName.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
                acceptButton.setDisable(true);
                return;
            }
        }
        groupName.setStyle("-fx-border-color: none;");
        acceptButton.setDisable(false);
        if (MenuStack.getInstance().getUser().previousChats().contains(groupName.getText())) {
            groupName.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            acceptButton.setDisable(true);
        }
    }
}