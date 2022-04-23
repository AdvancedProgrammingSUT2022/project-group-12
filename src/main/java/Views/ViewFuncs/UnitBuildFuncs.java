package Views.ViewFuncs;

import Controllers.GameController;
import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class UnitBuildFuncs extends UnitFuncs{


    public UnitBuildFuncs(Game game) {
        super(game);
    }

    public void unitBuildQuarry() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.STONE_MINE);
        if (response.isOK())
            System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.STONE_MINE));
        else System.out.println(response);
    }

    public void unitBuildPlantation() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.CULTIVATION);
        if (response.isOK())
            System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.CULTIVATION));
        else System.out.println(response);
    }

    public void unitBuildCamp() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.CAMP);
        if (response.isOK()) System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.CAMP));
        else System.out.println(response);
    }

    public void unitBuildPasture() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.PASTURE);
        if (response.isOK()) System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.PASTURE));
        else System.out.println(response);
    }

    public void unitBuildLumberMill() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.LUMBER_MILL);
        if (response.isOK())
            System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.LUMBER_MILL));
        else System.out.println(response);
    }

    public void unitBuildTradingPost() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.TRADING_POST);
        if (response.isOK())
            System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.TRADING_POST));
        else System.out.println(response);
    }

    public void unitBuildMine() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.MINE);
        if (response.isOK()) System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.MINE));
        else System.out.println(response);
    }

    public void unitBuildFarm() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.FARM);
        if (response.isOK()) {
            GameController.BuildImprovement(currentTile, ImprovementEnum.FARM);
            System.out.println("farm built successfully");
        } else {
            System.out.println(response);
        }
    }

    public void unitBuildRailRoad() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.RailRoad);
        if (response.isOK()) System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.RailRoad));
        else System.out.println(response);
    }

    public void unitBuildRoad() {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        CommandResponse response = isPossibleToBuild(currentTile, currentCivilization, ImprovementEnum.ROAD);
        if (response.isOK()) System.out.println(GameController.BuildImprovement(currentTile, ImprovementEnum.ROAD));
        else System.out.println(response);
    }

    private CommandResponse isPossibleToBuild(Tile currentTile, Civilization currentCivilization, ImprovementEnum improvement) {
        if (currentTile.getNonCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(currentCivilization.getCurrentTile().getNonCombatUnit().getCiv() == currentCivilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (isExists(currentTile, improvement)) {
            return CommandResponse.IMPROVEMENT_EXISTS;
        }
        if (isPossibleToBuildInThisTerrain(currentCivilization, improvement)) {
            return CommandResponse.YOU_HAVE_NOT_REQUIRED_OPTIONS;
        }
        return CommandResponse.OK;
    }
    private boolean isPossibleToBuildInThisTerrain(Civilization civilization, ImprovementEnum improvement) {
        if (improvement.hasRequiredTechs(civilization.getTechnologies())) {
            return false;
        }
        return !improvement.canBeBuiltOn(civilization.getCurrentTile().getTerrain().getFeatures());

    }
}
