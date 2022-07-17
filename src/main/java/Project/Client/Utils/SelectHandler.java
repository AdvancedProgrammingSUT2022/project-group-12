package Project.Client.Utils;

import Project.Client.Views.MenuStack;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import Project.Server.Views.RequestHandler;

public class SelectHandler {
    public static void sendSelectUnitRequest(Unit unit) {
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        MenuStack.getInstance().getCookies().setSelectedUnit(unit);
    }

    public static void sendSelectCityRequest(String cityName) {
        Location cityLocation = DatabaseQuerier.getCurrentCivLocationByName(cityName);
        String command = "select city " + " -p " + cityLocation.getRow() + " " + cityLocation.getCol();
        RequestHandler.getInstance().handle(command);
        // bebin to client dige city nadarim ino dorost kon age mishe mammnoon
        //MenuStack.getInstance().getCookies().setSelectedCity(city);
    }
    public static void sendSelectCityRequest(City city) {
        String command = "select city " + " -p " + city.getLocation().getRow() + " " + city.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        // bebin to client dige city nadarim ino dorost kon age mishe mammnoon
        //MenuStack.getInstance().getCookies().setSelectedCity(city);
    }
}
