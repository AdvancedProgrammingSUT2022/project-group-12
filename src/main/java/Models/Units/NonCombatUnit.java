package Models.Units;

import Enums.GameEnums.UnitEnum;

public class NonCombatUnit extends Unit {
    private final UnitEnum type;

    NonCombatUnit(UnitEnum type) {
        this.type = type;
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
