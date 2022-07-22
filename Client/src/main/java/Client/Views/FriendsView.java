package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class FriendsView implements ViewController {
    @FXML
    private HBox friendAcceptBox;
    @FXML
    private VBox friendsBox;
    @FXML
    private MenuButton addToFriendsMenu;
    @FXML
    private TextField typeFriendNameField;
    @FXML
    private MenuButton receivedFriendRequests;
    @FXML
    private VBox requestsBox;
    private ArrayList<User> users;
    private User selectedUser;
    private MenuItem selectedItem;

    public void initialize() {
        selectedUser = MenuStack.getInstance().getUser();
        users = DatabaseQuerier.getAllUsers();
        typeFriendNameField.setOnAction(this::startTyping);
        initUserSelect();
        initFriendRequests();
        initUserBox();
        initRequestBox();
    }

    private void initRequestBox() {
        requestsBox.getChildren().removeAll(requestsBox.getChildren());
        for (String name : selectedUser.getWaitingOnFriendRequest()){
            Text username = new Text(name);
            requestsBox.getChildren().add(username);
        }
    }

    private void startTyping(ActionEvent event) {
        if (!users.contains(typeFriendNameField.getText())) {
            typeFriendNameField.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            return;
        }
        typeFriendNameField.setStyle(null);
        User user = DatabaseQuerier.getUser(typeFriendNameField.getText());
        DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
        user.sendFriendRequest(selectedUser.getUsername());
        initRequestBox();
    }

    private void initUserBox() {
        for (String username : selectedUser.getFriends()) {
            Text friend = new Text(username);
            friendsBox.getChildren().add(friend);
        }
    }

    private void initFriendRequests() {
        for (String request : selectedUser.getFriendRequest()) {
            MenuItem item = new MenuItem(request);
            item.setOnAction(event -> {
                selectedItem = item;
                friendsBox.getChildren().add(new Text(request));
                friendsBox.getChildren().add(acceptBtn(request));
                friendsBox.getChildren().add(denialBtn(request));
                friendsBox.setSpacing(10);
            });
            receivedFriendRequests.getItems().add(item);
        }
    }

    private Button acceptBtn(String username) {
        Button button = new Button("Accept");
        button.setBackground(Background.fill(Color.GREEN));
        button.setOnMouseEntered(actionEvent -> button.setCursor(Cursor.HAND));
        button.setOnMouseClicked(actionEvent -> {
            DatabaseQuerier.acceptFriendRequest(selectedUser.getUsername(), username);
            friendsBox.getChildren().add(new Text(username));
            receivedFriendRequests.getItems().remove(selectedItem);
            friendAcceptBox.getChildren().removeAll(friendAcceptBox.getChildren());
        });
        return button;
    }

    private Button denialBtn(String username) {
        Button button = new Button("Denial");
        button.setBackground(Background.fill(Color.RED));
        button.setOnMouseEntered(actionEvent -> button.setCursor(Cursor.HAND));
        button.setOnMouseClicked(actionEvent -> {
            DatabaseQuerier.denyFriendRequest(selectedUser.getUsername(), username);
            friendsBox.getChildren().add(new Text(username));
            receivedFriendRequests.getItems().remove(selectedItem);
            friendAcceptBox.getChildren().removeAll(friendAcceptBox.getChildren());
        });
        return button;
    }

    private void initUserSelect() {
        for (User user : users) {
            MenuItem item = new MenuItem(user.getUsername());
            item.setOnAction(actionEvent -> {
                addToFriendsMenu.setText(user.getUsername());
                DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
                initRequestBox();
            });
            addToFriendsMenu.getItems().add(item);
        }
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
