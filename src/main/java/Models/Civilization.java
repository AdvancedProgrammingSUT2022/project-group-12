package Models;

import Enums.TechnologiesEnum;
import Models.Cities.City;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.CombatUnit;
import Models.Units.NonCombatUnit;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    // todo: complete

    private final User user;
    private int gold;
    private int beaker;
    private int happiness;
    private int production;
    private ArrayList<TechnologiesEnum> technologies;
    private HashMap<CombatUnit, Integer> combatUnits;
    private HashMap<NonCombatUnit, Integer> nonCombatUnits;
    private ArrayList<Tile> tiles;
    private TileGrid revealedTileGrid;
    private City capital;
    private ArrayList<String> isInWarWith;

    public Civilization(User user) {
        this.user = user;
    }

    public boolean isAlreadyInWarWith(String username) {
        return this.isInWarWith.contains(username);
    }

    public void goToWarWith(String username) {
        this.isInWarWith.add(username);
    }

    public void setHappiness(int happiness) {this.happiness = happiness;}

    public int getHappiness() {return happiness;}
}
