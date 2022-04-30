package Controllers.ValidateGameMenuFuncs;

import Controllers.GameController;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class InfoFuncs extends GameMenuFuncs {

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
        System.out.println(GameController.showDiplomaticInfo(game, currentCivilization));
    }

    public void ecoInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showEcoInfo(currentTile, currentCivilization);
    }

    public void militaryInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
    }

    public void notifInfo() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        GameController.showNotifInfo(currentTile, currentCivilization);
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
        GameController.showUnitsInfo(currentCivilization);
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