package Project.Server.Controllers;

import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Server.Models.Civilization;
import Project.SharedModels.Units.Unit;

import java.util.HashMap;
import java.util.Map;

public class CivilizationController {
    private final Civilization civilization;

    public CivilizationController(Civilization civilization) {
        this.civilization = civilization;
    }

    public String unitsPanel() {
        if (civilization.getUnits().isEmpty()) {
            return "";
        }
        StringBuilder unitInfo = new StringBuilder("Unit info:\n");
        for (Unit unit : civilization.getUnits()) {
            unitInfo.append(unit.getInfo()).append("===========\n");
        }
        return unitInfo.toString();
    }

    public String ownedTech() {
        if (civilization.getTechnologies().isEmpty()) {
            return "";
        }
        StringBuilder techs = new StringBuilder();
        for (TechnologyEnum tech : civilization.getTechnologies()) {
            techs.append("- <").append(tech.name()).append(">");
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
        if (civilization.getResearch().isEmpty()) {
            return "you are not doing any research at the moment";
        }
        StringBuilder research = new StringBuilder();
        TechnologyEnum researchingTech = civilization.getResearchingTechnology();
        research.append("Researching on: ").append(researchingTech.name()).append("\n")
                .append("Turns to complete: ").append(researchingTech.getCost() - civilization.getResearchAdvancement(researchingTech)).append("\n")
                .append("This research may lead to: ");
        for (TechnologyEnum tech : researchingTech.leadsToTech()) {
            research.append("\n").append(tech.name());
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
            cities.append(civilization.getCities().get(i).getInfo());
        }
        return cities.toString();
    }

    public String demographicPanel() {
        StringBuilder demographicInfo = new StringBuilder();
        demographicInfo.append("cities count: ").append(civilization.getCities().size()).append("\n")
                .append("owned tiles: ").append(civilization.getOwnedTiles().size()).append("\n")
                .append("units count: ").append(civilization.getUnits().size()).append("\n")
                .append("gold count: ").append(civilization.getGold()).append("\n")
                .append("is in war with [").append(civilization.getInWarWith().size()).append("] civilization(s)").append("\n")
                .append("is trading goods with [").append(civilization.getIsInEconomicRelation().size()).append("] civilization(s)").append("\n")
                .append("Happiness: ").append(civilization.getHappiness()).append("\n")
                .append("researching on [").append(civilization.getResearch().size()).append("] items").append("\n")
                .append("current research: ").append(civilization.getResearchingTechnology().name()).append("\n");
        return demographicInfo.toString();
    }

    public String notificationHistory() {
        return this.civilization.getNotifs().isEmpty() ? "no notification for now" : this.civilization.getNotifs().toString();
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
        militaryInfo.append("military Units: ").append(count).append("\n")
                .append("that are: ").append("\n").append(units)
                .append("food: ").append(civilization.getFood()).append("\n");
        return militaryInfo.toString();
    }

    public String economicOverview() {
        StringBuilder economicInfo = new StringBuilder();
        economicInfo.append("cities count: ").append(civilization.getCities().size()).append("\n").append("that are: ").append("\n")
                .append(citiesPanel()).append("owned tiles: ").append(civilization.getOwnedTiles().size()).append("\n")
                .append("unit count: ").append(civilization.getUnits().size()).append("\n")
                .append("that are: \n").append(unitsPanel())
                .append("gold count: ").append(civilization.getGold()).append("\n")
                .append("is in war with [").append(civilization.getInWarWith().size()).append("] civilization(s)\n")
                .append("is trading goods with [").append(civilization.getIsInEconomicRelation().size()).append("] civilization(s)\n")
                .append("happiness: ").append(civilization.getHappiness()).append("\n")
                .append("is currently researching on: ").append(civilization.getResearch().size()).append("\n");
        if (civilization.getResearch().isEmpty()) {
            economicInfo.append(":( you are not doing any research at the moment");
        } else {
            for (Map.Entry<TechnologyEnum, Integer> set : civilization.getResearch().entrySet()) {
                economicInfo.append("<").append(set.getKey().name()).append("> remained Turns: [").append(set.getValue()).append("]\n");
            }
        }
        return economicInfo.toString();
    }

}
