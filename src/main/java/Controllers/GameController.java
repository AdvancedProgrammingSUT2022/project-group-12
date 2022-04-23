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
    public static Game game; // todo: should be static?

    public GameController(Game newGame) {
        game = newGame;
    }

    public static String RepairTile(Tile currentTile) {
        // todo : complete
        return "Tile repaired successfully";
    }

    public static String RemoveRoute(Tile currentTile, ImprovementEnum improvementEnum) {
        return "route removed succesfully";
    }

    public static String RemoveJungle(Tile currentTile) {
        return "Jungle removed succesfully";
    }

    public static String BuildImprovment(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase() + " built succesfully";
    }

    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {

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

    public static StringBuilder showMilitaryInfo(Civilization currentCivilization) {
        StringBuilder unitsinfo=new StringBuilder("");
        ArrayList<CombatUnit> combatUnits=currentCivilization.getCombatUnits();
        ArrayList<NonCombatUnit> nonCombatUnits=currentCivilization.getNonCombatUnits();
        showCombatUnits(unitsinfo, combatUnits);
        showNonCombatUnits(unitsinfo, nonCombatUnits);
        return null;
    }

    private static void showNonCombatUnits(StringBuilder unitsinfo, ArrayList<NonCombatUnit> nonCombatUnits) {
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
            StringBuilder movmentPoint=new StringBuilder("MovementPoint : "+nonCombatEnum.getMovement()+"/"+nonCombatEnum.getType().getMovement());
            unitsinfo.append(nonCombatName+" "+nonCombatStrength+" "+movmentPoint+'\n');
        }
    }

    private static void showCombatUnits(StringBuilder unitsinfo, ArrayList<CombatUnit> combatUnits) {
        /***
         * in this function we are going to sort by name
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
            StringBuilder movmentPoint=new StringBuilder("MovementPoint : "+combatEnum.getMovement()+"/"+combatEnum.getType().getMovement());
            unitsinfo.append(combatName+" "+combatStrength+" "+movmentPoint+'\n');
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

    public static StringBuilder showUnitsInfo(Civilization currentCivilization) {
        StringBuilder militaryInfo=new StringBuilder("");
        //HashMap<UnitEnum, Integer> combatType=currentCivilization.getCombatUnits();

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

    private static ArrayList<Tile> findTheShortestPath(int targetRow, int targetCol, Tile sourceTile) { // use Coord/Location
        // Dijkstra algorithm for shortest path
        TileGrid tileGrid = game.getTileGrid();
        HashMap<Tile, Tile> parent = new HashMap<>();
        HashMap<Tile, Integer> distance = new HashMap<>();
        TreeMap<Integer, Tile> heap = new TreeMap<>();
        distance.put(sourceTile, 0);
        heap.put(0, sourceTile);
        Tile p;
        while (true) {
            Tile first = heap.pollFirstEntry().getValue();
            if (first.getRow() == targetRow && first.getCol() == targetCol) {
                p = first;
                break;
            }
            for (Tile neighbor : tileGrid.getNeighborsOf(first)) {
                // this is only true if weights are on tiles (graph vertexes)
                if (!distance.containsKey(neighbor)) {
                    int dist = distance.get(first) + neighbor.getTerrain().getMovementCost();
                    distance.put(neighbor, dist);
                    heap.put(dist, neighbor);
                    parent.put(neighbor, first);
                }
            }
        }
        ArrayList<Tile> path = new ArrayList<>();
        while (p != sourceTile) {
            path.add(p);
            p = parent.get(p);
        }
        return path;
    }

    public static String moveCombatUnit(int x, int y, Tile currentTile, Civilization currentCivilization) {
        ArrayList<Tile> shortestPath=findTheShortestPath(x,y,currentTile);
        if(shortestPath == null){return "move is impossible";}
        currentTile.getCombatUnit().setPathShouldCross(shortestPath);
        moveToNextTile(currentTile.getCombatUnit());
        return "combat unit moved successfully";
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
