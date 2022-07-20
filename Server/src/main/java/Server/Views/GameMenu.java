package Server.Views;

import Project.Enums.BuildingEnum;
import Project.Enums.ImprovementEnum;
import Project.Enums.TechnologyEnum;
import Project.Enums.UnitEnum;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Units.Unit;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Server.Controllers.CheatCodeController;
import Server.Controllers.CombatController;
import Server.Controllers.GameController;
import Server.Controllers.UnitCombatController;
import Server.Controllers.ValidateGameMenuFuncs.MapFuncs;
import Server.Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Server.Models.Civilization;
import Server.Utils.Command;
import Server.Utils.CommandException;
import Server.Utils.GameException;

import java.util.List;

public class GameMenu extends Menu {

    private final MapFuncs mapFuncs;
    private final UnitFuncs unitFuncs;
    private static Unit selectedUnit;
    private static City selectedCity;

    public GameMenu() {
        this.mapFuncs = new MapFuncs();
        this.unitFuncs = new UnitFuncs();
    }

    public static void printError(CommandResponse commandResponse) {
        System.out.println(commandResponse);
    }

    @Override
    public void firstRun() {
        this.startNewTurn();
    }

    private void startNewTurn() {
        try {
            GameController.getGame().startNewTurn();
        } catch (GameException e) { // game ended
            endGame();
        }
        showTheMap();
        printStartOfTurnInfo(GameController.getGame().getCurrentCivilization());
    }

    public void printStartOfTurnInfo(Civilization civilization) {
        System.out.println("turn " + (GameController.getGame().getGameTurnNumber() + 1) + ", turn of: " + civilization.getName());
    }

    private void showTheMap() {
        getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
    }

    public MapFuncs getMapFuncs() {
        return mapFuncs;
    }

    public UnitFuncs getUnitFuncs() {
        return unitFuncs;
    }

    @Override
    protected void handleCommand(Command command) {
        switch (command.getType()) {
            case "show current menu" -> answer(this.getName());
            case "menu exit" -> MenuStack.getInstance().popMenu();
            default -> this.findCategory(command);
        }
    }


    @Override
    protected void answer(Object message) {
        showTheMap();
        super.answer(message);
    }

