package Views;

import Models.Database;
import Models.User;
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
        this.newChat = false;
        MenuStack.getInstance().setUser(new User("username", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username1", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username2", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username3", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username5", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username4", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username6", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username7", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username8", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username9", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username0", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username22", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username1353", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("username3twr", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("usernameasa", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("usernamesasa", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("usernameaa", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("usernamea", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>());
        MenuStack.getInstance().setUser(new User("usernamefew", "password", "nickname"));
        MenuStack.getInstance().getUser().startChat("newName",new ArrayList<>(){{addAll(Database.getInstance().getUsers());}});
        MenuStack.getInstance().getUser().startChat("newName2",new ArrayList<>(){{addAll(Database.getInstance().getUsers());}});
        MenuStack.getInstance().getUser().startChat("newName3",new ArrayList<>(){{addAll(Database.getInstance().getUsers());}});
        MenuStack.getInstance().getUser().startChat("newName4",new ArrayList<>(){{addAll(Database.getInstance().getUsers());}});
        chatSelect.getItems().addAll(MenuStack.getInstance().getUser().previousChats());
        userSelect.getItems().addAll(Database.getInstance().getAllUsers());
        chatSelect.setOnAction(this::getChat);
        userSelect.setOnAction(this::getUser);
    }

    private void getChat(ActionEvent event) {
        if (newChat){
            //todo : fix
            newChat = false;
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
        newChat = !newChat;
        System.out.println(newChat);
        chatGroup.setText("");
        userBox1.getChildren().removeAll(userBox1);
        userBox2.getChildren().removeAll(userBox2);
        userBox3.getChildren().removeAll(userBox3);
        userBox4.getChildren().removeAll(userBox4);
        userBox5.getChildren().removeAll(userBox5);
    }

    private void getUser(ActionEvent event) {
        //todo : fix
        if (!newChat)
            clearEverything();
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
        System.out.println(users);
        System.exit(0);
    }

    public void acceptClick() {
        System.out.println(users);
        //todo : change to chat menu
    }

    public void backClick() {
        System.out.println("back");
        //todo : change to previous menu
    }
}
