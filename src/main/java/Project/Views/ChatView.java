package Project.Views;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Project.CommandlineViews.MenuStack;
import Project.Models.Message;

import java.util.ArrayList;

public class ChatView {
    @FXML
    private VBox chatBox;

    public void exitClick() {
        System.exit(0);
    }

    public void initialize() {
        ArrayList<Message> messages = MenuStack.getInstance().getUser().getChat().getMessages();
        for (Message message : messages) {
            Text text = new Text(messages.toString());
            chatBox.getChildren().add(text);
        }
    }
}