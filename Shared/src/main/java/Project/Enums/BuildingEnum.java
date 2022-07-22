package Project.Enums;

import Project.Models.Buildings.Building;

import java.util.ArrayList;
import java.util.List;

public enum BuildingEnum {
    BARRACK(80, 1, new ArrayList<>(List.of(TechnologyEnum.BRONZE_WORKING)), new ArrayList<>(List.of()), true),
    GRANARY(100, 1, new ArrayList<>(List.of(TechnologyEnum.POTTERY)), new ArrayList<>(List.of()), false),
    LIBRARY(80, 1, new ArrayList<>(List.of(TechnologyEnum.WRITING)), new ArrayList<>(List.of()), false),
    MONUMENT(60, 1, new ArrayList<>(List.of()), new ArrayList<>(List.of()), true),
    WALLS(100, 1, new ArrayList<>(List.of(TechnologyEnum.MASONRY)), new ArrayList<>(List.of()), true),
    WATER_MILL(120, 2, new ArrayList<>(List.of(TechnologyEnum.THE_WHEEL)), new ArrayList<>(List.of()), false),
    ARMORY(130, 3, new ArrayList<>(List.of(TechnologyEnum.IRON_WORKING)), new ArrayList<>(List.of(BuildingEnum.BARRACK)), true),
    BURIAL_TOMB(120, 0, new ArrayList<>(List.of(TechnologyEnum.PHILOSOPHY)), new ArrayList<>(List.of()), false),
    CIRCUS(150, 3, new ArrayList<>(List.of(TechnologyEnum.HORSEBACK_RIDING)), new ArrayList<>(List.of()), false),
    COLOSSEUM(150, 3, new ArrayList<>(List.of(TechnologyEnum.CONSTRUCTION)), new ArrayList<>(List.of()), false),
    COURT_HOUSE(200, 5, new ArrayList<>(List.of(TechnologyEnum.MATHEMATICS)), new ArrayList<>(List.of()), false),
    STABLE(100, 1, new ArrayList<>(List.of(TechnologyEnum.HORSEBACK_RIDING)), new ArrayList<>(List.of()), false),
    TEMPLE(120, 2, new ArrayList<>(List.of(TechnologyEnum.PHILOSOPHY)), new ArrayList<>(List.of(BuildingEnum.MONUMENT)), false),
    CASTLE(200, 3, new ArrayList<>(List.of(TechnologyEnum.CHIVALRY)), new ArrayList<>(List.of(BuildingEnum.WALLS)), true),
    FORAGE(150, 2, new ArrayList<>(List.of(TechnologyEnum.METAL_CASTING)), new ArrayList<>(List.of()), false),
    GARDEN(120, 2, new ArrayList<>(List.of(TechnologyEnum.THEOLOGY)), new ArrayList<>(List.of()), false),
    MARKET(120, 0, new ArrayList<>(List.of(TechnologyEnum.CURRENCY)), new ArrayList<>(List.of()), false),
    MINT(120, 0, new ArrayList<>(List.of(TechnologyEnum.CURRENCY)), new ArrayList<>(List.of()), false),
    MONASTERY(120, 2, new ArrayList<>(List.of(TechnologyEnum.THEOLOGY)), new ArrayList<>(List.of()), true),
    UNIVERSITY(200, 3, new ArrayList<>(List.of(TechnologyEnum.EDUCATION)), new ArrayList<>(List.of(BuildingEnum.LIBRARY)), false),
    WORKSHOP(100, 2, new ArrayList<>(List.of(TechnologyEnum.METAL_CASTING)), new ArrayList<>(List.of()), false),
    BANK(200, 0, new ArrayList<>(List.of(TechnologyEnum.BANKING)), new ArrayList<>(List.of(BuildingEnum.MARKET)), false),
    MILITARY_ACADEMY(350, 3, new ArrayList<>(List.of(TechnologyEnum.MILITARY_SCIENCE)), new ArrayList<>(List.of(BuildingEnum.BARRACK)), true),
    OPERA_HOUSE(220, 3, new ArrayList<>(List.of(TechnologyEnum.ACOUSTICS)), new ArrayList<>(List.of(BuildingEnum.TEMPLE, BuildingEnum.BURIAL_TOMB)), false),
    MUSEUM(350, 3, new ArrayList<>(List.of(TechnologyEnum.ARCHAEOLOGY)), new ArrayList<>(List.of(BuildingEnum.OPERA_HOUSE)), false),
    PUBLIC_SCHOOL(350, 3, new ArrayList<>(List.of(TechnologyEnum.SCIENTIFIC_THEORY)), new ArrayList<>(List.of(BuildingEnum.UNIVERSITY)), false),
    SATRAPS_COURT(220, 0, new ArrayList<>(List.of(TechnologyEnum.BANKING)), new ArrayList<>(List.of(BuildingEnum.MARKET)), true),
    THEATER(300, 5, new ArrayList<>(List.of(TechnologyEnum.PRINTING_PRESS)), new ArrayList<>(List.of(BuildingEnum.COLOSSEUM)), false),
    WINDMILL(180, 2, new ArrayList<>(List.of(TechnologyEnum.ECONOMICS)), new ArrayList<>(List.of()), false),
    ARSENAL(350, 3, new ArrayList<>(List.of(TechnologyEnum.RAILROAD)), new ArrayList<>(List.of(BuildingEnum.MILITARY_ACADEMY)), true),
    BROADCAST_TOWER(600, 3, new ArrayList<>(List.of(TechnologyEnum.RADIO)), new ArrayList<>(List.of(BuildingEnum.MUSEUM)), false),
    FACTORY(300, 3, new ArrayList<>(List.of(TechnologyEnum.STEAM_POWER)), new ArrayList<>(List.of()), false),
    HOSPITAL(400, 2, new ArrayList<>(List.of(TechnologyEnum.BIOLOGY)), new ArrayList<>(List.of()), false),
    MILITARY_BASE(450, 4, new ArrayList<>(List.of(TechnologyEnum.TELEGRAPH)), new ArrayList<>(List.of(BuildingEnum.CASTLE)), true),
    STOCK_EXCHANGE(650, 0, new ArrayList<>(List.of(TechnologyEnum.ELECTRICITY)), new ArrayList<>(List.of(BuildingEnum.BANK)), false);


