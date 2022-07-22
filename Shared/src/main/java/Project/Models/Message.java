package Project.Models;

import Project.Enums.AvatarURLEnum;
import javafx.scene.text.Text;

import java.util.Date;

public class Message {
    private final String from;
    private final AvatarURLEnum avatarURL;
    String detailedMessage;
    private String message;
    Boolean isSeen;
    Boolean isFirstTime;

    public Message(String from, AvatarURLEnum avatarURL, String message) {
        this.from = from;
        this.message = message;
        this.avatarURL = avatarURL;
        isSeen = false;
        isFirstTime = false;
        this.detailedMessage = "  " + from + " : " + message + " " + getCurrentDate();
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
        return new Text(detailedMessage);
    }

    public void editMessage(String message) {
        this.detailedMessage = from + " : " + message + " " + getCurrentDate() + " edited";
        this.message = message;
    }

    private String getCurrentDate() {
        Date date = new Date();
        return date.getHours() + ":" + date.getMinutes();
    }

    public String getMessage() {
        return message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public AvatarURLEnum getAvatarURL() {
        return avatarURL;
    }

    public String getFrom() {
        return from;
    }

    public Boolean isSeen() {
        return isSeen;
    }

    public Boolean isFirstTime() {
        return isFirstTime;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public void setFirstTime(Boolean firstTime) {
        isFirstTime = firstTime;
    }
}
