package Server.Controllers;

import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Server.Models.Civilization;
import Server.Models.Game;
import Server.Utils.CommandException;

import java.util.*;



public class GameController {
    protected static Game game;

    public GameController(Game newGame) {
        game = newGame;
    }

    public static void unitRepairTile(Unit unit) throws CommandException {
        if (unit.getUnitType() != UnitEnum.WORKER) {
            throw new CommandException(CommandResponse.WRONG_UNIT, UnitEnum.WORKER.name());
        }
        if (unit.getState() != UnitStates.WORKING) {
            throw new CommandException(CommandResponse.WORKER_IS_ALREADY_WORKING);
        }
        if (!GameController.getGameTile(unit.getLocation()).isDamaged()) {
            throw new CommandException(CommandResponse.NOT_DAMAGED);
        }
        NonCombatUnit worker = (NonCombatUnit) unit;
        worker.setToRepairTile();
        GameController.getGameTile(unit.getLocation()).setDamaged(false);
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

    public static boolean isAttackableEnemyOn(Location location, Civilization civilization, CombatUnit combatUnit) {
        // for now, we assume ranged units CANNOT attack non combats
        return isEnemyExists(location, civilization);
//        if (combatUnit instanceof RangedUnit) {
//            return isEnemyExists(location, civilization) || isNonCombatEnemyExists(location, civilization);
//        } else {
//            return isEnemyExists(location, civilization);
//        }
    }

    public static boolean isEnemyExists(Location location, Civilization civilization) {
        CombatUnit enemyUnit = GameController.getGameTile(location).getCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static boolean isNonCombatEnemyExists(Location location, Civilization civilization) {
        NonCombatUnit enemyUnit = GameController.getGameTile(location).getNonCombatUnit();
        return enemyUnit != null && enemyUnit.getCivilization() != civilization;
    }

    public static boolean isAttackableEnemyOn(Location location, Civilization civilization, City city) {
        CombatUnit enemyUnit = GameController.getGameTile(location).getCombatUnit();
        NonCombatUnit nonCombatEnemyUnit = GameController.getGameTile(location).getNonCombatUnit();
        return isEnemyExists(location, civilization) || isNonCombatEnemyExists(location, civilization);
    }

    public static boolean isAnEnemyCityAt(Location location, Civilization civilization) {
        City enemyCity = GameController.getGameTile(location).getCity();
        return (enemyCity != null && enemyCity.getCivilization() != civilization);
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
        if (unit.getUnitType() != UnitEnum.SETTLER) {
            throw new CommandException(CommandResponse.ONLY_SETTLERS_CAN_FOUND_CITY);
        }
        if (GameController.tileIsNearAnotherCity(tile)) {
            throw new CommandException(CommandResponse.CLOSE_TO_A_CITY);
        }
        boolean isCapital = civ.getCapital() == null;
        ArrayList<Tile> territoryTiles = game.getTileGrid().getAllTilesInRadius(tile, 1);
        String cityName = Constants.CITY_NAMES[new Random().nextInt(Constants.CITY_NAMES.length)];
        City city = new City(cityName, territoryTiles, civ, tile, isCapital);
        for (Tile neighbor : territoryTiles) neighbor.setCivilization(civ);
        tile.setCity(city);
        civ.addCity(city);
        GameController.deleteUnit(unit);
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
        tile.setUnitNull(unit);
        unit.getCivilization().removeUnit(unit);
    }

    public static void fortifyUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT, "combat unit");
        }
        if (unit.getState() == UnitStates.FORTIFY) {
            throw new CommandException(CommandResponse.UNIT_IS_FORTIFIED);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.FORTIFY);
    }

    public static void pillageUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT, "combat unit");
        }
        Tile unitTile = GameController.getGameTile(unit.getLocation());
        if (unitTile.getImprovementsExceptRoadOrRailRoad().size() == 0) {
            throw new CommandException(CommandResponse.IMPROVEMENT_DOESNT_EXISTS);
        }
        if (unit.getAvailableMoveCount() <= 0) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_MOVEMENT_COUNT);
        }
        unitTile.setDamaged(true);
        unit.decreaseAvailableMoveCount(1);
        int gold = Math.min(unitTile.getCivilization().getGold(), 10);
        unit.getCivilization().addGold(gold);
        unitTile.getCivilization().addGold(-gold);
    }

    public static void fortifyHealUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT, "combat unit");
        }
        if (unit.getState() == UnitStates.FORTIFY_UNTIL_HEAL) {
            throw new CommandException(CommandResponse.UNIT_IS_FORTIFIED);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.FORTIFY_UNTIL_HEAL);
    }

    public static void alertUnit(Unit unit) throws CommandException {
        if (!(unit instanceof CombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT, "combat unit");
        }
        if (unit.getState() == UnitStates.ALERT) {
            throw new CommandException(CommandResponse.UNIT_IS_NOT_SLEEP);
        }
        unit.setPathShouldCross(null);
        unit.setState(UnitStates.ALERT);
    }

    public static void sleepUnit(Unit unit) throws CommandException {
        if (!(unit instanceof NonCombatUnit)) {
            throw new CommandException(CommandResponse.WRONG_UNIT, "noncombat unit");
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

    public static StringBuilder showUnitsInfo(Civilization currentCivilization) {
        StringBuilder unitsInfo = new StringBuilder();
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
        nonCombatUnits.sort(Comparator.comparing(nonCombatUnit -> nonCombatUnit.getUnitType().name()));
        for (NonCombatUnit nonCombatEnum : nonCombatUnits) {
            StringBuilder nonCombatName = new StringBuilder("nonCombat name : " + nonCombatEnum.getUnitType().name());
            StringBuilder nonCombatStrength = new StringBuilder("Strength : -");
            StringBuilder movementPoint = new StringBuilder("MovementPoint : " + nonCombatEnum.getAvailableMoveCount() + "/" + nonCombatEnum.getUnitType().getMovement());
            unitsInfo.append(nonCombatName + " " + nonCombatStrength + " " + movementPoint + '\n');
        }
    }

    private static void showCombatUnits(StringBuilder unitsInfo, ArrayList<CombatUnit> combatUnits) {
        /***
         * in this function we are
         */
        combatUnits.sort(Comparator.comparing(combatUnit -> combatUnit.getUnitType().name()));
        for (CombatUnit combatEnum : combatUnits) {
            StringBuilder combatName = new StringBuilder("combat name : " + combatEnum.getUnitType().name());
            StringBuilder combatStrength = new StringBuilder("Strength : " + combatEnum.getCombatStrength());
            StringBuilder movementPoint = new StringBuilder("MovementPoint : " + combatEnum.getAvailableMoveCount() + "/" + combatEnum.getUnitType().getMovement());
            unitsInfo.append(combatName + " " + combatStrength + " " + movementPoint + '\n');
        }
    }

    public static StringBuilder showDiplomaticInfo(Game game, Civilization currentCivilization) {
        StringBuilder diplomaticInfo = new StringBuilder();
        ArrayList<Civilization> inWarWith = currentCivilization.getInWarWith();
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

    public static ImprovementEnum buildImprovement(Unit unit) throws CommandException {
        Tile tile = GameController.getGameTile(unit.getLocation());
        if (unit.getUnitType() != UnitEnum.WORKER) {
            throw new CommandException(CommandResponse.WRONG_UNIT, UnitEnum.WORKER.name());
        }
        NonCombatUnit worker = (NonCombatUnit) unit;
        if (worker.getState() == UnitStates.WORKING) {
            throw new CommandException(CommandResponse.WORKER_IS_ALREADY_WORKING);
        }
        if (tile.getTerrain().getResource() == null) {
            throw new CommandException(CommandResponse.NO_RESOURCE_ON_TILE);
        }
        ImprovementEnum improvement = tile.getTerrain().getResource().getImprovementNeeded();
        if (tile.getImprovements().contains(improvement)) {
            throw new CommandException(CommandResponse.IMPROVEMENT_EXISTS);
        }
        Civilization civ = worker.getCivilization();
        for (TechnologyEnum tech : improvement.getRequiredTechs()) {
            if (!civ.getTechnologies().contains(tech)) {
                throw new CommandException(CommandResponse.DO_NOT_HAVE_REQUIRED_TECHNOLOGY, tech.name());
            }
        }
        worker.setToBuildImprovement(improvement, tile);
        return improvement;
    }

    public static boolean checkForRivers(Tile tile, Tile tile1) {
        // TODOLater : check if is there a river or not
        return tile.getTerrain().getFeatures().contains(FeatureEnum.RIVER) && tile1.getTerrain().getFeatures().contains(FeatureEnum.RIVER);
    }

    public static void cityAssignCitizen(City city, Location location) throws CommandException {
        if (city.getCitizensCount() <= city.getCitizens().size()) {
            throw new CommandException(CommandResponse.NO_UNASSIGNED_CITIZEN);
        }
        Tile tile = GameController.getGameTile(location);
        if (!city.getTiles().contains(tile)) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() != null) throw new CommandException(CommandResponse.CITIZEN_ALREADY_WORKING_ON_TILE);
        tile.setCitizen(new Citizen(city, tile.getLocation()));
    }

    public static void cityUnassignCitizen(City city, Location location) throws CommandException {
        Tile tile = GameController.getGameTile(location);
        if (!city.getTiles().contains(tile)) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() == null) throw new CommandException(CommandResponse.NO_CITIZEN_ON_TILE);
        city.getCitizens().remove(tile.getCitizen());
        tile.setCitizen(null);
    }

    public static void cityCitizenSetLock(City city, Location location, boolean lock) throws CommandException {
        Tile tile = GameController.getGameTile(location);
        if (!city.getTiles().contains(tile)) throw new CommandException(CommandResponse.NOT_YOUR_TERRITORY);
        if (tile.getCitizen() == null) throw new CommandException(CommandResponse.NO_CITIZEN_ON_TILE);
        tile.getCitizen().setLock(lock);
    }

    public static void cityBuyTile(City city, Location location) throws CommandException {
        if (city.getCivilization().calculateCivilizationGold() < Constants.TILE_COST) {
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

    public static void cityBuildBuilding(City selectedCity, BuildingEnum building) throws CommandException {
        Civilization civ = selectedCity.getCivilization();
        if (civ.calculateCivilizationGold() < building.getCost()) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        }
        if (!civ.hasRequierdTech(building.getRequiredTechs())) {
            throw new CommandException(CommandResponse.DO_NOT_HAVE_REQUIRED_TECHNOLOGY);
        }
        civ.addGold(-building.getCost());
        Tile tile = selectedCity.getTile();
        Building buildingClass = new Building(building);
        selectedCity.addBuilding(buildingClass);
    }


    public static void cityBuyUnit(City city, UnitEnum unitEnum) throws CommandException {
        Civilization civ = city.getCivilization();
        if (civ.calculateCivilizationGold() < unitEnum.calculateGoldCost()) {
            throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        }
        civ.addGold(-unitEnum.calculateGoldCost());
        Tile tile = city.getTile();
        Unit unit = Unit.constructUnitFromEnum(unitEnum, civ, city.getLocation());
        civ.addUnit(unit);
        tile.placeUnit(unit);
    }

    public static void cityBuildUnit(City city, UnitEnum unitEnum) throws CommandException {
        if (!city.getCivilization().getTechnologies().contains(unitEnum.getRequiredTech())) {
            throw new CommandException(CommandResponse.DO_NOT_HAVE_REQUIRED_TECHNOLOGY);
        }
        if (!city.getCivilization().containsResource(unitEnum.getRequiredResource())) {
            throw new CommandException(CommandResponse.DO_NOT_HAVE_REQUIRED_RESOURCE);
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
        if(location == null) return null;
        return GameController.getGame().getTileGrid().getTile(location);
    }


    public static boolean isEnemyCityExists(Location nextLocation, Civilization civilization) {
        return GameController.getGameTile(nextLocation).getCity() != null && GameController.getGameTile(nextLocation).getCity().getCivilization() != civilization;
    }


    public static void sendTradeRequest(String civName, String request, String suggest,String tradeName) throws CommandException {
        Trade trade;
        Civilization rivalCiv = game.getCivByName(civName);
        Civilization currentCiv = game.getCurrentCivilization();
        if (request.chars().allMatch(Character::isDigit)) {
                if(rivalCiv.haveThisMoney(Integer.parseInt(request)))
                trade = new Trade(currentCiv.getResourceByName(suggest), null, null, Integer.parseInt(request), game.getCurrentCivilization(), rivalCiv,tradeName);
                else throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
        } else {
            if (suggest.chars().allMatch(Character::isDigit)) {
                if(currentCiv.haveThisMoney(Integer.parseInt(suggest)))
                    trade = new Trade(null, Integer.parseInt(suggest), rivalCiv.getResourceByName(request), null, game.getCurrentCivilization(), rivalCiv,tradeName);
                else throw new CommandException(CommandResponse.NOT_ENOUGH_GOLD);
            } else {
                trade = new Trade(currentCiv.getResourceByName(suggest), null, rivalCiv.getResourceByName(request), null, game.getCurrentCivilization(), rivalCiv,tradeName);
            }
        }
        rivalCiv.addNotification(trade);
    }

    public static void rejectTrade(String tradeName) throws CommandException {
        Trade trade = game.getCurrentCivilization().getTradeByName(tradeName);
        trade.reject();
    }
    public static void acceptTrade(String tradeName) throws CommandException {
        Trade trade = game.getCurrentCivilization().getTradeByName(tradeName);
        trade.accept();
    }

    public static void createDemand(String request,String hostCiv,String demandName) {
        Civilization civ = GameController.getGame().getCivByName(hostCiv);
        if (request.chars().allMatch(Character::isDigit)){
            Demand demand = new Demand(Integer.parseInt(request),null,GameController.getGame().getCurrentCivilization().getName(),civ.getName(),demandName);
            civ.getNotifications().add(demand);
        } else {
            Demand demand = new Demand(null,request,GameController.getGame().getCurrentCivilization().getName(),civ.getName(),demandName);
            civ.getNotifications().add(demand);
        }
    }

    public static void acceptDemand(String demandName) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        currentCiv.getDemandByName(demandName).acceptDemand();
    }
    public static void rejectDemand(String demandName) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        currentCiv.getDemandByName(demandName).rejectDemand();
    }

    public static void declareWar(String currentCivName, String guestCivName,String name) {
        DeclareWar declareWar = new DeclareWar(currentCivName,guestCivName,name);
        declareWar.declareWar();
        GameController.getGame().getCivByName(guestCivName).addNotification(declareWar);
    }

    public static void seenDeclareWar(Civilization currentCivilization, String name) {
        currentCivilization.getDeclareWarByName(name).seenDeclareWar();
    }
    public static void createPeace(String currentCivName, String guestCivName,String name) {
        Peace peace = new Peace(currentCivName,guestCivName,name);
        GameController.getGame().getCivByName(guestCivName).addNotification(peace);
    }
    public static void acceptPeace(String demandName) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        Peace peace = currentCiv.getPeaceByName(demandName);
        peace.peace();
        currentCiv.removeNotification(peace);
    }
    public static void rejectPeace(String demandName) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        Peace peace = currentCiv.getPeaceByName(demandName);
        peace.rejectPeace();
        currentCiv.removeNotification(peace);
    }
}