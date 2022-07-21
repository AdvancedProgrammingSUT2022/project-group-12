package Server.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Utils.CommandResponse;
import Project.Utils.DatabaseQueryType;
import Server.Controllers.CityHandler;
import Server.Controllers.GameController;
import Server.Models.Civilization;
import Server.Models.Database;
import Server.Utils.RequestHandler;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class MenuStack {
    private static MenuStack instance = null;
    private final ArrayList<Menu> menus = new ArrayList<>();
    private final LoginMenu loginMenu = new LoginMenu();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private Scanner scanner;
    private User currentUser;
    private HashMap<String, String> responseParameters = new HashMap<>();
    private RequestHandler requestHandler = null;

    public MenuStack() {

    }

    public static MenuStack getInstance() {
        if (instance == null) setInstance(new MenuStack());
        return instance;
    }

    private static void setInstance(MenuStack instance) {
        MenuStack.instance = instance;
    }

    public Scanner getScanner() {
        if(scanner == null) scanner = new Scanner(System.in);
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        if (this.scanner == null) this.scanner = scanner;
    }

    public void setNullScanner() {
        this.scanner = null;
    }

    public boolean isEmpty() {
        return this.getMenus().isEmpty();
    }

    public void gotoLoginMenu() {
        this.currentUser = null;
        if (this.menus.isEmpty()) {
            this.pushMenu(this.loginMenu);
        } else {
            this.popMenu();
        }
    }

    public CommandResponse runCommand(String line) {
        return this.getTopMenu().runCommand(line);
    }

    public User getUser() {
        return this.currentUser;
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void pushMenu(Menu menu) {
        if (this.getTopMenu() != null) this.getTopMenu().resetShowName();
        this.getMenus().add(menu);
    }

    public Menu getTopMenu() {
        return this.getMenus().isEmpty() ? null : this.getMenus().get(this.getMenus().size() - 1);
    }

    private ArrayList<Menu> getMenus() {
        return menus;
    }

    public void popMenu() {
        this.getMenus().remove(this.getMenus().size() - 1);
    }

    public void gotoMainMenu() {
        this.pushMenu(this.mainMenu);
    }

    public void gotoProfileMenu() {
        this.pushMenu(this.profileMenu);
    }

    public String getOptionForAttack(String message) {
//        System.out.println();
//        WinCityDialog dialog = new WinCityDialog(message);
//        Optional<String> answer = dialog.showAndWait();
//        return answer.get();
        return "";
    }

    public String getOptionForAttack() {
//        System.out.println();
//        WinCityDialog dialog = new WinCityDialog();
//        Optional<String> answer = dialog.showAndWait();
//        return answer.get();
        return "";
    }


    public HashMap<String, String> getResponseParameters() {
        return responseParameters;
    }

    public void clearResponseParameters() {
        this.responseParameters.clear();
    }

    public void addResponseParameters(String key, String value) {
        this.responseParameters.put(key, value);
    }

    public String databaseQuery(DatabaseQueryType query, String[] params)  {

        Gson gson = new Gson();
        // todo: get civ token instead of current
        return switch (query) {
            case GET_SELECTED_CITY_GOLD -> gson.toJson(CityHandler.calculateGold(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_ASSIGNED_CITIZEN -> gson.toJson(CityHandler.getAssignedCitizens(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_PRODUCTION -> gson.toJson(CityHandler.calculateProduction(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_UNASSIGNED_CITIZEN -> gson.toJson(CityHandler.numberOfUnassignedCitizens(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_FOOD -> gson.toJson(CityHandler.calculateFood(GameMenu.getSelectedCity()));
            case GET_ALL_USERS -> gson.toJson(Database.getInstance().getAllUsers());
            case GET_ALL_USERNAMES -> gson.toJson(Database.getInstance().getAllUsernames());
            case GET_USER_BY_USERNAME -> gson.toJson(Database.getInstance().getUser(params[0]));
            case GET_CIV_TILES_LOCATIONS -> gson.toJson(GameController.getGame().getCurrentCivilization().getOwnedTiles().stream().map(Tile::getLocation).toList());
            case GET_CIV_RESOURCES -> gson.toJson(GameController.getGame().getCurrentCivilization().getResources());
            case GET_CIV_UNITS -> gson.toJson(GameController.getGame().getCurrentCivilization().getUnits());
            case GET_CURRENTCIV_UNITS_NAMES -> gson.toJson(GameController.getGame().getCurrentCivilization().getUnits().stream().map(e -> e.getUnitType().name()).collect(Collectors.toList()));
            case GET_CURRENTCIV_UNITS_LOCATIONS -> gson.toJson(GameController.getGame().getCurrentCivilization().getUnits().stream().map(Unit::getLocation).collect(Collectors.toList()));
            case GET_TILEGRID_SIZE -> {
                HashMap<String, Integer> hashMap = new HashMap<>();
                hashMap.put("Height", GameController.getGame().getTileGrid().getHeight());
                hashMap.put("Width", GameController.getGame().getTileGrid().getWidth());
                yield gson.toJson(hashMap);
            }
            case GET_ALL_UNITS_ENUMS -> gson.toJson(UnitEnum.values());
            case GET_ALL_BUILDING_ENUMS ->  gson.toJson(BuildingEnum.values());
            case GET_CURRENTCIV_HAPPINESS ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateHappiness());
            case GET_CURRENTCIV_FOOD ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationFood());
            case GET_CURRENTCIV_GOLD ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationGold());
            case GET_CURRENTCIV_SCIENCE ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateScience());
            case GET_CURRENTCIV_INWARWITH -> gson.toJson(GameController.getGame().getCivilizations().stream().filter(GameController.getGame().getCurrentCivilization()::isInWarWith).map(Civilization::getName).filter(name -> !(name.equals(GameController.getGame().getCurrentCivilization().getName()))).collect(Collectors.toList()));
            case GET_CURRENTCIV_INPEACEWITH -> gson.toJson(GameController.getGame().getCivilizations().stream().filter( c -> !(GameController.getGame().getCurrentCivilization().isInWarWith(c))).map(Civilization::getName).filter(name -> !(name.equals(GameController.getGame().getCurrentCivilization().getName()))).collect(Collectors.toList()));
            case GET_NEIGHBORS_CURRENTCIV_NAMES -> gson.toJson(GameController.getGame().getCivilizations().stream().filter(x -> !(x.getName().equals(GameController.getGame().getCurrentCivilization().getName()))).map(Civilization::getName).collect(Collectors.toList()));
            case GET_CIV_GOLD_BY_NAME  -> gson.toJson(GameController.getGame().getCivByName(params[0]).calculateCivilizationGold());
            case GET_CIV_RESOURCES_BY_NAME -> gson.toJson(GameController.getGame().getCivByName(params[0]).getResources());
            case GET_CURRENTCIV_NOTIFICATIONS -> gson.toJson(GameController.getGame().getCurrentCivilization().getNotifications());
            case GET_CURRENTCIV_CITIES_NAMES ->  gson.toJson(GameController.getGame().getCurrentCivilization().getCities().stream().map( city -> city.getName()).collect(Collectors.toList()));
            case GET_CURRENTCIV_CITIES_LOCATION_BY_NAME -> gson.toJson(GameController.getGame().getCurrentCivilization().getCityByName(params[0]).getLocation());
            case GET_SELECTED_UNIT -> gson.toJson(GameMenu.getSelectedUnit());
            case GET_SELECTED_CITY -> gson.toJson(GameMenu.getSelectedCity());
            case GET_TILE_BY_LOCATION -> gson.toJson(GameController.getGameTile(new Location(Integer.parseInt(params[0]), Integer.parseInt(params[1]))));
            case GET_TECHNOLOGIES -> gson.toJson(GameController.getGame().getCurrentCivilization().getTechnologies());
            case GET_RESEARCHING_TECHNOLOGIES -> gson.toJson(GameController.getGame().getCurrentCivilization().getResearchingTechnologies());
            case GET_RESEARCHING_TECHNOLOGY -> gson.toJson(GameController.getGame().getCurrentCivilization().getResearchingTechnology());
            case GET_CURRENTCIV_NAME -> gson.toJson(GameController.getGame().getCurrentCivilization().getName());
            case GET_TILE_GRID -> gson.toJson(GameController.getGame().getCurrentCivilization().getRevealedTileGrid());
            case GET_CIV_CAMERA_LOCATION -> gson.toJson(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
            case GET_SELECTED_CITY_PRODUCTION_QUEUE -> gson.toJson(GameMenu.getSelectedCity().getProductionQueue());
            case GET_AVAILABLE_BUILDINGS_NAME -> gson.toJson(Arrays.stream(BuildingEnum.values()).filter(e -> GameController.getGame().getCurrentCivilization().hasRequierdTech(e.getRequiredTechs())).map(Enum::name).collect(Collectors.toList())) ;
            case GET_AVAILABLE_UNITS_NAME -> gson.toJson(Arrays.stream(UnitEnum.values()).filter(e -> GameController.getGame().getCurrentCivilization().hasRequierdTech(Collections.singletonList(e.getRequiredTech())) && GameController.getGame().getCurrentCivilization().containsResource(e.getRequiredResource()))
                                                    .map(Enum::name).collect(Collectors.toList())) ;
        };
        }

    public void setCommandReceiver(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public RequestHandler getCommandReceiver() {
        return requestHandler;
    }
}
