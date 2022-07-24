package Client.Views;

import Client.Utils.ChatController;
import Client.Utils.DatabaseQuerier;
import Project.Enums.ChatType;
import Project.Models.Chat;
import Project.Models.Message;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatView implements ViewController, ChatController {
    public ScrollPane pane;
    @FXML
    Button deleteButton;
    @FXML
    private VBox chatBox;
    @FXML
    private TextField messageTextField;
    private Message currentEditingMessage = null;

    public void initialize() {
        try {
            if (MenuStack.getInstance().getCookies().getCurrentChat().getMessages() != null) {
                ArrayList<Message> messages = MenuStack.getInstance().getCookies().getCurrentChat().getMessages();
                for (Message message : messages) {
                    message.checkSeen(MenuStack.getInstance().getUser().getUsername());
                    chatBox.getChildren().add(createMessageBox(message));
                    System.out.println(currentEditingMessage);
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
            ChatView.this.currentEditingMessage = message;
            messageTextField.setText(message.getMessage());
            deleteButton.setDisable(false);
            deleteButton.setVisible(true);
        });
    }

    @FXML
    public void sendNewMessage() {
        System.out.println(currentEditingMessage);
        if (currentEditingMessage == null) {
            User user = MenuStack.getInstance().getUser();
            Message newMessage = new Message(user.getUsername(), user.getAvatarURL(), messageTextField.getText());
            System.out.println("send message");
            MenuStack.getInstance().getCookies().getCurrentChat().addMessage(newMessage);
            this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getCurrentChat());
            chatBox.getChildren().add(createMessageBox(newMessage));
        } else {
            System.out.println("edit");
            currentEditingMessage.editMessage(messageTextField.getText());
            currentEditingMessage = null;
            disableDeleteButton();
            this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getCurrentChat());
        }
        messageTextField.setText("");
    }

    public HBox createMessageBox(Message message) {
        HBox messageBox = new HBox();
        Image avatar = AvatarView.getAvatarImage(message.getAvatarURL());
        ImageView imageView = new ImageView(avatar);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        User user = MenuStack.getInstance().getUser();
        if (!message.getSender().equals(user.getUsername())) {
            Tooltip tooltip = new Tooltip("send friend request to " + message.getSender());
            Tooltip.install(imageView, tooltip);
            imageView.setOnMouseClicked(mouseEvent -> {
                UserDialog dialog = new UserDialog(DatabaseQuerier.getUser(message.getSender()));
                dialog.showAndWait();
                if (dialog.isOk()) {
                    DatabaseQuerier.sendFriendRequest(user.getUsername(),
                            message.getSender());
                }
            });
        }
        messageBox.getChildren().add(imageView);
        messageBox.setAlignment(Pos.CENTER);
        if (message.isSeen() && !message.isFirstTime()) {
            message.getText().setText(message.getText().getText() + "✓");
            message.setFirstTime(true);
        } else if (!message.isFirstTime()) {
            message.getText().setText(message.getText().getText() + " ✓");
        }
        messageBox.getChildren().add(message.getText());
        if (user.getUsername().equals(message.getSender())) messageBox.setOnMouseClicked(mouseEvent -> {
            ChatView.this.currentEditingMessage = message;
            messageTextField.setText(message.getMessage());
            deleteButton.setDisable(false);
            deleteButton.setVisible(true);
            messageBox.setCursor(Cursor.HAND);
        });
        return messageBox;
    }

    @FXML
    public void deleteMessage() {
        MenuStack.getInstance().getCookies().getCurrentChat().deleteMessage(currentEditingMessage);
        chatBox.getChildren().remove(createMessageBox(currentEditingMessage));
        disableDeleteButton();
        this.sendUpdateChatRequest(MenuStack.getInstance().getCookies().getCurrentChat());
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
            if (updateChat.getName().equals(MenuStack.getInstance().getCookies().getCurrentChat().getName())) {
                MenuStack.getInstance().getCookies().setCurrentChat(updateChat);
                if (MenuStack.getInstance().getCookies().getCurrentChat().getMessages() != null) {
                    ArrayList<Message> messages = MenuStack.getInstance().getCookies().getCurrentChat().getMessages();
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
             DatabaseQuerier.sendUpdateChatRequest(chat,ChatType.NORMAL_CHAT);
    }
}
