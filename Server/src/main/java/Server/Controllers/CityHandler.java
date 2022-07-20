package Server.Controllers;

import Project.Enums.*;
import Project.Models.Buildings.Building;
import Project.Models.Cities.City;
import Project.Models.Citizen;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CityHandler {


    public static double calculateCityHappiness(City city) {
        // calculate happiness
         int cityHappiness = 10;
         cityHappiness += city.getHappinessFromBuildings();
        //  affect unhappiness
        if (city.getCityState() == CityTypeEnum.ANNEXED) {
            cityHappiness += city.getCitizensCount() * (4 / 3.0);
            if (!haveCourtHouse(city)) cityHappiness += 5;
        } else {
            cityHappiness -= city.getCitizensCount();
            cityHappiness =- 3;
        }
        return cityHappiness;
    }

    private static boolean haveCourtHouse(City city) {
        for (Building building : city.getBuildings()) {
            if (building.getType() == BuildingEnum.COURT_HOUSE) {
                return true;
            }
        }
        return false;
    }
    public static double calculateCombatStrength(City city) {
        return AffectCityFeatures(city);
    }

    private static double AffectCityFeatures(City city) {
        double combatStrength = 10;
        combatStrength += city.getCitizensCount();
        combatStrength += city.getCombatStrengthFromBuildings();
        if (getCityTile(city).getTerrain().getTerrainType() == TerrainEnum.HILL) {
            combatStrength *= (4.0 / 3.0);
        }
        if (getCityTile(city).getCombatUnit() != null) {
            combatStrength *= (5.0 / 4.0);
        }
        return combatStrength;
    }

    public static double calculateProduction(City city) {
        double cityProduction = 1;
        cityProduction =+ city.getProductionFromBuildings();
        cityProduction =+ getSourcesFromTiles("production",city);
        cityProduction =+ city.getProductionFromCheat();
        cityProduction =+ getFromResource("production",city);
        //todo : how we can be aware of happiness type of civilization ? changed logic !!
        return cityProduction;
    }

    private static double getSourcesFromTiles(String foodOrProductionOrGold,City city) {
        double production = 0;
        double food = 0;
        double gold = 0;
        for (Tile tile :
                getCityTiles(city)) {
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

    public static int calculateGold(City city) {
        int gold = 0;
        gold += city.getGoldFromBuildings();
        gold += getFromResource("gold",city);
        gold += getSourcesFromTiles("gold",city);
        gold *= city.getGoldRatioFromBuildings();
        return gold;
    }

    private static double getFromResource(String name,City city) {
        double gold = 0;
        double production = 0;
        double food = 0;
        for (ResourceEnum resource : getAchievedResources(city)) {
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

    // question: why is city method in City?
    public static ArrayList<ResourceEnum> getAchievedResources(City city) {
        ArrayList<ResourceEnum> resources = new ArrayList<>(List.of(ResourceEnum.RESET));
        for (Tile tile : getCityTiles(city)) {
            ResourceEnum resource = tile.getTerrain().getResource();
            // todoLater: getResource instead todo : how we can ????????? hard man این فقط مونده
            if (tile.isResourceAchievedBy(resource, city.getCivilization())) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public static int calculateFood(City city) {
        double food = city.getFoodFromBuildings() + 2;
        food += (double) city.getFoodFromCheat();
        food += getSourcesFromTiles("food",city);
        food += getFromResource("food",city);
        food -= (double) city.getCitizensCount() * 2;
        if (food > 0) {
            food *= checkForHappinessState();
        }
        return (int) food;
    }

    public static int numberOfUnassignedCitizens(City city) {
        return city.getCitizensCount() - getCitizens(city).size();
    }

    private static double checkForHappinessState() {
        //todo : how we can be aware of happiness type of civilization ? change logic !
        return 1;
    }

    public static void killCitizen(City city) {
        Collections.shuffle(getCityTiles(city));
        for (Tile tile :
                getCityTiles(city)) {
            if (tile.getCitizen() != null && tile != getCityTile(city)) {
                tile.setCitizen(null);
                return;
            }
        }
    }

    public static void checkCitizenBirth(City city) {
        if (city.getProductionQueue().size() != 0 && city.getProductionQueue().get(0) instanceof Unit unit && unit.getUnitType() == UnitEnum.SETTLER) {
            return;
        }
        city.setRemainedFoodForCitizen(city.getRemainedFoodForCitizen() +  calculateFood(city));
        if (city.getRemainedFoodForCitizen() > Constants.FOOD_NEEDED_TO_BORN_CITIZEN) {
            city.setCitizensCount(city.getCitizensCount() + 1);
            city.setRemainedFoodForCitizen(0);
        }
    }
    public static ArrayList<Tile> getCityTiles(City city) {
        // todo : never use why ?
        return new ArrayList<>(city.getTilesLocations().stream().map(GameController::getGameTile).toList());
    }
    public static ArrayList<Citizen> getCitizens(City city) {
        ArrayList<Citizen> citizens = new ArrayList<>();
        for (Tile tile : getCityTiles(city)) {
            if (tile.getCitizen() != null && tile.getCitizen().getCity() == city) {
                citizens.add(tile.getCitizen());
            }
        }
        return citizens;
    }
    public static Tile getCityTile(City city) {
        return GameController.getGameTile(city.getLocation());
    }
    public static int calculateDamage(double strengthDiff) {
        Random random = new Random();
        double random_number = (double) random.nextInt(75, 125) / 100;
        return (int) (25 * Math.exp(strengthDiff / (25.0 * random_number)));
    }







}
