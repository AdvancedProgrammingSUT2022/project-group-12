package Models;

import Controllers.GameMenuController;
import Models.Tiles.Tile;

import java.util.ArrayList;
import java.util.Vector;

public class Game {
    private final ArrayList<User> users;
    private final Vector<User> userTurn;
    private ArrayList<Tile> tilesMap;
    private GameMenuController controller;
    public Game(ArrayList<User> users) {
        this.users = users;
        this.tilesMap = new ArrayList<>();
        this.userTurn = new Vector<>();
        for (int i = users.size() - 1; i > -1; i--) {
            this.userTurn.add(users.get(i));
        }
        this.controller = new GameMenuController(this);
    }

    private void generateMap() {

    }

    private void fullTurn() {
        for (int i = this.users.size() - 1; i > -1; i--) {
            this.userTurn.add(this.users.get(i));
        }
    }

    private void nextTurn() {
        this.userTurn.remove(this.userTurn.size() - 1);
    }
}