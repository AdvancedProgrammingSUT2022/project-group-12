package Project.Enums;

import Project.App;
import Project.Models.Buildings.Building;
import Project.Models.Buildings.BuildingNotes;
import Project.Models.Cities.City;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public enum BuildingEnum {
    BARRACK(80, 1, new ArrayList<>(List.of(TechnologyEnum.BRONZE_WORKING)), new ArrayList<>(List.of()), BuildingNoteEnum.BARRACK_NOTE.getNote(), true, "-url-"),
    GRANARY(100, 1, new ArrayList<>(List.of(TechnologyEnum.POTTERY)), new ArrayList<>(List.of()), BuildingNoteEnum.GRANARY_NOTE.getNote(), false, "-url-"),
    LIBRARY(80, 1, new ArrayList<>(List.of(TechnologyEnum.WRITING)), new ArrayList<>(List.of()), BuildingNoteEnum.LIBRARY_NOTE.getNote(), false, "-url-"),
    MONUMENT(60, 1, new ArrayList<>(List.of()), new ArrayList<>(List.of()), BuildingNoteEnum.MONUMENT_NOTE.getNote(), true, "-url-"),
    WALLS(100, 1, new ArrayList<>(List.of(TechnologyEnum.MASONRY)), new ArrayList<>(List.of()), BuildingNoteEnum.WALLS_NOTE.getNote(), true, "-url-"),
    WATER_MILL(120, 2, new ArrayList<>(List.of(TechnologyEnum.THE_WHEEL)), new ArrayList<>(List.of()), BuildingNoteEnum.WATER_MILLS_NOTE.getNote(), false, "-url-"),
    ARMORY(130, 3, new ArrayList<>(List.of(TechnologyEnum.IRON_WORKING)), new ArrayList<>(List.of(BuildingEnum.BARRACK)), BuildingNoteEnum.ARMORY_NOTE.getNote(), true, "-url-"),
    BURIAL_TOMB(120, 0, new ArrayList<>(List.of(TechnologyEnum.PHILOSOPHY)), new ArrayList<>(List.of()), BuildingNoteEnum.BURIAL_TOMB_NOTE.getNote(), false, "-url-"),
    CIRCUS(150, 3, new ArrayList<>(List.of(TechnologyEnum.HORSEBACK_RIDING)), new ArrayList<>(List.of()), BuildingNoteEnum.CIRCUS_NOTE.getNote(), false, "-url-"),
    COLOSSEUM(150, 3, new ArrayList<>(List.of(TechnologyEnum.CONSTRUCTION)), new ArrayList<>(List.of()), BuildingNoteEnum.COLOSSEUM_NOTE.getNote(), false, "-url-"),
    COURT_HOUSE(200, 5, new ArrayList<>(List.of(TechnologyEnum.MATHEMATICS)), new ArrayList<>(List.of()), BuildingNoteEnum.COURTHOUSE_NOTE.getNote(), false, "-url-"),
    STABLE(100, 1, new ArrayList<>(List.of(TechnologyEnum.HORSEBACK_RIDING)), new ArrayList<>(List.of()), BuildingNoteEnum.STABLE_NOTE.getNote(), false, "-url-"),
    TEMPLE(120, 2, new ArrayList<>(List.of(TechnologyEnum.PHILOSOPHY)), new ArrayList<>(List.of(BuildingEnum.MONUMENT)), BuildingNoteEnum.TEMPLE_NOTE.getNote(), false, "-url-"),
    CASTLE(200, 3, new ArrayList<>(List.of(TechnologyEnum.CHIVALRY)), new ArrayList<>(List.of(BuildingEnum.WALLS)), BuildingNoteEnum.CASTLE_NOTE.getNote(), true, "-url-"),
    FORAGE(150, 2, new ArrayList<>(List.of(TechnologyEnum.METAL_CASTING)), new ArrayList<>(List.of()), BuildingNoteEnum.FORGE_NOTE.getNote(), false, "-url-"),
    GARDEN(120, 2, new ArrayList<>(List.of(TechnologyEnum.THEOLOGY)), new ArrayList<>(List.of()), BuildingNoteEnum.GARDEN_NOTE.getNote(), false, "-url-"),
    MARKET(120, 0, new ArrayList<>(List.of(TechnologyEnum.CURRENCY)), new ArrayList<>(List.of()), BuildingNoteEnum.MARKET_NOTE.getNote(), false, "-url-"),
    MINT(120, 0, new ArrayList<>(List.of(TechnologyEnum.CURRENCY)), new ArrayList<>(List.of()), BuildingNoteEnum.MINT_NOTE.getNote(), false, "-url-"),
    MONASTERY(120, 2, new ArrayList<>(List.of(TechnologyEnum.TECHNO)), new ArrayList<>(List.of()), BuildingNoteEnum.MONASTERY_NOTE.getNote(), true, "-url-"),
    UNIVERSITY(200, 3, new ArrayList<>(List.of(TechnologyEnum.EDUCATION)), new ArrayList<>(List.of(BuildingEnum.LIBRARY)), BuildingNoteEnum.UNIVERSITY_NOTE.getNote(), false, "-url-"),
    WORKSHOP(100, 2, new ArrayList<>(List.of(TechnologyEnum.METAL_CASTING)), new ArrayList<>(List.of()), BuildingNoteEnum.WORKSHOP_NOTE.getNote(), false, "-url-"),
    BANK(200, 0, new ArrayList<>(List.of(TechnologyEnum.BANKING)), new ArrayList<>(List.of(BuildingEnum.MARKET)), BuildingNoteEnum.BANK.getNote(), false, "-url-"),
    MILITARY_ACADEMY(350, 3, new ArrayList<>(List.of(TechnologyEnum.MILITARY_SCIENCE)), new ArrayList<>(List.of(BuildingEnum.BARRACK)), BuildingNoteEnum.MILITARY_ACADEMY_NOTE.getNote(), true, "-url-"),
    OPERA_HOUSE(220, 3, new ArrayList<>(List.of(TechnologyEnum.ACOUSTICS)), new ArrayList<>(List.of(BuildingEnum.TEMPLE, BuildingEnum.BURIAL_TOMB)), BuildingNoteEnum.OPERA_HOUSE_NOTE.getNote(), false, "-url-"),
    MUSEUM(350, 3, new ArrayList<>(List.of(TechnologyEnum.ARCHAEOLOGY)), new ArrayList<>(List.of(BuildingEnum.OPERA_HOUSE)), BuildingNoteEnum.MUSEUM_NOTE.getNote(), false, "-url-"),
    PUBLIC_SCHOOL(350, 3, new ArrayList<>(List.of(TechnologyEnum.SCIENTIFIC_THEORY)), new ArrayList<>(List.of(BuildingEnum.UNIVERSITY)), BuildingNoteEnum.PUBLIC_SCHOOL_NOTE.getNote(), false, "-url-"),
    SATRAPS_COURT(220, 0, new ArrayList<>(List.of(TechnologyEnum.BANKING)), new ArrayList<>(List.of(BuildingEnum.MARKET)), BuildingNoteEnum.SATRAPS_COURT_NOTE.getNote(), true, "-url-"),
    THEATER(300, 5, new ArrayList<>(List.of(TechnologyEnum.PRINTING_PRESS)), new ArrayList<>(List.of(BuildingEnum.COLOSSEUM)), BuildingNoteEnum.THEATER_NOTE.getNote(), false, "-url-"),
    WINDMILL(180, 2, new ArrayList<>(List.of(TechnologyEnum.ECONOMICS)), new ArrayList<>(List.of()), BuildingNoteEnum.WINDMILL_NOTE.getNote(), false, "-url-"),
    ARSENAL(350, 3, new ArrayList<>(List.of(TechnologyEnum.RAILROAD)), new ArrayList<>(List.of(BuildingEnum.MILITARY_ACADEMY)), BuildingNoteEnum.ARSENAL_NOTE.getNote(), true, "-url-"),
    BROADCAST_TOWER(600, 3, new ArrayList<>(List.of(TechnologyEnum.RADIO)), new ArrayList<>(List.of(BuildingEnum.MUSEUM)), BuildingNoteEnum.BROADCAST_TOWER_NOTE.getNote(), false, "-url-"),
    FACTORY(300, 3, new ArrayList<>(List.of(TechnologyEnum.STEAM_POWER)), new ArrayList<>(List.of()), BuildingNoteEnum.FACTORY_NOTE.getNote(), false, "-url-"),
    HOSPITAL(400, 2, new ArrayList<>(List.of(TechnologyEnum.BIOLOGY)), new ArrayList<>(List.of()), BuildingNoteEnum.HOSPITAL_NOTE.getNote(), false, "-url-"),
    MILITARY_BASE(450, 4, new ArrayList<>(List.of(TechnologyEnum.TELEGRAPH)), new ArrayList<>(List.of(BuildingEnum.CASTLE)), BuildingNoteEnum.MILITARY_BASE_NOTE.getNote(), true, "-url-"),
    STOCK_EXCHANGE(650, 0, new ArrayList<>(List.of(TechnologyEnum.ELECTRICITY)), new ArrayList<>(List.of(BuildingEnum.BANK)), BuildingNoteEnum.STOCK_EXCHANGE_NOTE.getNote(), false, "-url-");


    private final int cost;
    private final int maintenance;
    private final List<TechnologyEnum> requiredTechs;
    private final List<BuildingEnum> requiredBuildings;
    private final BuildingNotes note;
    private final boolean isCombatBuilding;
    private final String assetUrl;

    BuildingEnum(int cost, int maintenance, List<TechnologyEnum> requiredTechs, List<BuildingEnum> requiredBuildings, BuildingNotes buildingNotes, boolean isCombatBuilding, String assetUrl) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechs = requiredTechs;
        this.requiredBuildings = requiredBuildings;
        this.note = buildingNotes;
        this.isCombatBuilding = isCombatBuilding;
        this.assetUrl = assetUrl;
    }

    public static Building getBuildingEnumByName(String name) throws CommandException {
        for (BuildingEnum buildingEnum :
                BuildingEnum.values()) {
            if (buildingEnum.toString().equals(name)) {
                return new Building(buildingEnum);
            }
        }
        throw new CommandException(CommandResponse.NO_BUILDING_WITH_THIS_NAME);
    }

    public Image getImage() {
        return new Image(App.class.getResource("/images/assets/buildings/" + assetUrl).toExternalForm());
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

    public BuildingNotes<City> getNote() {
        return note;
    }
}