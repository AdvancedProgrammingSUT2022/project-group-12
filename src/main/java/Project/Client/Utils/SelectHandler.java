package Project.Client.Utils;

import Project.Client.Views.MenuStack;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import Project.Server.Views.RequestHandler;

public class SelectHandler {
    public static void sendSelectUnitRequest(String unitName,Location location) {
        DatabaseQuerier.sendSelectUnitRequest(unitName,String.valueOf(location.getRow()),String.valueOf(location.getCol()));
    }

    public static void sendSelectCityRequest(String cityName) {
        Location cityLocation = DatabaseQuerier.getCurrentCivLocationByName(cityName);
        String command = "select city " + " -p " + cityLocation.getRow() + " " + cityLocation.getCol();
        RequestHandler.getInstance().handle(command);
    }
    public static void sendSelectCityRequest(City city) {
        String command = "select city " + " -p " + city.getLocation().getRow() + " " + city.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
    }
}
