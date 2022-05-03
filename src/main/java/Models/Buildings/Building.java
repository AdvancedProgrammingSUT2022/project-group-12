package Models.Buildings;

import Enums.BuildingEnum;
import Enums.TechnologyEnum;
import Models.Cities.City;
import Models.Production;

import java.util.List;

public class Building extends Production {
    private final int cost;
    private final int maintenance;
    private final List<TechnologyEnum> requiredTechs;
    private final List<BuildingEnum> requiredBuildings;
    private final BuildingEnum type;
    private final BuildingNotes note;

    public Building(BuildingEnum type) {
        this.cost = type.getCost();
        this.maintenance = type.getMaintenance();
        this.requiredTechs = type.getRequiredTechs();
        this.requiredBuildings = type.getRequiredBuildings();
        this.note = type.getNote();
        this.type=type;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public List<TechnologyEnum> getRequiredTechs() {
        return requiredTechs;
    }

    public List<BuildingEnum> getRequiredBuildings() {
        return requiredBuildings;
    }

    public BuildingNotes getNote() {
        return note;
    }

    public BuildingEnum getType() {
        return type;
    }

    @Override
    public void note(City city) {

    }
}
