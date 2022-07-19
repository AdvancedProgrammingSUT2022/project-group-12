package Project.Utils;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final RequestType requestType;
    private final Map<String, String> parameters = new HashMap<>();
    private final String token;

    public Request(RequestType requestType, String token) {
        this.requestType = requestType;
        this.token = token;
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getToken() {
        return token;
    }
}
