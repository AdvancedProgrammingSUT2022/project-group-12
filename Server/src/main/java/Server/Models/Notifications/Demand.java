package Server.Models.Notifications;


import Project.Enums.ResourceEnum;
import Server.Controllers.GameController;
import Server.Models.Civilization;
import Server.Utils.CommandException;

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
        Civilization hostCiv = GameController.getGame().getCivByName(hostCivName);
        if (this.requierdGold == null) {
            return "Demand : " + resourceName + " " + hostCivName;
        } else {
            return "Demand : " + requierdGold + " Gold " + hostCivName;
        }
    }

    public void acceptDemand() {
        Civilization hostCiv = GameController.getGame().getCivByName(hostCivName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestCivName);
        if (this.requierdGold == null) {
                hostCiv.addResource(ResourceEnum.getResourceEnumByName(resourceName));
                guestCiv.removeResource(ResourceEnum.getResourceEnumByName(resourceName));
        } else {
            hostCiv.addGold(requierdGold);
            guestCiv.decreaseGold(requierdGold);
        }
        guestCiv.removeDemand(this);
    }

    public void rejectDemand() {
        Civilization guestCiv = GameController.getGame().getCivByName(guestCivName);
        guestCiv.removeDemand(this);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public String getMessage() {
        return super.message;
    }
}
