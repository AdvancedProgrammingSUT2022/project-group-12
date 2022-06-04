package Project.Models;

import java.util.ArrayList;

public class Chat {
    //    private static int counter;
    private final String name;
    private final ArrayList<Message> messages;
    private ArrayList<User> users;

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
        messages.add(new Message(username, message));
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}
