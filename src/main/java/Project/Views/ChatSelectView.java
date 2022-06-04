package Project.Views;

import Project.Models.Database;
import Project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatSelectView {
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

    private boolean newChat;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    @FXML
    private ChoiceBox<String> userSelect;
    private ArrayList<String> users = new ArrayList<>();

    public void initialize() {
        User user1 = new User("uname", "password", "nickname");
        User user2 = new User("uname2", "password", "nickname");
        User user3 = new User("uname3", "password", "nickname");
        User user4 = new User("uname4", "password", "nickname");
        User user5 = new User("uname5", "password", "nickname");
        user1.startChat("chat1",Database.getInstance().getUsers());
        user1.startChat("chat2",Database.getInstance().getUsers());
        user1.startChat("chat3",Database.getInstance().getUsers());
        user1.startChat("chat4",Database.getInstance().getUsers());
        MenuStack.getInstance().setUser(user1);
        ArrayList<String> users = Database.getInstance().getAllUsers();
        users.remove(MenuStack.getInstance().getUser().getUsername());
        userSelect.getItems().addAll(users);
        this.newChat = false;
        chatSelect.getItems().addAll(MenuStack.getInstance().getUser().previousChats());
        chatSelect.setOnAction(this::getChat);
        userSelect.setOnAction(this::getUser);
    }

    private void getChat(ActionEvent event) {
        if (newChat) {
            clearEverything();
        }
        chatGroup.setText(chatSelect.getValue());
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
        userSelect.getItems().removeAll(userSelect.getItems());
        ArrayList<String> users = Database.getInstance().getAllUsers();
        users.remove(MenuStack.getInstance().getUser().getUsername());
        userSelect.getItems().addAll(users);
        newChat = !newChat;
        System.out.println(newChat);
        chatGroup.setText("");
        userBox1.getChildren().removeAll(userBox1.getChildren());
        userBox2.getChildren().removeAll(userBox2.getChildren());
        userBox3.getChildren().removeAll(userBox3.getChildren());
        userBox4.getChildren().removeAll(userBox4.getChildren());
        userBox5.getChildren().removeAll(userBox5.getChildren());
    }

    private void getUser(ActionEvent event) {
        if (!newChat)
            clearEverything();
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
        userSelect.getItems().remove(userSelect.getValue());
    }


    public void exitClick() {
        System.out.println(users);
        System.exit(0);
    }

    public void acceptClick() {
        System.out.println(users);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ChatPage"));
    }

    public void backClick() {
        System.out.println("back");
        MenuStack.getInstance().popMenu();
    }
}