    private void findCategory(Command command) {
        //debug
//        if (selectedCity != null) {
//            System.out.println("selectedCity.calculateFood() = " + selectedCity.calculateFood());
//            System.out.println("selectedCity.calculateCityHappiness() = " + selectedCity.calculateCityHappiness());
//            System.out.println("selectedCity.calculateGold() = " + selectedCity.calculateGold());
//            System.out.println("this.getSelectedCity().getCivilization().calculateScience() = " + this.getSelectedCity().getCivilization().calculateScience());
//        }
        switch (command.getCategory()) {
            case "info" -> this.info(command);
            case "trade" -> this.trade(command);
            case "demand" -> this.demand(command);
            case "declare" -> this.declareWar(command);
            case "peace" -> this.peace(command);
            case "select" -> this.select(command);
            case "unit" -> this.unit(command);
            case "map" -> this.map(command);
            case "city" -> this.city(command);
            case "research" -> this.research(command);
            case "cheat" -> this.cheat(command);
            case "end" -> this.end(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void peace(Command command) {
        switch (command.getSubCategory()){
            case "create" -> createPeace(command);
            case "accept" -> acceptPeace(command);
            case "reject" -> rejectPeace(command);
        }
    }

    private void rejectPeace(Command command) {
        try {
            command.abbreviate("name",'n');
            GameController.rejectPeace(command.getOption("name"));
            answer("peace rejected successfully");
        }
        catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptPeace(Command command) {
        try {
            command.abbreviate("name",'n');
            GameController.acceptPeace(command.getOption("name"));
            answer("peace accepted successfully");
        }
        catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPeace(Command command) {
        try {
            command.abbreviate("civName",'c');
            command.abbreviate("name",'n');
            GameController.createPeace(GameController.getGame().getCurrentCivilization().getName(),command.getOption("civName"),command.getOption("name"));
            answer("peace created successfully");
        }
        catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void declareWar(Command command) {
        switch (command.getSubSubCategory()){
            case "create" -> createDeclareWar(command);
            case "seen" -> seenDeclareWar(command);
        }
    }

    private void seenDeclareWar(Command command) {
        try {
            command.abbreviate("name",'n');
            GameController.seenDeclareWar(GameController.getGame().getCurrentCivilization(),command.getOption("name"));
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDeclareWar(Command command) {
           try {
               command.abbreviate("civilization",'c');
               command.abbreviate("name",'n');
               GameController.declareWar(GameController.getGame().getCurrentCivilization().getName(),command.getOption("civilization"),command.getOption("name"));
               answer("war declares to " + command.getOption("civilization") + " successfully");
           } catch (CommandException e) {
               throw new RuntimeException(e);
           }
    }

    private void demand(Command command) {
        switch (command.getSubCategory()){
            case "create" -> createDemand(command);
            case "accept" -> acceptDemand(command);
            case "reject" -> rejectDemand(command);
        }
    }

    private void rejectDemand(Command command) {
        try {
            command.abbreviate("demandName",'d');
            String demandName = command.getOption("demandName");
            GameController.rejectDemand(demandName);
            answer(demandName + " rejected successfully");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptDemand(Command command) {
        try {
            command.abbreviate("demandName",'d');
            String demandName = command.getOption("demandName");
            GameController.acceptDemand(demandName);
            answer("Demand accepted successfully");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDemand(Command command) {
        try {
            command.abbreviate("request", 'r');
            command.abbreviate("civName",'n');
            command.abbreviate("demandName",'d');
            String request = command.getOption("request");
            GameController.createDemand(request,command.getOption("civName"),command.getOption("demandName"));
            answer("Demand created successfully");
        }
        catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void trade(Command command){
        switch (command.getSubCategory()){
            case "reject" -> this.rejectTrade(command);
            case "accept" -> this.acceptTrade(command);
            case "create" -> this.makeTrade(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void acceptTrade(Command command) {
        try {
            command.abbreviate("name", 'n');
            GameController.acceptTrade(command.getOption("name"));
            answer("trade accepted successfully");
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_TECHNOLOGY_NAME));
        }
    }

    private void rejectTrade(Command command) {
        try {
            command.abbreviate("name", 'n');
            GameController.rejectTrade(command.getOption("name"));
            answer("trade rejected successfully");
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_TECHNOLOGY_NAME));
        }
    }

    private void makeTrade(Command command) {
        try {
            command.abbreviate("civilization", 'c');
            command.abbreviate("name", 'n');
            command.abbreviate("request", 'r');
            command.abbreviate("suggest",'s');
            GameController.sendTradeRequest(command.getOption("civilization"),command.getOption("request"),command.getOption("suggest"),command.getOption("name"));
            answer("trade suggested successfully");
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_TECHNOLOGY_NAME));
        }
    }

    private void research(Command command) {
        try {
            command.abbreviate("technology", 't');
            command.assertOptions(List.of("technology"));
            String technologyName = command.getOption("technology");
            TechnologyEnum technology = TechnologyEnum.valueOf(technologyName.toUpperCase());
            GameController.getGame().getCurrentCivilization().startResearchOnTech(technology);
            answer("started to research on " + technology.name());
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_TECHNOLOGY_NAME));
        }
    }

    private void cheat(Command command) {
        switch (command.getSubCategory()) {
            case "increase" -> this.cheatIncrease(command);
            case "spawn" -> this.cheatSpawn(command);
            case "map" -> this.cheatMap(command);
            case "finish" -> this.cheatFinish(command);
            case "teleport" -> this.cheatTeleport(command);
            case "reveal" -> this.cheatMapReveal(command);
            case "unlock" -> this.cheatUnlock(command);
            case "heal" -> this.cheatHeal(command);
            case "build" -> this.cheatAddBuilding(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cheatAddBuilding(Command command) {
        if (selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        try {
            command.abbreviate("name", 'n');
            String buildingName = command.getOption("name").toUpperCase();
            CheatCodeController.getInstance().addBuilding(BuildingEnum.getBuildingEnumByName(buildingName), selectedCity);
            System.out.println("selectedCity : " + selectedCity.getName() + " Building : " + buildingName.toLowerCase() + " added successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatUnlock(Command command) {
        switch (command.getSubSubCategory()) {
            case "technologies" -> this.cheatUnlockTechnologies();
            default -> answer(new CommandException(CommandResponse.INVALID_SUBCOMMAND));
        }
    }

    private void cheatUnlockTechnologies() {
        CheatCodeController.getInstance().unlockTechnologies(GameController.getGame().getCurrentCivilization());
        answer("all technologies unlocked for you");
    }

    private void cheatHeal(Command command) {
        switch (command.getSubSubCategory()) {
            case "city" -> this.cheatHealCity(command);
            case "unit" -> this.cheatHealUnit(command);
            default -> answer(new CommandException(CommandResponse.INVALID_SUBSUBCOMMAND));
        }
    }

    private void cheatTeleport(Command command) {
        try {
            if (selectedUnit == null) {
                answer(new CommandException(CommandResponse.NO_UNIT_SELECTED));
                return;
            }
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CheatCodeController.getInstance().teleport(location, selectedUnit);
            setCamera(location);
            answer("unit teleported on " + location + "  successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatFinish(Command command) {
        switch (command.getSubSubCategory()) {
            case "products" -> this.cheatFinishProducts();
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cheatFinishProducts() {
        if (this.selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        try {
            CheatCodeController.getInstance().finishProducts(this.selectedCity);
            answer("production of " + this.selectedCity.getName() + " finished successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatMap(Command command) {
        switch (command.getSubSubCategory()) {
            case "reveal" -> this.cheatMapReveal(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cheatMapReveal(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CheatCodeController.getInstance().revealTile(location);
            setCamera(location);
            answer("tile " + location + " revealed successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void setCamera(Location location) {
        GameController.getGame().getCurrentCivilization().setCurrentSelectedGridLocation(location);
    }

    private Location getCamera() {
        return GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation();
    }

    private void cheatSpawn(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> this.cheatSpawnUnit(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cheatSpawnUnit(Command command) {
        try {
            command.abbreviate("unit", 'u');
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("unit", "position"));
            Location location = command.getLocationOption("position");
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName.toUpperCase());
            Civilization civilization = GameController.getGame().getCurrentCivilization();
            CheatCodeController.getInstance().spawnUnit(unit, civilization, location);
            setCamera(location);
            answer(unitName + " spawned at " + location + " successfully");
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_UNIT_NAME));
        }
    }

    private void cheatIncrease(Command command) {
        switch (command.getSubSubCategory()) {
            case "gold" -> this.cheatIncreaseGold(command);
            case "production" -> this.cheatIncreaseProduction(command);
            case "food" -> this.cheatIncreaseFood(command);
            case "happiness" -> this.cheatIncreaseHappiness(command);
            case "science" -> this.cheatIncreaseBeaker(command);
            case "movement" -> this.cheatIncreaseMovementCost(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }


    private void cheatHealCity(Command command) {
        if (selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        CheatCodeController.getInstance().healCity(selectedCity);
        answer("city healed successfully !");
    }

    private void cheatHealUnit(Command command) {
        if (selectedUnit == null) {
            answer(new CommandException(CommandResponse.UNIT_NOT_SELECTED));
            return;
        }
        CheatCodeController.getInstance().healUnit(selectedUnit);
        answer("unit healed successfully !");
    }

    private void cheatIncreaseMovementCost(Command command) {
        try {
            if (selectedUnit == null) {
                answer(new CommandException(CommandResponse.UNIT_NOT_SELECTED));
                return;
            }
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseMovement(selectedUnit, amount);
            answer("unit movement increased " + amount + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseBeaker(Command command) {
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseBeaker(amount);
            answer("beaker increased " + amount + " units successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseHappiness(Command command) {
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseHappiness(amount);
            answer("happiness increased " + amount + " units successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseGold(Command command) {
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseGold(amount);
            answer(amount + " gold added successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseProduction(Command command) {
        if (this.selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseProduction(this.selectedCity, amount);
            answer(amount + " production added to " + this.selectedCity.getName() + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseFood(Command command) {
        if (this.selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseFood(this.selectedCity, amount);
            answer(amount + " food added to " + this.selectedCity.getName() + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void end(Command command) {
        switch (command.getSubCategory()) {
            case "turn" -> this.endTurn();
            case "game" -> this.endGame();
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void endGame() {
        MenuStack.getInstance().popMenu();
        answer("game ended successfully");
    }

    private void endTurn() {
        String message;
        try {
            message = GameController.getGame().endCurrentTurn();
            this.selectedCity = null;
            this.selectedUnit = null;
        } catch (CommandException e) {
            answer(e);
            return;
        }
        this.selectedUnit = null;
        this.selectedCity = null;
        System.out.println("end of turn"); // todo: convert to answer() or not?
        System.out.println("------------------------------");
        this.startNewTurn();
        answer(message);
    }

    private void city(Command command) {
        if (this.selectedCity == null) {
            answer(new CommandException(CommandResponse.CITY_NOT_SELECTED));
            return;
        }
        switch (command.getSubCategory()) {
            case "citizen" -> cityCitizen(command);
            case "build" -> cityBuild(command);
            case "buy" -> cityBuy(command);
            case "attack" -> cityAttack(command);
            case "queue" -> cityQueue(command);
            // not required in graphics
//            case "info" -> answer(this.selectedCity.getInfo());
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityQueue(Command command) {
        switch (command.getSubSubCategory()) {
            case "show" -> cityQueueShow();
            case "remove" -> cityQueueRemove(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cityQueueRemove(Command command) {
        try {
            command.abbreviate("number", 'n');
            int index = 0;
            if (command.getOption("number") != null) {
                index = command.getIntOption("number") - 1;
            }
            this.getSelectedCity().removeFromProductionQueue(index);
            System.out.println("production number " + (index + 1) + " removed successfully");
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void cityQueueShow() {
        answer(this.getSelectedCity().getQueueInfo());
    }


    private void cityBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> cityBuildUnit(command);
            case "building" -> cityBuildBuilding(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cityBuildBuilding(Command command) {
        try {
            command.abbreviate("name", 'n');
            command.assertOptions(List.of("name"));
            String buildingName = command.getOption("name");
            BuildingEnum building = BuildingEnum.valueOf(buildingName.toUpperCase());
            GameController.cityBuildBuilding(selectedCity, building);
            answer(building + " added to production queue of " + this.selectedCity.getName());
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_UNIT_NAME));
        }

    }

    private void cityBuildUnit(Command command) {
        try {
            command.abbreviate("unit", 'u');
            command.assertOptions(List.of("unit"));
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName.toUpperCase());
            GameController.cityBuildUnit(selectedCity, unit);
            answer(unitName + " added to production queue of " + this.selectedCity.getName());
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_UNIT_NAME));
        }
    }

    private void cityBuy(Command command) {
        switch (command.getSubSubCategory().trim()) {
            case "tile" -> cityBuyTile(command);
            case "unit" -> cityBuyUnit(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cityBuyUnit(Command command) {
        try {
            command.abbreviate("unit", 'u');
            command.assertOptions(List.of("unit"));
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName.toUpperCase());
            GameController.cityBuyUnit(selectedCity, unit);
            answer(unitName + " bought and added at " + this.selectedCity.getName());
        } catch (CommandException e) {
            answer(e);
        } catch (IllegalArgumentException e) {
            answer(new CommandException(CommandResponse.INVALID_UNIT_NAME));
        }
    }

    private void cityBuyTile(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            GameController.cityBuyTile(selectedCity, location);
            answer("tile " + location + " bought for " + this.selectedCity.getName());
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cityCitizen(Command command) {
        switch (command.getSubSubCategory()) {
            case "assign" -> cityCitizenModify(command, true);
            case "unassign" -> cityCitizenModify(command, false);
            case "lock" -> cityCitizenChangeLock(command, true);
            case "unlock" -> cityCitizenChangeLock(command, false);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void cityCitizenChangeLock(Command command, boolean lock) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            GameController.cityCitizenSetLock(selectedCity, location, lock);
            if (lock) {
                answer("citizen successfully locked on " + location);
            } else {
                answer("citizen successfully unlocked from " + location);
            }
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void info(Command command) {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        switch (command.getSubCategory()) {
            case "research" -> answer(civ.getController().researchPanel());
            case "units" -> answer(civ.getController().unitsPanel());
            case "cities" -> answer(civ.getController().citiesPanel());
//            case "diplomacy" -> answer(civ.getController());
//            case "victory" -> answer(civ.getController());
            case "demographics" -> answer(civ.getController().demographicPanel());
            case "notifications" -> answer(civ.getController().notificationHistory());
            case "military" -> answer(civ.getController().militaryOverview());
            case "economic" -> answer(civ.getController().economicOverview());
//            case "diplomatic" -> answer(civ.getController());
//            case "deals" -> answer(civ.getController());
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void select(Command command) {
        switch (command.getSubCategory()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void selectUnit(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
        } catch (CommandException e) {
            answer(e);
            return;
        }
        if (command.getSubSubCategory().isBlank()) {
            this.setSelectedUnit(command, null);
        } else {
            switch (command.getSubSubCategory()) {
                case "combat" -> this.setSelectedUnit(command, true);
                case "noncombat" -> this.setSelectedUnit(command, false);
                default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
            }
        }
    }

    private void setSelectedUnit(Command command, Boolean isCombatUnit) {
        try {
            Location location = command.getLocationOption("position");
            GameController.getGame().getTileGrid().assertLocationValid(location);
            selectedUnit = GameController.getGame().getSelectedUnit(GameController.getGame().getCurrentCivilization(), location, isCombatUnit);
            setCamera(location);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("unit selected: " + selectedUnit.getUnitType().name());
    }

    // might require to set null every time
    private void selectCity(Command command) {
        try {
            command.abbreviate("name", 'n');
            command.abbreviate("position", 'p');
            Civilization civ = GameController.getGame().getCurrentCivilization();
            if (command.getOption("position") != null) {
                this.selectedCity = GameController.selectCityByPosition(civ, command.getLocationOption("position"));
            } else if (command.getOption("name") != null) {
                this.selectedCity = GameController.selectCityByName(civ, command.getOption("name"));
            } else {
                answer(new CommandException(CommandResponse.MISSING_REQUIRED_OPTION, "name/position"));
            }
            setCamera(this.selectedCity.getLocation());
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("city selected: " + selectedCity.getName());
    }

    private void unit(Command command) {
        if (this.selectedUnit == null) {
            answer(new CommandException(CommandResponse.UNIT_NOT_SELECTED));
            return;
        }
        try {
            switch (command.getSubCategory().trim()) {
                case "move" -> this.unitMove(command);
                case "found" -> this.foundCity(command);
                case "build" -> this.unitBuild(command);
                case "remove" -> this.unitRemove(command);
                case "attack" -> this.unitAttack(command);
                case "fortify" -> this.unitFortify(command);
                case "sleep" -> this.unitSleep();
                case "alert" -> this.unitAlert();
                case "wake" -> this.unitWakeUp();
                case "delete" -> this.unitDelete();
                case "repair" -> this.unitRepairTile();
                case "cancel" -> this.unitCancel();
                case "setup" -> this.unitSetup();
                case "pillage" -> this.unitPillage();
                case "info" -> answer(this.selectedUnit.getInfo());
                default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
            }
            this.selectedUnit = null;
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void unitPillage() throws CommandException {
        try {
            GameController.pillageUnit(this.selectedUnit);
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void unitSleep() throws CommandException {
        GameController.sleepUnit(this.selectedUnit);
        answer("unit slept successfully");
    }

    private void unitAlert() throws CommandException {
        GameController.alertUnit(this.selectedUnit);
        answer("unit alerted successfully");
    }

    private void unitWakeUp() throws CommandException {
        GameController.wakeUpUnit(this.selectedUnit);
        answer("unit waked up successfully");
    }

    private void unitDelete() {
        GameController.deleteUnit(this.selectedUnit);
        answer("unit deleted successfully");
    }

    private void unitRepairTile() throws CommandException {
        GameController.unitRepairTile(this.selectedUnit);
        answer("tile repaired successfully");
    }

    private void unitCancel() {
        GameController.cancelMissionUnit(this.selectedUnit);
        answer("unit mission canceled successfully");
    }

    private void unitSetup() throws CommandException {
        UnitCombatController.setupUnit(this.selectedUnit);
        answer("siege unit has set up successfully");
    }

    private void unitFortify(Command command) {
        try {
            unitFuncs.unitFortify(selectedUnit, command);
            answer("unit fortified successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory()) {
//            case "road" -> this.unitBuildImprovement(ImprovementEnum.ROAD);
//            case "railRoad" -> this.unitBuildImprovement(ImprovementEnum.RAILROAD);
            case "improvement" -> this.unitBuildImprovement(selectedUnit);
//            case "farm" -> this.unitBuildImprovement(ImprovementEnum.FARM);
//            case "mine" -> this.unitBuildImprovement(ImprovementEnum.MINE);
//            case "tradingPost" -> this.unitBuildImprovement(ImprovementEnum.TRADING_POST);
//            case "lumberMill" -> this.unitBuildImprovement(ImprovementEnum.LUMBER_MILL);
//            case "pasture" -> this.unitBuildImprovement(ImprovementEnum.PASTURE);
//            case "camp" -> this.unitBuildImprovement(ImprovementEnum.CAMP);
//            case "plantation" -> this.unitBuildImprovement(ImprovementEnum.CULTIVATION);
//            case "quarry" -> this.unitBuildImprovement(ImprovementEnum.STONE_MINE);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory()) {
//            case "route" -> getUnitFuncs().unitRemoveRoute();
//            case "jungle" -> getUnitFuncs().unitRemoveJungle();
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void map(Command command) {
        switch (command.getSubCategory()) {
            case "show" -> this.mapShow(command);
            case "move" -> this.mapMove(command);
            case "info" -> this.mapInfo(command);
            default -> answer(new CommandException(CommandResponse.INVALID_COMMAND));
        }
    }

    private void mapInfo(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            setCamera(location);
            answer(GameController.getGameTile(location).getInfo());
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void mapMove(Command command) {
        try {
            command.abbreviate("amount", 'a');
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            String direction = command.getSubSubCategory();
            if (!List.of("right", "left", "up", "down").contains(direction)) {
                answer(new CommandException(CommandResponse.INVALID_DIRECTION));
                return;
            }
            getMapFuncs().moveMapByDirection(direction, amount);
            answer("map moved " + amount + " unit " + direction + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void unitMove(Command command) {
        // bug: unit not going in the shortest path
        // bug: unit not continue its move each turn
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            getUnitFuncs().unitMoveTo(selectedUnit, location);
            setCamera(this.selectedUnit.getLocation());
            if (isCurrentTileHaveRuin(location)){
                GameController.getGameTile(selectedUnit.getLocation()).achieveRuin();
                selectedUnit.getCivilization().addGold(Constants.GOLD_PRIZE_RUIN);
                answer("unit moved to " + location + " successfully and ruin is achieved and added 30 gold to your civilization");
                return;
            }
            answer("unit moved to " + location + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private boolean isCurrentTileHaveRuin(Location location) {
        if(GameController.getGameTile(selectedUnit.getLocation()).hasRuin()){
            return true;
        }
        return false;
    }

    private void foundCity(Command command) {
        try {
            if (!command.getSubSubCategory().equals("city")) {
                answer(new CommandException(CommandResponse.INVALID_COMMAND));
            }
            City city = GameController.foundCity(this.selectedUnit);
            answer("city found successfully: " + city.getName());
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cityCitizenModify(Command command, boolean isAssigning) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            if (isAssigning) {
                GameController.cityAssignCitizen(selectedCity, location);
                answer("citizen successfully assigned on " + location);
            } else {
                GameController.cityUnassignCitizen(selectedCity, location);
                answer("citizen successfully unassigned from " + location);
            }
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void cityAttack(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CombatController.AttackCity(this.selectedCity, location);
            answer("city attack successful");
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void unitAttack(Command command) {
        try {
            command.abbreviate("position", 'p');
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            String ans = CombatController.AttackUnit(selectedUnit, location);
            MenuStack.getInstance().clearResponseParameters();
            MenuStack.getInstance().addResponseParameters("unitDamage", "?");
            MenuStack.getInstance().addResponseParameters("enemyDamage", "?");
            answer(ans);
//            answer("unit attack successful");
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void unitBuildImprovement(Unit selectedUnit) {
        try {
            ImprovementEnum improvement = GameController.buildImprovement(selectedUnit);
            answer("improvement " + improvement + " built on " + this.selectedUnit.getLocation() + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void mapShow(Command command) {
        if ((command.getOption("position")) != null) {
            try {
                Location location = command.getLocationOption("position");
                setCamera(location);
            } catch (CommandException e) {
                answer(e);
            }
        }
        showTheMap();
    }

    public static Unit getSelectedUnit() {
        return selectedUnit;
    }
    public static City getSelectedCity(){
        return selectedCity;
    }

}
