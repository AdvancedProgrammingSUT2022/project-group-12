package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;
import Models.Tiles.Tile;

public class NonRangedUnit extends CombatUnit {


    public NonRangedUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
    }

    private static int calculateCombatStrength(NonRangedUnit nonRangedUnit, Tile itsTile) {
        int strength = nonRangedUnit.getType().getCombatStrength();
        strength = AffectTerrainFeatures(strength, itsTile);
        strength = HealthBarAffect(strength, nonRangedUnit);
        return strength;
    }

    private void horseman() {

    }

    private void spearman() {

    }

    private void swordsman() {

    }

    private void longSwordsman() {

    }

    private void pikeMan() {

    }

    private void infantry() {

    }

    private void warrior() {

    }

    private void scout() {

    }

    private void lancer() {

    }

    private void cavalry() {

    }

    private void knight() {

    }

    private void musketMan() {

    }

    private void panzer() {

    }

    private void antiTankGun() {

    }

    private void tank() {

    }

    private void rifleman() {

    }

}
