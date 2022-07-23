package Server.Utils;

import Project.Models.User;
import Project.Utils.*;
import Server.Controllers.LoginMenuController;
import Server.Controllers.MainMenuController;
import Server.Models.Database;
import Server.Views.MenuStack;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class RequestHandler implements Runnable {
    private final Connection connection;
    private String connectedToken = null;

    public RequestHandler(Socket socket) {
        this.connection = new Connection(socket);
    }

    private void sendCommandResponse(CommandResponse commandResponse) {
        System.out.println("Response = " + commandResponse);
        Response response = new Response(commandResponse);
        if (commandResponse.isOK())
            response.addParameter("successMessage", commandResponse.getMessage());
        this.sendResponse(response);
    }

    private void sendResponse(Response response) {
        this.connection.send(new Gson().toJson(response));
    }

    public void handleRequest() throws IOException {
        String json = this.connection.listen();
        if (json == null) throw new IOException();
//        System.out.println("json: " + json);
        Request request = new Gson().fromJson(json, Request.class);
        System.out.println("ReqType = " + request.getRequestType());
        switch (request.getRequestType()) {
            case CREATE_USER -> {
                String username = request.getParameter("username");
                String nickname = request.getParameter("nickname");
                String password = request.getParameter("password");
                try {
                    LoginMenuController.createUser(username, nickname, password);
                    this.sendCommandResponse(CommandResponse.OK);
                } catch (CommandException e) {
                    this.sendCommandResponse(e.getResponse());
                }
            }
            case LOGIN_USER -> {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                try {
                    User user = LoginMenuController.loginUser(username, password);
                    String token = Database.getInstance().receiveTokenFor(user);
                    this.connectedToken = token;
                    MenuStackManager.getInstance().addMenuStackFor(token, user);
                    user.setLastLoginDate(new Date(System.currentTimeMillis()));
                    MainMenuController.reloadClientScoreboards();
                    Response response = new Response(CommandResponse.OK);
                    response.addParameter("loginToken", token);
                    this.sendResponse(response);
                } catch (CommandException e) {
                    this.sendCommandResponse(e.getResponse());
                }
            }
            case SET_SOCKET_FOR_UPDATE_TRACKER -> {
                MenuStack menuStack = MenuStackManager.getInstance().getMenuStackByToken(request.getToken());
                if (menuStack == null) {
                    this.sendCommandResponse(CommandResponse.INVALID_TOKEN);
                    break;
                }
                menuStack.setUpdateNotifier(new UpdateNotifier(this.connection));
                this.sendCommandResponse(CommandResponse.OK);
            }
            case RUN_SERVERVIEW_COMMAND -> {
                MenuStack menuStack = MenuStackManager.getInstance().getMenuStackByToken(request.getToken());
                if (menuStack == null) {
                    this.sendCommandResponse(CommandResponse.INVALID_TOKEN);
                    break;
                }
                String command = request.getParameter("command");
                System.out.println("command received: " + command);
                CommandResponse commandResponse = CommandResponse.OK;
                try {
                    menuStack.runCommand(command);
                } catch (ResponseException e) {
                    commandResponse = e.getResponse();
                    if (commandResponse.isOK()) {
                        commandResponse.setMessage(e.getSuccessMessage());
                    }
                }
                System.err.println("command ran without answer!");
                this.sendCommandResponse(commandResponse);
            }
            case QUERY_DATABASE -> {
                MenuStack menuStack = MenuStackManager.getInstance().getMenuStackByToken(request.getToken());
                if (menuStack == null) {
                    this.sendCommandResponse(CommandResponse.INVALID_TOKEN);
                    break;
                }
                DatabaseQueryType queryType = request.getQueryType();
                String[] queryParams = request.getQueryParams();
                String databaseResponse = menuStack.databaseQuery(queryType, queryParams);
                this.connection.send(databaseResponse);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                this.handleRequest();
            } catch (IOException e) {
                System.out.println("a client socket disconnected");
                if (this.connectedToken != null) {
                    MenuStack menuStack = MenuStackManager.getInstance().getMenuStackByToken(this.connectedToken);
                    MainMenuController.logout(menuStack);
                }
                this.connection.closeSocket();
                break;
            }
        }
    }
}
