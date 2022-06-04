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
        text.setWrappingWidth(550);
    }

    @Override
    public String toString() {
        return from + "\n" + message;
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
