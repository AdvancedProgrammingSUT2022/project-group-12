package Models;

import java.util.ArrayList;

public class User {
    private final String username;
    private String password;
    private final String nickname;
    private ArrayList<Game> gamesPlayed;
    private int score;
    private ArrayList<String> isInWarWith;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.gamesPlayed = new ArrayList<>();
        this.score = 0;
        this.isInWarWith = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public ArrayList<Game> getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getScore() {
        return this.score;
    }

    public void changePassword(String newPass) {
        this.password = newPass;
    }

    public void addGame(Game newGame) {
        this.gamesPlayed.add(newGame);
    }

    public void removeGame(Game selectedGame) {
        this.gamesPlayed.remove(selectedGame);
    }

    public boolean isAlreadyInWarWith(String username) {
        return this.isInWarWith.contains(username);
    }

    public void goToWarWith(String username) {
        this.isInWarWith.add(username);
    }

    public void setScore(int newScore) {
        this.score += newScore;
    }
}