package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Terrains.Terrain;

public class RangedUnit extends CombatUnit {
    private int rangedCombat;
    private int rangedCombatStrength;

    public RangedUnit(UnitEnum type, Terrain terrain, Civilization civ) {
        super(type, terrain, civ);
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
}
