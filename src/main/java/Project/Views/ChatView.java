package Project.Views;

import Project.Models.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ChatView implements ViewController {
    @FXML
    Button deleteButton;
    @FXML
    private VBox chatBox;
    @FXML
    private ScrollBar scrollBar;
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
                    Text text = new Text(message.toString());
                    text.maxWidth(475);
                    chatBox.getChildren().add(text);
                }
            }
        } catch (Exception e) {

        }
        System.out.println(scrollBar.getMax() + " " + scrollBar.getMin());
        scrollBar.valueProperty().addListener(event -> {
            chatBox.setTranslateY(-(double) (scrollBar.getValue() * chatBox.getHeight()) / (scrollBar.getMax()));
        });
        chatBox.setMaxHeight(100);
        chatBox.setMaxWidth(552);
    }
    @FXML
    public void sendNewMessage() {
        System.out.println((scrollBar.getValue() * chatBox.getHeight()) / (scrollBar.getMax()) + " " + chatBox.getLayoutY() + " " + scrollBar.getMax() + " " + chatBox.getTranslateY() + " " + chatBox.getMaxHeight() + " " + chatBox.getScaleY() + " " + chatBox.getPrefHeight() + " " + chatBox.getHeight());
        if (currentEditingMessage == null) {
            Message newMessage = new Message(MenuStack.getInstance().getUser().getUsername(), messageTextField.getText());
            MenuStack.getInstance().getUser().getChat().sendMessage(newMessage);
            newMessage.getText().setOnMouseClicked(mouseEvent -> {
                ChatView.this.currentEditingMessage = newMessage;
                messageTextField.setText(newMessage.getMessage());
                deleteButton.setDisable(false);
                deleteButton.setVisible(true);
            });
            chatBox.getChildren().add(newMessage.getText());
        } else {
            currentEditingMessage.editMessage(messageTextField.getText());
            currentEditingMessage = null;
            disableDeleteButton();
        }
        messageTextField.setText("");
    }

    @FXML
    public void deleteMessage() {
        MenuStack.getInstance().getUser().getChat().deleteMessage(currentEditingMessage);
        chatBox.getChildren().remove(currentEditingMessage.getText());
        disableDeleteButton();
        currentEditingMessage = null;
    }

    public void disableDeleteButton() {
        deleteButton.setDisable(true);
        deleteButton.setVisible(false);
    }

    public void changeMessage(String message) {
        messageTextField.setText(message);
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
        MenuStack.getInstance().popMenu();
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ChatSelectPage"));
    }
}
