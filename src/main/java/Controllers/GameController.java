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


    public static void RepairTile(Tile currentTile) {
        //tode : complete
    }

    public static void RemoveRoute(Tile currentTile,ImprovementEnum improvementEnum) {
    }

    public static void RemoveJungle(Tile currentTile) {
    }



    public static void BuildImprovment(Tile currentTile, ImprovementEnum improvementEnum) {
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

    public static void FoundCity(Tile currentTile) {
    }


    public static void garrsionUnit(Tile currentTile, Civilization civilization) {
    }

    public static void fortifyUnit(Tile currentTile, Civilization civilization) {
    }

    public static void fortifyHealUnit(Tile currentTile, Civilization civilization) {
    }

    public static void AlertUnit(Tile currentTile, Civilization civilization) {
    }

    public static void sleepNonCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static void sleepCombatUnit(Civilization currentCivilizaion, Tile currentTile) {
    }

    public static StringBuilder showCity(City city) {
    return null;
    }
    public static CommandResponseEnum showCity(int parseInt, int parseInt1, Game game) {
        return null;
    }

    public static void showNonCombatInfo(Tile currentTile) {
    }

    public static void showCombatInfo(Tile currentTile) {
    }

    public static StringBuilder showResearchInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showCitiesInfo(Tile currentTile, Civilization currentCivilization) {
    }
    public static StringBuilder showUnitsInfo(Tile currentTile, Civilization currentCivilization) {
    }
    public static StringBuilder showDiplomacyInfo(Tile currentTile, Civilization currentCivilization) {
    }
    public static StringBuilder showVictoryInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showDemographicsInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showNotifInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showMilitaryInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showEcoInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showDiplomaticInfo(Tile currentTile, Civilization currentCivilization) {
    }

    public static StringBuilder showDealsInfo(Tile currentTile, Civilization currentCivilization) {
    }
}
