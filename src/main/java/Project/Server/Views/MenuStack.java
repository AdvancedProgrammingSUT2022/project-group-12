package Project.Server.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.UnitEnum;
import Project.Models.Civilization;
import Project.Models.Database;
import Project.Models.Tiles.Tile;
import Project.Models.User;
import Project.Server.Controllers.GameController;
import Project.Utils.CommandResponse;
import Project.Utils.DatabaseQueryType;
import Project.Client.Views.WinCityDialog;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;
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
        System.out.println();
        WinCityDialog dialog = new WinCityDialog(message);
        Optional<String> answer = dialog.showAndWait();
        return answer.get();
    }

    public String getOptionForAttack() {
        System.out.println();
        WinCityDialog dialog = new WinCityDialog();
        Optional<String> answer = dialog.showAndWait();
        return answer.get();
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

    public String databaseQuery(DatabaseQueryType query, String[] params) {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        Gson gson = new Gson();
        // todo: get civ token instead of current
        return switch (query) {
            case GET_ALL_USERS -> gson.toJson(Database.getInstance().getAllUsers());
            case GET_ALL_USERNAMES -> gson.toJson(Database.getInstance().getAllUsernames());
            case GET_USER_BY_USERNAME -> gson.toJson(Database.getInstance().getUser(params[0]));
            case GET_CIV_TILES_LOCATIONS -> gson.toJson(GameController.getGame().getCurrentCivilization().getOwnedTiles().stream().map(Tile::getLocation).toList());
            case GET_CIV_RESOURCES -> gson.toJson(GameController.getGame().getCurrentCivilization().getResources());
            case GET_CIV_UNITS -> gson.toJson(GameController.getGame().getCurrentCivilization().getUnits());
            case GET_TILEGRID_SIZE -> gson.toJson(new HashMap<String,Integer>() {{
                put("Height",GameController.getGame().getTileGrid().getHeight());
                put("Width",GameController.getGame().getTileGrid().getWidth());
            }});
            case GET_ALL_UNITS_ENUMS -> gson.toJson(UnitEnum.values());
            case GET_ALL_BUILDING_ENUMS ->  gson.toJson(BuildingEnum.values());
            case GET_CURRENTCIV_HAPPINESS ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateHappiness());
            case GET_CURRENTCIV_FOOD ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationFood());
            case GET_CURRENTCIV_GOLD ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationGold());
            case GET_CURRENTCIV_SCIENCE ->  gson.toJson(GameController.getGame().getCurrentCivilization().calculateScience());
            case GET_CURRENTCIV_INWARWITH -> gson.toJson(GameController.getGame().getCivilizations().stream().filter(currentCiv::isInWarWith).map(Civilization::getName).collect(Collectors.toList()));
        };
    }
}