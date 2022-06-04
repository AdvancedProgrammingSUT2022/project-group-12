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

    public void deleteMessage(Message message) {
        for (Message searchingMessage : messages) {
            if (searchingMessage.getMessage().equals(message.getMessage()) &&
                    searchingMessage.getSender().equals(message.getSender())) {
                messages.remove(searchingMessage);
                return;
            }
        }
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

    public void sendMessage(Message message) {
        messages.add(message);
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}