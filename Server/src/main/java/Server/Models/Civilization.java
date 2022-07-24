package Server.Models;


import Project.Enums.*;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Notifications.*;
import Project.Models.Resource;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Server.Controllers.CityHandler;
import Server.Controllers.GameController;
import Server.Utils.BuildingNotesLoader;
import Server.Utils.CommandException;
import Server.Utils.UpdateNotifier;

import java.util.*;

public class Civilization implements Comparator {
    private final User user;
    private final String name;
    private final TerrainColor color;
    private final ArrayList<City> cities;
    private final TileGrid revealedTileGrid;
    private final ArrayList<Unit> units;
    private final HashMap<UnitEnum, Integer> unitCountByCategory;
    private final ArrayList<TechnologyEnum> technologies = new ArrayList<>(List.of(TechnologyEnum.RESET));
    private final HashMap<TechnologyEnum, Integer> researchingTechnologies;
    private final ArrayList<Civilization> inWarWith;
    private final int production;
    private final ArrayList<Resource> resources;
    private final ArrayList<Notification> notifications;
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
    private Boolean hasCreateFirstCity = false;
    private Location currentSelectedGridLocation = new Location(0, 0);
    private Location initialLocation;

    public Civilization(User user, TerrainColor color, int tileGridHeight, int tileGridWidth) {
        this.color = color;
        this.researchingTechnology = null;
        this.user = user;
        this.units = new ArrayList<>();
        this.gold = 100;
        this.production = 0;
        this.inWarWith = new ArrayList<>();
        this.name = user.getUsername();
        this.revealedTileGrid = new TileGrid(tileGridHeight, tileGridWidth);
        this.cities = new ArrayList<>();
        this.researchingTechnologies = new HashMap<>();
        this.happinessFromCheat = 0;
        this.goldFromCheat = 0;
        this.beaker = calculateScience();
        this.cheatBeaker = 0;
        this.beakerFromBuildings = 0;
        this.beakerRatioFromBuildings = 1;
        this.unitCountByCategory = new HashMap<>();
        this.resources = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.calculateHappiness();
    }

    public void createAddAndPlaceUnit(UnitEnum unitEnum, Location location) throws CommandException {
        Unit unit = Unit.constructUnitFromEnum(unitEnum, this.getName(), location);
        this.addUnit(unit);
        GameController.placeUnit(unit, GameController.getGameTile(location));
    }

    public void advanceResearchTech(UpdateNotifier updateNotifier) {
        if (this.researchingTechnology == null) /* never */ return;

        this.beaker = calculateScience();
        int temp = researchingTechnologies.get(researchingTechnology) + this.beaker;
        researchingTechnologies.remove(researchingTechnology);
        researchingTechnologies.put(researchingTechnology, temp);
        this.beaker = 0;
        if (researchingTechnologies.get(researchingTechnology) >= researchingTechnology.getCost()) {
            updateNotifier.sendAddTechnologyMessage(researchingTechnology.name());
            this.addTechnology(researchingTechnology);
            researchingTechnologies.remove(researchingTechnology);
            setResearchingTechnology(null);
            updateResearchingTechnology(researchingTechnology);
        }
    }

    private void updateResearchingTechnology(TechnologyEnum researchingTechnology) {
        for (TechnologyEnum te:
             TechnologyEnum.values()) {
            if(!this.technologies.contains(te) && !this.researchingTechnologies.containsKey(te) && this.technologies.containsAll(te.getPrerequisiteTechs())){
                this.researchingTechnologies.put(te,te.getCost());
            }
        }
    }

