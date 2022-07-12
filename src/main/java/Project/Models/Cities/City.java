package Project.Models.Cities;

import Project.Enums.*;
import Project.Models.Buildings.Building;
import Project.Models.Citizen;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Production;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Server.Controllers.CheatCodeController;
import Project.Utils.CommandException;
import Project.Utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Tile cityTile;
    private final ArrayList<Production> productionQueue;
    private final String name;
    protected boolean isCapital;
    private int citizensCount;
    private int remainsToBornCitizen;
    private double productionFromCheat;
    private Civilization civilization;
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

    public City(String name, ArrayList<Tile> tiles, Civilization civ, Tile tile, boolean isCapital) {
        this.tiles = tiles;
        this.tiles.add(tile);
        this.gold = 100;
        this.production = 1 + tile.calculateSources("production");
        this.health = Constants.CITY_FULL_HEALTH;
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
        this.goldFromBuildings = 0;
        this.goldRatioFromBuildings = 1;
        this.combatStrengthFromBuildings = 0;
        this.remainedFoodForCitizen = 0;
    }

    public double calculateCombatStrength() {
        return AffectCityFeatures(this);
    }

    private double AffectCityFeatures(City city) {
        this.combatStrength = 10;
        this.combatStrength += citizensCount;
        this.combatStrength += combatStrengthFromBuildings;
        if (city.getTile().getTerrain().getTerrainType() == TerrainEnum.HILL) {
            this.combatStrength *= (4.0 / 3.0);
        }
        if (city.getTile().getCombatUnit() != null) {
            this.combatStrength *= (5.0 / 4.0);
        }
        return this.combatStrength;
    }

    public int calculateDamage(double strengthDiff) {
        Random random = new Random();
        double random_number = (double) random.nextInt(75, 125) / 100;
        return (int) (25 * exp(strengthDiff / (25.0 * random_number)));
    }

    public Tile getTile() {
        return cityTile;
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

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void applyBuildingNotes() {
        for (Building building : this.buildings) {
            building.getNote().note(this);
            this.setGoldFromBuildings(this.getGoldFromBuildings() - building.getType().getMaintenance());
        }
    }

    public void finishProducts() throws CommandException {
        if (productionQueue.isEmpty()) {
            return;
        }
        int size = productionQueue.size();
        while (!productionQueue.isEmpty()) {
            Production product = productionQueue.get(0);
            advanceProductionQueue();
            if (productionQueue.size() < size) {
                if (product instanceof Unit) {
                    CheatCodeController.getInstance().spawnUnit(((Unit) product).getType(), this.getCivilization(), getLocation());
                }
                size = productionQueue.size();
            }
        }
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
                try {
                    this.cityTile.placeUnit(unit);
                } catch (CommandException e) {
                    // we should guarantee emptiness of the tile at the last turn
                    throw new RuntimeException(e);
                }
            }
        }
    }



    public double calculateCityHappiness() {


         // calculate happiness
        this.localHappiness = 10;
        this.localHappiness += this.happinessFromBuildings;
        //  affect unhappiness
        System.out.println("localHappiness after happiness from building = " + localHappiness);
        if (cityState == CityTypeEnum.ANNEXED) {
            this.localHappiness -= (citizensCount + citizensCount / 3);
            if (!haveCourtHouse()) this.localHappiness -= 5;
        } else {
            this.localHappiness -= citizensCount;
            System.out.println("citizensCount = " + citizensCount);
            System.out.println("localHappiness after  = " + localHappiness);
            this.localHappiness -= 3;
        }

        return this.localHappiness;
    }

    private boolean haveCourtHouse() {
        for (Building building : buildings) {
            if (building.getType() == BuildingEnum.COURT_HOUSE) {
                return true;
            }
        }
        return false;
    }

    private double calculateProduction() throws CommandException {
        this.production = 1;
        this.production += this.productionFromBuildings;
        this.production += getSourcesFromTiles("production");
        this.production += getProductionFromCheat();
        this.production += getFromResource("production");
        if (this.civilization.getHappinessType() == HappinessTypeEnum.HAPPY) {
            return production;
        } else {
            return production * 2 / 3;
        }
    }

    private double getSourcesFromTiles(String foodOrProductionOrGold) {
        double production = 0;
        double food = 0;
        double gold = 0;
        for (Tile tile :
                this.getTiles()) {
            if (tile.getCitizen() != null) {
                food += tile.calculateSources("food");
                production += tile.calculateSources("production");
                gold += tile.calculateSources("gold");
            }
        }
        switch (foodOrProductionOrGold) {
            case "food" -> {
                return food;
            }
            case "production" -> {
                return production;
            }
            case "gold" -> {
                return gold;
            }
            default -> throw new RuntimeException();
        }
    }

    public int calculateGold() {
        int gold = 0;
        gold += this.goldFromBuildings;
        gold += getFromResource("gold");
        gold += getSourcesFromTiles("gold");
        gold *= this.goldRatioFromBuildings;
        return gold;
    }

    private double getFromResource(String name) {
        double gold = 0;
        double production = 0;
        double food = 0;
        for (ResourceEnum resource : this.getAchievedResources()) {
            food += resource.getFoodCount();
            production += resource.getProductsCount();
            gold += resource.getGoldCount();
        }
        return switch (name) {
            case "gold" -> gold;
            case "production" -> production;
            case "food" -> food;
            default -> 0;
        };
    }

    public ArrayList<ResourceEnum> getAchievedResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>(List.of(ResourceEnum.RESET));
        for (Tile tile : this.getTiles()) {
            ResourceEnum resource = tile.getTerrain().getResource();
            // todoLater: getResource instead
            if (tile.isResourceAchievedBy(resource, cityTile.getCivilization())) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public int calculateFood() {
        int food = this.getFoodFromBuildings() + 2;
        food += this.foodFromCheat;
        food += (int) getSourcesFromTiles("food");
        food += (int) getFromResource("food");
        food -= this.citizensCount * 2;

        if (food > 0) {
            food *= checkForHappinessState();
        }
        return food;
    }

    public int numberOfUnassignedCitizens() {
        return citizensCount - this.getCitizens().size();
    }

    private int checkForHappinessState() {
        if (this.civilization.getHappinessType() != HappinessTypeEnum.HAPPY) {
            return 2 / 3;
        }
        return 1;
    }

    public void killCitizen() {
        Collections.shuffle(this.getTiles());
        for (Tile tile :
                this.getTiles()) {
            if (tile.getCitizen() != null && tile != this.getTile()) {
                tile.setCitizen(null);
                return;
            }
        }
    }

    public void checkCitizenBirth() {
        if (productionQueue.size() != 0 && productionQueue.get(0) instanceof Unit unit && unit.getType() == UnitEnum.SETTLER) {
            return;
        }
        this.remainedFoodForCitizen += calculateFood();
        if (this.remainedFoodForCitizen > Constants.FOOD_NEEDED_TO_BORN_CITIZEN) {
            this.citizensCount += 1;
            this.remainedFoodForCitizen = 0;
        }
    }

    public String getQueueInfo() {
        if (this.getProductionQueue().isEmpty()) return "queue is empty";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.getProductionQueue().size(); i++) {
            Production production = this.getProductionQueue().get(i);
            if (production instanceof Unit unit) { // todo: buildings
                stringBuilder.append(i + 1 + ". " + unit.getType().name() + ", remained " + unit.getRemainedProduction() + " production\n");
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

    public String getInfo() {
        return name + "\n" +
                "tile: " + cityTile.getTerrain().getTerrainType().name() + "\n" +
                "citizen count " + citizensCount + "\n" +
                "food production " + calculateFood() + "\n";
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
        this.tiles.add(tile);
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

    public Civilization getCivilization() {
        return civilization;
    }

    public void setCivilization(Civilization civ) {
        this.civilization = civ;
    }

    public Location getLocation() {
        return this.cityTile.getLocation();
    }


}
