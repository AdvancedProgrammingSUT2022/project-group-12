package Models;

import java.util.ArrayList;

public class User {
    private final String username;
    private final ArrayList<Game> gamesPlayed;
    private String nickname;
    private String password;
    private int score;
    private Game runningGame;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.gamesPlayed = new ArrayList<>();
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

    public ArrayList<Game> getGamesPlayed() {
        return this.gamesPlayed;
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

    public void addGame(Game newGame) {
        this.gamesPlayed.add(newGame);
    }

    public void removeGame(Game selectedGame) {
        this.gamesPlayed.remove(selectedGame);
    }

    public Game getRunningGame() {
        return runningGame;
    }

    public void setRunningGame(Game runningGame) {
        this.runningGame = runningGame;
    }
}