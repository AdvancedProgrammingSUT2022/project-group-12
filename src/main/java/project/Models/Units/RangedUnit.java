package project.Models.Units;

import project.Enums.UnitEnum;
import project.Models.Civilization;
import project.Models.Location;

public class RangedUnit extends CombatUnit {
    private double rangedCombat;
    private double rangedCombatStrength;

    public RangedUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
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
