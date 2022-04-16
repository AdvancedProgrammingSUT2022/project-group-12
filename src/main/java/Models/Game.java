package Models;

import Models.Tiles.Tile;
import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.Vector;

public class Game {
    private ArrayList<Civilization> civs;
    private final Vector<Civilization> civTurn;
    private TileGrid tileGrid; // todo: replace with TileGrid

    public Game(ArrayList<User> users) {
        // todo: fix
//        this.civs = users;
        this.civTurn = new Vector<>();
        for (int i = users.size() - 1; i > -1; i--) {
//            this.civTurn.add(users.get(i));
        }
    }

    private void generateMap() {

    }

    private void fullTurn() {
        for (int i = this.civs.size() - 1; i > -1; i--) {
            this.civTurn.add(this.civs.get(i));
        }
    }

    private void nextTurn() {
        this.civTurn.remove(this.civTurn.size() - 1);
    }
}