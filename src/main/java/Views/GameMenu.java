package Views;

import Controllers.Command;
import Controllers.GameController;
import Controllers.GameMenuController;
import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

import java.util.List;

public class GameMenu extends Menu {

    private Game game;
    public GameMenu(Game game){
        this.game=game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "show current menu" -> System.out.println("Game Menu");
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> this.findCategory(command);
        }
    }

    private void findCategory(Command command) {
        switch (command.getCategory()) {
            case "info" -> this.info(command);
            case "select" -> this.select(command);
            case "unit" -> this.unit(command);
            case "map" -> this.map(command);
        }
    }

    private void info(Command command) {
        switch (command.getType().trim()) {
            case "info research" -> this.researchInfo();
            case "info units" -> this.unitsInfo();
            case "info cities" -> this.citiesInfo();
            case "info diplomacy" -> this.diplomacyInfo();
            case "info victory" -> this.victoryInfo();
            case "info demographics" -> this.demographicsInfo();
            case "info notifications" -> this.notifInfo();
            case "info military" -> this.militaryInfo();
            case "info economic" -> this.ecoInfo();
            case "info diplomatic" -> this.diplomaticInfo();
            case "info deals" -> this.dealsInfo();
        }
    }

    private void select(Command command) {
        switch (command.getType().trim()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
        }
    }

    private void unit(Command command) {
        switch (command.getSubCategory().trim()) {
            case "moveTo" -> this.unitMoveTo(command);
            case "sleep" -> this.unitSleep(command);
            case "alert" -> this.unitAlert();
            case "fortify" -> this.unitFortify(command);
            case "garrison" -> this.unitGarrison(command);
            case "setup" -> this.unitSetup(command);
            case "attack" -> this.unitAttack(command);
            case "found" -> this.unitFound(command);
            case "cancel" -> this.unitCancel(command);
            case "wake" -> this.unitWake(command);
            case "delete" -> this.unitDelete(command);
            case "build" -> this.unitBuild(command);
            case "remove" -> this.unitRemove(command);
            case "repair" -> this.unitRepair(command);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory().trim()) {
            case "road" -> this.unitBuildRoad();
            case "railRoad" -> this.unitBuildRailRoad();
            case "farm" -> this.unitBuildFarm();
            case "mine" -> this.unitBuildMine();
            case "tradingPost" -> this.unitBuildTradingPost();
            case "lumberMill" -> this.unitBuildLumberMill();
            case "pasture" -> this.unitBuildPasture();
            case "camp" -> this.unitBuildCamp();
            case "plantation" -> this.unitBuildPlantation();
            case "quarry" -> this.unitBuildQuarry();
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory().trim()) {
            case "route" -> this.unitRemoveRoute();
            case "jungle" -> this.unitRemoveJungle();
        }
    }

    private void map(Command command) {
        switch (command.getSubCategory().trim()) {
            case "show" -> this.showMap(command);
            case "move" -> this.moveMap(command);
        }
    }

    private void moveMap(Command command) {
            switch (command.getSubSubCategory().trim()) {
                case "right" -> this.moveMapByDirection(command,"right");
                case "left" -> this.moveMapByDirection(command,"left");
                case "up" -> this.moveMapByDirection(command,"up");
                case "down" -> this.moveMapByDirection(command,"down");
                default -> System.out.println(CommandResponse.INVALID_DIRECTION);
            }
    }
    private void dealsInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showDealsInfo(currentTile,currentCivilization);
    }

    private void diplomaticInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showDiplomaticInfo(currentTile,currentCivilization);
    }

    private void ecoInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showEcoInfo(currentTile,currentCivilization);
    }

    private void militaryInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showMilitaryInfo(currentTile,currentCivilization);
    }

    private void notifInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showNotifInfo(currentTile,currentCivilization);
    }

    private void demographicsInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showDemographicsInfo(currentTile,currentCivilization);
    }

    private void victoryInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showVictoryInfo(currentTile,currentCivilization);
    }

    private void diplomacyInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showDiplomacyInfo(currentTile,currentCivilization);
    }

    private void unitsInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        GameController.showUnitsInfo(currentTile,currentCivilization);
    }
    private void researchInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        System.out.println(GameController.showResearchInfo(currentTile,currentCivilization));
    }
    private void citiesInfo() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        System.out.println(GameController.showCitiesInfo(currentTile,currentCivilization));
    }
    private void showMap(Command command) {
        String key;
       if((key=command.getOption("position")) != null){
            showMapPosition(key);
       }else if((key=command.getOption("cityname")) != null){
               City city=getCityWithThisName(getCurrentCilization(),key);
               System.out.println(city == null ? "city doesn't exists with this name" : GameMenuController.showMapOnCity(city));
       }else {
           System.out.println(CommandResponse.CommandMissingRequiredOption);
       }
    }
    private void showMapPosition(String key){
        String[] cordinates=key.split("\\s+");
            CommandResponse response=isCorrectPosition((cordinates[0]),(cordinates[1]),this.getGame());
            int row=0,col=0;
            if(response.isOK()){row=Integer.parseInt(cordinates[0]); col=Integer.parseInt(cordinates[1]);}
            System.out.println(!response.isOK() ? response : GameMenuController.showMapOnPosition(row,col,this.getGame()));
    }

    private void moveMapByDirection(Command command,String direction) {
        CommandResponse response=validateCommandForMoveByDirection(command.getType().trim(),command.getCategory(),command.getSubCategory(),command.getSubSubCategory(),command,direction);
            String key=command.getOption("amount");
                    switch (direction) {
                        case "down" -> System.out.println(response.isOK() ? GameMenuController.moveMapDown(Integer.parseInt(key)) : response);
                        case "up" -> System.out.println(response.isOK() ? GameMenuController.moveMapUp(Integer.parseInt(key)) : response);
                        case "right" -> System.out.println(response.isOK() ? GameMenuController.moveMapRight(Integer.parseInt(key)) : response);
                        case "left" -> System.out.println(response.isOK() ? GameMenuController.moveMapLeft(Integer.parseInt(key)) : response);

                }
            }
    public CommandResponse validateCommandForMoveByDirection(String type, String category, String subCategory, String subSubCategory, Command command, String direction){
        if(type.trim().length() > (category+" "+subCategory+" "+ subSubCategory).length()) return CommandResponse.INVALID_COMMAND;
        CommandResponse response;
        if((response=command.validateOptions(List.of("amount"))).isOK()){
            String amount=command.getOption("amount");
            response=isCorrectPosition(amount,this.getGame(),direction);
        }
        return response;
    }

    private void unitDelete(Command command) {
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                                if(response.isOK())GameController.deletenonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                                if(response.isOK())GameController.deleteCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponse.CommandMissingRequiredOption;
        }
        System.out.println(response.isOK() ? "unit deleted successfully" : response);
    }
    private CommandResponse validateFornonCombatUnit(Tile currentTile,Civilization civilization) {
        if(!(civilization.getCurrentTile().getNonCombatUnit().getType()== null)){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv()== civilization)){return CommandResponse.WRONG_UNIT;}
        return CommandResponse.OK;
    }
    private CommandResponse validateForCombatUnit(Tile currentTile, Civilization civilizaion) {
        if(!(civilizaion.getCurrentTile().getCombatUnit().getType()== null)){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilizaion.getCurrentTile().getCombatUnit().getCiv()== civilizaion)){return CommandResponse.WRONG_UNIT;}
        return CommandResponse.OK;
    }

    private void unitWake(Command command) {
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.wakeUpNonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.wakeUpCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponse.CommandMissingRequiredOption;
        }
            System.out.println(response.isOK() ? "unit waked up successfully" : response);
    }

    private void unitCancel(Command command) {
        if(!command.getSubSubCategory().equals("mission")){
            System.out.println(CommandResponse.INVALID_COMMAND); return;
        }
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.CancelMissionNonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.CancelMissionCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponse.CommandMissingRequiredOption;
        }
            System.out.println(response.isOK() ? "unit waked up successfully" : response);
    }

    private void unitFound(Command command) {
          if(!command.getSubSubCategory().equals("city")){
              System.out.println(CommandResponse.INVALID_COMMAND); return;
          }
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateTileForFoundingCity(currentTile,currentCivilizaion);
        String message;
        if(response.isOK()) System.out.println(GameController.FoundCity(currentTile));
        else System.out.println(response);
    }

    private CommandResponse validateTileForFoundingCity(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.SETTLER)) {return CommandResponse.WRONG_UNIT;}
        if(!isPossibleToBuildCity(currentTile)){return CommandResponse.IMPOSSIBLE_CITY;}
        return CommandResponse.OK;
    }

    private boolean isPossibleToBuildCity(Tile currentTile) {
        //TODO : need at least 4 tile around it to build the city
        return true;
    }

    private void unitAttack(Command command) {
        String key;
        if((key=command.getOption("position"))==null){
            System.out.println(CommandResponse.CommandMissingRequiredOption); return;
        }
        String[] cordinates=key.split("\\s+");
            Civilization civilizaion= getCurrentCilization();
            Tile currentTile=getCurrentTile();
            CommandResponse response=isCorrectPosition((cordinates[0]),(cordinates[1]),this.getGame());
            int row=0,col=0;
            if(response.isOK()){row=Integer.parseInt(cordinates[0]); col=Integer.parseInt(cordinates[1]);}
            System.out.println(response.isOK() ? GameController.AttackUnit(row,col,this.getGame(),currentTile,civilizaion) : response);
    }

    private void unitSetup(Command command) {
    }

    private void unitGarrison(Command command) {
        Civilization civilization= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateForGarrison(currentTile,civilization);
        if(response.isOK()) System.out.println(GameController.garrsionUnit(currentTile,civilization));
        else System.out.println(response);
    }
    private CommandResponse validateForGarrison(Tile currentTile, Civilization civilization) {
        if(currentTile.getCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if(!(civilization.getCurrentTile().getCity() == null)){return CommandResponse.CITY_DOESNT_EXISTS;}
        return CommandResponse.OK;

    }

    private void unitFortify(Command command) {
        Civilization civilization= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        try {
            if (command.getSubSubCategory().equals("heal")){
                CommandResponse response=validateForCombatUnit(currentTile,civilization);
                if(response.isOK())GameController.fortifyHealUnit(currentTile,civilization);
                else System.out.println(response);
            }else{
                System.out.println(CommandResponse.INVALID_COMMAND);
            }
        }catch (Exception e){
            CommandResponse response=validateForCombatUnit(currentTile,civilization);
            if(response.isOK()) System.out.println(GameController.fortifyUnit(currentTile,civilization));
            else System.out.println(response);
        }
    }
    private void unitAlert() {
        Civilization civilization= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateForCombatUnit(currentTile,civilization);
        if(response.isOK()) System.out.println(GameController.AlertUnit(currentTile,civilization));
        else System.out.println(response);
    }

    private void unitSleep(Command command) {
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response;
        try {
            switch (command.getSubSubCategory())
            {
                case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                    if(response.isOK()) System.out.println(GameController.sleepNonCombatUnit(currentCivilizaion,currentTile));  break;
                case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                    if(response.isOK()) System.out.println(GameController.sleepCombatUnit(currentCivilizaion,currentTile));     break;
                default : response=CommandResponse.CommandMissingRequiredOption;
            }
            if(!response.isOK())System.out.println(response);
        }catch (Exception e){
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void unitMoveTo(Command command) {
        Civilization currentCivilization= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        if(command.getSubSubCategory().equals("noncombat")){
            String key;
            if((key=command.getOption("position")) !=null){
                try {
                    String[] cordinates=key.split("\\s+");
                    CommandResponse response=validateTileForMovingUnit(currentTile,currentCivilization,cordinates[0],cordinates[1],"noncombat");
                    System.out.println(response.isOK() ? GameController.moveNonCombatUnit(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),currentTile,currentCivilization)
                                       : response);
                }catch (Exception e){
                    System.out.println(CommandResponse.INVALID_POSITION);
                }
            }else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        }else if(command.getSubSubCategory().equals("combat")){
            String key;
            if((key=command.getOption("position")) !=null){
                try {
                    String[] cordinates=key.split("\\s+");
                    CommandResponse response=validateTileForMovingUnit(currentTile,currentCivilization,cordinates[0],cordinates[1],"combat");
                    System.out.println(response.isOK() ? GameController.moveCombatUnit(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),currentTile,currentCivilization)
                                       : response);
                }catch (Exception e){
                    System.out.println(CommandResponse.INVALID_POSITION);
                }
            }else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        }else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }
    private CommandResponse validateTileForMovingUnit(Tile currentTile, Civilization civilization,String row_s,String col_s,String combat) {
        CommandResponse response=isCorrectPosition(row_s,col_s,this.getGame());
        if(!response.isOK()){return response;}
        if(combat.equals("noncombat")){
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        }else {
            if(currentTile.getCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
            if(!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        }
        return CommandResponse.OK;
    }

    private void unitRepair(Command command) {
        Civilization currentCivilization= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateTileForRepairing(currentTile,currentCivilization);
        if(response.isOK())System.out.println(GameController.RepairTile(currentTile));
        else System.out.println(response);
    }
    private CommandResponse validateTileForRepairing(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponse.WRONG_UNIT;}
        if(!isDamaged(currentTile)){return CommandResponse.NOT_DAMAGED;}
            return CommandResponse.OK;
    }
    private boolean isDamaged(Tile currentTile) {
        if(currentTile.isDamaged()) return true;
        return false;
    }

    private void unitRemoveJungle() {
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateTileForRemovingJungle(currentTile,currentCivilizaion);
        if(response.isOK())System.out.println(GameController.RemoveJungle(currentTile));
        else System.out.println(response);
    }
    private CommandResponse validateTileForRemovingJungle(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponse.WRONG_UNIT;}
        if(!isJungleExists(currentTile)){return CommandResponse.JUNGLE_DOESNT_EXISTS;}
        return CommandResponse.OK;
    }
    private boolean isJungleExists(Tile currentTile) {
        //TODO : complete
        if(currentTile.getTerrain().getFeatures().contains(TerrainEnum.JUNGLE)){return true;}
        return false;
    }

    private boolean isPossibleToBuildInThisTerrain(Civilization civilization,ImprovementEnum improvement) {
        if(improvement.hasRequiredTechs(civilization.getTechnologies())){return false;}
        if(improvement.canBeBuiltOn(civilization.getCurrentTile().getTerrain().getFeatures())){return false;}
        return true;

    }

    private boolean isExists(Tile currentTile,ImprovementEnum improvementEnum) {
        if(currentTile.getTerrain().getImprovements().contains(improvementEnum)){return true;}
        return false;
    }

    private void unitRemoveRoute(){
        Civilization currentCivilizaion= getCurrentCilization();
        Tile currentTile=getCurrentTile();
        CommandResponse response=validateTileForRemovingRoute(currentTile,currentCivilizaion);
        if(response.isOK()){
           if(isExists(currentTile,ImprovementEnum.RailRoad)) System.out.println(GameController.RemoveRoute(currentTile,ImprovementEnum.RailRoad));
           if(isExists(currentTile,ImprovementEnum.ROAD)) System.out.println(GameController.RemoveRoute(currentTile,ImprovementEnum.ROAD));
        }else {System.out.println(response);}
    }
    private CommandResponse validateTileForRemovingRoute(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponse.WRONG_UNIT;}
        if(!isExists(currentTile,ImprovementEnum.ROAD) && !isExists(currentTile,ImprovementEnum.RailRoad)){return CommandResponse.ROUTE_DOESNT_EXISTS;}
        return CommandResponse.OK;
    }

    private void unitBuildQuarry() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.STONE_MINE);
        if(response.isOK())System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.STONE_MINE));
        else System.out.println(response);
    }

    private void unitBuildPlantation() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.CULTIVATION);
        if(response.isOK()) System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.CULTIVATION));
        else System.out.println(response);
    }

    private void unitBuildCamp() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.CAMP);
        if(response.isOK())System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.CAMP));
        else System.out.println(response);
    }

    private void unitBuildPasture() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.PASTURE);
        if(response.isOK()) System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.PASTURE));
        else System.out.println(response);
    }

    private void unitBuildLumberMill() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.LUMBER_MILL);
        if(response.isOK()) System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.LUMBER_MILL));
        else System.out.println(response);
    }

    private void unitBuildTradingPost() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.TRADING_POST);
        if(response.isOK()) System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.TRADING_POST));
        else System.out.println(response);
    }

    private void unitBuildMine() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.MINE);
        if(response.isOK()) System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.MINE));
        else System.out.println(response);
    }
    private void unitBuildFarm() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization =  getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.FARM);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.FARM);
            System.out.println("farm built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildRailRoad() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization = getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.RailRoad);
        if(response.isOK())System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.RailRoad));
        else System.out.println(response);
    }

    private void unitBuildRoad() {
        Tile currentTile=getCurrentTile();
        Civilization currentCivilization = getCurrentCilization();
        CommandResponse response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.ROAD);
        if(response.isOK())System.out.println(GameController.BuildImprovment(currentTile,ImprovementEnum.ROAD));
        else System.out.println(response);
    }
    private CommandResponse isPossibleToBuild(Tile currentTile, Civilization currentCivilization,ImprovementEnum improvement) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponse.UNIT_DOESNT_EXISTS;}
        if(!(currentCivilization.getCurrentTile().getNonCombatUnit().getCiv() == currentCivilization)){return CommandResponse.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponse.WRONG_UNIT;}
        if(isExists(currentTile,improvement)){return CommandResponse.IMPROVMENT_EXISTS;}
        if(isPossibleToBuildInThisTerrain(currentCivilization,improvement)){return CommandResponse.YOU_HAVE_NOT_REQUIRED_OPTIONS;}
        return CommandResponse.OK;
    }
    private void selectCity(Command command) {
            Tile currentTile=getCurrentTile();
            Civilization currentCivilization =getCurrentCilization();
            String key;
            if((key=command.getOption("cityName")) != null){
                City city;
                if((city=getCityWithThisName(currentCivilization,key))!= null){System.out.println(GameController.showCity(city));}
                else {System.out.println("city with this name doesn't exists");}
            }else if ((key=command.getOption("cityPositon")) != null) {
                String[] cordinates=key.split("\\s+");
                    CommandResponse response=isCorrectPosition(cordinates[0],cordinates[1],this.getGame());
                    System.out.println(!response.isOK() ? response : GameController.showCity(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),this.getGame()));
                 }else{
                    System.out.println(CommandResponse.INVALID_COMMAND);
                }
    }

    private void selectUnit(Command command) {
        try {
          switch (command.getSubSubCategory()) {
              case "combat"-> selectCombatUnit(command);
              case "noncombat"-> selectNonCombatUnit(command);
              default -> System.out.println(CommandResponse.INVALID_COMMAND);
          }
        }catch (Exception e){
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void selectNonCombatUnit(Command command) {
        try {
            Tile currentTile=getCurrentTile();
            String key;
            if((key=command.getOption("position")) != null) {
                String[] cordinates=key.split("\\s+");
                CommandResponse response = isCorrectPosition(cordinates[0],cordinates[1],this.getGame());
                System.out.println(response.isOK() ? GameController.showNonCombatInfo(currentTile.getNonCombatUnit()) : response );
            }else{
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        }catch (Exception e){
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void selectCombatUnit(Command command) {
        try {
            Tile currentTile=getCurrentTile();
            String key;
            if((key=command.getOption("position")) != null) {
                String[] cordinates=key.split("\\s+");
                CommandResponse response = isCorrectPosition(cordinates[0],cordinates[1],this.getGame());
                System.out.println(response.isOK() ? GameController.showCombatInfo(currentTile.getCombatUnit()) : response );
            }else{
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        }catch (Exception e){
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private City getCityWithThisName(Civilization currentCivilization, String key) {
        for (City city:
             currentCivilization.getCities()) {
            if(city.getName().equals(key)){return city;}
        }
        return null;
    }


    private  CommandResponse isCorrectPosition(String row_s, String col_s,Game game){
       try {
           int row = Integer.parseInt(row_s);
           int col = Integer.parseInt(col_s);
           if (row > game.getTileGrid().getHeight() || row < 0 || col > game.getTileGrid().getWidth() || col < 0)
               return CommandResponse.INVALID_POSITION;
           return CommandResponse.OK;
       }catch (Exception e){
          return CommandResponse.INVALID_COMMAND;
       }
    }
    private  CommandResponse isCorrectPosition(String amount_s,Game game,String direction){
        try {
            //TODO : complete
            int amount = Integer.parseInt(amount_s);
            CommandResponse response;
            switch (direction) {
                case "right" -> response= validateRightWardMove(amount);
                case "left" -> response= validateLeftWardMove(amount);
                case "up" -> response= validateUpWardMove(amount);
                case "down" -> response= validateDownWardMove(amount);
                default -> response=CommandResponse.INVALID_DIRECTION;
            }
            return response;
        }catch (Exception e){
            return CommandResponse.INVALID_COMMAND;
        }
    }
    private CommandResponse validateRightWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateLeftWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateUpWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private CommandResponse validateDownWardMove(int amount){
        //TODO : validate rightward move
        return CommandResponse.OK;
    }
    private Civilization getCurrentCilization(){
        return game.getCivTurn().get(game.getCivTurn().size()-1);
    }
    private Tile getCurrentTile(){
        return game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
    }

}
