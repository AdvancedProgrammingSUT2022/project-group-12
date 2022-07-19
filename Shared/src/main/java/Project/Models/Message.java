package Project.Models;

import Project.Server.Models.Database;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Date;

public class Message {
    private final String from;
    Text text;
    private String message;
    HBox messageBox;
    Boolean isSeen;
    Boolean isFirstTime;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        isSeen = false;
        isFirstTime = false;
        text = new Text("  " + from + " : " + message + " " + getCurrentDate());
        text.setWrappingWidth(500);
        messageBox = new HBox();
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



    public HBox getMessageBox() {
        messageBox = new HBox();
        ImageView imageView = new ImageView(Database.getInstance().getUser(from).getImage());
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        messageBox.getChildren().add(imageView);
        messageBox.setAlignment(Pos.CENTER);
        if (isSeen && !isFirstTime) {
            this.text.setText(text.getText() + "✓");
            isFirstTime = true;
        } else if (!isFirstTime) {
            this.text.setText(text.getText() + " ✓");
        }
        messageBox.getChildren().add(this.text);
        return messageBox;
    }
}
