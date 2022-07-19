package Project.Models.Units;

import Project.Models.Cities.Enums.ImprovementEnum;
import Project.Models.Cities.Enums.UnitEnum;
import Project.Models.Cities.Enums.UnitStates;
import Project.Models.Location;
import Project.Models.Tiles.Tile;

public class NonCombatUnit extends Unit {
    private ImprovementEnum currentBuildingImprovement = null;
    private int remainingTime;
    String type;

    public NonCombatUnit(UnitEnum type, String civilizationName, Location location) {
        super(type, civilizationName, location);
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
