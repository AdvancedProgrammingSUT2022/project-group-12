package Server.Controllers.ValidateGameMenuFuncs;

import Project.Enums.FeatureEnum;
import Project.Enums.ImprovementEnum;
import Project.Enums.UnitEnum;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import Server.Controllers.GameController;
import Server.Controllers.MovingController;
import Server.Models.Civilization;
import Server.Utils.Command;
import Server.Utils.CommandException;

import java.util.ArrayList;

public class UnitFuncs {

    private static CommandResponse validateForCity(Tile currentTile, Civilization civilization) {
        if (currentTile.getCity() == null) return CommandResponse.CITY_DOES_NOT_EXISTS;
        if (currentTile.getCity().getCivName() != civilization.getName()) {
            return CommandResponse.NOT_HAVING_CITY;
        }
        return CommandResponse.OK;
    }

    public void unitFortify(Unit selectedUnit, Command command) throws CommandException {
        if (command.getSubSubCategory().equals("heal")) {
            GameController.fortifyHealUnit(selectedUnit);
            return;
        } else if (command.getSubSubCategory().trim().equals("")) {
            GameController.fortifyUnit(selectedUnit);
            return;
        }
        throw new CommandException(CommandResponse.INVALID_COMMAND);
    }

    public void unitMoveTo(Unit unit, Location location) throws CommandException {
        Civilization currentCivilization = GameController.getGame().getCurrentCivilization();
        Location currentGridLocation = currentCivilization.getCurrentGridLocation();
        Tile currentTile = GameController.getGameTile(currentGridLocation);
        validateTileForMovingUnit(location, unit);
        MovingController.moveUnit(location, currentTile, unit);

    }



    public static void validateTileForMovingUnit(Location location, Unit unit) throws CommandException {
        if(unit == null){
            throw new CommandException(CommandResponse.NO_UNIT_SELECTED);
        }
        if (!GameController.getGame().getTileGrid().isLocationValid(location)) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
        if (location == unit.getLocation()) throw new CommandException(CommandResponse.UNIT_IS_ALREADY_ON_TILE);
        Tile nextTile = GameController.getGameTile(location);
        if (unit instanceof NonCombatUnit && nextTile.getNonCombatUnit() != null ||
                unit instanceof CombatUnit && nextTile.getCombatUnit() != null) {
            throw new CommandException(CommandResponse.TILE_IS_FULL);
        }
        if (!nextTile.getTerrain().getTerrainType().canBePassed()) {
            throw new CommandException(CommandResponse.IMPOSSIBLE_MOVE);
        }
    }

//    public void unitRemoveJungle() {
//        Civilization currentCivilization = getCurrentCivilization();
//        Tile currentTile = getCurrentTile();
//        CommandResponse response = validateTileForRemovingJungle(currentTile, currentCivilization);
//        if (response.isOK()) {
//            System.out.println(GameController.RemoveJungle(currentTile));
//        } else {
//            System.out.println(response);
//        }
//    }

    private CommandResponse validateTileForRemovingJungle(Tile currentTile, Civilization civilization) {
        if (!(currentTile.getNonCombatUnit().getUnitType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!currentTile.getTerrain().getFeatures().contains(FeatureEnum.JUNGLE)) {
            return CommandResponse.JUNGLE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }

    public void unitRemoveRoute() {
//        Civilization currentCivilization = getCurrentCivilization();
//        Tile currentTile = getCurrentTile();
//        CommandResponse response = validateTileForRemovingRoute(currentTile, currentCivilization);
//        if (response.isOK()) {
//            if (isExists(currentTile, ImprovementEnum.RAILROAD)) {
//                System.out.println(GameController.RemoveRoute(currentTile, ImprovementEnum.RAILROAD));
//            }
//            if (isExists(currentTile, ImprovementEnum.ROAD)) {
//                System.out.println(GameController.RemoveRoute(currentTile, ImprovementEnum.ROAD));
//            }
//        } else {
//            System.out.println(response);
//        }
    }

    private CommandResponse validateTileForRemovingRoute(Tile currentTile) {
        if (!(currentTile.getNonCombatUnit().getUnitType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        ArrayList<ImprovementEnum> tileImprovements = currentTile.getImprovements();
        if (!tileImprovements.contains(ImprovementEnum.ROAD) && !tileImprovements.contains(ImprovementEnum.RAILROAD)) {
            return CommandResponse.ROUTE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }
}
