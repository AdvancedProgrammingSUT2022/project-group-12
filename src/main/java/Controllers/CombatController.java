package Controllers;

import Models.Cities.City;
import Models.Location;
import Models.Units.Unit;

public class CombatController {

    public static void AttackUnit(Unit unit, Location location) {
//        if (isAttackToCity(location.getRow(), location.getCol(), civilization)) {
//            return AttackToCity(location, game.getTileGrid(), currentTile, civilization, game.getTileGrid().getTile(location).getCity());
//        }
//        if (!isEnemyIsReadyForAttack(location.getRow(), location.getCol(), civilization, currentTile.getCombatUnit())) {
//            return "enemy doesn't exists there";
//        }
//        if (currentTile.getCombatUnit() instanceof RangedUnit) {
//            return AttackRangedUnit(location, game.getTileGrid(), currentTile, civilization, (RangedUnit) currentTile.getCombatUnit());
//        } else {
//            return AttackNonRangedUnit(location, game.getTileGrid(), currentTile, civilization, (NonRangedUnit) currentTile.getCombatUnit());
//        }
    }

    public static void AttackCity(City city, Location location) {
//        if (isAttackToCity(location.getRow(), location.getCol(), civilization)) {
//            return cityAttackToCity(location, GameController.getGame().getTileGrid(), currentTile, civilization, GameController.getGame().getTileGrid().getTile(location).getCity());
//        }
//        if (!isEnemyIsReadyForAttack(location.getRow(), location.getCol(), civilization, currentTile.getCombatUnit())) {
//            return "enemy doesn't exists there";
//        }
//        return CityAttackRangedUnit(location, GameController.getGame().getTileGrid(), currentTile, civilization, currentTile.getCity());
    }
}
