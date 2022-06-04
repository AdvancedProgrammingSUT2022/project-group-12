package Project.Views;

import Project.CommandlineViews.MenuStackDisabled;
import Project.Models.Message;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatView {
    @FXML
    private VBox chatBox;
    @FXML
    private TextField message;


    public void exitClick() {
        System.exit(0);
    }

    public void initialize() {
        ArrayList<Message> messages = MenuStackDisabled.getInstance().getUser().getChat().getMessages();
        for (Message message : messages) {
            Text text = new Text(messages.toString());
            chatBox.getChildren().add(text);
        }
    }


    public void sendNewMessage() {
        MenuStack.getInstance().getUser().getChat().sendMessage(MenuStack.getInstance().getUser().getUsername(), message.getText());

    }
}