    public void advanceWorkerWorks() {
        for (Unit unit : this.getUnits()) {
            if (unit.getUnitType() == UnitEnum.WORKER) {
                NonCombatUnit worker = (NonCombatUnit) unit;
                worker.decreaseRemainingTime(1);
                if (worker.getRemainingTime() <= 0) {
                    worker.removeWork();
                    ImprovementEnum improvement = worker.getCurrentBuildingImprovement();
                    Tile workerTile = GameController.getGameTile(worker.getLocation());
                    if (improvement != null) {
                        workerTile.addImprovement(improvement);
                        GameController.getCivByName(workerTile.getCivName()).updateResources(workerTile);
                    }
                    workerTile.setDamaged(false);
                }
            }
        }
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public int calculateCivilizationGold() {
        int gold = calculateGoldBenefitsFromThisTurn();
        return gold + this.getGold();
    }

    private int calculateGoldBenefitsFromThisTurn() {
        int gold = this.getGoldFromCheat();
        for (City city : this.getCities()) {
            gold += CityHandler.calculateGold(city);
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

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int calculateScience() {
        int beaker = Constants.DEFUALT_BEAKER_PER_TURN;
        beaker += beakerFromBuildings;
//        System.out.println("beaker = " + beaker);;
        beaker += cheatBeaker;
//        System.out.println("cheatBeaker = " + cheatBeaker);
        for (City city : this.getCities()) {
            beaker += city.getCitizensCount();
        }
        beaker *= beakerRatioFromBuildings;
//        System.out.println("beakerRatioFromBuildings = " + beakerRatioFromBuildings);
        if (calculateCivilizationGold() < 0) {
            beaker -= Math.abs(this.calculateCivilizationGold());
        }
//        System.out.println("beaker = " + beaker);
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
            applyCityBuildingNotes(city);
        }
    }

    private void applyCityBuildingNotes(City city) {
        for (BuildingEnum bu:
                city.getBuildings().stream().map(Building::getBuildingType).toList()) {
            BuildingNotesLoader.getBuildingNotes().get(bu).note(city);
        }
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
        if (this.unitCountByCategory.containsKey(unit.getUnitType())) {
            int count = unitCountByCategory.get(unit.getUnitType());
            unitCountByCategory.remove(unit.getUnitType());
            unitCountByCategory.put(unit.getUnitType(), count);
        } else
            unitCountByCategory.put(unit.getUnitType(), 1);
    }

    public HashMap<UnitEnum, Integer> getUnitCountByCategory() {
        return unitCountByCategory;
    }

    public double calculateHappiness() {
        this.happiness = 0;
        this.happiness += this.happinessFromCheat;
        if (this.cities.size() == 0) {
            this.happiness += 10;
            return this.happiness;
        }
        for (City city : this.getCities()) {
            this.happiness += CityHandler.calculateCityHappiness(city);
        }
        int numberOfLuxuryResource = this.getAchievedLuxuryResources().size();
        this.happiness += numberOfLuxuryResource * 4;
        return happiness;
    }

    public ArrayList<ResourceEnum> getAchievedLuxuryResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (Resource resource : this.resources) {
            if (resource.getResourceEnum().getType() == ResourceTypeEnum.LUXURY) {
                resources.add(resource.getResourceEnum());
            }
        }
        return resources;
    }

    public ArrayList<ResourceEnum> getAchievedResources() {
        ArrayList<ResourceEnum> resources = new ArrayList<>();
        for (City city : this.cities) {
            for (ResourceEnum resource : CityHandler.getAchievedResources(city)) {
                if (!resources.contains(resource)) {
                    resources.add(resource);
                }
            }
        }
        return resources;
    }

    public void updateResources(Tile tile) {
        if (tile.getImprovements().contains(tile.getTerrain().getResource().getImprovementNeeded())) {
            resources.add(new Resource(tile.getTerrain().getResource(), tile.getLocation()));
        }
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }

    public void addGold(int value) {
        this.goldFromCheat += value;
    }

    public void resetMoveCount() {
        for (Unit unit : this.getUnits()) {
            unit.setAvailableMoveCount(unit.getUnitType().getMovement());
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public City getCityByName(String name) {
        for (City city :
                this.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
    }


    private boolean hasRequierdResource(ResourceEnum requiredResource) {
        if(this.resources.stream().map(Resource::getResourceEnum).toList().contains(requiredResource) || requiredResource == ResourceEnum.RESET){
            return true;
        }
        return false;
    }

    public int calculateCivilizationFood() {
        int food = Constants.PRIMAL_FOOD;
        for (City city : this.getCities()) {
            food += CityHandler.calculateFood(city);
        }
        this.food = food;
        return food;
    }

    public int calculateSuccess() {
        return gold + cities.size() + units.size() + technologies.size() + happiness
                 - inWarWith.size() + beaker + this.getOwnedTiles().size();
    }

    public boolean isInWarWith(Civilization civilization) {
        return this.inWarWith.contains(civilization);
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
            tiles.addAll(CityHandler.getCityTiles(city));
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

    public ArrayList<Civilization> getInWarWith() {
        return this.inWarWith;
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
        if (happiness > 0) {
            return HappinessTypeEnum.HAPPY;
        } else if (happiness > -10) {
            return HappinessTypeEnum.UNHAPPY;
        } else {
            return HappinessTypeEnum.VERY_UNHAPPY;
        }
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



    public void addTechnology(TechnologyEnum technology) {
        this.technologies.add(technology);
    }

    public int getResearchAdvancement(TechnologyEnum technology) {
        return this.researchingTechnologies.getOrDefault(technology, 0);
    }

    public void addResearchingTechnology(TechnologyEnum technologyEnum) {
        this.getResearchingTechnologies().put(technologyEnum, technologyEnum.getCost());
    }

    public boolean hasRequierdTech(List<TechnologyEnum> requiredTechs) {
        for (TechnologyEnum tech :
                requiredTechs) {
            if (!this.technologies.contains(tech)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public boolean containsResource(ResourceEnum resourceEnum) {
        if (resourceEnum == ResourceEnum.RESET) return true;
        return resources.stream().anyMatch(resource -> resource.getResourceEnum() == resourceEnum);
    }

    public Resource getResourceByName(ResourceEnum resourceEnum) {
        for (Resource resource :
                resources) {
            //todo check
            if (resource.getResourceEnum() == resourceEnum) {
                return resource;
            }
        }
        return null;
    }

    public ResourceEnum getResourceByName(String name) throws CommandException {
        ResourceEnum resourceEnum = ResourceEnum.getResourceEnumByName(name);
        if (containsResource(resourceEnum)) {
            return resourceEnum;
        }
        throw new CommandException(CommandResponse.NO_RESOURCE_WITH_THIS_NAME);
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void removeResource(ResourceEnum resourceEnum) {
        Resource resource = this.getResourceByName(resourceEnum);
        Tile tile = GameController.getGameTile(resource.getLocation());
        if (tile != null) tile.removeResource();
        this.resources.remove(resource);
    }

    public void decreaseGold(int value) {
        this.gold -= value;
    }

    public void addResource(ResourceEnum resourceEnum) {
        this.resources.add(new Resource(resourceEnum, null));
    }

    public void removeNotification(Notification notification) {
        this.notifications.remove(notification);
    }

    public boolean haveThisMoney(int value) {
        if (this.calculateCivilizationGold() < value) {
            return false;
        }
        return true;
    }

    public Trade getTradeByName(String name) throws CommandException {
        for (Notification notif :
                this.notifications) {
            if (notif instanceof Trade trade) {
                if (trade.getName().equals(name)) {
                    return trade;
                }
            }
        }
        throw new CommandException(CommandResponse.NO_TRADE_WITH_THIS_NAME);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public ArrayList<ResourceEnum> getResourceEnums() {
        ArrayList<ResourceEnum> resourceEnums = new ArrayList<>();
        for (Resource resource :
                resources) {
            resourceEnums.add(resource.getResourceEnum());
        }
        return resourceEnums;
    }

    public void removeDemand(Demand demand) {
        this.notifications.remove(demand);
    }

    public Demand getDemandByName(String demandName) {
        for (Notification notif:
             this.notifications) {
            if(notif instanceof Demand demand){
                System.out.println("demand.genName() = " + demand.getName());
                System.out.println("demandName = " + demandName);
                if(demand.getName().equals(demandName)){
                    return demand;
                }
            }
        }
        return null;
    }

    public void declareWar(Civilization guestCiv) {
        this.inWarWith.add(guestCiv);
    }
    public void peace(Civilization guestCiv) {
        if(inWarWith.contains(guestCiv)){
            inWarWith.remove(guestCiv);
        }
    }

    public DeclareWar getDeclareWarByName(String declareWarName) {
        for (Notification notif:
                this.notifications) {
            if(notif instanceof DeclareWar declareWar){
                if(declareWar.getName().equals(declareWarName)){
                    return declareWar;
                }
            }
        }
        return null;
    }

    public Peace getPeaceByName(String peaceName) {
        for (Notification notif:
                this.notifications) {
            if(notif instanceof Peace peace){
                if(peace.getName().equals(peaceName)){
                    return peace;
                }
            }
        }
        return null;
    }

    public Location getInitialLocation() {
        return this.initialLocation;
    }

    public void setHasCreateFirstCity(Boolean hasCreateFirstCity) {
        this.hasCreateFirstCity = hasCreateFirstCity;
    }

    public void setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    public boolean isOwnerOfItsCapital() {
        System.out.println(!hasCreateFirstCity + " " + units.size() + " " + Arrays.toString(units.toArray()));
        if(!hasCreateFirstCity && units.size() != 0)  return true;
        if(cities.size() == 0) return false;
        for (City city:
             this.cities) {
            if(city.isCapital()){
              return true;
            }
        }
        return false;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Civilization c1 && o2 instanceof Civilization c2) {
            if (c2.calculateSuccess() > c1.calculateSuccess()) {
                return 1;
            }
        }
        return -1;
    }

    public void removeCity(City city) {
        this.cities.remove(city);
    }
}