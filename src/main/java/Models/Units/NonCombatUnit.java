package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

public class NonCombatUnit extends Unit {
    public NonCombatUnit(UnitEnum type, Civilization civilization, Location location) {
        super(type, civilization, location);
    }

    public NonCombatUnit(UnitEnum type, Civilization civilization, Location location, int productionCost) {
        super(type, civilization, location, productionCost);
    }


    public UnitEnum getType() {
        return type;
    }
}
