package Project.Models.Buildings;

import Project.Models.Cities.Enums.BuildingEnum;
import Project.Models.Cities.Enums.TechnologyEnum;
import Project.Models.Cities.City;
import Project.Models.Production;

import java.util.List;

public class Building extends Production {

    private final int cost;
    private final int maintenance;
    private final List<TechnologyEnum> requiredTechs;
    private final List<BuildingEnum> requiredBuildings;
    private final BuildingEnum type;

    public Building(BuildingEnum type) {
        super(type.getCost());
        this.cost = type.getCost();
        this.maintenance = type.getMaintenance();
        this.requiredTechs = type.getRequiredTechs();
        this.requiredBuildings = type.getRequiredBuildings();
        this.type = type;
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

    public BuildingEnum getType() {
        return type;
    }


    @Override
    public void note(City city) {

    }
}
