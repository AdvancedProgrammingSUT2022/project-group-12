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
    private
    VBox usersBox;
    @FXML
    private MenuButton userSelect;
    @FXML
    private TextField typeUser;
    @FXML
    private MenuButton friendRequests;
    @FXML
    private HBox friendAccept;
    private ArrayList<User> users;
    private User selectedUser;
    private MenuItem selectedItem;

    public void initialize() {
        selectedUser = MenuStack.getInstance().getUser();
        users = DatabaseQuerier.getAllUsers();
        typeUser.setOnAction(this::startTyping);
        initUserSelect();
        initFriendRequests();
        initUserBox();
    }

    private void startTyping(ActionEvent event) {
        if (!users.contains(typeUser.getText())) {
            typeUser.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            return;
        }
        typeUser.setStyle(null);
        User user = DatabaseQuerier.getUser(typeUser.getText());
        user.sendFriendRequest(selectedUser.getUsername());
        DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
        selectedUser.addToWaitingOnFriendRequest(user.getUsername());
        user.sendFriendRequest(selectedUser.getUsername());
    }

    private void initUserBox() {
        for (String username : selectedUser.getFriends()) {
            Text name = new Text(username);
            usersBox.getChildren().add(name);
        }
    }

    private void initFriendRequests() {
        for (String username : selectedUser.getFriendRequest()) {
            MenuItem item = new MenuItem(username);
            friendRequests.setText(username);
            item.setOnAction(actionEvent -> {
                friendAccept.getChildren().removeAll(friendAccept.getChildren());
                Text text = new Text(username);
                selectedItem = item;
                friendAccept.getChildren().add(text);
                friendAccept.getChildren().add(acceptBtn(username));
                friendAccept.getChildren().add(denialBtn(username));
                friendAccept.setSpacing(10);
            });
            friendRequests.getItems().add(item);
        }
    }

    private Button acceptBtn(String username) {
        User checkedUser = DatabaseQuerier.getUser(username);
        Button button = new Button("Accept");
        button.setBackground(Background.fill(Color.GREEN));
        button.setOnMouseClicked(actionEvent -> {
            button.setCursor(Cursor.HAND);
            selectedUser.acceptFriend(username);
            usersBox.getChildren().add(new Text(username));
            checkedUser.acceptFriend(selectedUser.getUsername());
            friendRequests.getItems().remove(selectedItem);
            friendAccept.getChildren().removeAll(friendAccept.getChildren());
        });
        return button;
    }

    private Button denialBtn(String username) {
        User checkedUser = DatabaseQuerier.getUser(username);
        Button button = new Button("Denial");
        button.setBackground(Background.fill(Color.RED));
        button.setOnMouseClicked(actionEvent -> {
            button.setCursor(Cursor.HAND);
            DatabaseQuerier.denyFriendRequest(selectedUser.getUsername(), checkedUser.getUsername());
            selectedUser.denyFriend(username);
            checkedUser.denyFriend(selectedUser.getUsername());
            friendRequests.getItems().remove(selectedItem);
            friendAccept.getChildren().removeAll(friendAccept.getChildren());
        });
        return button;
    }

    private void initUserSelect() {
        for (User user : users) {
            MenuItem item = new MenuItem(user.getUsername());
            item.setOnAction(actionEvent -> {
                userSelect.setText(user.getUsername());
                user.sendFriendRequest(selectedUser.getUsername());
                DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
                selectedUser.addToWaitingOnFriendRequest(user.getUsername());
                user.sendFriendRequest(selectedUser.getUsername());
            });
            userSelect.getItems().add(item);
        }
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
