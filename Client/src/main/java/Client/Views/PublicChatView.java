package Client.Views;

import Client.Utils.ChatController;
import Client.Utils.DatabaseQuerier;
import Project.Enums.ChatType;
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

public class PublicChatView implements ViewController, ChatController {

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
            MenuStack.getInstance().getCookies().setPublicChat(DatabaseQuerier.getPublicChat());
            if (MenuStack.getInstance().getCookies().getPublicChat().getMessages() != null) {
                ArrayList<Message> messages = MenuStack.getInstance().getCookies().getPublicChat().getMessages();
                for (Message message : messages) {
                    message.checkSeen(MenuStack.getInstance().getUser().getUsername());
                    chatBox.getChildren().add(createMessageBox(message));
                    if(message.getSender().equals(MenuStack.getInstance().getUser().getUsername())) {
                        makeMessageEditable(message);
                    }
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

    @Override
    public void makeMessageEditable(Message message) {
        message.getText().setOnMouseClicked(mouseEvent -> {
            PublicChatView.this.currentEditingMessage = message;
            messageTextField.setText(message.getMessage());
            deleteButton.setDisable(false);
            deleteButton.setVisible(true);
        });
    }

    @FXML
    public void sendNewMessage() {
        if (currentEditingMessage == null) {
            User user = MenuStack.getInstance().getUser();
            Message newMessage = new Message(user.getUsername(), user.getAvatarURL(), messageTextField.getText());
            MenuStack.getInstance().getCookies().getPublicChat().getMessages().add(newMessage);
            this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getPublicChat());
            chatBox.getChildren().add(createMessageBox(newMessage));
        } else {
            System.out.println("edit");
            currentEditingMessage.editMessage(messageTextField.getText());
            currentEditingMessage = null;
            disableDeleteButton();
            this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getPublicChat());
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
            PublicChatView.this.currentEditingMessage = message;
            messageTextField.setText(message.getMessage());
            deleteButton.setDisable(false);
            deleteButton.setVisible(true);
        });
        return messageBox;
    }

    @FXML
    public void deleteMessage() {
        MenuStack.getInstance().getCookies().getPublicChat().deleteMessage(currentEditingMessage);
        chatBox.getChildren().remove(createMessageBox(currentEditingMessage));
        disableDeleteButton();
        this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getPublicChat());
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
    @Override
    public void updateChat(Chat updateChat) {
        chatBox.getChildren().clear();
        if (updateChat.getName().equals(MenuStack.getInstance().getCookies().getPublicChat().getName())) {
            MenuStack.getInstance().getCookies().setPublicChat(updateChat);
            if (MenuStack.getInstance().getCookies().getPublicChat().getMessages() != null) {
                ArrayList<Message> messages = MenuStack.getInstance().getCookies().getPublicChat().getMessages();
                for (Message message : messages) {
                    message.checkSeen(MenuStack.getInstance().getUser().getUsername());
                    if(message.getSender().equals(MenuStack.getInstance().getUser().getUsername())) {
                        makeMessageEditable(message);
                    }
                    chatBox.getChildren().add(createMessageBox(message));
                }
            }
        }
    }

    @Override
    public void sendUpdateChatRequest(Chat chat) {
         DatabaseQuerier.sendUpdateChatRequest(chat,ChatType.PUBLIC_CHAT);
    }


}
