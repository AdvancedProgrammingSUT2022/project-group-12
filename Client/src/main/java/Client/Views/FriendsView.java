package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private ArrayList<String> allUsernames;
    private User selectedUser;
    private MenuItem selectedItem;

    public void initialize() {
        selectedUser = DatabaseQuerier.getUser(MenuStack.getInstance().getUser().getUsername());
        users = DatabaseQuerier.getAllUsers();
        allUsernames = DatabaseQuerier.getAllUsernames();
        initUserSelect();
        initFriendRequests();
        initUserBox();
        initRequestBox();
    }

    private void initRequestBox() {
        requestsBox.getChildren().removeAll(requestsBox.getChildren());
        selectedUser = DatabaseQuerier.getUser(selectedUser.getUsername());
        for (String name : selectedUser.getWaitingOnFriendRequest()) {
            requestsBox.getChildren().add(createFriendBox(DatabaseQuerier.getUser(name)));
        }
        requestsBox.setSpacing(10);
    }

    private void initUserBox() {
        for (String username : selectedUser.getFriends()) {
            friendsBox.getChildren().add(createFriendBox(DatabaseQuerier.getUser(username)));
        }
    }

    private void initFriendRequests() {
        for (String request : selectedUser.getFriendRequest()) {
            MenuItem item = new MenuItem(request);
            item.setOnAction(event -> {
                selectedItem = item;
                friendAcceptBox.getChildren().add(createFriendBox(DatabaseQuerier.getUser(request)));
                friendAcceptBox.getChildren().add(acceptBtn(request));
                friendAcceptBox.getChildren().add(denialBtn(request));
                friendAcceptBox.setSpacing(10);
            });
            receivedFriendRequests.getItems().add(item);
        }
    }

    private HBox createFriendBox(User user) {
        HBox hBox = new HBox();
        ImageView avatar = new ImageView(AvatarView.getAvatarImage(user.getAvatarURL()));
        avatar.setFitHeight(15);
        avatar.setFitWidth(15);
        avatar.setOnMouseClicked(mouseEvent -> DatabaseQuerier.sendFriendRequest(MenuStack.getInstance().getUser().getUsername(),
                user.getUsername()));
        hBox.getChildren().add(avatar);
        Text username = new Text(user.getUsername());
        hBox.getChildren().add(username);
        hBox.setSpacing(10);
        hBox.setOnMouseEntered(mouseEvent -> hBox.setCursor(Cursor.HAND));
        hBox.setOnMouseClicked(mouseEvent -> new UserDialog(user).showAndWait());
        return hBox;
    }

    private Button acceptBtn(String username) {
        Button button = new Button("Accept");
        button.setBackground(Background.fill(Color.GREEN));
        button.setOnMouseEntered(actionEvent -> button.setCursor(Cursor.HAND));
        button.setOnMouseClicked(actionEvent -> {
            DatabaseQuerier.acceptFriendRequest(selectedUser.getUsername(), username);
            friendsBox.getChildren().add(createFriendBox(DatabaseQuerier.getUser(username)));
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
                UserDialog dialog = new UserDialog(user);
                dialog.showAndWait();
                if (dialog.isOk()) {
                    addToFriendsMenu.setText(user.getUsername());
                    DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
                    initRequestBox();
                }
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

    public void searchForFriend() {
        String name = typeFriendNameField.getText();
        if (!allUsernames.contains(name)) {
            typeFriendNameField.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            return;
        }
        typeFriendNameField.setStyle(null);
        User user = DatabaseQuerier.getUser(name);
        UserDialog dialog = new UserDialog(user);
        dialog.showAndWait();
        if (dialog.isOk()) {
            DatabaseQuerier.sendFriendRequest(selectedUser.getUsername(), user.getUsername());
            user.sendFriendRequest(selectedUser.getUsername());
            initRequestBox();
        }
    }
}
