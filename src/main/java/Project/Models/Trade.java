package Project.Models;

import Project.Enums.ResourceEnum;
import javafx.util.Pair;

public class Trade extends Notification{
    String name;
    ResourceEnum  suggestResources;
    Integer suggestGold;
    ResourceEnum requiredResources;
    Integer requiredGold;
    Civilization hostCiv;
    Civilization guestCiv;

    public Trade(ResourceEnum suggestResources, Integer suggestGold, ResourceEnum requiredResources, Integer requiredGold, Civilization hostCiv, Civilization guestCiv,String name) {
        this.suggestResources = suggestResources;
        this.suggestGold = suggestGold;
        this.requiredResources = requiredResources;
        this.requiredGold = requiredGold;
        this.hostCiv = hostCiv;
        this.guestCiv = guestCiv;
        this.message = initializeMessage();
        this.name = name;
    }
    private String initializeMessage(){
        if(this.suggestResources == null){
                return "host civ : " + hostCiv.getName() + " Suggest : Gold " + suggestGold + " Requires : Resource " + requiredResources;
        } else {
            if(requiredResources == null){
                return "host civ : " + hostCiv.getName() + " Suggest : Resource " + suggestResources +  " Requires : Gold " + requiredGold;
            } else {
                return "host civ : " + hostCiv.getName() + " Suggest : Resource " + suggestResources + " Requires : Resource " + requiredResources;
            }
        }
    }
    public void accept(){
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
        guestCiv.removeNotification(this);
    }

    public String getName() {
        return name;
    }
}
