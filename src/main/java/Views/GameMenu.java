package Views;

import Controllers.Command;
import Controllers.GameController;
import Controllers.GameMenuController;
import Enums.CommandResponseEnum;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

import java.awt.image.LookupOp;

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
        switch (command.getType()) {
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
        switch (command.getType()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
        }
    }

    private void unit(Command command) {
        switch (command.getSubCategory()) {
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
        switch (command.getSubSubCategory()) {
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
        switch (command.getSubSubCategory()) {
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
                default -> System.out.println(CommandResponseEnum.INVALID_DIRECTION);
            }
    }

    private void showMap(Command command) {
        String key;
       if((key=command.getOption("position")) != null){
            showMapPosition(key);
       }else if((key=command.getOption("cityname")) != null){
           String[] cordinates=key.split("\\s+");
           try {
               CommandResponseEnum response=GameMenuController.showMapOnPosition(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),this.getGame());
               System.out.println(!response.isOK() ? response : "Position changed succesfully");
           }catch (Exception e){
               System.out.println(CommandResponseEnum.INVALID_POSITION);
           }
       }else {
           System.out.println(CommandResponseEnum.CommandMissingRequiredOption);
       }
    }
    private void showMapPosition(String key){
        String[] cordinates=key.split("\\s+");
        try {
            CommandResponseEnum response=GameMenuController.showMapOnPosition(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),this.getGame());
            System.out.println(!response.isOK() ? response : "Position changed succesfully");
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_POSITION);
        }
    }
    private void showMapCity(String key){
        CommandResponseEnum response=GameMenuController.showMapOnCity(key,game);
        System.out.println(!response.isOK() ? response : "Position changed succesfully");
    }




    private void moveMapByDirection(Command command,String direction) {
        CommandResponseEnum response;
        if(command.validateCommand(command.getType().trim(),command.getCategory(),command.getSubCategory(),command.getSubSubCategory())){
            response=CommandResponseEnum.OK;
          String key;
            if((key=command.getOption("amount")) != null) {
            try {
                switch (direction) {
                    case "down" -> response = GameMenuController.moveMapDown(Integer.parseInt(key));
                    case "up" -> response = GameMenuController.moveMapUp(Integer.parseInt(key));
                    case "right" -> response = GameMenuController.moveMapRight(Integer.parseInt(key));
                    case "left" -> response = GameMenuController.moveMapLeft(Integer.parseInt(key));
                }
            }
            catch (Exception e){response=CommandResponseEnum.INVALID_NUMBER;}
          }else{
                response=CommandResponseEnum.CommandMissingRequiredOption;
            }
        }else {
            response=CommandResponseEnum.INVALID_COMMAND;
        }
        System.out.println(!response.isOK() ? response : "map moved successfully");
    }

    private void unitDelete(Command command) {
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                                if(response.isOK())GameController.deletenonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                                if(response.isOK())GameController.deleteCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponseEnum.CommandMissingRequiredOption;
        }
        if(response.isOK()){
            System.out.println("unit deleted successfully");
        }else {
            System.out.println(response);
        }

    }

    private CommandResponseEnum validateFornonCombatUnit(Tile currentTile,Civilization civilization) {
        if(!(civilization.getCurrentTile().getNonCombatUnit().getType()== null)){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv()== civilization)){return CommandResponseEnum.WRONG_UNIT;}
        return CommandResponseEnum.OK;
    }
    private CommandResponseEnum validateForCombatUnit(Tile currentTile, Civilization civilizaion) {
        if(!(civilizaion.getCurrentTile().getCombatUnit().getType()== null)){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilizaion.getCurrentTile().getCombatUnit().getCiv()== civilizaion)){return CommandResponseEnum.WRONG_UNIT;}
        return CommandResponseEnum.OK;
    }

    private void unitWake(Command command) {
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.wakeUpNonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.wakeUpCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponseEnum.CommandMissingRequiredOption;
        }
        if(response.isOK()){
            System.out.println("unit waked up successfully");
        }else {
            System.out.println(response);
        }
    }


    private void unitCancel(Command command) {
        if(!command.getSubSubCategory().equals("mission")){
            System.out.println(CommandResponseEnum.INVALID_COMMAND); return;
        }
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response;
        switch (command.getOption("unit"))
        {
            case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.CancelMissionNonCombatUnit(currentCivilizaion,currentTile);  break;
            case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                if(response.isOK())GameController.CancelMissionCombatUnit(currentCivilizaion,currentTile);     break;
            default : response=CommandResponseEnum.CommandMissingRequiredOption;
        }
        if(response.isOK()){
            System.out.println("unit waked up successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitFound(Command command) {
          if(!command.getSubSubCategory().equals("city")){
              System.out.println(CommandResponseEnum.INVALID_COMMAND); return;
          }
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateTileForFoundingCity(currentTile,currentCivilizaion);
        if(response.isOK()){
            GameController.FoundCity(currentTile);
            System.out.println(response);
        }else {
            System.out.println(response);
        }


    }

    private CommandResponseEnum validateTileForFoundingCity(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.SETTLER)) {return CommandResponseEnum.WRONG_UNIT;}
        if(!isPossibleToBuildCity(currentTile)){return CommandResponseEnum.IMPOSSIBLE_CITY;}
        return CommandResponseEnum.OK;
    }

    private boolean isPossibleToBuildCity(Tile currentTile) {
        //TODO : complete
        return true;
    }

    private void unitAttack(Command command) {
        String key;
        if((key=command.getOption("position"))==null){
            System.out.println(CommandResponseEnum.CommandMissingRequiredOption); return;
        }
        String[] cordinates=key.split("\\s+");
        try {
            Civilization civilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
            Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
            CommandResponseEnum response=GameMenuController.AttackUnit(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),this.getGame(),currentTile,civilizaion);
            System.out.println(!response.isOK() ? response : "attack successfully happened");
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_POSITION);
        }
    }

    private void unitSetup(Command command) {
    }

    private void unitGarrison(Command command) {
        Civilization civilization=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateForGarrison(currentTile,civilization);
        if(response.isOK()){
            GameController.garrsionUnit(currentTile,civilization);
            System.out.println("unit garrsioned successfully");
        }else {
            System.out.println(response);
        }
    }

    private CommandResponseEnum validateForGarrison(Tile currentTile, Civilization civilization) {
        if(currentTile.getCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if(!(civilization.getCurrentTile().getCity() == null)){return CommandResponseEnum.CITY_DOESNT_EXISTS;}
        return CommandResponseEnum.OK;

    }

    private void unitFortify(Command command) {
        try {
            if (command.getSubSubCategory().equals("heal")){
                Civilization civilization=game.getCivTurn().get(game.getCivTurn().size()-1);
                Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
                CommandResponseEnum response=validateForCombatUnit(currentTile,civilization);
                if(response.isOK()){
                    GameController.fortifyHealUnit(currentTile,civilization);
                    System.out.println("unit fortified successfully");
                }else {
                    System.out.println(response);
                }
            }else {
                System.out.println(CommandResponseEnum.INVALID_COMMAND);
            }
        }catch (Exception e){
            Civilization civilization=game.getCivTurn().get(game.getCivTurn().size()-1);
            Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
            CommandResponseEnum response=validateForCombatUnit(currentTile,civilization);
            if(response.isOK()){
                GameController.fortifyHealUnit(currentTile,civilization);
                System.out.println("unit fortified successfully");
            }else {
                System.out.println(response);
            }
        }
    }

    private void unitAlert() {
        Civilization civilization=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateForCombatUnit(currentTile,civilization);
        if(response.isOK()){
            GameController.AlertUnit(currentTile,civilization);
            System.out.println("unit alerted successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitSleep(Command command) {
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response;
        try {
            switch (command.getSubSubCategory())
            {
                case "non combat" : response=validateFornonCombatUnit(currentTile,currentCivilizaion);
                    if(response.isOK())GameController.sleepNonCombatUnit(currentCivilizaion,currentTile);  break;
                case "combat"     : response=validateForCombatUnit(currentTile,currentCivilizaion);
                    if(response.isOK())GameController.sleepCombatUnit(currentCivilizaion,currentTile);     break;
                default : response=CommandResponseEnum.CommandMissingRequiredOption;
            }
            if(response.isOK()){
                System.out.println("unit sleeped successfully");
            }else {
                System.out.println(response);
            }
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_COMMAND);
        }
    }

    private void unitMoveTo(Command command) {
        //TODO : complete
    }

    private void unitRepair(Command command) {
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateTileForRepairing(currentTile,currentCivilizaion);
        if(response.isOK()){
            GameController.RepairTile(currentTile);
            System.out.println(response);
        }else {
            System.out.println(response);
        }
    }

    private boolean isDamaged() {
        //TODO : complete
        return true;
    }

    private CommandResponseEnum validateTileForRepairing(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponseEnum.WRONG_UNIT;}
        //TODO :  بررسی شرط عدم وجود خرابی
        if(!isDamaged()){return CommandResponseEnum.NOT_DAMAGED;}
            return CommandResponseEnum.OK;
    }
    private CommandResponseEnum validateTileForRemovingRoute(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponseEnum.WRONG_UNIT;}
        if(!isExists(currentTile,ImprovementEnum.ROAD) && !isExists(currentTile,ImprovementEnum.RailRoad)){return CommandResponseEnum.ROUTE_DOESNT_EXISTS;}
        return CommandResponseEnum.OK;
    }
    private CommandResponseEnum validateTileForRemovingJungle(Tile currentTile, Civilization civilization) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponseEnum.WRONG_UNIT;}
        if(!isJungleExists(currentTile)){return CommandResponseEnum.JUNGLE_DOESNT_EXISTS;}
        return CommandResponseEnum.OK;
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


    private void unitRemoveJungle() {
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateTileForRemovingJungle(currentTile,currentCivilizaion);
        if(response.isOK()){
            GameController.RemoveJungle(currentTile);
            System.out.println("route removed succesfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitRemoveRoute(){
        Civilization currentCivilizaion=game.getCivTurn().get(game.getCivTurn().size()-1);
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        CommandResponseEnum response=validateTileForRemovingRoute(currentTile,currentCivilizaion);
        if(response.isOK()){
           if(isExists(currentTile,ImprovementEnum.RailRoad)) GameController.RemoveRoute(currentTile,ImprovementEnum.RailRoad);
           if(isExists(currentTile,ImprovementEnum.ROAD)) GameController.RemoveRoute(currentTile,ImprovementEnum.ROAD);
            System.out.println("route removed succesfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildQuarry() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.STONE_MINE);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.STONE_MINE);
            System.out.println("quarry built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildPlantation() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.CULTIVATION);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.CULTIVATION);
            System.out.println("cultivation built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildCamp() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.CAMP);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.CAMP);
            System.out.println("camp built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildPasture() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.PASTURE);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.PASTURE);
            System.out.println("pasture built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildLumberMill() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.LUMBER_MILL);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.LUMBER_MILL);
            System.out.println("lumber mill built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildTradingPost() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.TRADING_POST);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.TRADING_POST);
            System.out.println("trading post built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildMine() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.MINE);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.MINE);
            System.out.println("mine built successfully");
        }else {
            System.out.println(response);
        }
    }
    private CommandResponseEnum isPossibleToBuild(Tile currentTile, Civilization currentCivilization,ImprovementEnum improvement) {
        if(currentTile.getNonCombatUnit() == null){return CommandResponseEnum.UNIT_DOESNT_EXISTS;}
        if(!(currentCivilization.getCurrentTile().getNonCombatUnit().getCiv() == currentCivilization)){return CommandResponseEnum.NOT_HAVING_UNIT;}
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {return CommandResponseEnum.WRONG_UNIT;}
        if(isExists(currentTile,improvement)){return CommandResponseEnum.IMPROVMENT_EXISTS;}
        if(isPossibleToBuildInThisTerrain(currentCivilization,improvement)){return CommandResponseEnum.YOU_HAVE_NOT_REQUIRED_OPTIONS;}
        return CommandResponseEnum.OK;
    }


    private void unitBuildFarm() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.FARM);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.FARM);
            System.out.println("farm built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void unitBuildRailRoad() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.RailRoad);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.RailRoad);
            System.out.println("rail road built successfully");
        }else {
            System.out.println(response);
        }
    }



    private void unitBuildRoad() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        CommandResponseEnum response=isPossibleToBuild(currentTile,currentCivilization,ImprovementEnum.ROAD);
        if(response.isOK()){
            GameController.BuildImprovment(currentTile,ImprovementEnum.ROAD);
            System.out.println("road built successfully");
        }else {
            System.out.println(response);
        }
    }

    private void selectCity(Command command) {
        try {
            Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
            Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
            String key;
            if((key=command.getOption("cityName")) != null){
                City city;
                if((city=getCityWithThisName(currentCivilization,key))!= null){System.out.println(GameController.showCity(city));}
                else {System.out.println("city with this name doesn't exists");}
            } else if ((key=command.getOption("cityPositon")) != null) {
                String[] cordinates=key.split("\\s+");
                try {
                    CommandResponseEnum response=GameController.showCity(Integer.parseInt(cordinates[0]),Integer.parseInt(cordinates[1]),this.getGame());
                    System.out.println(!response.isOK() ? response : "city showed succesfully");
                }catch (Exception e){
                    System.out.println(CommandResponseEnum.INVALID_POSITION);
                }
                 }else{
                    System.out.println(CommandResponseEnum.INVALID_COMMAND);
                }
            }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_COMMAND);
        }
    }

    private void selectUnit(Command command) {
        try {
          switch (command.getSubSubCategory()) {
              case "combat"-> selectCombatUnit();
              case "noncombat"-> selectNonCombatUnit();
              default -> System.out.println(CommandResponseEnum.INVALID_COMMAND);
          }
          return;
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_COMMAND);
        }
    }

    private void selectNonCombatUnit(Command command) {
        try {
            Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
            CommandResponseEnum response = currentTile.getNonCombatUnit()==null ? CommandResponseEnum.UNIT_DOESNT_EXISTS : CommandResponseEnum.OK;
            if(response.isOK()){
                GameController.showNonCombatInfo(currentTile);
            }else {
                System.out.println(response);
            }
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_POSITION);
        }
    }



    private void selectCombatUnit(Command command) {
        try {
            Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
            CommandResponseEnum response = currentTile.getCombatUnit()==null ? CommandResponseEnum.UNIT_DOESNT_EXISTS : CommandResponseEnum.OK;
            if(response.isOK()){
                GameController.showCombatInfo(currentTile);
            }else {
                System.out.println(response);
            }
        }catch (Exception e){
            System.out.println(CommandResponseEnum.INVALID_POSITION);
        }
    }

    private City getCityWithThisName(Civilization currentCivilization, String key) {
        for (City city:
             currentCivilization.getCities()) {
            if(city.getName().equals(key)){return city;}
        }
        return null;
    }

    private void dealsInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showDealsInfo(currentTile,currentCivilization);
    }

    private void diplomaticInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showDiplomaticInfo(currentTile,currentCivilization);
    }

    private void ecoInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showEcoInfo(currentTile,currentCivilization);
    }

    private void militaryInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showMilitaryInfo(currentTile,currentCivilization);
    }

    private void notifInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showNotifInfo(currentTile,currentCivilization);
    }

    private void demographicsInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showDemographicsInfo(currentTile,currentCivilization);
    }

    private void victoryInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showVictoryInfo(currentTile,currentCivilization);
    }

    private void diplomacyInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showDiplomacyInfo(currentTile,currentCivilization);
    }

    private void unitsInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        GameController.showUnitsInfo(currentTile,currentCivilization);
    }

    private void citiesInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        System.out.println(GameController.showCitiesInfo(currentTile,currentCivilization));
    }
    private  boolean isCorrectPosition(int row, int col,Game game){
        if(row > game.getTileGrid().getHeight() || row < 0 ||  col > game.getTileGrid().getWidth() || col < 0) return false;
        return true;
    }
    private void researchInfo() {
        Tile currentTile=game.getCivTurn().get(game.getCivTurn().size()-1).getCurrentTile();
        Civilization currentCivilization = game.getCivTurn().get(game.getCivTurn().size()-1);
        System.out.println(GameController.showResearchInfo(currentTile,currentCivilization));
    }
}
