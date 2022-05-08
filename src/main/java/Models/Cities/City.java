package Models.Cities;

import Enums.BuildingEnum;
import Enums.CityTypeEnum;
import Enums.ResourceEnum;
import Models.Buildings.Building;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private Civilization isOwnedBy;
    private final int citizensCount;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Tile cityTile;
    private final ArrayList<Production> productionQueue;
    protected boolean isCapital;
    private final String name;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private double happinessFromBuildings;
    private int gold;
    private double production;
    private int food;
    private ArrayList<ResourceEnum> resources;
    private ResourceEnum reservedResource;
    private int hitPoint;
    private double combatStrength;
    private int beaker;
    private double localHappiness;
    private CityTypeEnum cityState;

    public City(String name, ArrayList<Tile> tiles, Civilization civ, Tile tile, boolean isCapital) {
        this.tiles = tiles;
        this.tiles.add(tile);
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.gold = tile.getTerrain().getGoldCount();
        this.production = 1 + tile.calculateProductionCount();
        this.resources = new ArrayList<>(List.of(tile.getTerrain().getResource()));
        this.hitPoint = 2000;
        this.combatStrength = 10;
        this.isCapital = isCapital;
        this.citizensCount = 1;
        this.food = tile.calculateFoodCount();
        this.localHappiness = 10;
        this.buildings = new ArrayList<>();
        this.isOwnedBy = civ;
        this.range = 2;
        this.cityTile = tile;
        this.name = name;
        this.cityState = CityTypeEnum.RAW;
        //TODO : check the range of the city and the combat strength of that
        this.productionQueue = new ArrayList<>();
        this.happinessFromBuildings=0;
    }

    private void affectCitizens() {
        for (Tile tile : this.getTiles()) {
            if (tile.getCitizen().getCity() == this) {
                // affect citizen
                this.food += tile.calculateFoodCount();
                this.production += tile.calculateProductionCount();
            }
        }
    }

    public static int calculateCombatStrength(City city, Tile cityTile) {
        int strength = (int) city.getCombatStrength();
        strength = AffectCityFeatures(city);
        strength = HealthBarAffect(strength, city);
        return strength;
    }

    protected static int HealthBarAffect(int strength, City city) {
        return (city.getHitPoint() / 2000) * strength;
    }


    static int AffectCityFeatures(City city) {
        //todo : affect the citizen and buildings on combat strength
        return 0;
    }

    public static void calculateDamage(City city, int strengthDiff, Random random) {
        double random_number = random.nextInt(50) + 75;
        random_number /= 100;
        city.setHitPoint(city.getHitPoint() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public int getRange() {
        return range;
    }

    public Location getLocation() {
        return this.cityTile.getLocation();
    }

    public Civilization getCivilization() {
        return this.isOwnedBy;
    }
    public void setCivilization(Civilization civ){ this.isOwnedBy = civ; }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public ArrayList<ResourceEnum> getResources() {
        return this.resources;
    }

    public void setResources(ArrayList<ResourceEnum> resources) {
        this.resources = resources;
    }

    public int getHitPoint() {
        return this.hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public double getCombatStrength() {
        return this.combatStrength;
    }

    public void setCombatStrength(double combatStrength) {
        this.combatStrength = combatStrength;
    }

    public int getCitizensCount() {
        return citizensCount;
    }

    public int getBeaker() {
        return beaker;
    }

    public void setBeaker(int beaker) {
        this.beaker = beaker;
    }

    public boolean isCapital() {
        return this.isCapital;
    }

    public void setCapital(boolean isCapital) {
        this.isCapital = isCapital;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public double getHappiness() {
        return localHappiness;
    }

    public CityTypeEnum getCityState() {
        return cityState;
    }

    public void setHappiness(double happiness) {
        this.localHappiness = happiness;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public void setReservedResource(ResourceEnum reservedResource) {
        this.reservedResource = reservedResource;
    }

    public ResourceEnum getReservedResource() {
        return reservedResource;
    }

    public boolean hasBuilding(BuildingEnum buildingName) {
        return this.buildings.contains(buildingName);
    }

    public void addBuilding(Building cityStructure) {
        this.buildings.add(cityStructure);
    }

    public String getName() {
        return this.name;
    }

    public Civilization getIsOwnedBy() {
        return isOwnedBy;
    }

    public void applyBuildingNotes() {
        for (Building building : this.buildings) {
            //  building.getNote().note(this); // todo: how to call the note function?
        }
    }

    public double getHappinessFromBuildings() {
        return happinessFromBuildings;
    }

    public void setHappinessFromBuildings(double happinessFromBuildings) {
        this.happinessFromBuildings = happinessFromBuildings;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void advanceProductionQueue() {
        productionQueue.get(0).decreaseRemainedProduction(this.getProduction());
        if (productionQueue.get(0).getRemainedProduction() <= 0) {
            Production production = productionQueue.remove(0);
            if (production instanceof Building) {
                this.buildings.add((Building) production);
            } else if (production instanceof Unit) {
                this.getCivilization().addUnit((Unit) production);
                // todo: what if there is already a unit on city tile?
                if (production instanceof CombatUnit) {
                    this.cityTile.setCombatUnit((CombatUnit) production);
                } else if (production instanceof NonCombatUnit) {
                    this.cityTile.setNonCombatUnit((NonCombatUnit) production);
                }
            }
        }
    }
    public double calculateCityHappiness(){
        /***
         * calculate happiness
         */
        this.localHappiness = 0;
        this.localHappiness += this.happinessFromBuildings;
        /***
         * affect unhappiness
         */
        if (cityState == CityTypeEnum.ANNEXED) {
            this.localHappiness -= (citizensCount + citizensCount / 3);
            if(!haveCourtHouse()) this.localHappiness -= 5;
        } else {
            this.localHappiness -= citizensCount;
            this.localHappiness -= 3;
        }

        return this.localHappiness <= citizensCount ? this.localHappiness : citizensCount;
    }

    private boolean haveCourtHouse() {
        for (Building building:
             buildings) {
            if(building.getType() == BuildingEnum.COURT_HOUSE){return true;}
        }
        return false;
    }

    public double numberOfLuxuryResources(){
        double counter = 0;
        checkForReservedResource(this.reservedResource);
        for (Tile tile :
                this.getTiles()) {
            Terrain terrain = tile.getTerrain();
            ResourceEnum resource = terrain.getResource();
            if (resource.isLuxury() && terrain.getImprovements().contains(resource.getImprovementNeeded())) {
                ++counter;
            }
        }
        return counter;
    }

    private void checkForReservedResource(ResourceEnum reservedResource) {
        if(reservedResource == null){return;}
        if(reservedResource.getImprovementNeeded().hasRequiredTechs(this.isOwnedBy.getTechnologies())){
            this.resources.add(reservedResource); reservedResource=null;
        }
    }

    public void setCityState(CityTypeEnum cityState) {
        this.cityState = cityState;
    }
    public Tile getTile() {
        return this.cityTile;
    }

}
