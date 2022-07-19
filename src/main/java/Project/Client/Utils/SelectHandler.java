package Project.Client.Utils;

import Project.Client.Views.MenuStack;
import Project.Server.Views.RequestHandler;
import Project.SharedModels.Cities.City;
import Project.SharedModels.Units.CombatUnit;
import Project.SharedModels.Units.Unit;

public class SelectHandler {
    public static void sendSelectUnitRequest(Unit unit) {
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        DatabaseQuerier.getSelectedUnit();
        MenuStack.getInstance().getCookies().setSelectedUnit(unit);
    }

    public static void sendSelectCityRequest(City city) {

    }
}
