package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Enums.ImprovementEnum;
import Enums.TerrainEnum;
import Enums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;

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

    private CommandResponse isPossibleToBuild(Tile currentTile, Civilization currentCivilization, ImprovementEnum improvement) {
//        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
//            return CommandResponse.WRONG_UNIT;
//        }
//        if (isExists(currentTile, improvement)) {
//            return CommandResponse.IMPROVEMENT_EXISTS;
//        }
//        if (isPossibleToBuildInThisTerrain(currentCivilization, improvement)) {
//            return CommandResponse.YOU_HAVE_NOT_REQUIRED_OPTIONS;
//        }
        return CommandResponse.OK;
    }

    private boolean isPossibleToBuildInThisTerrain(Civilization civilization, ImprovementEnum improvement) {
//        return improvement.canBeBuiltOn(civilization.getCurrentTile().getTerrain().getFeatures()) && improvement.hasRequiredTechs(civilization.getTechnologies());
        return true;
    }

    private void validateTileForFoundingCity(Tile currentTile, Civilization civilization) throws CommandException {
        if (currentTile.getNonCombatUnit() == null || currentTile.getNonCombatUnit().getType() != UnitEnum.SETTLER) {
            throw new CommandException(CommandResponse.NO_SETTLER_ON_TILE);
        }
        if (!isPossibleToBuildCity(currentTile)) {
            throw new CommandException(CommandResponse.IMPOSSIBLE_CITY);
        }
    }

    private boolean isPossibleToBuildCity(Tile tile) {
        for (Tile neighbor : GameController.getGame().getTileGrid().getAllTilesInRadius(tile, Constants.CITY_DISTANCE)) {
            if (neighbor.getCity() != null) return false;
        }
        return true;

    }

//    public void unitGarrison(Command command) {
//        Civilization civilization = getCurrentCivilization();
//        Tile currentTile = getCurrentTile();
//        CommandResponse response = validateForGarrison(currentTile, civilization);
//        if (response.isOK()) {
//            System.out.println(GameController.garrisonUnit(currentTile, civilization));
//        } else {
//            System.out.println(response);
//        }
//    }

//    private CommandResponse validateForGarrison(Tile currentTile, Civilization civilization) {
//        if (currentTile.getCombatUnit() == null) {
//            return CommandResponse.UNIT_DOES_NOT_EXISTS;
//        }
//        if (!(civilization.getCurrentTile().getCombatUnit().getCivilization() == civilization)) {
//            return CommandResponse.NOT_HAVING_UNIT;
//        }
//        if (!(civilization.getCurrentTile().getCity() == null)) {
//            return CommandResponse.CITY_DOES_NOT_EXISTS;
//        }
//        return CommandResponse.OK;
//
//    }

    public String unitFortify(Unit selectedUnit, Command command) throws CommandException {
        if (command.getSubSubCategory().equals("heal")) {
            return GameController.fortifyHealUnit(selectedUnit);
        } else if (command.getSubSubCategory().trim().equals("")) {
            return GameController.fortifyUnit(selectedUnit);
        }
        throw new CommandException(CommandResponse.INVALID_COMMAND);
    }

    public void unitMoveTo(Unit unit, Location location) throws CommandException {
        Civilization currentCivilization = GameController.getGame().getCurrentCivilization();
        Location currentGridLocation = currentCivilization.getCurrentGridLocation();
        Tile currentTile = currentCivilization.getRevealedTileGrid().getTile(currentGridLocation);
        validateTileForMovingUnit(location, unit);
        moveUnit(location, currentTile, currentCivilization, unit);
    }

    private void validateTileForMovingUnit(Location location, Unit unit) throws CommandException {
        if (!GameController.getGame().getTileGrid().isLocationValid(location)) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
        if (location == unit.getLocation()) throw new CommandException(CommandResponse.UNIT_IS_ALREADY_ON_TILE);
        Tile nextTile = GameController.getGame().getTileGrid().getTile(location);
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
        if (!isJungleExists(currentTile)) {
            return CommandResponse.JUNGLE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }

    private boolean isJungleExists(Tile currentTile) {
        //TODO : complete
        return currentTile.getTerrain().getFeatures().contains(TerrainEnum.JUNGLE);
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
        ArrayList<ImprovementEnum> tileImprovements = currentTile.getTerrain().getImprovements();
        if (!tileImprovements.contains(ImprovementEnum.ROAD) && !tileImprovements.contains(ImprovementEnum.RAILROAD)) {
            return CommandResponse.ROUTE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }

    public City foundCity(Location location) throws CommandException {
        Civilization currentCivilization = GameController.getGame().getCurrentCivilization();
        Tile tile = GameController.getGame().getTileGrid().getTile(location);
        validateTileForFoundingCity(tile, currentCivilization);
        return GameController.foundCity(currentCivilization, currentCivilization.getCurrentGridLocation());
    }
}
