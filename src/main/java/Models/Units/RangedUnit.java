package Models.Units;

import Enums.GameEnums.UnitEnum;

public class RangedUnit extends CombatUnit {
    private int rangedCombat;
    private int rangedCombatStrength;

    RangedUnit(UnitEnum type) {
        super(type);
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
