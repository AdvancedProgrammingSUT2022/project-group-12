package Models.Units;

import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Terrains.Terrain;

public class NonCombatUnit extends Unit {

    public NonCombatUnit(UnitEnum type, Terrain terrain, Civilization civ) {
        super(type, terrain, civ);
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
