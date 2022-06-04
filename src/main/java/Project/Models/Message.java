package Project.Models;

import javafx.scene.text.Text;

public class Message {
    private final String from;
    Text text;
    private String message;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        text = new Text(from + " : " + message);
        text.setWrappingWidth(500);
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
        this.text.setText(from + " : " + message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
