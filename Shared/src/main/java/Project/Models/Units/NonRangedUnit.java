package Project.Models.Units;

import Project.Enums.UnitEnum;
import Project.Models.Location;

public class NonRangedUnit extends CombatUnit {

    public NonRangedUnit(UnitEnum type, String civ, Location location) {
        super(type, civ, location);
        super.type = this.getClass().getName();
    }

    private void horseman() {

    }

    private void spearman() {

    }

    private void swordsman() {

    }

    private void longSwordsman() {

    }

    private void pikeMan() {

    }

    private void infantry() {

    }

    private void warrior() {

    }

    private void scout() {

    }

    private void lancer() {

    }

    private void cavalry() {

    }

    private void knight() {

    }

    private void musketMan() {

    }

    private void panzer() {

    }

    private void antiTankGun() {

    }

    private void tank() {

    }

    private void rifleman() {

    }

}