    private final int cost;
    private final int maintenance;
    private final List<TechnologyEnum> requiredTechs;
    private final List<BuildingEnum> requiredBuildings;
    private final boolean isCombatBuilding;

    BuildingEnum(int cost, int maintenance, List<TechnologyEnum> requiredTechs, List<BuildingEnum> requiredBuildings, boolean isCombatBuilding) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechs = requiredTechs;
        this.requiredBuildings = requiredBuildings;
        this.isCombatBuilding = isCombatBuilding;
    }

    public static Building getBuildingEnumByName(String name) {
        for (BuildingEnum buildingEnum :
                BuildingEnum.values()) {
            if (buildingEnum.toString().equals(name)) {
                Building building = new Building(buildingEnum);
                return building;
            }
        }
        return null;
    }


    public int getCost() {
        return this.cost;
    }

    public int getMaintenance() {
        return this.maintenance;
    }

    public boolean isCombatBuilding() {
        return isCombatBuilding;
    }

    public List<TechnologyEnum> getRequiredTechs() {
        return this.requiredTechs;
    }

    public String requiredTechName() {
        StringBuilder name = new StringBuilder();
        for (TechnologyEnum technologyEnum : requiredTechs)
            name.append(technologyEnum).append(" ");
        return name.toString();
    }

    public boolean checkIfHasRequiredTechs(List<TechnologyEnum> techList) {
        for (TechnologyEnum list : this.requiredTechs) {
            if (!techList.contains(list)) {
                return false;
            }
        }
        return true;
    }

    public List<BuildingEnum> getRequiredBuildings() {
        return requiredBuildings;
    }

}