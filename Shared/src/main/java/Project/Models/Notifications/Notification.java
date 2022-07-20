package Project.Models.Notifications;

public abstract class  Notification {

    String message;
    String name;

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " : " + message;
    }
}
