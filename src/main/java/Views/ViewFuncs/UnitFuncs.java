package Views.ViewFuncs;

import Enums.GameEnums.ImprovementEnum;
import Models.Game;
import Models.Tiles.Tile;

public class UnitFuncs extends GameMemuFuncs {
    UnitFuncs(Game game) {
        super(game);
    }

    protected boolean isExists(Tile currentTile, ImprovementEnum improvementEnum) {
        return currentTile.getTerrain().getImprovements().contains(improvementEnum);
    }
}
