package Controllers;

import Enums.GameEnums.TechnologyEnum;
import Models.Civilization;

public class CivilizationController {
    public StringBuilder exploreInfo() {
        return new StringBuilder();
    }

    public StringBuilder unitInfo() {
        return new StringBuilder();
    }

    public StringBuilder citiesInfo() {
        return new StringBuilder();
    }

    public void setTurn() {

    }
     public static void addTechnology(TechnologyEnum tech, Civilization civilization){
         /***
          * add new technology and remove from current researching techs and current tech
          */
        civilization.getTechnologies().add(tech);
        civilization.getResearchingTechnologies().remove(tech.name());
        civilization.setCurrentTech(null);
     }
    public void applyCheatSheet() {

    }
}
