package Project.Client.Utils;

import Project.Enums.BuildingEnum;
import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Server.Utils.DatabaseQueryType;
import Project.Server.Views.RequestHandler;
import Project.SharedModels.Cities.City;
import Project.SharedModels.Location;
import Project.SharedModels.Notification;
import Project.SharedModels.Resource;
import Project.SharedModels.Tiles.Tile;
import Project.SharedModels.Tiles.TileGrid;
import Project.SharedModels.Units.CombatUnit;
import Project.SharedModels.Units.Unit;
import Project.SharedModels.User;
import Project.SharedUtils.Notifier;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseQuerier {

    public static ArrayList<User> getAllUsers() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERS);
        TypeToken<ArrayList<User>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getAllUsernames() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_USERNAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static User getUser(String username) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_USER_BY_USERNAME, username);
        TypeToken<User> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Location> getCivTilesLocations() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_TILES_LOCATIONS);
        TypeToken<ArrayList<Location>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Unit> getCurrentCivilizationUnits() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_UNITS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<Unit>>() {
        }.getType());
    }

    public static ArrayList<Resource> getCivResources() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_RESOURCES);
        TypeToken<ArrayList<Resource>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static HashMap<String, Integer> getTileGridSize() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_TILEGRID_SIZE);
        return new Gson().fromJson(json, new TypeToken<HashMap<String, Integer>>() {
        }.getType());

    }

    public static ArrayList<UnitEnum> getUnitEnums() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_UNITS_ENUMS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<UnitEnum>>() {
        }.getType());
    }

    public static ArrayList<BuildingEnum> getBuildingEnums() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_ALL_BUILDING_ENUMS);
        return new Gson().fromJson(json, new TypeToken<ArrayList<BuildingEnum>>() {
        }.getType());
    }

    public static int getHappinessOfCurrentCiv() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_HAPPINESS);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static int getFoodOfCurrentCiv() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_FOOD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static int getGoldOfCurrentCiv() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_GOLD);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static int getScienceOfCurrentCiv() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_SCIENCE);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static ArrayList<String> getCurrentCivInWarWith() {

        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_INWARWITH);
        return new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public static List<String> getNeighborsCivsName() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_NEIGHBORS_CURRENTCIV_NAMES);
        return new Gson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    public static int getGoldCivilizationByName(String name) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_GOLD_BY_NAME, name);
        return new Gson().fromJson(json, new TypeToken<Integer>() {
        }.getType());
    }

    public static ArrayList<Resource> getCivResourcesByName(String name) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CIV_RESOURCES_BY_NAME, name);
        TypeToken<ArrayList<Resource>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<Notification> getCurrentCivNotifications() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_NOTIFICATIONS);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Notification.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        TypeToken<ArrayList<Notification>> typeToken = new TypeToken<>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    public static ArrayList<Location> getCurrentCivUnitsLocation() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_UNITS_LOCATIONS);
        TypeToken<ArrayList<Location>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getCurrentCivCitiesNames() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_CITIES_NAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static ArrayList<String> getCurrentCivUnitsNames() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_UNITS_NAMES);
        TypeToken<ArrayList<String>> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static Location getCurrentCivLocationByName(String cityName) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_CITIES_LOCATION_BY_NAME, cityName);
        TypeToken<Location> typeToken = new TypeToken<>() {
        };
        return new Gson().fromJson(json, typeToken.getType());
    }

    public static Unit getSelectedUnit() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_UNIT);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Unit.class);
    }

    public static Tile getTileByLocation(String row, String col) {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_TILE_BY_LOCATION, row, col);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notifier.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Tile.class);
    }

    public static City getSelectedCity() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_SELECTED_CITY);
        return new Gson().fromJson(json, City.class);
    }

    public static ArrayList<TechnologyEnum> getTechnologies() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_TECHNOLOGIES);
        return new Gson().fromJson(json, new TypeToken<ArrayList<TechnologyEnum>>() {
        }.getType());
    }

    public static String getCurrentCivName() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_NAME);
        return new Gson().fromJson(json, String.class);
    }

    public static TechnologyEnum getResearchingTechnology() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_RESEARCHING_TECHNOLOGY);
        return new Gson().fromJson(json, new TypeToken<TechnologyEnum>() {
        }.getType());
    }

    public static HashMap<TechnologyEnum, Integer> getResearchingTechnologies() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_RESEARCHING_TECHNOLOGIES);
        return new Gson().fromJson(json, new TypeToken<HashMap<TechnologyEnum, Integer>>() {
        }.getType());
    }

    public static TileGrid getTileGrid() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_TILE_GRID);
//        System.out.println(json);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notifier.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        Gson gson = gsonBuilder.create();
        TypeToken<TileGrid> typeToken = new TypeToken<>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    public static List<String> getCurrentCivInPeaceWith() {
        String json = RequestHandler.getInstance().databaseQuery(DatabaseQueryType.GET_CURRENTCIV_INPEACEWITH);
        return new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
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
