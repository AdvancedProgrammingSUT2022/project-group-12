package Models.Cities;

import Controllers.CheatCodeController;
import Enums.*;
import Models.Buildings.Building;
import Models.Citizen;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Tiles.Tile;
import Models.Units.Unit;
import Utils.CommandException;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private final int citizensCount;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Tile cityTile;
    private final ArrayList<Production> productionQueue;
    private final String name;
    private  double productionFromCheat;
    protected boolean isCapital;
    private Civilization civilization;
    private double happinessFromBuildings;
    private int foodFromBuildings;
    private int foodFromCheat;
    private int goldProductionValue;
    private double production;
    private double productionFromBuildings;
    private int food;
    private int hitPoint;
    private double combatStrength;
    private int beaker;
    private double localHappiness;
    private CityTypeEnum cityState;

    public City(String name, ArrayList<Tile> tiles, Civilization civ, Tile tile, boolean isCapital) {
        this.tiles = tiles;
        this.tiles.add(tile);
        this.goldProductionValue = tile.getTerrain().getGoldCount();
        this.production = 1 + tile.calculateProductionCount();
        this.hitPoint = 2000;
        this.combatStrength = 10;
        this.isCapital = isCapital;
        this.citizensCount = 1;
        this.food = 1;
        this.localHappiness = 10;
        this.buildings = new ArrayList<>();
        this.civilization = civ;
        this.range = 2;
        this.cityTile = tile;
        this.name = name;
        this.cityState = CityTypeEnum.RAW;
        //TODO : check the range of the city and the combat strength of that
        this.productionQueue = new ArrayList<>();
        this.happinessFromBuildings = 0;
        this.foodFromBuildings = 0;
        this.foodFromCheat = 0;
        this.productionFromBuildings = 0;
        this.productionFromCheat = 0;

    }

    static int AffectCityFeatures(City city) {
        //todo : affect the citizen and buildings on combat strength
        return 0;
    }

    public static void calculateDamage(City city, double strengthDiff, Random random) {
        double random_number = random.nextInt(50) + 75;
        random_number /= 100;
        city.setHitPoint(city.getHitPoint() - (int) (25 * exp(strengthDiff / (25.0 * random_number))));
    }

    public int calculateCombatStrength() {
        return HealthBarAffect(AffectCityFeatures(this));
    }

    protected int HealthBarAffect(int strength) {
        return (this.getHitPoint() / 2000) * strength;
    }

    public ArrayList<Production> getProductionQueue() {
        return productionQueue;
    }

    public ArrayList<Citizen> getCitizens() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        for (Tile tile : this.getTiles()) {
            if (tile.getCitizen() != null && tile.getCitizen().getCity() == this) {
                citizens.add(tile.getCitizen());
            }
        }
        return citizens;
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

    public int getRange() {
        return range;
    }

    public void setProductionFromCheat(double productionFromCheat) {
        this.productionFromCheat = productionFromCheat;
    }

    public Location getLocation() {
        return this.cityTile.getLocation();
    }

    public int getGoldProductionValue() {
        return this.goldProductionValue;
    }

    public void setGoldProductionValue(int goldProductionValue) {
        this.goldProductionValue = goldProductionValue;
    }

    public ArrayList<ResourceEnum> getResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (Tile tile : this.getTiles()) {
            ResourceEnum resource = tile.getTerrain().getResource();
            if (tile.getImprovements().contains(resource.getImprovementNeeded())) {
                resources.add(resource);
            }
        }
        return resources;
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

    public void setProductionFromBuildings(double productionFromBuildings) {
        this.productionFromBuildings = productionFromBuildings;
    }

    public double getProductionFromBuildings() {
        return productionFromBuildings;
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

    public void setFoodFromCheat(int foodFromCheat) {
        this.foodFromCheat = foodFromCheat;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
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

    public double getProduction() {
        return production;
    }

    public void setProduction(double production) {
        this.production = production;
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

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civ) {
        this.civilization = civ;
    }

    public void applyBuildingNotes() {
        for (Building building : this.buildings) {
            building.getNote().note(this); // todo: how to call the note function?
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

    public void addToProductionQueue(Production production) {
        this.productionQueue.add(production);
    }

    public void removeFromProductionQueue(int index) {
        this.productionQueue.remove(index);
    }

    public void advanceProductionQueue() {
        // productionQueue cannot be empty (Game.endCurrentTurn guarantee)
        productionQueue.get(0).decreaseRemainedProduction(this.getProduction());
        if (productionQueue.get(0).getRemainedProduction() <= 0) {
            Production production = productionQueue.remove(0);
            if (production instanceof Building) {
                this.buildings.add((Building) production);
            } else if (production instanceof Unit unit) {
                this.getCivilization().addUnit(unit);
                // todo: already a unit on city tile
                unit.getCivilization().addUnit(unit);
                this.cityTile.setUnit(unit);
            }
        }
    }

    public double calculateCityHappiness() {
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
            if (!haveCourtHouse()) this.localHappiness -= 5;
        } else {
            this.localHappiness -= citizensCount;
            this.localHappiness -= 3;
        }

        return this.localHappiness <= citizensCount ? this.localHappiness : citizensCount;
    }

    private boolean haveCourtHouse() {
        for (Building building : buildings) {
            if (building.getType() == BuildingEnum.COURT_HOUSE) {
                return true;
            }
        }
        return false;
    }

    public int getFoodFromCheat() {
        return foodFromCheat;
    }

    public void setFoodFromBuildings(int foodFromBuildings) {
        this.foodFromBuildings = foodFromBuildings;
    }

    public int getFoodFromBuildings() {
        return foodFromBuildings;
    }

    public Tile getTile() {
        return this.cityTile;
    }

    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    public void finishProducts() throws CommandException {
        if (productionQueue.isEmpty())
            return;
        int size = productionQueue.size();
        while (!productionQueue.isEmpty()) {
            Production product = productionQueue.get(0);
            advanceProductionQueue();
            if (productionQueue.size() < size) {
                if (product instanceof Unit) {
                    CheatCodeController.getInstance().spawnUnit(((Unit) product).getType(), getLocation());
                }
                size = productionQueue.size();
            }
        }
    }
    public int calculateFood(){
        int food = this.getFoodFromBuildings() + 2;
        food += this.foodFromCheat;
        food += (int)getFoodOrProductionFromTiles("food");
        food -= this.citizensCount * 2;
        food = settlerEffectOnFoods(food);
        if(food < 0){
            //todo kill citizen
        }
        if(food > 0 ){
           food = checkForHappinessState(food);
        }
        return food;
    }

    private int checkForHappinessState(int food) {
        if(this.civilization.getHappinessType() != HappinessTypeEnum.HAPPY){
          return   food * 2 / 3;
        }
        return food;
    }

    public double getProductionFromCheat() {
        return productionFromCheat;
    }

    private int settlerEffectOnFoods(int food) {
        if(productionQueue.get(0) instanceof Unit unit && unit.getType() == UnitEnum.SETTLER && food > 0 ){
            food = 0;
        }
        return food;
    }
    private double getFoodOrProductionFromTiles(String foodOrProduction) {
        double production = 0;
        double food = 0;
        for (Tile tile:
             this.getTiles()) {
            if(tile.getCitizen() != null){
                food += tile.calculateFoodCount();
                production += tile.calculateProductionCount();
            }
        }
        if(foodOrProduction.equals("food"))  return food;
        else return production;
    }

    private double calculateProduction(){
        this.production = 1;
        this.production += this.productionFromBuildings;
        this.production += getFoodOrProductionFromTiles("production");
        this.production += getProductionFromCheat();
        this.production += getFromResource("production");
        return production;

    }

    private double getFromResource(String name) {
        double gold = 0;
        double production = 0;
        double food = 0;
        for (ResourceEnum resource : this.getResources()) {
            food += resource.getFoodCount();
            production += resource.getProductCount();
            gold += resource.getGoldCount();
        }
        return switch (name) {
            case "gold" -> gold;
            case "production" -> production;
            case "food" -> food;
        };
    }
}
