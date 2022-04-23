package Views.ViewFuncs;

import Controllers.GameController;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class InfoFuncs extends GameMemuFuncs{

    public InfoFuncs(Game game) {
        super(game);
    }

    public void dealsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDealsInfo(currentTile, currentCivilization);
    }

    public void diplomaticInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDiplomaticInfo(currentTile, currentCivilization);
    }

    public void ecoInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showEcoInfo(currentTile, currentCivilization);
    }

    public void militaryInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showMilitaryInfo(currentTile, currentCivilization);
    }

    public void notifInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showNotificationInfo(currentTile, currentCivilization);
    }

    public void demographicsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDemographicsInfo(currentTile, currentCivilization);
    }

    public void victoryInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showVictoryInfo(currentTile, currentCivilization);
    }

    public void diplomacyInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDiplomacyInfo(currentTile, currentCivilization);
    }

    public void unitsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showUnitsInfo(currentTile, currentCivilization);
    }

    public void researchInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        System.out.println(GameController.showResearchInfo(currentTile, currentCivilization));
    }

    public void citiesInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        System.out.println(GameController.showCitiesInfo(currentTile, currentCivilization));
    }






}
