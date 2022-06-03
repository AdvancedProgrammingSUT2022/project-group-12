package Models;

public class Message {
    private String from;
    private String message;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    @Override
    public String toString() {
        return from + "\n" + message;
    }
}
