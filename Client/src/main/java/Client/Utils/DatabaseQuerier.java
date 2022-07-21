package Client.Utils;

import Project.Enums.BuildingEnum;
import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Models.*;
import Project.Models.Cities.City;
import Project.Models.Notifications.Notification;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.DatabaseQueryType;
import Project.Utils.Notifier;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseQuerier {

    public static ArrayList<User> getAllUsers() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERS);
        TypeToken<ArrayList<User>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getAllUsernames() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERNAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static User getUser(String username) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_USER_BY_USERNAME, username);
        TypeToken<User> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Location> getCivTilesLocations() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_TILES_LOCATIONS);
        TypeToken<ArrayList<Location>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Unit> getCurrentCivilizationUnits() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_UNITS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<Unit>>() {
        }.getType());
    }

    public static ArrayList<Resource> getCivResources() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_RESOURCES);
        TypeToken<ArrayList<Resource>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static HashMap<String, Integer> getTileGridSize() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_TILEGRID_SIZE);
        return new Gson().fromJson(json, new TypeToken<HashMap<String, Integer>>() {
        }.getType());

    }

    public static ArrayList<UnitEnum> getUnitEnums() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_UNITS_ENUMS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<UnitEnum>>() {
        }.getType());
    }

    public static ArrayList<BuildingEnum> getBuildingEnums() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_BUILDING_ENUMS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<BuildingEnum>>() {
        }.getType());
    }

    public static int getHappinessOfCurrentCiv() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_HAPPINESS);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static void sendFriendRequest(String sender, String receiver) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.SEND_FRIEND_REQUEST,sender,receiver);
    }
    public static void acceptFriendRequest(String sender, String receiver) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.ACCEPT_FRIEND_REQUEST,sender,receiver);
    }
    public static void denyFriendRequest(String sender, String receiver) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.DENY_FRIEND_REQUEST,sender,receiver);
    }

    public static int getFoodOfCurrentCiv() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_FOOD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static int getGoldOfCurrentCiv() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_GOLD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static int getScienceOfCurrentCiv() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_SCIENCE);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static ArrayList<String> getCurrentCivInWarWith() {

        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_INWARWITH);
        return new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public static List<String> getNeighborsCivsName() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_NEIGHBORS_CURRENTCIV_NAMES);
        return new Gson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    public static int getGoldCivilizationByName(String name) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_GOLD_BY_NAME, name);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static ArrayList<Resource> getCivResourcesByName(String name) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_RESOURCES_BY_NAME, name);
        TypeToken<ArrayList<Resource>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Notification> getCurrentCivNotifications() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_NOTIFICATIONS);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Notification.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        TypeToken<ArrayList<Notification>> typeToken = new TypeToken<>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    public static ArrayList<Location> getCurrentCivUnitsLocation() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_UNITS_LOCATIONS);
        TypeToken<ArrayList<Location>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getCurrentCivCitiesNames() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_CITIES_NAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getCurrentCivUnitsNames() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_UNITS_NAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static Location getCurrentCivLocationByName(String cityName) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_CITIES_LOCATION_BY_NAME, cityName);
        TypeToken<Location> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static Unit getSelectedUnit() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_UNIT);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Unit.class);
    }

    public static Tile getTileByLocation(String row, String col) {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_TILE_BY_LOCATION, row, col);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notifier.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Tile.class);
    }

    public static City getSelectedCity() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY);
        return new Gson().fromJson(json, City.class);
    }

    public static ArrayList<TechnologyEnum> getTechnologies() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_TECHNOLOGIES);
        return new Gson().fromJson(json, new TypeToken<ArrayList<TechnologyEnum>>() {
        }.getType());
    }

    public static String getCurrentCivName() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_NAME);
        return new Gson().fromJson(json, String.class);
    }

    public static TechnologyEnum getResearchingTechnology() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_RESEARCHING_TECHNOLOGY);
        return new Gson().fromJson(json, new TypeToken<TechnologyEnum>() {
        }.getType());
    }

    public static HashMap<TechnologyEnum, Integer> getResearchingTechnologies() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_RESEARCHING_TECHNOLOGIES);
        return new Gson().fromJson(json, new TypeToken<HashMap<TechnologyEnum, Integer>>() {
        }.getType());
    }

    public static TileGrid getTileGrid() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_TILE_GRID);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notifier.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Production.class,new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        TypeToken<TileGrid> typeToken = new TypeToken<>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    public static List<String> getCurrentCivInPeaceWith() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_INPEACEWITH);
        return new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public static Location getCivCameraLocation() {
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_CAMERA_LOCATION);
        return new Gson().fromJson(json, new TypeToken<Location>() {
        }.getType());
    }
    public static int getSelectedCityGold(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_GOLD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }
    public static double getSelectedCityProduction(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_PRODUCTION);
        return new Gson().fromJson(json, new TypeToken<Double>() {
        }.getType());
    }
    public static int getSelectedCityFood(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_FOOD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }
    public static int getSelectedCityNumberOfUnAssignedCitizen(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_UNASSIGNED_CITIZEN);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }
    public static ArrayList<Citizen> getSelectedCityAssignedCitizens(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_ASSIGNED_CITIZEN);
        return new Gson().fromJson(json, new TypeToken<ArrayList<Citizen>>() {
        }.getType());
    }
    public static ArrayList<Production> getSelectedCityProductionQueue(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY_PRODUCTION_QUEUE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Production.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, new TypeToken<ArrayList<Production>>() {
        }.getType());
    }
    public static ArrayList<String> getCurrentCivAvailableProductions(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_AVAILABLE_BUILDINGS_NAME);
        String json2 = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_AVAILABLE_UNITS_NAME);
        ArrayList<String> productsName = new ArrayList<>(new Gson().fromJson(json,new TypeToken<ArrayList<String>>(){}.getType()));
        productsName.addAll(new Gson().fromJson(json2,new TypeToken<ArrayList<String>>(){}.getType()));
        return productsName;
    }
    public static ArrayList<String> getInvitedGames(){
        String json = RequestSender.getInstance().databaseQuery(DatabaseQueryType.GET_INVITED_GAMES_NAMES);
        return new Gson().fromJson(json,new TypeToken<ArrayList<String>>(){}.getType());
    }
}
class MyJsonDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
        JsonObject jsonObject = var1.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        Class<?> clazz;
        try {
            clazz = Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return var3.deserialize(var1,clazz);
    }

}
