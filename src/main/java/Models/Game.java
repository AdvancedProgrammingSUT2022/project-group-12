package Models;

import Controllers.GameController;
import Models.Tiles.TileGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Game {
    public final GameController controller;
    private final ArrayList<Civilization> civs;
    private final Vector<Civilization> civTurn;
    private int gameTurn;
    private final TileGrid tileGrid;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid();
        this.civTurn = new Vector<>();
        for (User user : users) {
            civs.add(new Civilization(user));
        }
        this.controller = new GameController(this);
    }

    public void fullPlayersMove() {
        for (int i = this.civs.size() - 1; i > -1; i--) {
            this.civTurn.add(this.civs.get(i));
        }
    }

    public Vector<Civilization> getCivTurn() {
        return civTurn;
    }

    public void nextPlayerMove() {
        this.civTurn.remove(this.civTurn.size() - 1);
        if (this.civTurn.isEmpty()) {
            fullPlayersMove();
        }
    }

    public void nextTurn() {
        this.gameTurn++;
        if (this.gameTurn > 25) {
            //TODO : end game
        }
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