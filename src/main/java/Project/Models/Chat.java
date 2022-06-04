package Project.Models;

import java.util.ArrayList;

public class Chat {
    private final String name;
    private final ArrayList<Message> messages;
    private final ArrayList<User> users;

    public Chat(ArrayList<User> usersInChat, String name) {
        this.name = name;
        this.messages = new ArrayList<>();
        this.users = usersInChat;
    }


    public String getName() {
        return this.name;
    }

    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : this.users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public void sendMessage(String message, String username) {
        Message newMessage = new Message(username, message);
        messages.add(newMessage);

    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}
