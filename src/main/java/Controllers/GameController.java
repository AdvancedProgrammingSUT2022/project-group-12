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

import java.util.*;


public class GameController {
    public static Game game;

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
        CombatUnit enemyUnit=game.getTileGrid().getTile(row, col).getCombatUnit();
        if(enemyUnit != null && enemyUnit.getCiv() != civilization)return true;
        return false;
    }
    protected static boolean isNonCombatEnemyExists(int row, int col, Civilization civilization) {
        NonCombatUnit enemyUnit=game.getTileGrid().getTile(row, col).getNonCombatUnit();
        if(enemyUnit != null && enemyUnit.getCiv() != civilization)return true;
        return false;
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

    public static String FoundCity(Tile currentTile) {
        return "city found successfully";
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
        showCurrentTech(researchInfo, technologies, currentTech);
        showOtherTechs(researchInfo, technologies);
        return researchInfo;
    }
    private static void showCurrentTech(StringBuilder researchInfo, HashMap<TechnologyEnum, Integer> technologies, TechnologyEnum currentTech) {
        researchInfo.append("Current research : "+ currentTech);
        researchInfo.append("Science remains : "+ technologies.get(currentTech)+"\n");
    }

    private static void showOtherTechs(StringBuilder researchInfo, HashMap<TechnologyEnum, Integer> technologies) {
        for (Map.Entry<TechnologyEnum,Integer> tech:
             technologies.entrySet()) {
            researchInfo.append("research name :"+tech.getKey().name()+" science remains : "+tech.getValue()+"\n");
        }
    }

    public static StringBuilder showCitiesInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showUnitsInfo(Civilization currentCivilization) {
        StringBuilder unitsInfo=new StringBuilder("");
        ArrayList<CombatUnit> combatUnits=currentCivilization.getCombatUnits();
        ArrayList<NonCombatUnit> nonCombatUnits=currentCivilization.getNonCombatUnits();
        showCombatUnits(unitsInfo, combatUnits);
        showNonCombatUnits(unitsInfo,nonCombatUnits);
        return unitsInfo;
    }
    private static void showNonCombatUnits(StringBuilder unitsInfo, ArrayList<NonCombatUnit> nonCombatUnits) {
        /***
         * in this function we are going to sort by name
         */
        Collections.sort(nonCombatUnits,new Comparator<NonCombatUnit>(){
            public int compare(NonCombatUnit nonCombatUnit1,NonCombatUnit nonCombatUnit2){
                return nonCombatUnit1.getType().name().compareTo(nonCombatUnit2.getType().name());
            }

        });
        for (NonCombatUnit nonCombatEnum:
                nonCombatUnits) {
            StringBuilder nonCombatName=new StringBuilder("nonCombat name : "+nonCombatEnum.getType().name());
            StringBuilder nonCombatStrength=new StringBuilder("Strength : -");
            StringBuilder movementPoint=new StringBuilder("MovementPoint : "+nonCombatEnum.getMovement()+"/"+nonCombatEnum.getType().getMovement());
            unitsInfo.append(nonCombatName+" "+nonCombatStrength+" "+movementPoint+'\n');
        }
    }
    private static void showCombatUnits(StringBuilder unitsInfo, ArrayList<CombatUnit> combatUnits) {
        /***
         * in this function we are
         */
        Collections.sort(combatUnits,new Comparator<CombatUnit>(){

            public int compare(CombatUnit combatUnit1,CombatUnit combatUnit2){
                return combatUnit1.getType().name().compareTo(combatUnit2.getType().name());
            }

        });
        for (CombatUnit combatEnum:
                combatUnits) {
            StringBuilder combatName=new StringBuilder("combat name : "+combatEnum.getType().name());
            StringBuilder combatStrength=new StringBuilder("Strength : "+combatEnum.getCombatStrength());
            StringBuilder movementPoint=new StringBuilder("MovementPoint : "+combatEnum.getMovement()+"/"+combatEnum.getType().getMovement());
            unitsInfo.append(combatName+" "+combatStrength+" "+movementPoint+'\n');
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
        StringBuilder militaryInfo=new StringBuilder("");
        ArrayList<CombatUnit> combatType=currentCivilization.getCombatUnits();

        return null;
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDiplomaticInfo(Game game,Civilization currentCivilization) {
        StringBuilder diplomaticInfo=new StringBuilder("");
        ArrayList<Civilization> inWarWith=currentCivilization.getIsInWarWith();
        for (Civilization civ:
             game.getCivs()) {
          if(civ == currentCivilization) continue;
          diplomaticInfo.append("civilization name : "+civ.getName()+" state : ");
          if(currentCivilization.isInWarWith(civ)){diplomaticInfo.append("WAR!!\n");}
          else {diplomaticInfo.append("Neutral\n");}
        }
        return diplomaticInfo;
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }



    public static void deleteNonCombatUnit(Civilization currentCivilization, Tile currentTile) {
        // todo, dummy function
    }

    public static Boolean BuildImprovement(Tile currentTile, ImprovementEnum stoneMine) {
        // todo, dummy function
        return null;
    }

    public CommandResponse battle(Civilization attacking, Civilization defending) {

        return CommandResponse.OK;
    }

    public CommandResponse movement(Unit moving) {
        return CommandResponse.OK;
    }

    public Game getGame() {
        return game;
    }

    public TileGrid getGameTileGrid() {
        return game.getTileGrid();
    }
}
