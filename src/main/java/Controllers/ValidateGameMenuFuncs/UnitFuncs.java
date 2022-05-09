package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Enums.ImprovementEnum;
import Enums.TerrainEnum;
import Enums.UnitEnum;
import Enums.VisibilityEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.NonCombatUnit;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;

import static Controllers.MovingController.moveUnit;

public class UnitFuncs extends GameMenuFuncs {

    public UnitFuncs(Game game) {
        super(game);
    }

    protected static void validateForNonCombatUnit(NonCombatUnit nonCombatUnit,Civilization civilization) throws CommandException {
        if (!(nonCombatUnit == null)) {
            throw new CommandException(CommandResponse.UNIT_DOES_NOT_EXISTS);
        }
        if (nonCombatUnit.getCivilization() == civilization) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
    }

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
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.SETTLER)) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (!isPossibleToBuildCity(currentTile)) {
            throw new CommandException(CommandResponse.IMPOSSIBLE_CITY);
        }
    }

    private boolean isPossibleToBuildCity(Tile currentTile) {
        //TODO : need at least 4 tile around it to build the city
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

    public void unitFortify(Command command) {
//        Civilization civilization = getCurrentCivilization();
//        Tile currentTile = getCurrentTile();
//        try {
//            if (command.getSubSubCategory().equals("heal")) {
//                CommandResponse response = validateForCombatUnit(currentTile, civilization);
//                if (response.isOK()) {
//                    GameController.fortifyHealUnit(currentTile, civilization);
//                } else {
//                    System.out.println(response);
//                }
//            } else {
//                System.out.println(CommandResponse.INVALID_COMMAND);
//            }
//        } catch (Exception e) {
//            CommandResponse response = validateForCombatUnit(currentTile, civilization);
//            if (response.isOK()) {
//                System.out.println(GameController.fortifyUnit(currentTile, civilization));
//            } else {
//                System.out.println(response);
//            }
//        }
    }

    public void unitAlert() {
//        Civilization civilization = getCurrentCivilization();
//        Tile currentTile = getCurrentTile();
//        CommandResponse response = validateForCombatUnit(currentTile, civilization);
//        if (response.isOK()) {
//            System.out.println(GameController.AlertUnit(currentTile, civilization));
//        } else {
//            System.out.println(response);
//        }
    }

    public String unitMoveTo(Location location, String combatType) throws CommandException {
        Civilization currentCivilization = getCurrentCivilization();
        Location currentGridLocation = currentCivilization.getCurrentGridLocation();
        Tile currentTile = currentCivilization.getRevealedTileGrid().getTile(currentGridLocation);
        validateTileForMovingUnit(currentTile, currentCivilization, location, combatType);
        if (combatType.equals("noncombat")) {
            return moveUnit(location, currentTile, currentCivilization, currentTile.getNonCombatUnit());
        } else {
            return moveUnit(location, currentTile, currentCivilization, currentTile.getCombatUnit());
        }
    }

    private void validateTileForMovingUnit(Tile currentTile, Civilization civilization, Location location, String combatType) throws CommandException {
        isCorrectPosition(String.valueOf(location.getRow()), String.valueOf(location.getCol()));
        Tile nextTile = civilization.getRevealedTileGrid().getTile(location);
        if (combatType.equals("noncombat")) {
            if (nextTile.getNonCombatUnit() != null) {
                throw new CommandException(CommandResponse.TILE_IS_FULL);
            }
            if (!nextTile.getTerrain().getTerrainType().canBePassed() || nextTile.getState() == VisibilityEnum.FOG_OF_WAR) {
                throw new CommandException(CommandResponse.IMPOSSIBLE_MOVE);
            }
        } else {
            if (getGame().getTileGrid().getTile(location).getCombatUnit() != null) {
                throw new CommandException(CommandResponse.TILE_IS_FULL);
            }
            if (!nextTile.getTerrain().getTerrainType().canBePassed() || nextTile.getState() == VisibilityEnum.FOG_OF_WAR) {
                throw new CommandException(CommandResponse.IMPOSSIBLE_MOVE);
            }
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

    private CommandResponse validateTileForRemovingRoute(Tile currentTile, Civilization civilization) {
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!isExists(currentTile, ImprovementEnum.ROAD) && !isExists(currentTile, ImprovementEnum.RAILROAD)) {
            return CommandResponse.ROUTE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }

    protected boolean isExists(Tile currentTile, ImprovementEnum improvementEnum) {
        return currentTile.getTerrain().getImprovements().contains(improvementEnum);
    }

    public City foundCity(Location location) throws CommandException {
        Civilization currentCivilization = getCurrentCivilization();
        Tile tile = GameController.getGame().getTileGrid().getTile(location);
        validateTileForFoundingCity(tile, currentCivilization);
        return GameController.foundCity(currentCivilization, currentCivilization.getCurrentGridLocation());
    }
}
