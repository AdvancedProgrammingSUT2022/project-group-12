package Controllers;

import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.TechnologyEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class GameController {
    public final Game game;

    public GameController(Game newGame) {
        this.game = newGame;
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

    public static String BuildImprovement(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase() + " built successfully";
    }

    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {

        return "attack successfully happened";
    }

    public static void deleteNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
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

    public static String FoundCity(Tile currentTile) {
        return "city found successfully";
    }

    public static String garrsionUnit(Tile currentTile, Civilization civilization) {
        return "unit garrsioned successfully";
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
        return "unit sleeped successfully";
    }

<<<<<<< HEAD
    public static String sleepCombatUnit(Civilization currentCivilizaion, Tile currentTile) {

=======
    public static String sleepCombatUnit(Civilization currentCivilization, Tile currentTile) {
>>>>>>> 64e5a35a7f9e7a9ee77c8361ed48c26fa1879ec4
        return "unit sleeped successfully";
    }

    public static StringBuilder showCity(City city) {
        return null;
    }

    public static CommandResponse showCity(int parseInt, int parseInt1, Game game) {
        return null;
    }

    public static StringBuilder showNonCombatInfo(NonCombatUnit nonCombatUnit) {
        return null;
    }

    public static StringBuilder showCombatInfo(CombatUnit CombatUnit) {

        return null;
    }

    public static StringBuilder showResearchInfo(Tile currentTile, Civilization currentCivilization) {
       StringBuilder researchInfo=new StringBuilder("");
       HashMap<TechnologyEnum, Integer> technologies=new HashMap<>(currentCivilization.getResearchingTechnologies());
       TechnologyEnum currentTech=currentCivilization.getCurrentTech();
       researchInfo.append("Current research : "+currentTech);
       researchInfo.append("Science remains : "+technologies.get(currentTech)+"\n");
        for (Map.Entry<TechnologyEnum,Integer> tech:
             technologies.entrySet()) {
            researchInfo.append("research name :"+tech.getKey().name()+" science remains : "+tech.getValue()+"\n");
        }
        return researchInfo;
    }
    public static StringBuilder showCitiesInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showUnitsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
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
        StringBuilder militaryInfo=new StringBuilder("");
        ArrayList<>
        return null;
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDiplomaticInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static String moveNonCombatUnit(int x, int y, Tile currentTile, Civilization currentCivilization) {
        ArrayList<Tile> shortestPath=findTheShortestPath(x,y,currentTile);
        if(shortestPath == null){return "move is impossible";}
        currentTile.getNonCombatUnit().setPathShouldCross(shortestPath);
        moveToNextTile(currentTile.getNonCombatUnit());
        return "noncombat unit moved successfully";
    }

    private static void moveToNextTile(Unit unit) {
        unit.setRow(unit.getPathShouldCross().get(0).getRow());
        unit.setColumn(unit.getPathShouldCross().get(0).getCol());
        unit.getPathShouldCross().remove(0);
    }

    private static ArrayList<Tile> findTheShortestPath(int x, int y, Tile currentTile) {
        //TODO : find the shortest path
        return null;
    }

    public static String moveCombatUnit(int x, int y, Tile currentTile, Civilization currentCivilization) {
        ArrayList<Tile> shortestPath=findTheShortestPath(x,y,currentTile);
        if(shortestPath == null){return "move is impossible";}
        currentTile.getCombatUnit().setPathShouldCross(shortestPath);
        moveToNextTile(currentTile.getCombatUnit());
        return "combat unit moved successfully";
    }

    public CommandResponse battle(Civilization attacking, Civilization defending) {

        return CommandResponse.OK;
    }

    public CommandResponse movement(Unit moving) {
        return CommandResponse.OK;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
