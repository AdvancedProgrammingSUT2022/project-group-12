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
    private TextField messageTextField;
    private Message currentEditingMessage = null;


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
        if (currentEditingMessage == null) {
            Message newMessage = new Message(MenuStack.getInstance().getUser().getUsername(), messageTextField.getText());
            MenuStack.getInstance().getUser().getChat().sendMessage(newMessage);
            newMessage.getText().setOnMouseClicked(mouseEvent -> {
                ChatView.this.currentEditingMessage = newMessage;
                messageTextField.setText(newMessage.getMessage());
            });
            chatBox.getChildren().add(newMessage.getText());
        } else {
            currentEditingMessage.editMessage(messageTextField.getText());
            currentEditingMessage = null;
        }
        messageTextField.setText("");
    }

    public void changeMessage(String message) {
        messageTextField.setText(message);
    }
}
