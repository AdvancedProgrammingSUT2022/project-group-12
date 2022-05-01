package Models.Units;

import Enums.UnitEnum;
import Models.Civilization;
import Models.Location;

public class NonCombatUnit extends Unit {
    public NonCombatUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
    }

    public void settler() {

    }

    public void worker() {

    }

    public void buildRoad() {

    }

    public void destroyRoad() {

    }

    public void createProduct() {

    }

    public UnitEnum getType() {
        return type;
    }
}
