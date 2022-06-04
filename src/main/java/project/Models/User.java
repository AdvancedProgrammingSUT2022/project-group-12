package project.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private final String username;
    private String nickname;
    private String password;
    private int score;
    private HashMap<String, Chat> chats;
    private Chat currentChat;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.chats = new HashMap<>();
        this.score = 0;
        Database data = Database.getInstance();
        data.addUser(this);
        data.serialize();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean passwordMatchCheck(String password) {
        return this.password.equals(password);
    }

    public String getNickname() {
        return this.nickname;
    }


    public int getScore() {
        return this.score;
    }

    public void setScore(int newScore) {
        this.score = newScore;
        Database.getInstance().serialize();
    }

    public void changePassword(String newPass) {
        this.password = newPass;
        Database.getInstance().serialize();
    }

    public void startChat(String name, ArrayList<User> users) {
        if (!chats.containsKey(name))
            chats.put(name, new Chat(users, name));
    }

    public void setCurrentChat(String name) {
        this.currentChat = chats.get(name);
    }

    public Chat getChat() {
        return this.currentChat;
    }

    public String[] previousChats() {
        String[] chatNames = new String[chats.keySet().size()];
        chatNames = chats.keySet().toArray(chatNames);
        return chatNames;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
        Database.getInstance().serialize();
    }


}