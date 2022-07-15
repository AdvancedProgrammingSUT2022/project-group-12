package Project.Server.Views;

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
        return MenuStack.getInstance().databaseQuery(query, params);
    }
}
