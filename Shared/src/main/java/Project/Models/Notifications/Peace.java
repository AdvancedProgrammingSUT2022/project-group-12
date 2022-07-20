package Project.Models.Notifications;



public class Peace extends Notification{
    String hostName;
    String guestName;
    String type;

    public Peace(String civName, String guestName, String name){
        this.hostName = civName;
        this.guestName = guestName;
        super.message = createMessage();
        super.name = name;
        this.type = this.getClass().getName();
    }

    private String createMessage() {
        return hostName + " want to make peace with you";
    }


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}
