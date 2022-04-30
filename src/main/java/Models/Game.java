package Models;

import Models.Tiles.TileGrid;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Civilization> civs;
    private int gameTurn;
    private final TileGrid tileGrid;

    public Game(ArrayList<User> users) {
        this.civs = new ArrayList<>();
        this.tileGrid = new TileGrid();
        for (User user : users) {
            civs.add(new Civilization(user));
        }
    }

//    public void fullPlayersMove() {
//        for (int i = this.civs.size() - 1; i > -1; i--) {
//            this.civTurn.add(this.civs.get(i));
//        }
//    }

//    public ArrayList<Civilization> getCivTurn() {
//        return civTurn;
//    }

//    public void nextPlayerMove() {
//        this.civTurn.remove(this.civTurn.size() - 1);
//        if (this.civTurn.isEmpty()) {
//            fullPlayersMove();
//        }
//    }

    public Civilization getCurrentCivilization() {
        return civs.get(this.gameTurn % civs.size());
    }

    public void startNextTurn() {
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
}