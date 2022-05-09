package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

public class NonCombatUnit extends Unit {
    public NonCombatUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
    }

    public UnitEnum getType() {
        return type;
    }
}
