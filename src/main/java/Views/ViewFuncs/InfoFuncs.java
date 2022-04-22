package Views.ViewFuncs;

import Controllers.GameController;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class InfoFuncs extends GameMemuFuncs{

    private  void dealsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDealsInfo(currentTile, currentCivilization);
    }

    private void diplomaticInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDiplomaticInfo(currentTile, currentCivilization);
    }

    private void ecoInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showEcoInfo(currentTile, currentCivilization);
    }

    private void militaryInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showMilitaryInfo(currentTile, currentCivilization);
    }

    private void notifInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showNotifInfo(currentTile, currentCivilization);
    }

    private void demographicsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDemographicsInfo(currentTile, currentCivilization);
    }

    private void victoryInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showVictoryInfo(currentTile, currentCivilization);
    }

    private void diplomacyInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showDiplomacyInfo(currentTile, currentCivilization);
    }

    private void unitsInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showUnitsInfo(currentTile, currentCivilization);
    }

    private void researchInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        System.out.println(GameController.showResearchInfo(currentTile, currentCivilization));
    }

    private void citiesInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        System.out.println(GameController.showCitiesInfo(currentTile, currentCivilization));
    }






}
