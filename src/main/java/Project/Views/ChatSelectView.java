package Project.Views;

import Project.Models.Chat;
import Project.Models.Database;
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
    @FXML
    private VBox userBox2;
    @FXML
    private VBox userBox3;
    @FXML
    private VBox userBox4;
    @FXML
    private VBox userBox5;

    private boolean chatSelected;
    private boolean userSelected;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    @FXML
    private ChoiceBox<String> userSelect;
    private ArrayList<String> users = new ArrayList<>();

    public void initialize() {
        ArrayList<String> users = Database.getInstance().getAllUsers();
        users.remove(MenuStack.getInstance().getUser().getUsername());
        chatSelect.getItems().addAll(MenuStack.getInstance().getUser().previousChats());
        userSelect.getItems().removeAll(userSelect.getItems());
        userSelect.getItems().addAll(users);
        this.chatSelected = false;
        this.userSelected = false;
        chatSelect.setOnAction(this::getChat);
        userSelect.setOnAction(this::getUser);
    }

    private void getChat(ActionEvent event) {
        clearEverything();
        userSelected = true;
        chatSelected = false;
        groupName.setText(chatSelect.getValue());
        MenuStack.getInstance().getUser().setCurrentChat(chatSelect.getValue());
        for (String username : MenuStack.getInstance().getUser().getChat().getUsernames()) {
            if (userBox1.getChildren().size() < 10)
                userBox1.getChildren().add(new Text(username));
            else if (userBox2.getChildren().size() < 10)
                userBox2.getChildren().add(new Text(username));
            else if (userBox3.getChildren().size() < 10)
                userBox3.getChildren().add(new Text(username));
            else if (userBox4.getChildren().size() < 10)
                userBox4.getChildren().add(new Text(username));
            else if (userBox5.getChildren().size() < 10)
                userBox5.getChildren().add(new Text(username));
        }
    }

    private void clearEverything() {
        groupName.setStyle("-fx-border-color: none;");
        chatGroup.setText("");
        userBox1.getChildren().removeAll(userBox1.getChildren());
        userBox2.getChildren().removeAll(userBox2.getChildren());
        userBox3.getChildren().removeAll(userBox3.getChildren());
        userBox4.getChildren().removeAll(userBox4.getChildren());
        userBox5.getChildren().removeAll(userBox5.getChildren());
    }

    private void getUser(ActionEvent event) {
        if (!chatSelected) {
            clearEverything();
            chatSelected = true;
            userSelected = false;
        }
        if (users.contains(userSelect.getValue()))
            return;
        users.add(userSelect.getValue());
        selectedUsers.add(Database.getInstance().getUser(userSelect.getValue()));
        if (userBox1.getChildren().size() < 10)
            userBox1.getChildren().add(new Text(userSelect.getValue()));
        else if (userBox2.getChildren().size() < 10)
            userBox2.getChildren().add(new Text(userSelect.getValue()));
        else if (userBox3.getChildren().size() < 10)
            userBox3.getChildren().add(new Text(userSelect.getValue()));
        else if (userBox4.getChildren().size() < 10)
            userBox4.getChildren().add(new Text(userSelect.getValue()));
        else if (userBox5.getChildren().size() < 10)
            userBox5.getChildren().add(new Text(userSelect.getValue()));
    }


    public void exitClick() {
        System.exit(0);
    }

    public void acceptClick() {
        if (groupName.getText().length() == 0){
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
