package Models.Units;

import Enums.ImprovementEnum;
import Enums.UnitEnum;
import Enums.UnitStates;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;

public class NonCombatUnit extends Unit {
    private ImprovementEnum currentBuildingImprovement = null;
    private int remainingTime;

    public NonCombatUnit(UnitEnum type, Civilization civilization, Location location) {
        super(type, civilization, location);
    }

    public NonCombatUnit(UnitEnum type, Civilization civilization, Location location, int productionCost) {
        super(type, civilization, location, productionCost);
    }


    public UnitEnum getType() {
        return type;
    }

    public void setToBuildImprovement(ImprovementEnum improvement, Tile tile) {
        this.setState(UnitStates.WORKING);
        this.currentBuildingImprovement = improvement;
        this.remainingTime = improvement.getImprovementBuildRequiredTime(tile.getTerrain());
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void decreaseRemainingTime(int value) {
        this.remainingTime -= value;
    }

    public ImprovementEnum getCurrentBuildingImprovement() {
        return currentBuildingImprovement;
    }

    public void removeWork() {
        this.currentBuildingImprovement = null;
        this.setState(UnitStates.AWAKE);
    }
}
