package Models;

import java.util.ArrayList;

public class User {
    private final String username;
    private final String nickname;
    private String password;
    private int score;
    private ArrayList<Game> gamesPlayed;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.gamesPlayed = new ArrayList<>();
        this.score = 0;
        Database data = Database.getInstance();
        data.addUserToList(this);
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

    public void setScore(int newScore) {
        this.score += newScore;
    }
}