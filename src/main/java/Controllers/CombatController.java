package Controllers;

import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;
import Models.Units.NonRangedUnit;
import Models.Units.RangedUnit;

import static Controllers.CityCombatController.*;
import static Controllers.UnitCombatController.AttackNonRangedUnit;
import static Controllers.UnitCombatController.AttackRangedUnit;

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
