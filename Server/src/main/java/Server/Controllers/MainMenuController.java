package Server.Controllers;

import Project.Models.OpenGame;
import Project.Models.User;
import Project.Utils.RequestType;
import Server.Models.Database;
import Server.Models.Game;
import Server.Utils.CommandException;
import Server.Utils.MenuStackManager;
import Server.Views.MenuStack;

import java.util.ArrayList;

public class MainMenuController {

    // replaced with Database Query
    public static Game startNewGame(ArrayList<String> usernames, int width, int height,User currentUser) throws CommandException {
        return null;
//        ArrayList<User> users = new ArrayList<>();
//        for (String username : usernames) {
//            User user = Database.getInstance().getUser(username);
//            if (user == null) {
//                throw new CommandException(CommandResponse.USER_DOES_NOT_EXISTS, username);
//            } else {
//                users.add(user);
//            }
//        }
//        Game game = new Game(users, height, width);
//        Database.getInstance().addGame(game);
//        System.out.println("game.getUsers().contains() = " + game.getUsers().contains(currentUser));
//        GameController.setGame(game);
//        return game;
    }

    public static void bindUpdateNotifier(Game game, MenuStack userMenuStack, boolean isShow) {
        if (userMenuStack.getUpdateNotifier() != null) {
            game.bindTileUpdatesTo(userMenuStack.getUser(), userMenuStack.getUpdateNotifier(), isShow);
        } else {
            System.err.println("WARNING: no UpdateNotifier was bound by the client, updates will not be sent");
        }
    }

    public static void reloadClientScoreboards() {
        for (var menuStack : MenuStackManager.getInstance().getAllMenuStacks()) {
            if (menuStack.getUpdateNotifier() != null) menuStack.getUpdateNotifier().sendSimpleRequest(RequestType.RELOAD_SCOREBOARD);
        }
    }

    public static void logout(MenuStack menuStack) {
        System.out.println("logout ran for " + menuStack.getUser().getUsername());
        Database.getInstance().invalidateTokenFor(menuStack.getUser());
        Database.getInstance().removeFromAnyRoom(menuStack.getUser());
        MenuStackManager.getInstance().removeMenuStackOf(menuStack.getUser());
        MainMenuController.reloadClientScoreboards();
        menuStack.invalidate();
    }

    public static void reloadRoomForPlayers(OpenGame room) {
        for (User player : room.getPlayers()) {
            MenuStack menuStack = MenuStackManager.getInstance().getMenuStackOfUser(player);
            menuStack.getUpdateNotifier().sendSimpleRequest(RequestType.RELOAD_ROOM_PLAYERS);
        }
    }

    public static void leaveRoom(String userToken, String roomToken) {
        OpenGame room = Database.getInstance().getOpenGameByToken(roomToken);
        room.removePlayer(Database.getInstance().getUserByToken(userToken));
        if (room.getPlayers().size() == 0) {
            Database.getInstance().removeOpenGame(room);
            return;
        }
        MainMenuController.reloadRoomForPlayers(room);
    }

    public static void joinRoom(String userToken, String gameToken) {
        OpenGame room = Database.getInstance().getOpenGameByToken(gameToken);
        room.addPlayer(Database.getInstance().getUserByToken(userToken));
        MainMenuController.reloadRoomForPlayers(room);
    }
}
