package Project.Views;

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

        try {
            if (MenuStack.getInstance().getUser().getChat().getMessages() != null) {
                ArrayList<Message> messages = MenuStack.getInstance().getUser().getChat().getMessages();
                for (Message message : messages) {
                    Text text = new Text(messages.toString());
                    chatBox.getChildren().add(text);
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void sendNewMessage() {
        System.out.println(message.getText());
        System.out.println("hellllllllllll");
        Message newMessage = new Message(MenuStack.getInstance().getUser().getUsername(), message.getText());
        MenuStack.getInstance().getUser().getChat().sendMessage(newMessage);
        chatBox.getChildren().add(newMessage.getTextArea());
    }
}
