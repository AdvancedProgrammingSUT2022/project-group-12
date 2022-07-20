package Server.Controllers;

import Project.Enums.ResourceEnum;
import Project.Models.Notifications.DeclareWar;
import Project.Models.Notifications.Demand;
import Project.Models.Notifications.Peace;
import Project.Models.Notifications.Trade;
import Server.Models.Civilization;
import Server.Utils.CommandException;

public class DiplomacyController {
    public static void acceptTrade(Trade trade){
        Civilization hostCiv = GameController.getGame().getCivByName(trade.getHostCivName());
        Civilization guestCiv = GameController.getGame().getCivByName(trade.getGuestCivName());
        if(trade.getSuggestResources() == null){
            guestCiv.addGold(trade.getSuggestGold());
            guestCiv.removeResource(trade.getRequiredResources());
            hostCiv.decreaseGold(trade.getSuggestGold());
            hostCiv.addResource(trade.getRequiredResources());
        } else {
            if(trade.getRequiredResources() == null){
                guestCiv.addResource(trade.getSuggestResources());
                guestCiv.decreaseGold(trade.getRequiredGold());
                hostCiv.addGold(trade.getRequiredGold());
                hostCiv.removeResource(trade.getSuggestResources());
            } else {
                guestCiv.addResource(trade.getSuggestResources());
                guestCiv.removeResource(trade.getRequiredResources());
                hostCiv.addResource(trade.getRequiredResources());
                hostCiv.removeResource(trade.getSuggestResources());
            }
        }
        guestCiv.removeNotification(trade);
    }

    public static void rejectTrade(Trade trade){
        Civilization guestCiv = GameController.getGame().getCivByName(trade.getGuestCivName());
        guestCiv.removeNotification(trade);
    }
    public static void declareWar(DeclareWar declareWar){
        Civilization hostCiv = GameController.getGame().getCivByName(declareWar.getHostName());
        Civilization guestCiv = GameController.getGame().getCivByName(declareWar.getGuestName());
        hostCiv.declareWar(guestCiv);
        guestCiv.declareWar(hostCiv);
    }
    public static void seenDeclareWar(DeclareWar declareWar){
        Civilization hostCiv = GameController.getGame().getCivByName(declareWar.getHostName());
        Civilization guestCiv = GameController.getGame().getCivByName(declareWar.getGuestName());
    }
    public static void makePeace(Peace peace){
        Civilization hostCiv = GameController.getGame().getCivByName(peace.getHostName());
        Civilization guestCiv = GameController.getGame().getCivByName(peace.getGuestName());
        hostCiv.peace(guestCiv);
        guestCiv.peace(hostCiv);
    }

    public static void rejectPeace(Peace peace){
        Civilization hostCiv = GameController.getGame().getCivByName(peace.getHostName());
        Civilization guestCiv = GameController.getGame().getCivByName(peace.getGuestName());
    }
    public static void acceptDemand(Demand demand) {
        Civilization hostCiv = GameController.getGame().getCivByName(demand.getHostCivName());
        Civilization guestCiv = GameController.getGame().getCivByName(demand.getGuestCivName());
        if (demand.getRequierdGold() == null) {
                hostCiv.addResource(ResourceEnum.getResourceEnumByName(demand.getResourceName()));
                guestCiv.removeResource(ResourceEnum.getResourceEnumByName(demand.getResourceName()));
        } else {
            hostCiv.addGold(demand.getRequierdGold());
            guestCiv.decreaseGold(demand.getRequierdGold());
        }
        guestCiv.removeDemand(demand);
    }
    public static void rejectDemand(Demand demand) {
        Civilization guestCiv = GameController.getGame().getCivByName(demand.getGuestCivName());
        guestCiv.removeDemand(demand);
    }




}
