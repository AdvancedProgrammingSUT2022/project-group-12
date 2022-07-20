package Project.Utils;

import Project.Models.Notifications.Notification;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import com.google.gson.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

public class CustomGson {
    private static final Gson instance;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Unit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(CombatUnit.class, new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notifier.class,new MyJsonDeserializer<>());
        gsonBuilder.registerTypeAdapter(Notification.class,new MyJsonDeserializer<>());
        instance = gsonBuilder.create();
    }

    private CustomGson() {

    }

    public static Gson getInstance() {
        return instance;
    }
}

class MyJsonDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
        JsonObject jsonObject = var1.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        Class<?> clazz;
        try {
            clazz = Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return var3.deserialize(var1,clazz);
    }
}
