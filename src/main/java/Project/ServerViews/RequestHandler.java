package Project.ServerViews;

import Project.Utils.CommandResponse;

public class RequestHandler {
    private static final RequestHandler instance = new RequestHandler();
    private RequestHandler() {
        MenuStack.getInstance().gotoLoginMenu();
    }

    public static RequestHandler getInstance() {
        return instance;
    }

    public CommandResponse handle(String line) {
        return MenuStack.getInstance().runCommand(line);
    }
}
