package Project.Models.Cities;

import Project.Enums.CityTypeEnum;
import Project.Models.Buildings.Building;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Utils.Constants;

import java.util.ArrayList;

public class City {
    private final ArrayList<Location> tilesLocations;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Location location;
    private final ArrayList<Production> productionQueue;
    private final String name;
    protected boolean isCapital;
    private int citizensCount;
    private double productionFromCheat;
    private double happinessFromBuildings;
    private int foodFromBuildings;
    private int foodFromCheat;
    private int gold;
    private int goldFromBuildings;
    private int goldRatioFromBuildings;
    private double production;
    private double productionFromBuildings;
    private int food;
    private int health;
    private double combatStrength;
    private double combatStrengthFromBuildings;
    private int beaker;
    private double localHappiness;
    private CityTypeEnum cityState;
    private int remainedFoodForCitizen;
    private String civName;

    public City(String name, ArrayList<Tile> tiles, String civName, Tile tile, boolean isCapital) {
        this.tilesLocations = new ArrayList<>(tiles.stream().map(Tile::getLocation).toList());
        if (!this.tilesLocations.contains(tile.getLocation())) this.tilesLocations.add(tile.getLocation());
        this.gold = 100;
        this.production = 1 + tile.calculateSources("production");
        this.health = Constants.CITY_FULL_HEALTH;
        this.combatStrength = 10;
        this.isCapital = isCapital;
        this.citizensCount = 1;
        this.food = 1;
        this.buildings = new ArrayList<>();
        this.civName = civName;
        this.range = 2;
        this.location = tile.getLocation();
        this.name = name;
        this.cityState = CityTypeEnum.RAW;
        //TODO : check the range of the city and the combat strength of that
        this.productionQueue = new ArrayList<>();
        this.happinessFromBuildings = 0;
        this.foodFromBuildings = 0;
        this.foodFromCheat = 0;
        this.productionFromBuildings = 0;
        this.productionFromCheat = 0;
        this.goldFromBuildings = 0;
        this.goldRatioFromBuildings = 1;
        this.combatStrengthFromBuildings = 0;
        this.remainedFoodForCitizen = 0;
    }

    public ArrayList<Production> finishProductsAndReturnIt() {
        ArrayList<Production> productions = new ArrayList<>(this.productionQueue);
        this.productionQueue.clear();
        return productions;
    }

    public Production advanceProductionQueue(double productionOfCity) {
        // productionQueue cannot be empty (Game.endCurrentTurn guarantee)
        productionQueue.get(0).decreaseRemainedProduction(productionOfCity);
        if (productionQueue.get(0).getRemainedProduction() <= 0) {
            Production production = productionQueue.remove(0);
            return production;
        } else return null;
    }

    public String getQueueInfo() {
        if (this.getProductionQueue().isEmpty()) return "queue is empty";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.getProductionQueue().size(); i++) {
            Production production = this.getProductionQueue().get(i);
            if (production instanceof Unit unit) { // todo: buildings
                stringBuilder.append(i + 1 + ". " + unit.getUnitType().name() + ", remained " + unit.getRemainedProduction() + " production\n");
            }
        }
        return stringBuilder.toString();
    }

    public double getProductionFromCheat() {
        return productionFromCheat;
    }


    public void setProductionFromCheat(double productionFromCheat) {
        this.productionFromCheat = productionFromCheat;
    }

    public int getFoodFromBuildings() {
        return foodFromBuildings;
    }

    public void setFoodFromBuildings(int foodFromBuildings) {
        this.foodFromBuildings = foodFromBuildings;
    }

    public int getGoldRatioFromBuildings() {
        return goldRatioFromBuildings;
    }

    public int getGoldFromBuildings() {
        return goldFromBuildings;
    }

    public void setGoldFromBuildings(int goldFromBuildings) {
        this.goldFromBuildings = goldFromBuildings;
    }

    public void setGoldRatioFromBuildings(int goldRatioFromBuildings) {
        this.goldRatioFromBuildings = goldRatioFromBuildings;
    }

    public int getRange() {
        return range;
    }


    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setGoldProductionValue(int gold) {
        this.gold = gold;
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

    public void setCitizensCount(int citizensCount) {
        this.citizensCount = citizensCount;
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

    public double getProductionFromBuildings() {
        return productionFromBuildings;
    }

    public void setProductionFromBuildings(double productionFromBuildings) {
        this.productionFromBuildings = productionFromBuildings;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public double getHappiness() {
        return localHappiness;
    }

    public void setHappiness(double happiness) {
        this.localHappiness = happiness;
    }

    public CityTypeEnum getCityState() {
        return cityState;
    }

    public void setCityState(CityTypeEnum cityState) {
        this.cityState = cityState;
    }

    public boolean hasBuilding(Building buildingName) {
        return this.buildings.contains(buildingName);
    }

    public void addBuilding(Building cityStructure) {
        this.buildings.add(cityStructure);
    }

    public String getName() {
        return this.name;
    }

    public double getLocalHappiness() {
        return localHappiness;
    }

    public int getFoodFromCheat() {
        return foodFromCheat;
    }

    public void setFoodFromCheat(int foodFromCheat) {
        this.foodFromCheat = foodFromCheat;
    }

    public double getHappinessFromBuildings() {
        return happinessFromBuildings;
    }

    public void setHappinessFromBuildings(double happinessFromBuildings) {
        this.happinessFromBuildings = happinessFromBuildings;
    }

    public double getCombatStrengthFromBuildings() {
        return combatStrengthFromBuildings;
    }

    public void setCombatStrengthFromBuildings(double combatStrengthFromBuildings) {
        this.combatStrengthFromBuildings = combatStrengthFromBuildings;
    }

    public void decreaseHealth(int value) {
        this.health -= value;
    }


    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }



    public void setLocalHappiness(double localHappiness) {
        this.localHappiness = localHappiness;
    }

    public void setRemainedFoodForCitizen(int remainedFoodForCitizen) {
        this.remainedFoodForCitizen = remainedFoodForCitizen;
    }

    public void setCivName(String civName) {
        this.civName = civName;
    }

    public ArrayList<Location> getTilesLocations() {
        return tilesLocations;
    }

    public int getRemainedFoodForCitizen() {
        return remainedFoodForCitizen;
    }

    public String getCivName() {
        return civName;
    }

    public void addToProductionQueue(Production production) {
        this.productionQueue.add(production);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void removeFromProductionQueue(int index) {
        this.productionQueue.remove(index);
    }

    public void addTile(Tile tile) {
        this.tilesLocations.add(tile.getLocation());
    }

    public ArrayList<Production> getProductionQueue() {
        return productionQueue;
    }

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public Location getLocation() {
        return this.location;
    }



}
