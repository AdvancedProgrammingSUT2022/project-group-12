package Models;

import java.util.ArrayList;

public class Chat {
    private final String name;
    private final ArrayList<Message> messages;

    public Chat(ArrayList<User> usersInChat, String name) {
        this.name = name;
        this.messages = new ArrayList<>();
        for (User user : usersInChat) {
            usersInChat.remove(user);
            user.startChat(name, usersInChat);
        }
        //todo : improve
    }

    public String getName() {
        return this.name;
    }

    public void sendMessage(String message, String username) {
        messages.add(new Message(username, message));
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}
