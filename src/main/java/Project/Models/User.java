package Project.Models;

import Project.Enums.AvatarURLEnum;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class User {
    private final String username;
    private transient final HashMap<String, Chat> chats;
    private String imageUrl;
    private String nickname;
    private Game game;
    private String password;
    private int score;
    private Date lastWinDate = new Date(new Random().nextLong(0, System.currentTimeMillis())); // todo: update
    private Date lastLoginDate = new Date(new Random().nextLong(0, System.currentTimeMillis())); // todo: update
    private transient Chat currentChat;
    private HashMap<String, Game> gamesRequests;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.gamesRequests = new HashMap<>();
        this.password = password;
        this.chats = new HashMap<>();
        this.score = 0;
        this.imageUrl = assignRandomAvatar();
        if (Database.getInstance().getAllUsernames().contains(username))
            return;
        Database data = Database.getInstance();
        data.addUser(this);
        data.serialize();
    }

    public HashMap<String, Game> getGames() {
        return gamesRequests;
    }

    public void setGameString(String user, Game game) {
        gamesRequests.put(user, game);
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private String assignRandomAvatar() {
        ArrayList<String> filePaths = new ArrayList<>(AvatarURLEnum.IMG0.getPaths());
        int randomNum = new Random().nextInt(filePaths.size()) - 1;
        if (randomNum < 0)
            randomNum = 0;
        return filePaths.get(randomNum);
    }


    public Image getImage() {
        if (AvatarURLEnum.IMG0.contains(imageUrl))
            return AvatarURLEnum.valueOf(imageUrl).getImage();
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

    public void startChat(Chat newChat) {
        if (chats.containsKey(newChat.getName()))
            return;

        chats.put(newChat.getName(), newChat);
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


    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public Date getLastWinDate() {
        return lastWinDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", chats=" + chats +
                ", imageUrl='" + imageUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                ", lastWinDate=" + lastWinDate +
                ", lastLoginDate=" + lastLoginDate +
                ", currentChat=" + currentChat +
                '}';
    }
}