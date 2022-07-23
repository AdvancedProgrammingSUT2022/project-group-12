package Project.Models;

import Project.Enums.AvatarURLEnum;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class User {
    private final String username;
    private final ArrayList<String> friends;
    private transient final HashMap<String, Chat> chats;
    private final ArrayList<String> friendRequest;
    private final ArrayList<String> waitingOnFriendRequest;
    private String imageUrl;
    private String nickname;
    private String password;
    private int score;
    private Date lastWinDate = null;
    private Date lastLoginDate = null;
    private transient Chat currentChat;

    public User(String username, String password, String nickname) {
        this.friends = new ArrayList<>();
        this.friendRequest = new ArrayList<>();
        this.waitingOnFriendRequest = new ArrayList<>();
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.chats = new HashMap<>();
        this.score = 0;
        this.imageUrl = assignRandomAvatar();
    }

    public ArrayList<String> getFriendRequest() {
        return friendRequest;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addFriend(String username) {
        if (!this.friends.contains(username))
            this.friends.add(username);
    }

    public void addToWaitingOnFriendRequest(String username) {
        if (!this.waitingOnFriendRequest.contains(username))
            this.waitingOnFriendRequest.add(username);
    }

    public void acceptFriend(String username) {
        friendRequest.remove(username);
        waitingOnFriendRequest.remove(username);
        addFriend(username);
    }

    public void denyFriend(String username) {
        getFriendRequest().remove(username);
        waitingOnFriendRequest.remove(username);
    }

    public ArrayList<String> getWaitingOnFriendRequest() {
        return waitingOnFriendRequest;
    }

    public void sendFriendRequest(String username) {
        if (!this.friendRequest.contains(username))
            this.friendRequest.add(username);
    }

    private String assignRandomAvatar() {
        ArrayList<String> filePaths = new ArrayList<>(AvatarURLEnum.IMG0.getPaths());
        int randomNum = new Random().nextInt(filePaths.size()) - 1;
        if (randomNum < 0)
            randomNum = 0;
        return filePaths.get(randomNum);
    }


    @NotNull
    public AvatarURLEnum getAvatarURL() {
        return AvatarURLEnum.valueOf(imageUrl);
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
//        Database.getInstance().serialize();
    }

    public void changePassword(String newPass) {
        this.password = newPass;
//        Database.getInstance().serialize();
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
//        Database.getInstance().serialize();
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

    public void setLastWinDate(Date lastWinDate) {
        this.lastWinDate = lastWinDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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