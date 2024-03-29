package Server.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.ChatType;
import Project.Enums.UnitEnum;
import Project.Models.Chat;
import Project.Models.Location;
import Project.Models.OpenGame;
import Project.Models.Tiles.Tile;
import Project.Models.Tiles.TileGrid;
import Project.Models.Units.Unit;
import Project.Models.User;
import Project.Utils.CommandResponse;
import Project.Utils.DatabaseQueryType;
import Project.Utils.Pair;
import Project.Utils.RequestType;
import Server.Controllers.ChatController;
import Server.Controllers.CityHandler;
import Server.Controllers.GameController;
import Server.Controllers.MainMenuController;
import Server.Models.Civilization;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.MenuStackManager;
import Server.Utils.UpdateNotifier;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuStack {
    private final ArrayList<Menu> menus = new ArrayList<>();
    private final MainMenu mainMenu = new MainMenu();
    private final ProfileMenu profileMenu = new ProfileMenu();
    private final User user;
    private Game currentGame;
    private Scanner scanner;
    private HashMap<String, String> responseParameters = new HashMap<>();
    private UpdateNotifier updateNotifier = null;
    private boolean valid = true;

    public MenuStack(User user) {
        this.user = user;
    }

    public Scanner getScanner() {
        if (scanner == null) scanner = new Scanner(System.in);
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        if (this.scanner == null) this.scanner = scanner;
    }

    public boolean isEmpty() {
        return this.getMenus().isEmpty();
    }

    public CommandResponse runCommand(String line) {
        return this.getTopMenu().runCommand(line);
    }

    public User getUser() {
        return this.user;
    }

    public void pushMenu(Menu menu) {
        menu.setMenuStack(this);
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

    public String databaseQuery(DatabaseQueryType query, String[] params) {
        GameController.setGame(this.currentGame);
        Gson gson = new Gson();
        // todo: get civ token instead of current
        return switch (query) {
            case GET_SELECTED_CITY_GOLD -> gson.toJson(CityHandler.calculateGold(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_ASSIGNED_CITIZEN ->
                    gson.toJson(CityHandler.getAssignedCitizens(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_PRODUCTION ->
                    gson.toJson(CityHandler.calculateProduction(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_UNASSIGNED_CITIZEN ->
                    gson.toJson(CityHandler.numberOfUnassignedCitizens(GameMenu.getSelectedCity()));
            case GET_SELECTED_CITY_FOOD -> gson.toJson(CityHandler.calculateFood(GameMenu.getSelectedCity()));
            case GET_ALL_USERS -> gson.toJson(Database.getInstance().getAllUsers());
            case GET_ALL_USERNAMES -> gson.toJson(Database.getInstance().getAllUsernames());
            case GET_USER_BY_USERNAME -> gson.toJson(Database.getInstance().getUser(params[0]));
            case GET_CIV_TILES_LOCATIONS ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getOwnedTiles().stream().map(Tile::getLocation).toList());
            case GET_CIV_RESOURCES -> gson.toJson(GameController.getGame().getCurrentCivilization().getResources());
            case GET_CIV_UNITS -> gson.toJson(GameController.getGame().getCurrentCivilization().getUnits());
            case GET_CURRENTCIV_UNITS_NAMES ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getUnits().stream().map(e -> e.getUnitType().name()).collect(Collectors.toList()));
            case GET_CURRENTCIV_UNITS_LOCATIONS ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getUnits().stream().map(Unit::getLocation).collect(Collectors.toList()));
            case GET_TILEGRID_SIZE -> {
                TileGrid tileGrid = GameController.getGame().getTileGrid();
                yield gson.toJson(new Pair<>(tileGrid.getHeight(), tileGrid.getWidth()));
            }
            case ACCEPT_FRIEND_REQUEST -> {
                User sender = Database.getInstance().getUser(params[0]);
                User receiver = Database.getInstance().getUser(params[1]);
                sender.acceptFriend(receiver.getUsername());
                receiver.acceptFriend(sender.getUsername());
                yield gson.toJson(Database.getInstance().getAllUsernames());
            }
            case DENY_FRIEND_REQUEST -> {
                User sender = Database.getInstance().getUser(params[0]);
                User receiver = Database.getInstance().getUser(params[1]);
                sender.denyFriend(receiver.getUsername());
                receiver.denyFriend(sender.getUsername());
                yield gson.toJson(Database.getInstance().getAllUsernames());

            }
            case SEND_FRIEND_REQUEST -> {
                User sender = Database.getInstance().getUser(params[0]);
                User receiver = Database.getInstance().getUser(params[1]);
                if (sender == receiver) yield null;
                sender.addToWaitingOnFriendRequest(receiver.getUsername());
                receiver.sendFriendRequest(sender.getUsername());
                yield null;
            }
            case GET_ALL_UNITS_ENUMS -> gson.toJson(UnitEnum.values());
            case GET_ALL_BUILDING_ENUMS -> gson.toJson(BuildingEnum.values());
            case GET_CURRENTCIV_HAPPINESS ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().calculateHappiness());
            case GET_CURRENTCIV_FOOD ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationFood());
            case GET_CURRENTCIV_GOLD ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().calculateCivilizationGold());
            case GET_CURRENTCIV_SCIENCE ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().calculateScience());
            case GET_CURRENTCIV_INWARWITH ->
                    gson.toJson(GameController.getGame().getCivilizations().stream().filter(GameController.getGame().getCurrentCivilization()::isInWarWith).map(Civilization::getName).filter(name -> !(name.equals(GameController.getGame().getCurrentCivilization().getName()))).collect(Collectors.toList()));
            case GET_CURRENTCIV_INPEACEWITH ->
                    gson.toJson(GameController.getGame().getCivilizations().stream().filter(c -> !(GameController.getGame().getCurrentCivilization().isInWarWith(c))).map(Civilization::getName).filter(name -> !(name.equals(GameController.getGame().getCurrentCivilization().getName()))).collect(Collectors.toList()));
            case GET_NEIGHBORS_CURRENTCIV_NAMES ->
                    gson.toJson(GameController.getGame().getCivilizations().stream().filter(x -> !(x.getName().equals(GameController.getGame().getCurrentCivilization().getName()))).map(Civilization::getName).collect(Collectors.toList()));
            case GET_CIV_GOLD_BY_NAME ->
                    gson.toJson(GameController.getGame().getCivByName(params[0]).calculateCivilizationGold());
            case GET_CIV_RESOURCES_BY_NAME ->
                    gson.toJson(GameController.getGame().getCivByName(params[0]).getResources());
            case GET_CURRENTCIV_NOTIFICATIONS ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getNotifications());
            case GET_CURRENTCIV_CITIES ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getCities());
            case GET_CURRENTCIV_CITIES_LOCATION_BY_NAME ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getCityByName(params[0]).getLocation());
            case GET_SELECTED_UNIT -> gson.toJson(GameMenu.getSelectedUnit());
            case GET_SELECTED_CITY -> gson.toJson(GameMenu.getSelectedCity());
            case GET_TILE_BY_LOCATION ->
                    gson.toJson(GameController.getGameTile(new Location(Integer.parseInt(params[0]), Integer.parseInt(params[1]))));
            case GET_CURRENT_CIV_TECHNOLOGIES -> gson.toJson(GameController.getGame().getCurrentCivilization().getTechnologies());
            case GET_RESEARCHING_TECHNOLOGIES ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getResearchingTechnologies());
            case GET_RESEARCHING_TECHNOLOGY ->
                    gson.toJson(GameController.getGame().getCurrentCivilization().getResearchingTechnology());
            case GET_CURRENTCIV_NAME -> gson.toJson(GameController.getGame().getCurrentCivilization().getName());
            case GET_TILE_GRID_OF -> {
                Civilization civOfUser = GameController.getGame().getCivOfUser(Database.getInstance().getUserByToken(params[0]));
                yield gson.toJson((civOfUser == null) ? GameController.getGame().getTileGrid() : civOfUser.getRevealedTileGrid());
            }
            case GET_CIV_INITIAL_LOCATION ->
                    gson.toJson(GameController.getGame().getCivOfUser(Database.getInstance().getUserByToken(params[0])).getInitialLocation());
            case GET_SELECTED_CITY_PRODUCTION_QUEUE -> gson.toJson(GameMenu.getSelectedCity().getProductionQueue());
            case GET_AVAILABLE_BUILDINGS_NAME ->
                    gson.toJson(Arrays.stream(BuildingEnum.values()).filter(e -> GameController.getGame().getCurrentCivilization().hasRequierdTech(e.getRequiredTechs())).map(Enum::name).collect(Collectors.toList()));
            case GET_AVAILABLE_UNITS_NAME ->
                    gson.toJson(Arrays.stream(UnitEnum.values()).filter(e -> GameController.getGame().getCurrentCivilization().getTechnologies().contains(e.getRequiredTech()) && GameController.getGame().getCurrentCivilization().containsResource(e.getRequiredResource()))
                            .map(Enum::name).collect(Collectors.toList()));
            case GET_INVITED_GAMES_NAMES -> gson.toJson(Database.getInstance().getInvitedGamesFor(this.getUser()));
            case GET_IS_PLAYING_ALLOWED -> gson.toJson(GameController.getGame().getCurrentCivilization().civUser() == Database.getInstance().getUserByToken(params[0]));
            case GET_IS_USERNAME_ONLINE -> gson.toJson(Database.getInstance().isUsernameOnline(params[0]));
            case GET_CHAT_BY_NAME -> {
                Chat chat = Database.getInstance().getChats().stream().filter(e -> e.getUsernames().contains(user.getUsername())).filter(e -> e.getName().equals(params[0])).toList().get(0);
                yield gson.toJson(chat);
            }
            case GET_USER_CHATS_NAMES ->  gson.toJson(Database.getInstance().getChats().stream().filter(e -> e.getUsernames().contains(user.getUsername())).map(Chat::getName).collect(Collectors.toList()));
            case SEND_CHAT_TO_CREATE -> {
                ChatController.createNewChat(gson.fromJson(params[0],Chat.class));
                yield null;
            }
            case UPDATE_CHAT -> {
                ChatController.updateChat(gson.fromJson(params[0],Chat.class),gson.fromJson(params[1], ChatType.class));
                yield null;
            }
            case GET_ORIGINAL_TILE_GRID -> gson.toJson(GameController.getGame().getTileGrid());
            case GET_CIVS_SCORES -> gson.toJson(GameController.getGame().getCivNamesAndScore());
            case GET_CURRENT_YEAR -> gson.toJson(GameController.getGame().getCurrentYear());
            case GET_PUBLIC_CHAT -> gson.toJson(Database.getInstance().getPublicChat());
            case SEND_PUBLIC_CHAT_UPDATE, SEND_LOBBY_CHAT_UPDATE -> {
                System.out.println("server recieved chat and get");
                ChatController.updateChat(gson.fromJson(params[0],Chat.class),gson.fromJson(params[1], ChatType.class));
                yield null;
            }
            case CREATE_GAME -> {
                String adminToken = params[0];
                String name = params[1];
                int height = Integer.parseInt(params[2]);
                int width = Integer.parseInt(params[3]);
                int playerLimit = Integer.parseInt(params[4]);
                boolean isPrivate = Boolean.parseBoolean(params[5]);
                String token = Database.getInstance().createOpenGame(adminToken, name, height, width, playerLimit, isPrivate);
                yield gson.toJson(token);
            }
            case GET_OPENGAME_BY_TOKEN -> gson.toJson(Database.getInstance().getOpenGameByToken(params[0]));
            case LEAVE_ROOM -> {
                MainMenuController.leaveRoom(params[0], params[1]);
                yield null;
            }
            case JOIN_ROOM -> {
                MainMenuController.joinRoom(params[0], params[1]);
                yield null;
            }
            case GET_PUBLIC_OPENGAME_ITEMS_CHOOSE -> gson.toJson(new ArrayList<>(Database.getInstance().getSomePublicOpenGames().stream().map(this::openGame2itemMapper).toList()));
            case GET_RUNNING_GAME_ITEMS_CHOOSE -> gson.toJson(new ArrayList<>(Database.getInstance().getSomePublicRunningGames().stream().map(this::game2itemMapper).toList()));
            case GET_RUNNING_GAME_ITEMS_OF_USER -> gson.toJson(new ArrayList<>(Database.getInstance().getRunningGamesOf(Database.getInstance().getUser(params[0])).stream().map(this::game2itemMapper).toList()));
            case START_GAME -> {
                OpenGame openGame = Database.getInstance().getOpenGameByToken(params[0]);
                Game game = new Game(openGame.getToken(), openGame.getName(), openGame.isPrivate(), openGame.getPlayers(), openGame.getHeight(), openGame.getWidth());
                Database.getInstance().addGame(game);
                Database.getInstance().removeOpenGame(openGame);
                for (User player : game.getUsers()) {
                    MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(player);
                    ((MainMenu) menuStack.getTopMenu()).bindAndEnterNewGame(game);
                }
                ((GameMenu) this.getTopMenu()).initializeGameTurns();
                for (User player : game.getUsers()) {
                    MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(player);
                    menuStack.getUpdateNotifier().sendSimpleRequest(RequestType.GOTO_GAME_PAGE);
                }
                yield null;
            }
        };
    }

    private Pair<String, String> openGame2itemMapper(OpenGame openGame) {
        return new Pair<>(openGame.getName() + " (" + openGame.getPlayerLimit() + "x) : " + openGame.getPlayers().stream().map(User::getNickname).collect(Collectors.joining(" ")), openGame.getToken());
    }

    private Pair<String, String> game2itemMapper(Game game) {
        return new Pair<>(game.getName() + " (" + game.getUsers().size() + "x) : " + game.getUsers().stream().map(User::getNickname).collect(Collectors.joining(" ")), game.getToken());
    }

    public void invalidate() {
        this.valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public UpdateNotifier getUpdateNotifier() {
        return updateNotifier;
    }

    public void setUpdateNotifier(UpdateNotifier updateNotifier) {
        this.updateNotifier = updateNotifier;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
