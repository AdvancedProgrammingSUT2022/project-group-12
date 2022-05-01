package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Tiles.Tile;

public class RangedUnit extends CombatUnit {
    private int rangedCombat;
    private int rangedCombatStrength;

    public RangedUnit(UnitEnum type, Civilization civ) {
        super(type, civ);
    }

    public int getRangedCombat() {
        return rangedCombat;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    private void catapult() {

    }

    private void archer() {

    }

    private void chariotArcher() {

    }

    private void crossbowman() {

    }

    private void trebuchet() {

    }

    private void canon() {

    }

    private void artillery() {

    }
    protected static int calculateCombatStrength(RangedUnit rangedUnit, Tile itsTile){
        int strength=rangedUnit.getRangedCombatStrength();
        strength=AffectTerrainFeatures(strength,itsTile);
        strength=HealthBarAffect(strength,rangedUnit);
        return strength;
    }
}
