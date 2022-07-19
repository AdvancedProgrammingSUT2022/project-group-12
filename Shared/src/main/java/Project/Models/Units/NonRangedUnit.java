package Project.Models.Units;

import Project.Enums.UnitEnum;
import Project.Models.Location;
import Project.Server.Models.Civilization;

public class NonRangedUnit extends CombatUnit {

     String type;
    public NonRangedUnit(UnitEnum type, Civilization civ, Location location) {
        super(type, civ, location);
        this.type = this.getClass().getName();
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
