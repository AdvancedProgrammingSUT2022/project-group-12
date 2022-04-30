package Controllers;

import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Tiles.TileGrid;
import Models.Units.*;

import java.util.ArrayList;
import java.util.Random;

import static Controllers.CityCombatController.*;
import static Controllers.MovingController.findTheShortestPath;
import static Controllers.UnitCombatController.*;
import static java.lang.Math.exp;

public class CombatController extends GameController {
    public CombatController(Game newGame) {
        super(newGame);
    }
    public static String AttackUnit(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if(isAttackToCity(row,col,civilization)){return AttackToCity(row,col,game.getTileGrid(),currentTile,civilization,game.getTileGrid().getTile(row, col).getCity());}
        if(!isEnemyIsReadyForAttack(row,col,civilization,currentTile.getCombatUnit())) return "enemy doesn't exists there";
        if(currentTile.getCombatUnit() instanceof RangedUnit){ return  AttackRangedUnit(row,col,game.getTileGrid(),currentTile,civilization,(RangedUnit) currentTile.getCombatUnit());}
        else return AttackNonRangedUnit(row,col, game.getTileGrid(),currentTile,civilization,(NonRangedUnit) currentTile.getCombatUnit());
    }
    public static String AttackCity(int row, int col, Game game, Tile currentTile, Civilization civilization) {
        if(isAttackToCity(row,col,civilization)){return cityAttackToCity(row,col,game.getTileGrid(),currentTile,civilization,game.getTileGrid().getTile(row, col).getCity());}
        if(!isEnemyIsReadyForAttack(row,col,civilization,currentTile.getCombatUnit())) return "enemy doesn't exists there";
         return CityAttackRangedUnit(row,col,game.getTileGrid(),currentTile,civilization,currentTile.getCity());
    }
}
