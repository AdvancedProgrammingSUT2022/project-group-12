package Client.Utils;

import Client.Views.MenuStack;
import Project.Utils.*;
import com.google.gson.Gson;

import java.net.Socket;
import java.util.HashMap;

public class RequestHandler {
    private static final RequestHandler instance = new RequestHandler();
    private static Connection connection = null;
    private HashMap<String, String> responseParameterCache = null;

    private RequestHandler() {

    }

    public static RequestHandler getInstance() {
        return instance;
    }

    public static void initialize(Socket socket) {
        connection = new Connection(socket);
    }

    public CommandResponse handle(String line) {
        System.out.println("Request: " + line);
        Request request = new Request(RequestType.RUN_SERVERVIEW_COMMAND, MenuStack.getInstance().getCookies().getLoginToken());
        connection.send(new Gson().toJson(request));
        String responseJson = connection.listen();
        Response response = new Gson().fromJson(responseJson, Response.class);
        CommandResponse commandResponse = response.getCommandResponse();
        this.responseParameterCache = response.getParameters();
//            if(e.getResponse().isOK()){
//                e.getResponse().setMessage(e.getSuccessMessage());
//            }
//            return e.getResponse();
//        return null;
        return commandResponse;
    }

    public String getParameter(String key) {
        return responseParameterCache.get(key);
    }

    public String databaseQuery(DatabaseQueryType query, String... params) {
        Request request = new Request(RequestType.QUERY_DATABASE, MenuStack.getInstance().getCookies().getLoginToken());
        request.setQueryType(query);
        request.setQueryParams(params);
        connection.send(new Gson().toJson(request));
        String responseJson = connection.listen();
        System.out.println("databaseR: " + responseJson);
        return responseJson;
    }
}