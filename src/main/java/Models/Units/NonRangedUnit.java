package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Terrains.Terrain;
import Models.Tiles.Tile;

import java.util.Random;

import static java.lang.Math.exp;

public class NonRangedUnit extends CombatUnit {



    public NonRangedUnit(UnitEnum type, Terrain terrain, Civilization civ) {
        super(type, terrain, civ);
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
    private static int calculateCombatStrength(NonRangedUnit nonRangedUnit, Tile itsTile){
        int strength=nonRangedUnit.getType().getCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,nonRangedUnit);
        return strength;
    }

}
