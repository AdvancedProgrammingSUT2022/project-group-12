package Project.Models;

import javafx.scene.control.TextArea;

public class Message {
    private final String from;
    private final String message;
    TextArea textArea;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        textArea = new TextArea(message);
    }

    @Override
    public String toString() {
        return from + "\n" + message;
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
