package Project.Models.Units;

import Project.Enums.ImprovementEnum;
import Project.Enums.UnitEnum;
import Project.Enums.UnitStates;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Server.Models.Civilization;

public class NonCombatUnit extends Unit {
    private ImprovementEnum currentBuildingImprovement = null;
    private int remainingTime;
    String type;

    public NonCombatUnit(UnitEnum type, Civilization civilization, Location location) {
        super(type, civilization, location);
        this.type = this.getClass().getName();
    }



        public UnitEnum getUnitType() {
        return unitType;
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

    public void setToRepairTile() {
        this.setState(UnitStates.WORKING);
        this.remainingTime = 3;
    }
}
