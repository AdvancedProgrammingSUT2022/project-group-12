package Controllers;

import Enums.CommandResponseEnum;
import Enums.GameEnums.ImprovementEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Database;
import Models.Game;
import Models.Tiles.Tile;
import Models.User;
import Views.GameMenu;
import Views.MenuStack;

import java.util.ArrayList;
import java.util.Locale;

public class GameController {
    private static boolean checkUsernameValidation(ArrayList<String> usernames) {
        Database database = Database.getInstance();
        for (String list : usernames) {
            if (!database.checkForUsername(list)) {
                return false;
            }
        }
        return true;
    }

    public static CommandResponseEnum startNewGame(ArrayList<String> usernames) {
        ArrayList<User> users = new ArrayList<>();
        if (!checkUsernameValidation(usernames)) {
            return CommandResponseEnum.USER_DOESNT_EXISTS;
        }
        Database database = Database.getInstance();
        for (String username : usernames) {
            User user = database.getUser(username);
            users.add(user);
        }
        Game game = new Game(users);
        database.addGame(game);
        for (User user : users) {
            user.addGame(game);
            user.setRunningGame(game);
        }
        MenuStack.getInstance().pushMenu(new GameMenu(game));
        return CommandResponseEnum.OK;
    }


    public static String RepairTile(Tile currentTile) {
        //tode : complete
        return "Tile repaired successfully";
    }

    public static String RemoveRoute(Tile currentTile,ImprovementEnum improvementEnum) {
       return  "route removed succesfully";
    }

    public static String RemoveJungle(Tile currentTile) {
        return "Jungle removed succesfully";
    }



    public static String BuildImprovment(Tile currentTile, ImprovementEnum improvementEnum) {
        return ImprovementEnum.valueOf(improvementEnum.name()).toString().toLowerCase()+" built succesfully";
    }

   

    public static void deletenonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void deleteCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void wakeUpNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void wakeUpCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void CancelMissionNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void CancelMissionCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static String FoundCity(Tile currentTile) {
        return "city found successfully";
    }


    public static String garrsionUnit(Tile currentTile, Civilization civilization) {
        return "unit garrsioned successfully";
    }

    public static String fortifyUnit(Tile currentTile, Civilization civilization) {
    return "unit fortified successfully";
    }

    public static String fortifyHealUnit(Tile currentTile, Civilization civilization) {
        return "unit fortified successfully";
    }

    public static String AlertUnit(Tile currentTile, Civilization civilization) {
      return "unit alerted successfully";
    }

    public static String sleepNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
        return "unit sleeped successfully";
    }

    public static String sleepCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
        return "unit sleeped successfully";
    }

    public static StringBuilder showCity(City city) {
    return null;
    }
    public static CommandResponseEnum showCity(int parseInt, int parseInt1, Game game) {
        return null;
    }

    public static StringBuilder showNonCombatInfo(Tile currentTile) {
        return null;
    }

    public static StringBuilder showCombatInfo(Tile currentTile) {
        return null;
    }

    public static StringBuilder showResearchInfo(Tile currentTile, Civilization currentCivilization) {
          return null;
    }

    public static StringBuilder showCitiesInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }
    public static StringBuilder showUnitsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }
    public static StringBuilder showDiplomacyInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }
    public static StringBuilder showVictoryInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDemographicsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showNotifInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showMilitaryInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDiplomaticInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
        return null;
    }
}
