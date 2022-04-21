package Controllers;

import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Tiles.TileGrid;
import Models.Units.Unit;


public class GameController {
    public final Game game;
    private Civilization player1;
    private Civilization player2;

    public GameController(Game newGame) {
        this.game = newGame;
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

    public static String RepairTile(Tile currentTile) {
        //tode : complete
        return "Tile repaired successfully";
    }

    public static String RemoveRoute(Tile currentTile,ImprovementEnum improvementEnum) {
       return  "route removed succesfully";
    }

    public static String RemoveJungle(Tile currentTile) {
        return "Jungle removed succesfully";
    }



    public static String BuildImprovment(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase()+" built succesfully";
    }
    public static String AttackUnit(int row, int col, Game game,Tile currentTile,Civilization civilization) {

        return "attack successfully happened";
    }
   

    public static void deletenonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void deleteCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void wakeUpNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void wakeUpCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void CancelMissionNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void CancelMissionCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
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

    public static String sleepNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
        return "unit sleeped successfully";
    }

    public static String sleepCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
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
          return null;
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

    public static StringBuilder showNotifInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showMilitaryInfo(Tile currentTile, Civilization currentCivilization) {
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

    public static String moveNonCombatUnit(int parseInt, int parseInt1, Tile currentTile, Civilization currentCivilization) {
        return "noncombat unit moved successfully";

    }

    public static String moveCombatUnit(int parseInt, int parseInt1, Tile currentTile, Civilization currentCivilization) {
        //TODO : check the position
       return "combat unit moved successfully";
    }
}
