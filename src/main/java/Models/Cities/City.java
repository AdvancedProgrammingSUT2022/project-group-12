package Models.Cities;

import Controllers.CheatCodeController;
import Enums.*;
import Models.Buildings.Building;
import Models.Citizen;
import Models.Civilization;
import Models.Location;
import Models.Production;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;

import java.util.*;

import static java.lang.Math.exp;

public class City {
    private final ArrayList<Tile> tiles;
    private int citizensCount;
    private final int range;
    private final ArrayList<Building> buildings;
    private final Tile cityTile;
    private final ArrayList<Production> productionQueue;
    private final String name;
    private double productionFromCheat;
    protected boolean isCapital;
    private Civilization civilization;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private double happinessFromBuildings;
    private int foodFromBuildings;
    private int foodFromCheat;
    private int gold;
    private int goldFromBuildings;
    private int goldRatioFromBuildings;
    private double production;
    private double productionFromBuildings;
    private int food;
    private ArrayList<ResourceEnum> resources;
    private ResourceEnum reservedResource;
    private int hitPoint;
    private double combatStrength;
    private int beaker;
    private double localHappiness;
    private CityTypeEnum cityState;

    public City(String name, ArrayList<Tile> tiles, Civilization civ, Tile tile, boolean isCapital) throws CommandException {
        this.tiles = tiles;
        this.tiles.add(tile);
        this.combatUnit = null;
        this.nonCombatUnit = null;
        this.gold = tile.getTerrain().getGoldCount();
        this.production = 1 + tile.calculateSources("production");
        this.resources = new ArrayList<>(tile.getTerrain().getResource() == null ? List.of() : List.of(tile.getTerrain().getResource()));
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
        this.goldFromBuildings = 0;
        this.goldRatioFromBuildings = 1;

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


    public ArrayList<Citizen> getCitizens() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        for (Tile tile : this.getTiles()) {
            if (tile.getCitizen() != null && tile.getCitizen().getCity() == this) {
                citizens.add(tile.getCitizen());
            }
        }
        return citizens;
    }


    private void affectCitizens() throws CommandException {
        for (Tile tile : this.getTiles()) {
            if (tile.getCitizen().getCity() == this) {
                // affect citizen
                this.food += tile.calculateSources("food");
                this.production += tile.calculateSources("production");
            }
        }
    }


    public void applyBuildingNotes() {
        for (Building building : this.buildings) {
            building.getNote().note(this); // todo: how to call the note function?
            this.setGoldFromBuildings(getGoldFromBuildings() - building.getType().getMaintenance());
        }
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


    private boolean haveCourtHouse() {
        for (Building building : buildings) {
            if (building.getType() == BuildingEnum.COURT_HOUSE) {
                return true;
            }
        }
        return false;
    }

    public double numberOfLuxuryResources() {
        double counter = 0;
        checkForReservedResource(this.reservedResource);
        for (Tile tile :
                this.getTiles()) {
            Terrain terrain = tile.getTerrain();
            ResourceEnum resource = terrain.getResource();
            if (resource.isLuxury() && tile.getImprovements().contains(resource.getImprovementNeeded())) {
                ++counter;
            }
        }
        return counter;
    }

    private void checkForReservedResource(ResourceEnum reservedResource) {
        if (reservedResource == null) {
            return;
        }
        if (reservedResource.getImprovementNeeded().hasRequiredTechs(this.civilization.getTechnologies())) {
            this.resources.add(reservedResource);
            reservedResource = null;
        }
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
    public double calculateCityHappiness() {

        /***
         * calculate happiness
         */
        this.localHappiness = 10;
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

    public int calculateGold() throws CommandException {
        int gold = 0;
        gold *= this.goldRatioFromBuildings;
        gold += this.goldFromBuildings;
        gold += getFromResource("gold");
        gold += getSourcesFromTiles("gold");
        return gold;
    }

    public int calculateFood() throws CommandException {
        int food = this.getFoodFromBuildings() + 2;
        food += this.foodFromCheat;
        food += (int) getSourcesFromTiles("food");
        food -= this.citizensCount * 2;
        food = settlerEffectOnFoods(food);
        if (food < 0) {
            this.citizensCount -= 1;
            killCitizen();
        }
        if (food > 0) {
            food = checkForHappinessState(food);
        }
        return food;
    }

    private void killCitizen() {
        Collections.shuffle(this.getTiles());
        for (Tile tile :
                this.getTiles()) {
            if (tile.getCitizen() != null && tile != this.getTile()) {
                tile.setCitizen(null);
                return;
            }
        }
    }

    private int checkForHappinessState(int food) {
        if (this.civilization.getHappinessType() != HappinessTypeEnum.HAPPY) {
            return food * 2 / 3;
        }
        return food;
    }


    private int settlerEffectOnFoods(int food) {
        if (productionQueue.get(0) instanceof Unit unit && unit.getType() == UnitEnum.SETTLER && food > 0) {
            food = 0;
        }
        return food;
    }

    private double getSourcesFromTiles(String foodOrProductionOrGold) throws CommandException {
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
        }
        throw new CommandException(CommandResponse.INVALID_NAME);
    }


    private double getFromResource(String name) {
        double gold = 0;
        double production = 0;
        double food = 0;
        for (ResourceEnum resource :
                this.resources) {
            food += resource.getFoodCount();
            production += resource.getProductCount();
            gold += resource.getGoldCount();
        }
        switch (name) {
            case "gold" -> {
                return gold;
            }
            case "production" -> {
                return production;
            }
            case "food" -> {
                return food;
            }
        }
        return 0;
    }

    /***
     * setter and getters
     */

    public ArrayList<Production> getProductionQueue() {
        return productionQueue;
    }

    public void setGoldRatioFromBuildings(int goldRatioFromBuildings) {
        this.goldRatioFromBuildings = goldRatioFromBuildings;
    }

    public int getGoldRatioFromBuildings() {
        return goldRatioFromBuildings;
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

    public void setProductionFromCheat(double productionFromCheat) {
        this.productionFromCheat = productionFromCheat;
    }

    public Location getLocation() {
        return this.cityTile.getLocation();
    }

    public int getGold() {
        return this.gold;
    }

    public void setGoldProductionValue(int gold) {
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


    public ResourceEnum getReservedResource() {
        return reservedResource;
    }

    public void setReservedResource(ResourceEnum reservedResource) {
        this.reservedResource = reservedResource;
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

    public int getFoodFromBuildings() {
        return foodFromBuildings;
    }

    public Tile getTile() {
        return cityTile;
    }

    public double getProductionFromCheat() {
        return productionFromCheat;
    }


    public void setCitizensCount(int citizensCount) {
        this.citizensCount = citizensCount;
    }

    public int getGoldFromBuildings() {
        return goldFromBuildings;
    }

    public void setFoodFromBuildings(int foodFromBuildings) {
        this.foodFromBuildings = foodFromBuildings;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getLocalHappiness() {
        return localHappiness;
    }

    public void setGoldFromBuildings(int goldFromBuildings) {
        this.goldFromBuildings = goldFromBuildings;
    }

    public int getFoodFromCheat() {
        return foodFromCheat;
    }

    public double getHappinessFromBuildings() {
        return happinessFromBuildings;
    }


    public void setHappinessFromBuildings(double happinessFromBuildings) {
        this.happinessFromBuildings = happinessFromBuildings;
    }
}
