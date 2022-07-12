package Project.ServerViews;

import Project.Utils.CommandResponse;

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
        return MenuStack.getInstance().runCommand(line);
    }

    public HashMap<String, String> getParameters() {
        return MenuStack.getInstance().getResponseParameters();
    }

    public String getParameter(String key) {
        return MenuStack.getInstance().getResponseParameters().get(key);
    }
}
