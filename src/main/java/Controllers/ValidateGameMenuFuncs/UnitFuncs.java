package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Enums.FeatureEnum;
import Enums.ImprovementEnum;
import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;

import static Controllers.MovingController.moveUnit;

public class UnitFuncs {

    private static CommandResponse validateForCity(Tile currentTile, Civilization civilization) {
        if (currentTile.getCity() == null) return CommandResponse.CITY_DOES_NOT_EXISTS;
        if (currentTile.getCity().getCivilization() != civilization) {
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
        Tile currentTile = currentCivilization.getRevealedTileGrid().getTile(currentGridLocation);
        validateTileForMovingUnit(location, unit);
        moveUnit(location, currentTile, unit);
    }

    public static void validateTileForMovingUnit(Location location, Unit unit) throws CommandException {
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
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
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
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        ArrayList<ImprovementEnum> tileImprovements = currentTile.getImprovements();
        if (!tileImprovements.contains(ImprovementEnum.ROAD) && !tileImprovements.contains(ImprovementEnum.RAILROAD)) {
            return CommandResponse.ROUTE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }
}
