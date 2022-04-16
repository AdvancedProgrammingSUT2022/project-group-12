package Models;

import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.Vector;

public class Game {
    private final ArrayList<Civilization> civs;
    private final Vector<Civilization> civTurn;
    private final TileGrid tileGrid;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = TileGrid.GenerateRandom(10, 10, 999);
        this.civTurn = new Vector<>();
        for (User user : users) {
            civs.add(new Civilization(user));
        }
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