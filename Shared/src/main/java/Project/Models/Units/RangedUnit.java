package Project.Models.Units;

import Project.Enums.UnitEnum;
import Project.Models.Location;

public class RangedUnit extends CombatUnit {
    private double rangedCombat;
    private double rangedCombatStrength;

    public RangedUnit(UnitEnum type, String civ, Location location) {
        super(type, civ, location);
        super.type = this.getClass().getName();
    }

    public double getRangedCombat() {
        return rangedCombat;
    }

    public double getRangedCombatStrength() {
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
