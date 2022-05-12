package Models;

import Controllers.CivilizationController;
import Enums.*;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;
import Models.Units.Unit;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Civilization {

    private final User user;
    private final String name;
    private final TerrainColor color;
    private final CivilizationController controller;
    private final ArrayList<City> cities;
    private final ArrayList<String> notifications;
    private final ArrayList<Tile> ownedTiles;
    private final TileGrid revealedTileGrid;
    private final ArrayList<Unit> units;
    private final HashMap<TechnologyEnum, Integer> technologies;
    private final HashMap<TechnologyEnum, Integer> researchingTechnologies;
    private final ArrayList<Civilization> isInWarWith;
    private final ArrayList<Civilization> isInEconomicRelation;
    private final HappinessTypeEnum happinessType;
    private int gold;
    private int goldFromCheat;
    private final int production;
    private int food;
    private int beaker;
    private int cheatBeaker;
    private int beakerFromBuildings;
    private double beakerRatioFromBuildings;
    private int happiness;
    private int happinessFromCheat;
    private TechnologyEnum researchingTechnology;
    private TechnologyEnum currentTech;
    private ArrayList<CombatUnit> combatUnits;
    private ArrayList<NonCombatUnit> nonCombatUnits;
    private City capital = null;
    private Location currentSelectedGridLocation = new Location(0, 0);

    public Civilization(User user, TerrainColor color) {
        List<TerrainColor> colors = List.of(TerrainColor.GREEN, TerrainColor.RED);
        this.color = colors.get(new Random().nextInt(colors.size()));
        this.technologies = new HashMap<>();
        this.researchingTechnology = null;
        this.user = user;
        this.units = new ArrayList<>();
        this.gold = 1000;
        this.production = 0;
        this.isInWarWith = new ArrayList<>();
        this.name = user.getUsername();
        this.revealedTileGrid = new TileGrid(this, Constants.TILEGRID_HEIGHT, Constants.TILEGRID_WIDTH);
        this.controller = new CivilizationController(this);
        this.cities = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.ownedTiles = null;
        this.researchingTechnologies = new HashMap<>();
        this.happinessType = this.detectHappinessState(this.happiness);
        this.isInEconomicRelation = new ArrayList<>();
        this.happinessFromCheat = 0;
        this.goldFromCheat = 0;
        this.beaker = capital.getCitizensCount();
        this.cheatBeaker = 0;
        this.beakerFromBuildings = 0;
        this.beakerRatioFromBuildings = 0;
    }

    public void researchTech() {
        if (researchingTechnologies.get(researchingTechnology) == researchingTechnology.getCost()) {
            if (technologies.containsKey(researchingTechnology)) {
                int count = technologies.get(researchingTechnology) + 1;
                technologies.remove(researchingTechnology);
                technologies.put(researchingTechnology, count);
            }
            researchingTechnologies.remove(researchingTechnology);
            checkLeadsToTech();
            researchingTechnology = null;
        } else {
            int temp = researchingTechnologies.get(researchingTechnology) + beaker;
            researchingTechnologies.remove(researchingTechnology);
            researchingTechnologies.put(researchingTechnology, temp);
            beaker = 0;
        }
    }

    public void checkLeadsToTech() {
        ArrayList<TechnologyEnum> techs = researchingTechnology.leadsToTech();
        for (TechnologyEnum tech : techs) {
            if (!tech.hasPrerequisiteTechs(this.technologies)) {
                researchingTechnologies.put(tech, tech.getCost());
            }
        }
    }

    public void startResearchOnTech(TechnologyEnum tech) {
        if (!researchingTechnologies.containsKey(tech)) {
            this.researchingTechnologies.put(tech, tech.getCost());
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
            city.applyBuildingNotes();
        }
    }

    public boolean isPossibleToHaveThisResource(ResourceEnum sourceResourceEnum) {
        ImprovementEnum requiredImprovement = sourceResourceEnum.improvementNeeded();
        for (TechnologyEnum technology : requiredImprovement.getRequiredTechs()) {
            if (this.technologies.containsKey(technology)) return true;
        }
        return false;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
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

    public double calculateHappiness() {
        this.happiness = 0;
        this.happiness += this.happinessFromCheat;
        for (City city : this.getCities()) {
            this.happiness += city.calculateCityHappiness();
        }
        int numberOfLuxuryResource = this.getAvailableLuxuryResources().size();
        this.happiness += numberOfLuxuryResource * 4;
        detectHappinessState(happiness);
        return happiness;
    }

    public ArrayList<ResourceEnum> getAvailableLuxuryResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (City city : this.cities) {
            for (ResourceEnum resource : city.getResources()) {
                if (resource.isLuxury() && !resources.contains(resource)) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    public TerrainColor getColor() {
        return color;
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


    public City getCityByName(String name) throws CommandException {
        for (City city:
             this.getCities()) {
            if(city.getName().equals(name)){
                return city;
            }
        }
        throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }

    public int calculateCivilizationFood() throws CommandException {
        int food = 0;
        for (City city:
             this.getCities()) {
            food += city.calculateFood();
        }
        this.food = food;
        return food;
    }
    public int calculateCivilizationGold() throws CommandException {
        int gold = this.getGoldFromCheat();
        for (City city :
             this.getCities()) {
            gold += city.calculateGold();
        }
        this.gold = gold;
        return gold;
    }

    public int calculateScience() throws CommandException {
        int beaker = 0;
        beaker += beakerFromBuildings;
        beaker *= beakerRatioFromBuildings;
        beaker += cheatBeaker;
        for (City city:
             this.getCities()) {
            beaker += city.getCitizensCount();
        }
        if(calculateCivilizationGold() < 0 ){beaker -= this.getGold();}
        return beaker;


    }



    /***
     * setter and getters
     */




    public ArrayList<Unit> getUnits() {
        return units;
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

    public int getGold() {
        return this.gold;
    }

    public int getBeaker() {
        return this.beaker;
    }

    public int getProduction() {
        return this.production;
    }

    public HashMap<TechnologyEnum, Integer> getTechnologies() {
        return this.technologies;
    }

    public ArrayList<CombatUnit> getCombatUnits() {
        return combatUnits;
    }

    public ArrayList<NonCombatUnit> getNonCombatUnits() {
        return nonCombatUnits;
    }

    public ArrayList<Tile> getOwnedTiles() {
        return this.ownedTiles;
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

    public CivilizationController getController() {
        return this.controller;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public HashMap<TechnologyEnum, Integer> getResearch() {
        return this.researchingTechnologies;
    }

    public void setGoldFromCheat(int goldFromCheat) {
        this.goldFromCheat = goldFromCheat;
    }

    public int getGoldFromCheat() {
        return goldFromCheat;
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

    public void setHappinessFromCheat(int happinessFromCheat) {
        this.happinessFromCheat = happinessFromCheat;
    }

    public int getHappinessFromCheat() {
        return happinessFromCheat;
    }

    public int getFood() {
        return food;
    }

    public void setCheatBeaker(int cheatBeaker) {
        this.cheatBeaker = cheatBeaker;
    }

    public void setBeaker(int beaker) {
        this.beaker = beaker;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setBeakerFromBuildings(int beakerFromBuildings) {
        this.beakerFromBuildings = beakerFromBuildings;
    }

    public int getCheatBeaker() {
        return cheatBeaker;
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

    public TechnologyEnum getCurrentTech() {
        return currentTech;
    }

    public void setBeakerRatioFromBuildings(double beakerRationFromBuildings) {
        this.beakerRatioFromBuildings = beakerRationFromBuildings;
    }

    public double getBeakerRatioFromBuildings() {
        return beakerRatioFromBuildings;
    }
}