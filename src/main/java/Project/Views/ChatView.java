package Project.Views;

import Project.CommandlineViews.DisabledMenuStackDisabled;
import Project.Models.Message;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatView {
    @FXML
    private VBox chatBox;

    public void exitClick() {
        System.exit(0);
    }

    public void initialize() {
        ArrayList<Message> messages = DisabledMenuStackDisabled.getInstance().getUser().getChat().getMessages();
        for (Message message : messages) {
            Text text = new Text(messages.toString());
            chatBox.getChildren().add(text);
        }
    }
}
