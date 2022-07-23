package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.Chat;
import Project.Models.Message;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatView implements ViewController {
    public ScrollPane pane;
    @FXML
    Button deleteButton;
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
            if (MenuStack.getInstance().getCookies().getCurrentChat().getMessages() != null) {
                ArrayList<Message> messages = MenuStack.getInstance().getCookies().getCurrentChat().getMessages();
                for (Message message : messages) {
                    message.checkSeen(MenuStack.getInstance().getUser().getUsername());
                    chatBox.getChildren().add(createMessageBox(message));
                    System.out.println(currentEditingMessage);
                    message.getText().setOnMouseClicked(mouseEvent -> {
                        ChatView.this.currentEditingMessage = message;
                        messageTextField.setText(message.getMessage());
                        deleteButton.setDisable(false);
                        deleteButton.setVisible(true);
                    });
                }
            }
        } catch (Exception ignored) {

        }
        chatBox.setMaxHeight(100);
        chatBox.setMaxWidth(552);
        pane.setContent(chatBox);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @FXML
    public void sendNewMessage() {
        System.out.println(currentEditingMessage);
        if (currentEditingMessage == null) {
            User user = MenuStack.getInstance().getUser();
            Message newMessage = new Message(user.getUsername(), user.getAvatarURL(), messageTextField.getText());
            DatabaseQuerier.sendMessage(newMessage, MenuStack.getInstance().getCookies().getCurrentChat().getToken());
            chatBox.getChildren().add(createMessageBox(newMessage));
        } else {
            System.out.println("edit");
            currentEditingMessage.editMessage(messageTextField.getText());
            currentEditingMessage = null;
            disableDeleteButton();
            DatabaseQuerier.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getCurrentChat());
        }
        messageTextField.setText("");
    }

    public HBox createMessageBox(Message message) {
        HBox messageBox = new HBox();
        Image avatar = AvatarView.getAvatarImage(message.getAvatarURL());
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        messageBox.getChildren().add(imageView);
        messageBox.setAlignment(Pos.CENTER);
        if (message.isSeen() && !message.isFirstTime()) {
            message.getText().setText(message.getText().getText() + "✓");
            message.setFirstTime(true);
        } else if (!message.isFirstTime()) {
            message.getText().setText(message.getText().getText() + " ✓");
        }
        messageBox.getChildren().add(message.getText());
        messageBox.setOnMouseClicked(mouseEvent -> {
            ChatView.this.currentEditingMessage = message;
            messageTextField.setText(message.getMessage());
            deleteButton.setDisable(false);
            deleteButton.setVisible(true);
        });
        return messageBox;
    }

    @FXML
    public void deleteMessage() {
        MenuStack.getInstance().getCookies().getCurrentChat().deleteMessage(currentEditingMessage);
        chatBox.getChildren().remove(createMessageBox(currentEditingMessage));
        disableDeleteButton();
        DatabaseQuerier.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getCurrentChat());
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

    public void updateChat(Chat updateChat) {
            chatBox.getChildren().clear();
            if (updateChat.getName().equals(MenuStack.getInstance().getCookies().getCurrentChat().getName())) {
                MenuStack.getInstance().getCookies().setCurrentChat(updateChat);
                if (MenuStack.getInstance().getCookies().getCurrentChat().getMessages() != null) {
                    ArrayList<Message> messages = MenuStack.getInstance().getCookies().getCurrentChat().getMessages();
                    for (Message message : messages) {
                        message.checkSeen(MenuStack.getInstance().getUser().getUsername());
                        message.getText().setOnMouseClicked(mouseEvent -> {
                            ChatView.this.currentEditingMessage = message;
                            messageTextField.setText(message.getMessage());
                            deleteButton.setDisable(false);
                            deleteButton.setVisible(true);
                        });
                        chatBox.getChildren().add(createMessageBox(message));
                    }
                }
            }
    }
}
