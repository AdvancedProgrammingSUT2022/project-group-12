package Project.Models;

import Project.Utils.NameAndToken;
import Project.Utils.Notifier;
import Project.Utils.Observer;

import java.util.ArrayList;

public class Chat  {

    private final String token;
    private final String name;
    private final ArrayList<Message> messages;
    private  ArrayList<User> users;


    public Chat(ArrayList<User> usersInChat, NameAndToken nameAndToken) {
        this.name = nameAndToken.getName();
        this.token = nameAndToken.getToken();
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

//    public void sendMessage(Message message) {
//        messages.add(message);
//    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", messages=" + messages ;
    }

    public String getToken() {
        return token;
    }

   public void addUser(User user){
        this.users.add(user);
   }
}