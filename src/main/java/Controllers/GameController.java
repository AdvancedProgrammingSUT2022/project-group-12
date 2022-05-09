package Controllers;

import Enums.ImprovementEnum;
import Enums.ResourceEnum;
import Enums.TechnologyEnum;
import Enums.TerrainEnum;
import Models.Buildings.Building;
import Models.Cities.City;
import Models.Citizen;
import Models.Civilization;
import Models.Game;
import Models.Location;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.RangedUnit;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class GameController {
    protected static Game game;

    public GameController(Game newGame) {
        game = newGame;
    }

    public static String RepairTile(Tile currentTile) {
        // todo : complete
        return "Tile repaired successfully";
    }

    public static String RemoveRoute(Tile currentTile, ImprovementEnum improvementEnum) {
        return "route removed successfully";
    }

    public static String RemoveJungle(Tile currentTile) {
        return "Jungle removed successfully";
    }

    public static String BuildImprovment(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase() + " built successfully";
    }


    protected static boolean isEnemyExists(int row, int col, Civilization civilization) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(row, col).getCombatUnit();
        return enemyUnit != null && enemyUnit.getCiv() != civilization;
    }

    protected static boolean isEnemyExists(Location location, Civilization civilization) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(location).getCombatUnit();
        return enemyUnit != null && enemyUnit.getCiv() != civilization;
    }

    protected static boolean isEnemyIsReadyForAttack(int row, int col, Civilization civilization, CombatUnit combatUnit) {
        CombatUnit enemyUnit = game.getTileGrid().getTile(row, col).getCombatUnit();
        NonCombatUnit nonCombatEnemyUnit = game.getTileGrid().getTile(row, col).getNonCombatUnit();

        if (combatUnit instanceof RangedUnit) {
            return isEnemyExists(row, col, civilization) || isNonCombatEnemyExists(row, col, civilization);
        } else {
            return isEnemyExists(row, col, civilization);
        }
    }

    protected static boolean isAttackToCity(int row, int col, Civilization civilization) {
        City enemyCity = game.getTileGrid().getTile(row, col).getCity();
        return enemyCity != null;
    }

    protected static boolean isNonCombatEnemyExists(int row, int col, Civilization civilization) {
        NonCombatUnit enemyUnit = game.getTileGrid().getTile(row, col).getNonCombatUnit();
        return enemyUnit != null && enemyUnit.getCiv() != civilization;
    }

    protected static boolean isNonCombatEnemyExists(Location location, Civilization civilization) {
        NonCombatUnit enemyUnit = game.getTileGrid().getTile(location).getNonCombatUnit();
        return enemyUnit != null && enemyUnit.getCiv() != civilization;
    }

    public static void deleteMonCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static void deleteCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static void wakeUpNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static void wakeUpCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static void CancelMissionNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static void CancelMissionCombatUnit(Civilization currentCivilization, Tile currentTile) {
    }

    public static City foundCity(Civilization civ, Location location) throws CommandException {
        checkConditionsForCity(location, civ, game.getTileGrid().getTile(location));
        Tile currentTile = game.getTileGrid().getTile(location);
        int cityProduction = 1 + currentTile.calculateProductionCount();
        int food = 2 + currentTile.calculateFoodCount();
        boolean isCapital = civ.getCapital() == null;
        //todo : get a name for city from user
        ArrayList<Tile> assignedTiles = game.getTileGrid().getAllTilesInRadius(currentTile, 1);
        City city = new City("City1", assignedTiles, civ, currentTile, isCapital);
        for (Tile tile : assignedTiles) tile.setCivilization(civ);
        currentTile.setCity(city);
        civ.addCity(city);
        currentTile.setNonCombatUnit(null);
        if (isCapital) civ.setCapital(city);
        checkForResource(currentTile, city, civ);
        return city;
    }

    private static void checkConditionsForCity(Location location, Civilization civilization, Tile tile) throws CommandException {
        checkForHavingEnoughTiles(location, civilization, tile);
    }

    private static void checkForResource(Tile tile, City newCity, Civilization civ) {
        //Todo : fekre bishtar
        ResourceEnum cityResource = tile.getTerrain().getResource();
        if (cityResource != null) {
            if (civ.isPossibleToHaveThisResource(cityResource)) {
                newCity.getResources().add(cityResource);
            } else {
                newCity.setReservedResource(cityResource);
            }
        }
    }


    private static void checkForHavingEnoughTiles(Location location, Civilization civilization, Tile cityTile) throws CommandException {
        ArrayList<Tile> checkTiles = game.getTileGrid().getAllTilesInRadius(cityTile, 4);
        for (Tile tile :
                checkTiles) {
            if (tile.getCity() != null) {
                throw new CommandException(CommandResponse.TILE_IS_FULL);
            }
        }
    }

    public static String garrisonUnit(Tile currentTile, Civilization civilization) {
        return "unit garrisoned successfully";
    }

    public static String fortifyUnit(Tile currentTile, Civilization civilization) {
        return "unit fortified successfully";
    }

    public static String fortifyHealUnit(Tile currentTile, Civilization civilization) {
        return "unit fortified successfully";
    }

    public static String AlertUnit(Tile currentTile, Civilization civilization) {
        return "unit alerted successfully";
    }

    public static String sleepNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
        return "unit slept successfully";
    }

    public static String sleepCombatUnit(Civilization currentCivilization, Tile currentTile) {

        return "unit slept successfully";
    }

    public static StringBuilder showCity(City city) {
        StringBuilder message = new StringBuilder();
        message.append("city citizens : " + city.getCitizensCount() + "\n");
        for (Building building :
                city.getBuildings()) {
            message.append("city building : " + building.getType().toString().toLowerCase() + '\n');
        }
        message.append("city combat unit : " + city.getCombatUnit() + '\n');
        message.append("city nonCombat unit : " + city.getNonCombatUnit() + '\n');
        for (Tile cityTile :
                city.getTiles()) {
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
        nonCombatUnit.getCiv().setCurrentTile(game.getTileGrid().getTile(nonCombatUnit.getLocation()));
        return null;
    }

    public static StringBuilder showCombatInfo(CombatUnit combatUnit) {
        /***
         * change current tile to combatUnit tile
         */
        combatUnit.getCiv().setCurrentTile(game.getTileGrid().getTile(combatUnit.getLocation()));
        return null;
    }

    public static StringBuilder showResearchInfo(Tile currentTile, Civilization currentCivilization) {
        StringBuilder researchInfo = new StringBuilder();
        HashMap<TechnologyEnum, Integer> technologies = new HashMap<>(currentCivilization.getResearchingTechnologies());
        TechnologyEnum currentTech = currentCivilization.getCurrentTech();
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
        ArrayList<CombatUnit> combatUnits = currentCivilization.getCombatUnits();
        ArrayList<NonCombatUnit> nonCombatUnits = currentCivilization.getNonCombatUnits();
        showCombatUnits(unitsInfo, combatUnits);
        showNonCombatUnits(unitsInfo, nonCombatUnits);
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

    public static StringBuilder showNotifInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showMilitaryInfo(Tile currentTile, Civilization currentCivilization) {
        StringBuilder militaryInfo = new StringBuilder();
        ArrayList<CombatUnit> combatType = currentCivilization.getCombatUnits();

        return null;
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDiplomaticInfo(Game game, Civilization currentCivilization) {
        StringBuilder diplomaticInfo = new StringBuilder();
        ArrayList<Civilization> inWarWith = currentCivilization.getIsInWarWith();
        for (Civilization civ : game.getCivs()) {
            if (civ == currentCivilization) continue;
            diplomaticInfo.append("civilization name : ").append(civ.getName()).append(" state : ");
            if (currentCivilization.isInWarWith(civ)) {
                diplomaticInfo.append("WAR!!\n");
            } else {
                diplomaticInfo.append("Neutral\n");
            }
        }
        return diplomaticInfo;
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }


    public static void deleteNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
        // todo, dummy function
    }

    public static void buildImprovement(Tile tile, Civilization civ, ImprovementEnum improvement) throws CommandException {
        // todo, validations
        throw new CommandException("error folan");
    }

    public static boolean checkForRivers(Tile tile, Tile tile1) {
        //TODO : check if is there a river or not
        return tile.getTerrain().getFeatures().contains(TerrainEnum.RIVER) && tile1.getTerrain().getFeatures().contains(TerrainEnum.RIVER);
    }

    public static Game getGame() {
        return game;
    }

    public static void cityAssignCitizen(City city, Location location) throws CommandException {
        if (city.getCitizensCount() <= city.getCitizens().size()) {
            throw new CommandException(CommandResponse.NO_UNASSIGNED_CITIZEN);
        }
        Tile tile = GameController.getGame().getTileGrid().getTile(location);
        if (tile.getCitizen() != null) throw new CommandException(CommandResponse.CITIZEN_ALREADY_WORKING_ON_TILE);
        tile.setCitizen(new Citizen(city));
    }
}
