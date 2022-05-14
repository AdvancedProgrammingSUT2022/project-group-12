package Controllers;

import Enums.*;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Citizen;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.RangedUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;

import java.util.*;


public class GameController {
    protected static Game game;

    public GameController(Game newGame) {
        game = newGame;
    }

    public static void unitRepairTile(Unit unit) throws CommandException {
        if (unit.getType() == UnitEnum.WORKER) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (!GameController.getGameTile(unit.getLocation()).isDamaged()) {
            throw new CommandException(CommandResponse.NOT_DAMAGED);
        }
        // todo : complete
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameController.game = game;
    }

    public static String RemoveRoute(Tile currentTile, ImprovementEnum improvementEnum) {
        return "route removed successfully";
    }

    public static String RemoveJungle(Tile currentTile) {
        return "Jungle removed successfully";
    }

    public static String BuildImprovement(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase() + " built successfully";
    }

    public static boolean isEnemyExists(int row, int col, Civilization civilization) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(row, col).getCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static boolean isEnemyIsReadyForAttack(Location location, Civilization civilization, CombatUnit combatUnit) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(location).getCombatUnit();
        NonCombatUnit nonCombatEnemyUnit = game.getTileGrid().getTile(location).getNonCombatUnit();

        if (combatUnit instanceof RangedUnit) {
            return isEnemyExists(location, civilization) || isNonCombatEnemyExists(location, civilization);
        } else {
            return isEnemyExists(location, civilization);
        }
    }

    public static boolean isEnemyExists(Location location, Civilization civilization) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(location).getCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static boolean isNonCombatEnemyExists(Location location, Civilization civilization) {
        NonCombatUnit enemyUnit = game.getTileGrid().getTile(location).getNonCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static boolean isEnemyIsReadyForAttack(Location location, Civilization civilization, City city) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(location).getCombatUnit();
        NonCombatUnit nonCombatEnemyUnit = game.getTileGrid().getTile(location).getNonCombatUnit();
        return isEnemyExists(location, civilization) || isNonCombatEnemyExists(location, civilization);
    }

    public static boolean isAttackToCity(Location location, Civilization civilization) {
        City enemyCity = game.getTileGrid().getTile(location).getCity();
        return (enemyCity != null && enemyCity.getCivilization() != civilization);
    }

    public static boolean isNonCombatEnemyExists(int row, int col, Civilization civilization) {
        NonCombatUnit enemyUnit = game.getTileGrid().getTile(row, col).getNonCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static void wakeUpUnit(Unit unit) throws CommandException {
        if (unit.getState() == UnitStates.ALERT || unit.getState() == UnitStates.SLEEP || unit.getState() == UnitStates.FORTIFY) {
            unit.setState(UnitStates.AWAKE);
        }
        throw new CommandException(CommandResponse.UNIT_IS_NOT_SLEEP);
    }

    public static void cancelMissionUnit(Unit unit) {
    }

    public static City foundCity(Unit unit) throws CommandException {
        Location location = unit.getLocation();
        Civilization civ = unit.getCivilization();
        Tile tile = getGame().getTileGrid().getTile(location);
        if (unit.getType() != UnitEnum.SETTLER) {
            throw new CommandException(CommandResponse.ONLY_SETTLERS_CAN_FOUND_CITY);
        }
        if (GameController.tileIsNearAnotherCity(tile)) {
            throw new CommandException(CommandResponse.CLOSE_TO_A_CITY);
        }
        int cityProduction = 1 + tile.calculateSources("production");
        int food = 2 + tile.calculateSources("food");
        boolean isCapital = civ.getCapital() == null;
        ArrayList<Tile> territoryTiles = game.getTileGrid().getAllTilesInRadius(tile, 1);
        String cityName = Constants.CITY_NAMES[new Random().nextInt(Constants.CITY_NAMES.length)];
        City city = new City(cityName, territoryTiles, civ, tile, isCapital);
        for (Tile neighbor : territoryTiles) neighbor.setCivilization(civ);
        tile.setCity(city);
        civ.addCity(city);
        Unit settler = tile.getNonCombatUnit();
        GameController.deleteUnit(settler);
        if (isCapital) civ.setCapital(city);
        return city;
    }

    private static boolean tileIsNearAnotherCity(Tile tile) {
        for (Tile neighbor : GameController.getGame().getTileGrid().getAllTilesInRadius(tile, Constants.CITY_DISTANCE)) {
            if (neighbor.getCity() != null) return true;
        }
        return false;
    }

    public static void deleteUnit(Unit unit) {
        Tile tile = GameController.getGameTile(unit.getLocation());
        tile.deleteUnit(unit);
        unit.getCivilization().removeUnit(unit);
    }

    public static String fortifyUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (unit.getState() == UnitStates.FORTIFY) {
            throw new CommandException(CommandResponse.UNIT_IS_FORTIFIED);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.FORTIFY);
        return "unit fortified successfully";
    }

    public static String fortifyHealUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (unit.getState() == UnitStates.FORTIFY_UNTIL_HEAL) {
            throw new CommandException(CommandResponse.UNIT_IS_FORTIFIED);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.FORTIFY_UNTIL_HEAL);
        return "unit fortified successfully";
    }

    public static void alertUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (unit.getState() == UnitStates.ALERT) {
            throw new CommandException(CommandResponse.UNIT_IS_NOT_SLEEP);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.ALERT);
    }

    public static void sleepUnit(Unit unit) throws CommandException {
        if (!(unit instanceof NonCombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
        if (unit.getState() == UnitStates.SLEEP) {
            throw new CommandException(CommandResponse.UNIT_IS_SLEEP);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.SLEEP);
    }

    public static StringBuilder showCity(City city) {
        StringBuilder message = new StringBuilder();
        message.append("city citizens : " + city.getCitizensCount() + "\n");
        for (Building building : city.getBuildings()) {
            message.append("city building : " + building.getType().toString().toLowerCase() + '\n');
        }
//        message.append("city combat unit : " + city.getCombatUnit() + '\n');
//        message.append("city nonCombat unit : " + city.getNonCombatUnit() + '\n');
        for (Tile cityTile : city.getTiles()) {
            if (cityTile.getCitizen() != null) {
                message.append("citizen is in tile with position " + cityTile.getLocation().getRow() + " " + cityTile.getLocation().getCol() + '\n');
            }
        }
        //todo : calculate food science production gold
        message.append("city food : " + city.getFood() + '\n');
        message.append("city science : " + city.getBeaker() + '\n');
        message.append("city production : " + city.getProduction() + '\n');
        message.append("city gold : " + city.getGold() + '\n');
        return message;
    }

    public static StringBuilder showNonCombatInfo(NonCombatUnit nonCombatUnit) {
        /***
         * change current tile to combatUnit tile
         */
        return null;
    }

    public static StringBuilder showCombatInfo(CombatUnit combatUnit) {
        /***
         * change current tile to combatUnit tile
         */
        return null;
    }

    public static StringBuilder showResearchInfo(Tile currentTile, Civilization currentCivilization) {
        StringBuilder researchInfo = new StringBuilder();
        HashMap<TechnologyEnum, Integer> technologies = new HashMap<>(currentCivilization.getResearchingTechnologies());
        TechnologyEnum currentTech = currentCivilization.getResearchingTechnology();
        showCurrentTech(researchInfo, technologies, currentTech);
        showOtherTechs(researchInfo, technologies);
        return researchInfo;
    }

    private static void showCurrentTech(StringBuilder researchInfo, HashMap<TechnologyEnum, Integer> technologies, TechnologyEnum currentTech) {
        researchInfo.append("Current research : ").append(currentTech);
        researchInfo.append("Science remains : ").append(technologies.get(currentTech)).append("\n");
    }

    private static void showOtherTechs(StringBuilder researchInfo, HashMap<TechnologyEnum, Integer> technologies) {
        for (Map.Entry<TechnologyEnum, Integer> tech : technologies.entrySet()) {
            researchInfo.append("research name :").append(tech.getKey().name()).append(" science remains : ").append(tech.getValue()).append("\n");
        }
    }

    public static StringBuilder showCitiesInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showUnitsInfo(Civilization currentCivilization) {
        StringBuilder unitsInfo = new StringBuilder();
        // todo: fix
//        ArrayList<CombatUnit> combatUnits = currentCivilization.getCombatUnits();
//        ArrayList<NonCombatUnit> nonCombatUnits = currentCivilization.getNonCombatUnits();
//        showCombatUnits(unitsInfo, combatUnits);
//        showNonCombatUnits(unitsInfo, nonCombatUnits);
        return unitsInfo;
    }

    private static void showNonCombatUnits(StringBuilder unitsInfo, ArrayList<NonCombatUnit> nonCombatUnits) {
        /***
         * in this function we are going to sort by name
         */
        nonCombatUnits.sort(Comparator.comparing(nonCombatUnit -> nonCombatUnit.getType().name()));
        for (NonCombatUnit nonCombatEnum : nonCombatUnits) {
            StringBuilder nonCombatName = new StringBuilder("nonCombat name : " + nonCombatEnum.getType().name());
            StringBuilder nonCombatStrength = new StringBuilder("Strength : -");
            StringBuilder movementPoint = new StringBuilder("MovementPoint : " + nonCombatEnum.getAvailableMoveCount() + "/" + nonCombatEnum.getType().getMovement());
            unitsInfo.append(nonCombatName + " " + nonCombatStrength + " " + movementPoint + '\n');
        }
    }

    private static void showCombatUnits(StringBuilder unitsInfo, ArrayList<CombatUnit> combatUnits) {
        /***
         * in this function we are
         */
        combatUnits.sort(Comparator.comparing(combatUnit -> combatUnit.getType().name()));
        for (CombatUnit combatEnum : combatUnits) {
            StringBuilder combatName = new StringBuilder("combat name : " + combatEnum.getType().name());
            StringBuilder combatStrength = new StringBuilder("Strength : " + combatEnum.getCombatStrength());
            StringBuilder movementPoint = new StringBuilder("MovementPoint : " + combatEnum.getAvailableMoveCount() + "/" + combatEnum.getType().getMovement());
            unitsInfo.append(combatName + " " + combatStrength + " " + movementPoint + '\n');
        }
    }

    public static StringBuilder showDiplomacyInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showVictoryInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDemographicsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showNotificationInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showMilitaryInfo(Tile currentTile, Civilization currentCivilization) {
        StringBuilder militaryInfo = new StringBuilder();
        // todo: fix
//        ArrayList<CombatUnit> combatType = currentCivilization.getCombatUnits();

        return null;
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDiplomaticInfo(Game game, Civilization currentCivilization) {
        StringBuilder diplomaticInfo = new StringBuilder();
        ArrayList<Civilization> inWarWith = currentCivilization.getIsInWarWith();
        ArrayList<Civilization> economicPartnership = currentCivilization.economicRelations();
        for (Civilization civ : game.getCivilizations()) {
            if (civ == currentCivilization) continue;
            diplomaticInfo.append("civilization name : ").append(civ.getName()).append(" state : ");
            if (currentCivilization.isInWarWith(civ)) {
                diplomaticInfo.append("WAR!!\n");
            } else if (currentCivilization.isFriendWith(civ)) {
                diplomaticInfo.append("Economic relations\n");
            }
        }
        return diplomaticInfo;
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static void buildImprovement(Unit unit, ImprovementEnum improvement) throws CommandException {
        Tile tile = GameController.getGameTile(unit.getLocation());
        if (unit.getType() != UnitEnum.WORKER) {
            throw new CommandException(CommandResponse.WRONG_UNIT);
        }
//        if (improvement.hasRequiredTechs())
        if (tile.getImprovements().contains(improvement)) {
            throw new CommandException(CommandResponse.IMPROVEMENT_EXISTS);
        }
//        if (isPossibleToBuildInThisTerrain(currentCivilization, improvement)) {
//            throw new CommandException(CommandResponse.YOU_HAVE_NOT_REQUIRED_OPTIONS);
//        }
    }

    private static boolean isPossibleToBuildInThisTerrain(Civilization civilization, ImprovementEnum improvement) {
        // todo: complete
        //        return improvement.canBeBuiltOn(civilization.getCurrentTile().getTerrain().getFeatures()) && improvement.hasRequiredTechs(civilization.getTechnologies());
        return true;
    }

    public static boolean checkForRivers(Tile tile, Tile tile1) {
        //TODO : check if is there a river or not
        return tile.getTerrain().getFeatures().contains(TerrainEnum.RIVER) && tile1.getTerrain().getFeatures().contains(TerrainEnum.RIVER);
    }

    public static void cityAssignCitizen(City city, Location location) throws CommandException {
        if (city.getCitizensCount() <= city.getCitizens().size()) {
            throw new CommandException(CommandResponse.NO_UNASSIGNED_CITIZEN);
        }
        Tile tile = GameController.getGameTile(location);
        if (!city.getTiles().contains(tile)) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() != null) throw new CommandException(CommandResponse.CITIZEN_ALREADY_WORKING_ON_TILE);
        tile.setCitizen(new Citizen(city));
    }

    public static void cityUnassignCitizen(City city, Location location) throws CommandException {
        Tile tile = GameController.getGameTile(location);
        if (!city.getTiles().contains(tile)) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() == null) throw new CommandException(CommandResponse.NO_CITIZEN_ON_TILE);
        tile.setCitizen(null);
    }

    public static void cityCitizenSetLock(City city, Location location, boolean lock) throws CommandException {
        Tile tile = GameController.getGameTile(location);
        if (tile.getCity() != city) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() == null) throw new CommandException(CommandResponse.NO_CITIZEN_ON_TILE);
        tile.getCitizen().setLock(lock);
    }

    public static void cityBuyTile(City city, Location location) throws CommandException {
        if (city.getCivilization().getGold() < Constants.TILE_COST) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        }
        TileGrid tileGrid = GameController.getGame().getTileGrid();
        Tile tile = tileGrid.getTile(location);
        if (tile.getCity() != null) throw new CommandException(CommandResponse.ALREADY_FOR_A_CITY);
        boolean isNeighbor = false;
        for (Tile neighbor : tileGrid.getAllTilesInRadius(tile, 1)) {
            if (city.getTiles().contains(neighbor)) {
                isNeighbor = true;
                break;
            }
        }
        if (!isNeighbor) throw new CommandException(CommandResponse.NOT_ADJACENT_TO_CITY_TERRITORY);
        city.getCivilization().addGold(-Constants.TILE_COST);
        city.addTile(tile);
        tile.setCity(city);
    }


    public static void cityBuyUnit(City city, UnitEnum unitEnum) throws CommandException {
        Civilization civ = city.getCivilization();
        if (civ.getGold() < unitEnum.calculateGoldCost()) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        }
        civ.addGold(-unitEnum.calculateGoldCost());
        Tile tile = city.getTile();
        Unit unit = Unit.constructUnitFromEnum(unitEnum, civ, city.getLocation());
        tile.placeUnit(unit);
    }

    public static void cityBuildUnit(City city, UnitEnum unitEnum) throws CommandException {
        if (city.getCivilization().getGold() < unitEnum.getProductionCost()) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        }
        Unit unit = Unit.constructUnitFromEnum(unitEnum, city.getCivilization(), city.getLocation());
        unit.setRemainedProduction(unitEnum.getProductionCost());
        city.addToProductionQueue(unit);
    }

    public static City selectCityByPosition(Civilization civ, Location location) throws CommandException {
        if (!GameController.getGame().getTileGrid().isLocationValid(location)) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
        City city = GameController.getGameTile(location).getCity();
        if (city == null || city.getCivilization() != civ) {
            throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
        }
        return city;
    }

    public static City selectCityByName(Civilization civ, String name) throws CommandException {
        for (City city : civ.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }

    public static Tile getGameTile(Location location) {
        return GameController.getGame().getTileGrid().getTile(location);
    }
}