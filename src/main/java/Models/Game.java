package Models;

import Controllers.GameController;
import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Game {
    private static Game instance = null;
    public final GameController controller;
    private final ArrayList<Civilization> civs;
    private final Vector<Civilization> civTurn;
    private final TileGrid tileGrid;

    public Game(List<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid(50, 50);
        this.civTurn = new Vector<>();
        for (User user : users) {
            civs.add(new Civilization(user));
        }
        this.controller = new GameController(this);
    }

    private static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public static Game getInstance(ArrayList<User> users) {
        if (instance == null) {
            setInstance(new Game(users));
        }
        return instance;
    }

    private void fullTurn() {
        for (int i = this.civs.size() - 1; i > -1; i--) {
            this.civTurn.add(this.civs.get(i));
        }
    }

    public Vector<Civilization> getCivTurn() {
        return civTurn;
    }

    private void nextTurn() {
        this.civTurn.remove(this.civTurn.size() - 1);
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }

    public ArrayList<Civilization> getCivs() {
        return civs;
    }

    public GameController getController() {
        return this.controller;
    }


}