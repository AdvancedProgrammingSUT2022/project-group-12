package Controllers;

import Enums.GameEnums.TechnologyEnum;
import Models.Civilization;

import java.util.ArrayList;
import java.util.HashMap;

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
        checkForUpdateResearchingTechs(civilization);
        civilization.setCurrentTech(null);
     }
     private static void checkForUpdateResearchingTechs(Civilization civilization){
         /***
          * check if we can have access to new technologi for research
          */
        TechnologyEnum alltechs[] = TechnologyEnum.values();
         for (TechnologyEnum tech:
              alltechs) {
             if(!civilization.isHaveThisTech(tech) &&
             tech.hasPrerequisiteTechs(civilization.getTechnologies()) &&
             isThisTechExistsInResearchingTechs(tech,civilization.getResearchingTechnologies())
             ){civilization.getResearchingTechnologies().put(tech, tech.getCost());}
         }

     }
     private static boolean isThisTechExistsInResearchingTechs(TechnologyEnum tech, HashMap<TechnologyEnum, Integer> researchingTechs){
        return researchingTechs.containsKey(tech.name());
     }

    public void applyCheatSheet() {

    }
}
