package Models;

import Controllers.CivilizationController;
import Controllers.GameController;
import Enums.*;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Civilization {

    private final User user;
    private final String name;
    private final TerrainColor color;
    private final ArrayList<City> cities;
    private final ArrayList<String> notifications;
    private final TileGrid revealedTileGrid;
    private final ArrayList<Unit> units;
    private final HashMap<UnitEnum, Integer> unitCountByCategory;
    private final ArrayList<TechnologyEnum> technologies = new ArrayList<>(List.of(TechnologyEnum.RESET));
    private final HashMap<TechnologyEnum, Integer> researchingTechnologies;
    private final ArrayList<Civilization> isInWarWith;
    private final ArrayList<Civilization> isInEconomicRelation;
    private final HappinessTypeEnum happinessType;
    private final int production;
    private int gold;
    private int goldFromCheat;
    private int food;
    private int beaker;
    private int cheatBeaker;
    private int beakerFromBuildings;
    private double beakerRatioFromBuildings;
    private int happiness;
    private int happinessFromCheat;
    private TechnologyEnum researchingTechnology;
    private City capital = null;
    private Location currentSelectedGridLocation = new Location(0, 0);
    private final CivilizationController controller = new CivilizationController(this);

    public Civilization(User user, TerrainColor color) {
        this.color = color;
        this.researchingTechnology = null;
        this.user = user;
        this.units = new ArrayList<>();
        this.gold = 1000;
        this.production = 0;
        this.isInWarWith = new ArrayList<>();
        this.name = user.getUsername();
        this.revealedTileGrid = new TileGrid(Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.researchingTechnologies = new HashMap<>();
        this.happinessType = this.detectHappinessState(this.happiness);
        this.isInEconomicRelation = new ArrayList<>();
        this.happinessFromCheat = 0;
        this.goldFromCheat = 0;
        this.beaker = calculateScience();
        this.cheatBeaker = 0;
        this.beakerFromBuildings = 0;
        this.beakerRatioFromBuildings = 0;
        this.unitCountByCategory = new HashMap<>();
    }

    public HappinessTypeEnum detectHappinessState(double happiness) {
        if (happiness > 0) {
            return HappinessTypeEnum.HAPPY;
        }
        if (happiness > -10) {
            return HappinessTypeEnum.UNHAPPY;
        }
        return HappinessTypeEnum.VERY_UNHAPPY;
    }

    public void advanceResearchTech() {
        if (this.researchingTechnology == null) return;

        this.beaker = calculateScience();
        int temp = researchingTechnologies.get(researchingTechnology) + this.beaker;
        researchingTechnologies.remove(researchingTechnology);
        researchingTechnologies.put(researchingTechnology, temp);
        this.beaker = 0;

        if (researchingTechnologies.get(researchingTechnology) >= researchingTechnology.getCost()) {
            this.addTechnology(researchingTechnology);
            researchingTechnologies.remove(researchingTechnology);
            setResearchingTechnology(null);
        }
    }

    public void advanceWorkerWorks() {
        for (Unit unit : this.getUnits()) {
            if (unit.getType() == UnitEnum.WORKER) {
                NonCombatUnit worker = (NonCombatUnit) unit;
                worker.decreaseRemainingTime(1);
                if (worker.getRemainingTime() <= 0) {
                    worker.removeWork();
                    ImprovementEnum improvement = worker.getCurrentBuildingImprovement();
                    Tile workerTile = GameController.getGameTile(worker.getLocation());
                    if (improvement != null) workerTile.addImprovement(improvement);
                    workerTile.setDamaged(false);
                }
            }
        }
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public int calculateCivilizationGold() {
        int gold = this.getGoldFromCheat();
        for (City city : this.getCities()) {
            gold += city.calculateGold();
        }
        return gold;
    }

    public int getGoldFromCheat() {
        return goldFromCheat;
    }

    public void setGoldFromCheat(int goldFromCheat) {
        this.goldFromCheat = goldFromCheat;
    }

    public int getGold() {
        return this.gold;
    }

    public int calculateScience() {
        int beaker = beakerFromBuildings;
        beaker += cheatBeaker;
        for (City city : this.getCities()) {
            beaker += city.getCitizensCount();
        }
        // todo: fix
        beaker *= beakerRatioFromBuildings;
        if (calculateCivilizationGold() < 0) {
            beaker -= this.getGold();
        }
        return beaker;
    }

    public void startResearchOnTech(TechnologyEnum technology) throws CommandException {
        if (this.getCities().isEmpty()) {
            throw new CommandException(CommandResponse.NO_CITY_FOUNDED);
        }
        for (TechnologyEnum tech : technology.getPrerequisiteTechs()) {
            if (!this.getTechnologies().contains(tech)) {
                throw new CommandException(CommandResponse.DO_NOT_HAVE_REQUIRED_TECHNOLOGY, tech.name());
            }
        }
        this.researchingTechnology = technology;
        if (!researchingTechnologies.containsKey(technology)) {
            this.researchingTechnologies.put(technology, 0);
        }
    }

    public StringBuilder getNotifications() {
        StringBuilder notificationList = new StringBuilder();
        for (String message : this.notifications) {
            notificationList.append(message).append("\n");
        }
        return notificationList;
    }

    public void sendMessage(String message) {
        this.notifications.add(message);
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public void applyNotes() {
        this.setBeakerFromBuildings(0);
        this.setBeakerRatioFromBuildings(1);
        for (City city : this.cities) {
            city.setGoldFromBuildings(1);
            city.setHappinessFromBuildings(0);
            city.setFoodFromBuildings(0);
            city.setFoodFromCheat(0);
            city.setProductionFromCheat(0);
            city.setProductionFromBuildings(0);
            city.setCombatStrengthFromBuildings(0);
            city.applyBuildingNotes();
        }
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
        if (this.unitCountByCategory.containsKey(unit.getType())) {
            int count = unitCountByCategory.get(unit.getType());
            unitCountByCategory.remove(unit.getType());
            unitCountByCategory.put(unit.getType(), count);
        } else
            unitCountByCategory.put(unit.getType(), 1);
    }

    public HashMap<UnitEnum,Integer> getUnitCountByCategory(){
        return unitCountByCategory;
    }

    public double calculateHappiness() {
        this.happiness = 0;
        this.happiness += this.happinessFromCheat;
        for (City city : this.getCities()) {
            this.happiness += city.calculateCityHappiness();
        }
        int numberOfLuxuryResource = this.getAchievedLuxuryResources().size();
        this.happiness += numberOfLuxuryResource * 4;
        detectHappinessState(happiness);
        return happiness;
    }

    public ArrayList<ResourceEnum> getAchievedLuxuryResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (ResourceEnum resource : this.getAchievedResources()) {
            if (resource.getType() == ResourceTypeEnum.LUXURY) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public ArrayList<ResourceEnum> getAchievedResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (City city : this.cities) {
            for (ResourceEnum resource : city.getAchievedResources()) {
                if (!resources.contains(resource)) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    public void addGold(int value) {
        this.gold += value;
    }

    public void resetMoveCount() {
        for (Unit unit : this.getUnits()) {
            unit.setAvailableMoveCount(unit.getType().getMovement());
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public City getCityByName(String name) throws CommandException {
        for (City city :
                this.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }

    public int calculateCivilizationFood() throws CommandException {
        int food = 0;
        for (City city :
                this.getCities()) {
            food += city.calculateFood();
        }
        this.food = food;
        return food;
    }

    public int calculateSuccess() {
        return gold + cities.size() + units.size() + technologies.size() + happiness
                + isInEconomicRelation.size() - isInWarWith.size() + beaker + this.getOwnedTiles().size();
    }

    public boolean isInWarWith(Civilization civilization) {
        return this.isInWarWith.contains(civilization);
    }

    public boolean isFriendWith(Civilization civilization) {
        return this.isInEconomicRelation.contains(civilization);
    }

    public void goToWarWith(Civilization civilization) {
        this.isInWarWith.add(civilization);
    }

    public void endWarWith(Civilization civilization) {
        this.isInWarWith.remove(civilization);
    }

    public int getHappiness() {
        return this.happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness += happiness;
    }

    public HashMap<TechnologyEnum, Integer> getResearchingTechnologies() {
        return researchingTechnologies;
    }

    public int getBeaker() {
        return this.beaker;
    }

    public void setBeaker(int beaker) {
        this.beaker = beaker;
    }

    public int getProduction() {
        return this.production;
    }

    public ArrayList<TechnologyEnum> getTechnologies() {
        return this.technologies;
    }

    public ArrayList<Tile> getOwnedTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (City city : this.getCities()) {
            tiles.addAll(city.getTiles());
        }
        return tiles;
    }

    public TileGrid getRevealedTileGrid() {
        return this.revealedTileGrid;
    }

    public City getCapital() {
        return this.capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public TechnologyEnum getResearchingTechnology() {
        return researchingTechnology;
    }

    public void setResearchingTechnology(TechnologyEnum researchingTechnology) {
        this.researchingTechnology = researchingTechnology;
    }

    public ArrayList<Civilization> getIsInWarWith() {
        return this.isInWarWith;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return this.name.substring(0, Math.min(5, this.name.length()));
    }

    public HashMap<TechnologyEnum, Integer> getResearch() {
        return this.researchingTechnologies;
    }

    public HappinessTypeEnum getHappinessType() {
        return happinessType;
    }

    public User civUser() {
        return this.user;
    }

    public Location getCurrentSelectedGridLocation() {
        return currentSelectedGridLocation;
    }

    public void setCurrentSelectedGridLocation(Location currentSelectedGridLocation) {
        this.currentSelectedGridLocation = currentSelectedGridLocation;
    }

    public Location getCurrentGridLocation() {
        return this.currentSelectedGridLocation;
    }

    public TerrainColor getColor() {
        return color;
    }

    public ArrayList<Civilization> economicRelations() {
        return this.isInEconomicRelation;
    }

    public int getHappinessFromCheat() {
        return happinessFromCheat;
    }

    public void setHappinessFromCheat(int happinessFromCheat) {
        this.happinessFromCheat = happinessFromCheat;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getCheatBeaker() {
        return cheatBeaker;
    }

    public void setCheatBeaker(int cheatBeaker) {
        this.cheatBeaker = cheatBeaker;
    }

    public ArrayList<Civilization> getIsInEconomicRelation() {
        return isInEconomicRelation;
    }

    public User getUser() {
        return user;
    }

    public int getBeakerFromBuildings() {
        return beakerFromBuildings;
    }

    public void setBeakerFromBuildings(int beakerFromBuildings) {
        this.beakerFromBuildings = beakerFromBuildings;
    }

    public double getBeakerRatioFromBuildings() {
        return beakerRatioFromBuildings;
    }

    public void setBeakerRatioFromBuildings(double beakerRationFromBuildings) {
        this.beakerRatioFromBuildings = beakerRationFromBuildings;
    }

    public CivilizationController getController() {
        return controller;
    }

    public void addTechnology(TechnologyEnum technology) {
        this.getTechnologies().add(technology);
    }

    public int getResearchAdvancement(TechnologyEnum technology) {
        return this.researchingTechnologies.getOrDefault(technology, 0);
    }
}