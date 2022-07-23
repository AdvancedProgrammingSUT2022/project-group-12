package Client.Utils;

import Client.Views.*;
import Project.Models.Location;
import Project.Models.OpenGame;
import Project.Utils.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class UpdateTracker implements Runnable {
    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            System.out.println("updater socket connected to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("updater socket can't connect to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
            return;
        }
        Connection connection = new Connection(socket);

        Request socketRequest = new Request(RequestType.SET_SOCKET_FOR_UPDATE_TRACKER, MenuStack.getInstance().getCookies().getLoginToken());
        new Connection(socket).send(CustomGson.getInstance().toJson(socketRequest));

        System.out.println("updater bound with server successfully");
        while (true) {
            String update = connection.listen();
            Request request  = CustomGson.getInstance().fromJson(update, Request.class);
            if(request.getRequestType() == RequestType.UPDATE_TILE) {
                Location location = request.getRequestTile().getLocation();
                Platform.runLater(() -> GameView.updateTile(location, request.getRequestTile()));
            } else if(request.getRequestType() == RequestType.SHOW_NEW_TECHNOLOGY_ACHIEVED){
                String techName = request.getParameter("TechName");
                Platform.runLater(() -> MenuStack.getInstance().showSuccess(techName + " discovered successfully"));
            } else if(request.getRequestType() == RequestType.ALLOW_PLAY_TURN){
                Platform.runLater(() -> {
                    GameView.reloadTechnologies();
                    GameView.reloadHexGrid();
                    GameView.changeCoverVisibility(false);
                });
            } else if (request.getRequestType() == RequestType.UPDATE_CHAT) {
                if(MenuStack.getInstance().getTopMenu().getController() instanceof ChatView chatView) {
                   Platform.runLater(() -> chatView.updateChat(request.getRequestChat()));
                } else if (MenuStack.getInstance().getTopMenu().getController() instanceof ChatSelectView chatSelectView) {
                   Platform.runLater(() -> chatSelectView.updateChats(request.getRequestChat().getName()));
                }
            } else if(request.getRequestType() == RequestType.RELOAD_TILEGRID){
                Platform.runLater(() -> {
                    GameView.reloadTechnologies();
                    GameView.reloadHexGrid();
                });
            } else if(request.getRequestType() == RequestType.RELOAD_SCOREBOARD){
                System.out.println("score rel");
                Platform.runLater(ScoreboardView::reload);
            } else if (request.getRequestType() == RequestType.END_GAME) {
                if(MenuStack.getInstance().getTopMenu().getController() instanceof GameView gameView) {
                    Platform.runLater(() -> gameView.endGame(new Gson().fromJson(request.getParameter("civsScore"), new TypeToken<HashMap<String, Integer>>() {
                    }.getType())));
                }
            } else if(request.getRequestType() == RequestType.UPDATE_YEAR){
                if(MenuStack.getInstance().getTopMenu().getController() instanceof GameView gameView) {
                    Platform.runLater(() -> gameView.reloadDate(request.getParameter("currentDate")));
                }
            } else if(request.getRequestType() == RequestType.UPDATE_PUBLIC_CHAT){
                if(MenuStack.getInstance().getTopMenu().getController() instanceof PublicChatView publicChatView) {
                    Platform.runLater(() -> publicChatView.updateChat(request.getRequestChat()));
                }
            } else if (request.getRequestType() == RequestType.RELOAD_ROOM_PLAYERS) {
                Menu menu = MenuStack.getInstance().getTopMenu();
                if (menu.getController() instanceof GameRoomView gameRoomView) {
                    OpenGame newRoom = DatabaseQuerier.getOpenGameByToken(gameRoomView.getOpenGame().getToken());
                    Platform.runLater(() -> gameRoomView.updateOpenGame(newRoom));
                }
            } else if (request.getRequestType() == RequestType.GOTO_GAME_PAGE) {
                Platform.runLater(() -> {
                    MenuStack.getInstance().pushMenu(Menu.loadFromFXML("GamePage"));
                });
            }
        }
    }
}
