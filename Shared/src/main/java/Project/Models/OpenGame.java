package Project.Models;

import Project.Utils.Constants;
import Project.Utils.TokenGenerator;

import java.util.ArrayList;

public class OpenGame {
    private final String name;
    private final String token;
    private final int playerLimit;
    private final int height;
    private final int width;
    private final User admin;
    private final ArrayList<User> players = new ArrayList<>();
    private final boolean isPrivate;

    public OpenGame(String name, User admin, int height, int width, int playerLimit, boolean isPrivate) {
        this.token = TokenGenerator.generate(Constants.TOKEN_LENGTH);
        this.name = name == null ? token : name;
        this.height = height;
        this.width = width;
        this.playerLimit = playerLimit;
        this.admin = admin;
        this.isPrivate = isPrivate;
        this.addPlayer(admin);
    }

    public void addPlayer(User player) {
        players.add(player);
    }

    public void removePlayer(User player) {
        players.remove(player);
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public int getPlayerLimit() {
        return playerLimit;
    }

    public User getAdmin() {
        return admin;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
