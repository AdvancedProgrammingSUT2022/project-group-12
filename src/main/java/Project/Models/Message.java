package Project.Models;

import Project.Views.MenuStack;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Date;

public class Message {
    private final String from;
    Text text;
    ImageView imageView;
    private String message;
    HBox messageBox = new HBox();
    Boolean isSeen;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        isSeen = false;
        imageView = new ImageView(MenuStack.getInstance().getUser().getImage());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        text = new Text("  " + from + " : " + message + " " + getCurrentDate());
        text.setWrappingWidth(500);
    }

    public void checkSeen(String username) {
        if (!from.equals(username)) {
            isSeen = true;
        }
    }

    public String getSender() {
        return this.from;
    }

    @Override
    public String toString() {
        return from + " : " + message;
    }

    public Text getText() {
        return text;
    }

    public void editMessage(String message) {
        this.text.setText(from + " : " + message + " " + getCurrentDate() + " edited");
        this.message = message;
    }

    private String getCurrentDate() {
        Date date = new Date();
        return date.getHours() + ":" + date.getMinutes();
    }

    public String getMessage() {
        return message;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public HBox getMessageBox() {
        messageBox.getChildren().removeAll();
        messageBox.getChildren().add(imageView);
        messageBox.setAlignment(Pos.CENTER);
        if (isSeen) {
            this.text.setText(this.text.getText() + " " + "✓✓");
            messageBox.getChildren().add(this.text);
        } else {
            this.text.setText(this.text.getText() + " " + "✓");
            messageBox.getChildren().add(this.text);
        }
        return messageBox;
    }
}
