package Project.Models.Notifications;

import Project.Enums.ResourceEnum;
import Server.Controllers.GameController;
import Server.Models.Civilization;


public class Trade extends Notification{
    String type;
    ResourceEnum  suggestResources;
    Integer suggestGold;
    ResourceEnum requiredResources;
    Integer requiredGold;
    String hostCivName;
    String guestCivName;

    public Trade(ResourceEnum suggestResources, Integer suggestGold, ResourceEnum requiredResources, Integer requiredGold, Civilization hostCiv, Civilization guestCiv, String name) {
        this.suggestResources = suggestResources;
        this.suggestGold = suggestGold;
        this.requiredResources = requiredResources;
        this.requiredGold = requiredGold;
        this.hostCivName = hostCiv.getName();
        this.guestCivName = guestCiv.getName();
        this.message = initializeMessage();
        super.name = name;
        this.type = this.getClass().getName();
    }
    private String initializeMessage(){
        if(this.suggestResources == null){
                return "host civ : " + hostCivName + " Suggest : Gold " + suggestGold + " Requires : Resource " + requiredResources;
        } else {
            if(requiredResources == null){
                return "host civ : " + hostCivName + " Suggest : Resource " + suggestResources +  " Requires : Gold " + requiredGold;
            } else {
                return "host civ : " + hostCivName + " Suggest : Resource " + suggestResources + " Requires : Resource " + requiredResources;
            }
        }
    }
    public void accept(){
        Civilization hostCiv = GameController.getGame().getCivByName(hostCivName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestCivName);
        if(this.suggestResources == null){
            guestCiv.addGold(suggestGold);
            guestCiv.removeResource(requiredResources);
            hostCiv.decreaseGold(suggestGold);
            hostCiv.addResource(requiredResources);
        } else {
            if(requiredResources == null){
                guestCiv.addResource(suggestResources);
                guestCiv.decreaseGold(requiredGold);
                hostCiv.addGold(requiredGold);
                hostCiv.removeResource(suggestResources);
            } else {
                guestCiv.addResource(suggestResources);
                guestCiv.removeResource(requiredResources);
                hostCiv.addResource(requiredResources);
                hostCiv.removeResource(suggestResources);
            }
        }
        guestCiv.removeNotification(this);
    }

    public void reject(){
        Civilization guestCiv = GameController.getGame().getCivByName(guestCivName);
        guestCiv.removeNotification(this);
    }

    public String getName() {
        return super.getName();
    }
}
