package Project.Models.Notifications;


public class Demand extends Notification {
    String type;
    Integer requierdGold;
    String resourceName;
    final String hostCivName;
    final String guestCivName;

    public Demand(Integer requierdGold, String resourceName, String hostCiv, String guestCiv, String name) {
        this.requierdGold = requierdGold;
        this.resourceName = resourceName;
        this.hostCivName = hostCiv;
        this.guestCivName = guestCiv;
        super.message = createMessage();
        super.name = name;
        this.type = this.getClass().getName();
    }

    private String createMessage() {
        if (this.requierdGold == null) {
            return "Demand : " + resourceName + " " + hostCivName;
        } else {
            return "Demand : " + requierdGold + " Gold " + hostCivName;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRequierdGold() {
        return requierdGold;
    }

    public void setRequierdGold(Integer requierdGold) {
        this.requierdGold = requierdGold;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getHostCivName() {
        return hostCivName;
    }

    public String getGuestCivName() {
        return guestCivName;
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public String getMessage() {
        return super.message;
    }
}
