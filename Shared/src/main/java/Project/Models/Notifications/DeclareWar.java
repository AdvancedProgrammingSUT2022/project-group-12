package Project.Models.Notifications;

import Project.Server.Controllers.GameController;
import Project.Server.Models.Civilization;

public class DeclareWar extends Notification{
    String hostName;
    String guestName;
    String type;

    public DeclareWar(String civName, String guestName, String name){
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
   public void seenDeclareWar(){
       Civilization hostCiv = GameController.getGame().getCivByName(hostName);
       Civilization guestCiv = GameController.getGame().getCivByName(guestName);
   }


}
