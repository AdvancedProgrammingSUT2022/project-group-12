package Models;

import java.util.ArrayList;

public class User {
    private final String username;
    private String nickname;
    private String password;
    private int score;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.score = 0;
        Database data = Database.getInstance();
        data.addUser(this);
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
    }

    public void changePassword(String newPass) {
        this.password = newPass;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    @Override
    public String toString() {
        return "username : " + username +
                "\npassword : " + password +
                "\nnickname : " + nickname +
                "\nscore : " + score;
    }
}