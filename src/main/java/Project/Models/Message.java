package Project.Models;

import javafx.scene.text.Text;

import java.util.Date;

public class Message {
    private final String from;
    Text text;
    private String message;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        text = new Text(from + " : " + message + "  " + getCurrentDate());
        text.setWrappingWidth(550);
    }

    @Override
    public String toString() {
        return from + " : " + message;
    }

    public Text getText() {
        return text;
    }

    public void editMessage(String message) {
        this.text.setText(from + " : " + message + "  " + getCurrentDate());
        this.message = message;
    }

    private String getCurrentDate() {
        Date date = new Date();
        System.out.println("hello");
        String s = date.getDay() + " : " + date.getHours();
        System.out.println(s);
        return s;
    }

    public String getMessage() {
        return message;
    }


}
