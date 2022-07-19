package Client.Utils;

import Project.Utils.CommandResponse;
import Project.Utils.Request;
import Project.Utils.RequestType;
import Project.Utils.Response;
import com.google.gson.Gson;

import java.net.Socket;
import java.util.HashMap;

public class RequestHandler {
    private static final RequestHandler instance = new RequestHandler();
    private static Connection connection = null;

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
        Request request = new Request(RequestType.RUN_SERVERVIEW_COMMAND, "???");
        connection.send(new Gson().toJson(request));
        String responseJson = connection.listen();
        Response response = new Gson().fromJson(responseJson, Response.class);
        CommandResponse commandResponse = response.getCommandResponse();
//            if(e.getResponse().isOK()){
//                e.getResponse().setMessage(e.getSuccessMessage());
//            }
//            return e.getResponse();
//        return null;
        return commandResponse;
    }

    public HashMap<String, String> getParameters() {
        return MenuStack.getInstance().getResponseParameters();
    }

    public String getParameter(String key) {
        return MenuStack.getInstance().getResponseParameters().get(key);
    }

    public String databaseQuery(RequestType query, String... params) {
        String response = MenuStack.getInstance().databaseQuery(query, params);
//        System.out.println("databaseR: " + response);
        return response;
    }
}
