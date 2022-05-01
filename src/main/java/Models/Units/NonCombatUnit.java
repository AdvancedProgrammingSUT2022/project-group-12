package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Civilization;

public class NonCombatUnit extends Unit {
    public NonCombatUnit(UnitEnum type, Civilization civ) {
        super(type, civ);
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
