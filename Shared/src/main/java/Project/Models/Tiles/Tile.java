package Project.Models.Tiles;

import Project.Enums.*;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Location;
import Project.Models.Terrains.Terrain;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.Notifier;
import Project.Utils.NotifierUtil;
import Project.Utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class Tile implements Notifier<Tile> {
    private final Location location;
    private transient NotifierUtil<Tile> notifierUtil = null;
    protected ArrayList<ImprovementEnum> improvements = new ArrayList<>();
    private Terrain terrain;
    String type;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private boolean isDamaged;
    private boolean hasRoad;
    private boolean hasRailRoad;
    private boolean hasRiver;
    private VisibilityEnum state;
    private Citizen citizen = null;
    private boolean isRuin;
    private String civName = null;
    private City city;

    public Tile(Terrain terrain, Location tileLocation, String color) {
        this.location = tileLocation;
        this.isRuin = false;
        this.terrain = terrain;
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.isDamaged = false;
        this.city = null;
        this.hasRoad = false;
        this.type = this.getClass().getName();
        this.state = VisibilityEnum.FOG_OF_WAR;
    }

    public void copyPropertiesFrom(Tile that) {
        this.terrain = that.terrain;
        this.isRuin = that.isRuin;
        this.combatUnit = that.combatUnit;
        this.nonCombatUnit = that.nonCombatUnit;
        this.isDamaged = that.isDamaged;
        this.city = that.city;
        this.hasRoad = that.hasRoad;
        this.state = that.state;
        this.civName = that.civName;
        this.citizen = that.citizen;
        this.improvements = that.improvements;
        this.hasRiver = that.hasRiver;
        this.hasRailRoad = that.hasRailRoad;
        this.notifyObservers();
    }

    @Override
    public void addObserver(Observer<Tile> observer) {
        this.notifierUtil.addObserver(observer);
    }

    public void initializeNotifier() {
        this.notifierUtil = new NotifierUtil<>(this);
    }

    @Override
    public void notifyObservers() {
        if (this.notifierUtil != null) this.notifierUtil.notifyObservers();
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

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
        this.notifyObservers();
    }

    public VisibilityEnum getState() {
        return state;
    }

    public void setState(VisibilityEnum state) {
        this.state = state;
        this.notifyObservers();
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
        this.notifyObservers();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
        this.notifyObservers();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public CombatUnit getCombatUnit() {
        return combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
        this.notifyObservers();
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
        this.notifyObservers();
    }

    public void setCivilization(String civName) {
        this.civName = civName;
        this.notifyObservers();
    }
    public boolean hasRuin(){
        return isRuin;
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

    public int calculateSources(String name, ArrayList<TechnologyEnum> technologies) {
        int production = this.terrain.getProductsCount();
        int food = this.terrain.getFoodCount();
        int gold = this.terrain.getGoldCount();
        ResourceEnum resourceEnum = this.getTerrain().getResource();
        if (this.isResourceAchievedBy(resourceEnum, technologies)) {
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

    public boolean isResourceAchievedBy(ResourceEnum resourceEnum, ArrayList<TechnologyEnum> technologies) {
        if (resourceEnum == ResourceEnum.RESET) return true;
        return resourceEnum != null && technologies.containsAll(resourceEnum.getImprovementNeeded().getRequiredTechs())
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
        this.notifyObservers();
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
        this.notifyObservers();
    }

    public void addImprovement(ImprovementEnum improvement) {
        this.getImprovements().add(improvement);
        this.notifyObservers();
    }

    public List<ImprovementEnum> getImprovementsExceptRoadOrRailRoad() {
        List<ImprovementEnum> improvementEnums = improvements.stream().filter(improvementEnum -> {
            return improvementEnum == ImprovementEnum.ROAD || improvementEnum == ImprovementEnum.RAILROAD;
        }).toList();

        return improvementEnums;
    }

    public boolean isARuin() {
        return this.isRuin;
    }

    // todo
    public String getInfo() {
        return null;
    }

    public ResourceEnum getVisibleResource(ArrayList<TechnologyEnum> technologies) {
        ResourceEnum resource = this.getTerrain().getResource();
        if (resource == null || !technologies.containsAll(resource.getImprovementNeeded().getRequiredTechs())) return null;
        else return resource;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void removeResource() {
        this.terrain.setResource(null);
    }

    public void setRuin(boolean ruin) {
        isRuin = ruin;
    }

    public void achieveRuin() {
        this.setRuin(false);
        this.notifyObservers();
    }

    public String getCivName() {
        return civName;
    }
}
