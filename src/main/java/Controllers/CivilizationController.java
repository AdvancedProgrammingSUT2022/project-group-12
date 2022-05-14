package Controllers;

import Enums.TechnologyEnum;
import Enums.UnitEnum;
import Models.Civilization;
import Models.Units.Unit;

import java.util.HashMap;
import java.util.Map;

public class CivilizationController {
    private final Civilization civilization;

    public CivilizationController(Civilization civilization) {
        this.civilization = civilization;
    }

    public String unitPanel() {
        if (civilization.getUnits().isEmpty()) {
            return "";
        }
        StringBuilder unitInfo = new StringBuilder("Unit info:\n");
        for (Unit unit : civilization.getUnits()) {
            unitInfo.append("- <").append(unit)
                    .append("> Tile: <").append(GameController.getGameTile(unit.getLocation()).getTerrain().getTerrainType())
                    .append("> Movement: <").append(unit.getAvailableMoveCount())
                    .append("> Health: <").append(unit.getHealth())
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

    public String researchPanel() {
        if (civilization.getResearch().isEmpty())
            return "you are not doing any research at the moment";
        StringBuilder research = new StringBuilder();
        TechnologyEnum researchingTech = civilization.getResearchingTechnology();
        research.append("Researching on: ").append(researchingTech.name()).append("\n")
                .append("Turns to complete: ").append(civilization.getResearch().get(researchingTech)).append("\n")
                .append("This research may lead to: ");
        for (int i = 0; i < researchingTech.leadsToTech().size(); i++) {
            research.append("\n").append(researchingTech.leadsToTech().get(i).name());
        }
        return research.toString();
    }

    public String citiesPanel() {
        if (civilization.getCities().isEmpty())
            return "There are currently no cities under your civilization";
        StringBuilder cities = new StringBuilder();
        for (int i = 0; i < civilization.getCities().size(); i++) {
            if (civilization.getCities().get(i).isCapital())
                cities.append("* ");
            cities.append(civilization.getCities().get(i).getName()).append(" is on: ")
                    .append(civilization.getCities().get(i).getTile().getTerrain().getTerrainType().name())
                    .append(" and has ").append(civilization.getCities().get(i).getCitizens().size()).append(" citizens\n");
        }
        return cities.toString();
    }

    public String demographicPanel() {
        StringBuilder demographicInfo = new StringBuilder();
        demographicInfo.append("Your civilization has [").append(civilization.getCities().size()).append("] <cities> and [")
                .append(civilization.getOwnedTiles().size()).append("] owned tiles. It has [").append(civilization.getUnits().size())
                .append("] <units> and has [").append(civilization.getGold()).append("] <golds>. It is <In War With> [").append(civilization.getIsInWarWith().size())
                .append("] civilization(s) and has <Economic Relations with> [").append(civilization.getIsInEconomicRelation().size())
                .append("] civilization(s). Its citizens <Happiness> is as high as [").append(civilization.getHappiness())
                .append("] and it is working (researching) on [").append(civilization.getResearch().size()).append("] <technologies>. ");
        return demographicInfo.toString();
    }

    public String notificationHistory() {
        return this.civilization.getNotifications().toString();
    }

    public String militaryOverview() {
        StringBuilder units = new StringBuilder();
        int count = 0;
        for (Map.Entry<UnitEnum, Integer> set : civilization.getUnitCountByCategory().entrySet()) {
            if (set.getKey().isACombatUnit()) {
                units.append("Type: <").append(set.getKey()).append("> count: [").append(set.getValue()).append("]\n");
                count += set.getValue();
            }
        }
        StringBuilder militaryInfo = new StringBuilder();
        militaryInfo.append("Your civilization contains [").append(count).append("] military units that are as follows: \n")
                .append(units).append("It also has [").append(civilization.getFood()).append("] amount of food left.");
        return militaryInfo.toString();
    }

    public String economicOverview() {
        StringBuilder economicInfo = new StringBuilder();
        economicInfo.append("Your civilization has [").append(civilization.getCities().size()).append("] <cities> that are: ").append(citiesPanel()).append("and [")
                .append(civilization.getOwnedTiles().size()).append("] owned tiles. It has [").append(civilization.getUnits().size())
                .append("] <units> that are").append(unitPanel()).append("and has [").append(civilization.getGold()).append("] <golds>. It is <In War With> [").append(civilization.getIsInWarWith().size())
                .append("] civilization(s) and has <Economic Relations with> [").append(civilization.getIsInEconomicRelation().size())
                .append("] civilization(s). Its citizens <Happiness> is as high as [").append(civilization.getHappiness())
                .append("] and it is working (researching) on [").append(civilization.getResearch().size()).append("] <technologies> that are: \n");
        if (civilization.getResearch().isEmpty()) {
            economicInfo.append(":( you are not doing any research at the moment");
        } else {
            for (Map.Entry<TechnologyEnum, Integer> set : civilization.getResearch().entrySet()) {
                economicInfo.append("<").append(set.getKey().name()).append("> remained Turns: [").append(set.getValue()).append("]\n");
            }
        }
        return economicInfo.toString();
    }

    public void setTurn() {

    }

    public void applyCheatSheet() {

    }
}
