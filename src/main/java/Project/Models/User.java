package Project.Models;

import Project.Enums.AvatarURLEnum;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User {
    private final String username;
    private final HashMap<String, Chat> chats;
    private String imageUrl;
    private String nickname;
    private String password;
    private int score;
    private Chat currentChat;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.chats = new HashMap<>();
        this.score = 0;
        this.imageUrl = assignRandomAvatar();
        if (Database.getInstance().getAllUsers().contains(username))
            return;
        Database data = Database.getInstance();
        data.addUser(this);
        data.serialize();
    }

    private String assignRandomAvatar() {
        ArrayList<String> filePaths = new ArrayList<>(AvatarURLEnum.IMG01.getPaths());
        int randomNum = new Random().nextInt(filePaths.size()) - 1;
        if (randomNum < 0)
            randomNum = 0;
        return filePaths.get(randomNum);
    }


    public Image getImage() {
        if (AvatarURLEnum.IMG01.contains(imageUrl))
            return new Image(AvatarURLEnum.valueOf(imageUrl).getUrl());
        else
            return new Image(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public ArrayList<String> previousChats() {
        return new ArrayList<>(chats.keySet());
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
        Database.getInstance().serialize();
    }

    public int comparator(User o) {
        if (o.score > this.score)
            return 1;
        else if (o.score < this.score)
            return -1;
        else {
//            if (o.bestRecord > this.bestRecord)
//                return -1;
//            else if (o.bestRecord < this.bestRecord)
//                return 1;
//            else {
//                return Integer.compare(o.bestFinishDifficulty, this.bestFinishDifficulty);
//            }
            return 0;
        }
    }

}