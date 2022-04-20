package Models;

import Controllers.GameController;
import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.Vector;

public class Game {
    private static Game instance = null;

    private static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public static Game getInstance(ArrayList<User> users) {
        if (instance == null) {
            setInstance(new Game(users));
        }
        return instance;
    }

    private final ArrayList<Civilization> civs;
    private final Vector<Civilization> civTurn;
    private final TileGrid tileGrid;
    public final GameController controller;
    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = TileGrid.GenerateRandom(10, 10, 999);
        this.civTurn = new Vector<>();
        for (User user : users) {
            civs.add(new Civilization(user));
        }
        this.controller = new GameController(this);
    }

    private void fullTurn() {
        for (int i = this.civs.size() - 1; i > -1; i--) {
            this.civTurn.add(this.civs.get(i));
        }
    }

    private void nextTurn() {
        this.civTurn.remove(this.civTurn.size() - 1);
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }
}