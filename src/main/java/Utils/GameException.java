package Utils;

public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }

    public GameException(CommandResponse commandResponse) {
        super(commandResponse.toString());
    }

    public GameException(CommandResponse commandResponse, String item) {
        super(commandResponse.toString() + ": " + item);
    }

    public void print() {
        System.out.println("Wait, " + this.getMessage());
    }
}
