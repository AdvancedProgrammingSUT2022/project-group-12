package Client.Utils;

import Client.Views.MenuStack;
import Project.Models.Cities.City;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;

public class SelectHandler {
    public static void sendSelectUnitRequest(Unit unit) {
        System.out.println("unit = " + unit.getUnitType());
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestSender.getInstance().send(command);
    }

    public static void sendSelectCityRequest(City city) {
        String command = "select city -p " + city.getLocation().getRow() + " " + city.getLocation().getCol();
        RequestSender.getInstance().send(command);
        MenuStack.getInstance().getCookies().setSelectedCity(city);
    }
}
