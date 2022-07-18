package Project.Server.Views;

import Project.Client.Views.GameView;
import Project.Utils.CommandResponse;
import Project.Utils.DatabaseQueryType;
import Project.Utils.ResponseException;

import java.util.HashMap;

public class RequestHandler {
    private static final RequestHandler instance = new RequestHandler();

    private RequestHandler() {
        MenuStack.getInstance().gotoLoginMenu();
    }

    public static RequestHandler getInstance() {
        return instance;
    }

    public CommandResponse handle(String line) {
        System.out.println("Request: " + line);
        try {
            MenuStack.getInstance().runCommand(line);
        } catch (ResponseException e) {
            if(e.getResponse().isOK()){
                e.getResponse().setMessage(e.getSuccessMessage());
            }
            if (GameView.instance != null) {
                GameView.reloadHexGrid();
            }
            return e.getResponse();
        }
        return null;
    }

    public HashMap<String, String> getParameters() {
        return MenuStack.getInstance().getResponseParameters();
    }

    public String getParameter(String key) {
        return MenuStack.getInstance().getResponseParameters().get(key);
    }

    public String databaseQuery(DatabaseQueryType query, String... params) {
        String response = MenuStack.getInstance().databaseQuery(query, params);
//        System.out.println("databaseR: " + response);
        return response;
    }
}
