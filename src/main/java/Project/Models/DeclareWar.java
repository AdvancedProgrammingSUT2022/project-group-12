package Project.Models;

import Project.Server.Controllers.GameController;

public class DeclareWar extends Notification{
    String hostName;
    String guestName;
    String type;

    DeclareWar(String civName,String guestName,String name){
        this.hostName = civName;
        this.guestName = guestName;
        super.message = createMessage();
        super.name = name;
        this.type = this.getClass().getName();
    }

    private String createMessage() {
        return hostName + " declared war to you";
    }
    public void declareWar(){
        Civilization hostCiv = GameController.getGame().getCivByName(hostName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestName);
        hostCiv.declareWar(guestCiv);
        guestCiv.declareWar(hostCiv);
    }
    public void peace(){
        Civilization hostCiv = GameController.getGame().getCivByName(hostName);
        Civilization guestCiv = GameController.getGame().getCivByName(guestName);
        hostCiv.declareWar(guestCiv);
        guestCiv.declareWar(hostCiv);
    }

}
