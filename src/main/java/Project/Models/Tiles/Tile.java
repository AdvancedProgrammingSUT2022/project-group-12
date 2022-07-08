package Project.Models.Tiles;

import Project.Enums.FeatureEnum;
import Project.Enums.ImprovementEnum;
import Project.Enums.ResourceEnum;
import Project.Enums.VisibilityEnum;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Terrains.Terrain;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.CommandException;
import Project.Utils.CommandResponse;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final Hex hex;
    private final Location location;
    private final Terrain terrain;
    protected ArrayList<ImprovementEnum> improvements = new ArrayList<>();
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private int HP;
    private boolean isDamaged;
    private Civilization civilization;
    private City city;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private boolean hasRiver;
    private VisibilityEnum state;
    private Citizen citizen = null;

    public Tile(int multiply, Terrain terrain, Location tileLocation, String color) {
        //todo : initialize hex
        this.location = tileLocation;
        this.terrain = terrain;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.HP = 0;
        this.isDamaged = false;
        this.city = null;
        this.hasRoad = false;
        this.state = VisibilityEnum.FOG_OF_WAR;
        this.hex = new Hex(this, multiply, tileLocation.getCol(), tileLocation.getRow(), terrain.getTerrainType().getTerrainImageURL());
    }

    private Tile(Tile that) {
        this.hex = that.hex;
        this.location = that.location;
        this.terrain = that.terrain;
        this.combatUnit = that.combatUnit;
        this.nonCombatUnit = that.nonCombatUnit;
        this.HP = that.HP;
        this.isDamaged = that.isDamaged;
        this.city = that.city;
        this.hasRoad = that.hasRoad;
        this.state = that.state;
        this.civilization = that.civilization;
        this.citizen = that.citizen;
        this.improvements = that.improvements;
        this.hasRiver = that.hasRiver;
        this.hasRailRoad = that.hasRailRoad;
    }

    public void showUnit(Pane pane) {
        if (nonCombatUnit != null) {
            pane.getChildren().add(nonCombatUnit.createUnitGroup());
        }
        if (combatUnit != null) {
            pane.getChildren().add(nonCombatUnit.createUnitGroup());
        }
    }

    public double getXOnMap() {
        return hex.getPolygon().getLayoutX();
    }

    public double getYOnMap() {
        return hex.getPolygon().getLayoutY();
    }

    public Hex getHex() {
        return hex;
    }

    public boolean hasRoad() {
        return hasRoad;
    }

    public boolean hasRiver() {
        return this.hasRiver;
    }

    public void buildRoad() {
        this.hasRoad = true;
    }


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public VisibilityEnum getState() {
        return state;
    }

    public void setState(VisibilityEnum state) {
        this.state = state;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    private void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    private void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    // @NotNull reminds not to set tile units to null, directly
    public void placeUnit(@NotNull Unit unit) throws CommandException {
        if (unit == null) throw new RuntimeException();
        if (unit instanceof CombatUnit combatUnit) {
            if (this.getCombatUnit() != null)
                throw new CommandException(CommandResponse.COMBAT_UNIT_ALREADY_ON_TILE, this.getCombatUnit().getType().name());
            this.setCombatUnit(combatUnit);
        } else if (unit instanceof NonCombatUnit nonCombatUnit) {
            if (this.getNonCombatUnit() != null)
                throw new CommandException(CommandResponse.NONCOMBAT_UNIT_ALREADY_ON_TILE, this.getNonCombatUnit().getType().name());
            this.setNonCombatUnit(nonCombatUnit);
        }
    }


    public Civilization getCivilization() {
        return this.civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public Tile deepCopy() {
        return new Tile(this);
    }

    public void transferUnitTo(Unit unit, Tile that) {
        if (unit instanceof CombatUnit combatUnit) {
            this.setCombatUnit(null);
            that.setCombatUnit(combatUnit);
        } else if (unit instanceof NonCombatUnit nonCombatUnit) {
            this.setNonCombatUnit(null);
            that.setNonCombatUnit(nonCombatUnit);
        }
        unit.setLocation(that.getLocation());
    }

    public double calculateMovementCost() {
        double cost = terrain.getMovementCost();
        if (this.improvements.contains(ImprovementEnum.ROAD)) {
            cost *= (2.0 / 3);
        } else if (this.improvements.contains(ImprovementEnum.RAILROAD)) {
            cost /= 2;
        }
        return cost;
    }

    public int calculateSources(String name) {
        int production = this.terrain.getProductsCount();
        int food = this.terrain.getFoodCount();
        int gold = this.terrain.getGoldCount();
        ResourceEnum resourceEnum = this.getTerrain().getResource();
        if (this.isResourceAchieved(resourceEnum)) {
            production += resourceEnum.getProductsCount();
            food += resourceEnum.getFoodCount();
            gold += resourceEnum.getGoldCount();
        }
        for (FeatureEnum feature : terrain.getFeatures()) {
            production += feature.getProductsCount();
            food += feature.getFoodCount();
            gold += feature.getGoldCount();
        }
        return switch (name) {
            case "gold" -> gold;
            case "food" -> food;
            case "production" -> production;
            default -> 0;
        };
    }

    public boolean isResourceAchieved(ResourceEnum resourceEnum) {
        if (resourceEnum == ResourceEnum.RESET) return true;
        return resourceEnum != null && this.getCivilization() != null && this.getCivilization().getTechnologies().containsAll(resourceEnum.getImprovementNeeded().getRequiredTechs())
                && (this.getCity() != null || !this.isDamaged && this.getImprovements().contains(resourceEnum.getImprovementNeeded()));
    }

    public Location getLocation() {
        return location;
    }

    public void setUnitNull(Unit unit) {
        if (unit instanceof CombatUnit) {
            this.setCombatUnit(null);
        } else {
            this.setNonCombatUnit(null);
        }
    }

    public Unit getUnit() {
        if (combatUnit != null) {
            return combatUnit;
        } else if (nonCombatUnit != null) {
            return nonCombatUnit;
        }
        return null;
    }

    public ArrayList<ImprovementEnum> getImprovements() {
        return this.improvements;
    }

    public void clearLand() {
        this.terrain.clearLands();
    }

    public void addImprovement(ImprovementEnum improvement) {
        this.getImprovements().add(improvement);
    }

    public List<ImprovementEnum> getImprovementsExceptRoadOrRailRoad() {
        List<ImprovementEnum> improvementEnums = improvements.stream().filter(improvementEnum -> {
            return improvementEnum == ImprovementEnum.ROAD || improvementEnum == ImprovementEnum.RAILROAD;
        }).toList();

        return improvementEnums;
    }

    // todo
    public String getInfo() {
        return null;
    }

}
