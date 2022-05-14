package Controllers;

import Enums.TechnologyEnum;
import Models.Civilization;
import Models.Units.Unit;

import java.util.HashMap;
import java.util.Map;

public class CivilizationController {
    private final Civilization civilization;

    public CivilizationController(Civilization civilization) {
        this.civilization = civilization;
    }

    public String civilizationUnitInfo() {
        if (civilization.getUnits().isEmpty()) {
            return "";
        }
        StringBuilder unitInfo = new StringBuilder("Unit info:\n");
        for (Unit unit : civilization.getUnits()) {
            unitInfo.append("- <").append(unit)
                    .append("> Tile: <").append(GameController.getGameTile(unit.getLocation()).getTerrain().getTerrainType())
                    .append("> Movement: <").append(unit.getAvailableMoveCount())
                    .append("> Health: <").append(unit.getHealthBar())
                    .append("> Has work to do: <").append(unit.isWorking())
                    .append("> Current location: <").append(unit.getLocation().toString());
        }
        return unitInfo.toString();
    }

    public String ownedTech() {
        if (civilization.getTechnologies().isEmpty()) {
            return "";
        }
        StringBuilder techs = new StringBuilder();
        for (Map.Entry<TechnologyEnum, Integer> tech : civilization.getTechnologies().entrySet()) {
            techs.append("- <").append(tech.getKey().name()).append("> Count: <").append(tech.getValue()).append(">");
        }
        return techs.toString();
    }

    public String showTechTree() {
        if (civilization.getResearch().isEmpty()) {
            return "";
        }
        HashMap<TechnologyEnum, Integer> research = civilization.getResearch();
        StringBuilder techTree = new StringBuilder();
        int count = 1;
        for (Map.Entry<TechnologyEnum, Integer> set : research.entrySet()) {
            techTree.append(count).append("- <").append(set.getKey()).append("> remained: <").append(set.getValue());
            count++;
        }
        return techTree.toString();
    }

    public void setTurn() {

    }

    public void applyCheatSheet() {

    }
}
