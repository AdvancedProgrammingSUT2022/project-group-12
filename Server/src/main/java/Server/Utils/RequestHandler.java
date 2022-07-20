package Server.Utils;

import Project.Utils.*;
import Server.Views.MenuStack;
import com.google.gson.Gson;

import java.net.Socket;

public class RequestHandler {
    private final Connection connection;

    public RequestHandler(Socket socket) {
        this.connection = new Connection(socket);
    }

    public void handleRequest() {
        String json = this.connection.listen();
        Request request = new Gson().fromJson(json, Request.class);
        if (request.getRequestType() == RequestType.RUN_SERVERVIEW_COMMAND) {
            String command = request.getParameter("command");
            try {
                MenuStack.getInstance().runCommand(command);
            } catch (ResponseException e) {
                CommandResponse commandResponse = e.getResponse();
                if (commandResponse.isOK()) commandResponse.setMessage(e.getSuccessMessage());
                Response response = new Response(commandResponse);
                this.connection.send(new Gson().toJson(response));
            }
        } else if (request.getRequestType() == RequestType.QUERY_DATABASE) {
            DatabaseQueryType queryType = request.getQueryType();
            String[] queryParams = request.getQueryParams();
            String databaseResponse = MenuStack.getInstance().databaseQuery(queryType, queryParams);
            this.connection.send(databaseResponse);
        }
    }

    public void run() {
        while (true) {
            this.handleRequest();
        }
    }
}
