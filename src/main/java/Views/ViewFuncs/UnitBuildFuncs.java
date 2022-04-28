package Views.ViewFuncs;

import Controllers.GameController;
import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.UnitEnum;
import Exceptions.CommandException;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class UnitBuildFuncs extends UnitFuncs {


    public UnitBuildFuncs(Game game) {
        super(game);
    }

    public void unitBuild(ImprovementEnum improvement) {
        Tile currentTile = getCurrentTile();
        Civilization currentCivilization = getCurrentCivilization();
        try {
            GameController.buildImprovement(currentTile, currentCivilization, improvement);
        } catch (CommandException e) {
            e.print();
        }
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
