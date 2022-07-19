package Project.Models;

import Project.Server.Controllers.GameController;
import Project.Server.Models.Civilization;

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


    public void peace(){
        Civilization hostCiv = GameController.getGame().getCivByName(hostName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestName);
        hostCiv.peace(guestCiv);
        guestCiv.peace(hostCiv);
    }

    public void rejectPeace(){
        Civilization hostCiv = GameController.getGame().getCivByName(hostName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestName);
    }



}
