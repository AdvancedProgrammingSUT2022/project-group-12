package Views;

import Controllers.CheatCodeController;
import Controllers.CombatController;
import Controllers.GameController;
import Controllers.UnitCombatController;
import Controllers.ValidateGameMenuFuncs.InfoFuncs;
import Controllers.ValidateGameMenuFuncs.MapFuncs;
import Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Enums.ImprovementEnum;
import Enums.UnitEnum;
import Models.Cities.City;
import Models.Civilization;
import Models.Location;
import Models.Units.Unit;
import Utils.Command;
import Utils.CommandException;
import Utils.CommandResponse;
import Utils.GameException;

import java.util.List;

public class GameMenu extends Menu {

    private final InfoFuncs infoFuncs;
    private final MapFuncs mapFuncs;
    private final UnitFuncs unitFuncs;
    private Unit selectedUnit;
    private City selectedCity;

    public GameMenu() {
        this.infoFuncs = new InfoFuncs();
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
        GameController.getGame().startNewTurn();
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

    public InfoFuncs getInfoFuncs() {
        return infoFuncs;
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
        System.out.println(message);
    }

    private void findCategory(Command command) {
        switch (command.getCategory()) {
            case "info" -> this.info(command);
            case "select" -> this.select(command);
            case "unit" -> this.unit(command);
            case "map" -> this.map(command);
            case "city" -> this.city(command);
            case "cheat" -> this.cheat(command);
            case "end" -> this.end(command);
            default -> answer(CommandResponse.INVALID_COMMAND);
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatTeleport(Command command) {
        command.abbreviate("position", "p");
        try {
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
            default -> answer(CommandResponse.INVALID_COMMAND);
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatMapReveal(Command command) {
        command.abbreviate("position", "p");
        try {
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

    private void cheatSpawn(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> this.cheatSpawnUnit(command);
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatSpawnUnit(Command command) {
        command.abbreviate("unit", "u");
        command.abbreviate("position", "p");
        try {
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatIncreaseBeaker(Command command) {
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseBeaker(amount);
            answer("beaker increased " + amount + " units successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseHappiness(Command command) {
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseHappiness(amount);
            answer("happiness increased " + amount + " units successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cheatIncreaseGold(Command command) {
        command.abbreviate("amount", "a");
        try {
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
        command.abbreviate("amount", "a");
        try {
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
        command.abbreviate("amount", "a");
        try {
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void endGame() {
        MenuStack.getInstance().popMenu();
        answer("game ended successfully");
    }

    private void endTurn() {
        try {
            GameController.getGame().endCurrentTurn();
        } catch (GameException e) {
            answer(e);
            return;
        }
        System.out.println("end of turn");
        System.out.println("------------------------------");
        this.startNewTurn();
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> cityBuildUnit(command);
            case "building" -> cityBuildBuilding(command);
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityBuildBuilding(Command command) {

    }

    private void cityBuildUnit(Command command) {
        command.abbreviate("unit", "u");
        try {
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityBuyUnit(Command command) {
        command.abbreviate("unit", "u");
        try {
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
        command.abbreviate("position", "p");
        try {
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
            default -> answer(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityCitizenChangeLock(Command command, boolean lock) {
        command.abbreviate("position", "p");
        try {
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
        switch (command.getSubCategory()) {
            case "research" -> getInfoFuncs().researchInfo();
            case "units" -> getInfoFuncs().unitsInfo();
            case "cities" -> getInfoFuncs().citiesInfo();
            case "diplomacy" -> getInfoFuncs().diplomacyInfo();
            case "victory" -> getInfoFuncs().victoryInfo();
            case "demographics" -> getInfoFuncs().demographicsInfo();
            case "notifications" -> getInfoFuncs().notificationInfo();
            case "military" -> getInfoFuncs().militaryInfo();
            case "economic" -> getInfoFuncs().ecoInfo();
            case "diplomatic" -> getInfoFuncs().diplomaticInfo();
            case "deals" -> getInfoFuncs().dealsInfo();
            default -> answer(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void select(Command command) {
        switch (command.getSubCategory()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
            default -> answer(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void selectUnit(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
        } catch (CommandException e) {
            answer(e);
            return;
        }
        switch (command.getSubSubCategory()) {
            case "combat" -> this.setSelectedUnit(command, true);
            case "noncombat" -> this.setSelectedUnit(command, false);
            default -> answer(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void setSelectedUnit(Command command, boolean isCombatUnit) {
        try {
            Location location = command.getLocationOption("position");
            GameController.getGame().getTileGrid().assertLocationValid(location);
            selectedUnit = GameController.getGame().getSelectedUnit(GameController.getGame().getCurrentCivilization(), location, isCombatUnit);
            setCamera(location);
        } catch (CommandException e) {
            answer(e);
            return;
        }
        answer("unit selected: " + selectedUnit.getType().name());
    }

    private void selectCity(Command command) {
        command.abbreviate("name", "n");
        command.abbreviate("position", "p");
        try {
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
                default -> answer(CommandResponse.INVALID_SUBCOMMAND);
            }
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
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "road" -> this.unitBuildImprovement(ImprovementEnum.ROAD);
            case "railRoad" -> this.unitBuildImprovement(ImprovementEnum.RAILROAD);
            case "farm" -> this.unitBuildImprovement(ImprovementEnum.FARM);
            case "mine" -> this.unitBuildImprovement(ImprovementEnum.MINE);
            case "tradingPost" -> this.unitBuildImprovement(ImprovementEnum.TRADING_POST);
            case "lumberMill" -> this.unitBuildImprovement(ImprovementEnum.LUMBER_MILL);
            case "pasture" -> this.unitBuildImprovement(ImprovementEnum.PASTURE);
            case "camp" -> this.unitBuildImprovement(ImprovementEnum.CAMP);
            case "plantation" -> this.unitBuildImprovement(ImprovementEnum.CULTIVATION);
            case "quarry" -> this.unitBuildImprovement(ImprovementEnum.STONE_MINE);
            default -> answer(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory()) {
//            case "route" -> getUnitFuncs().unitRemoveRoute();
//            case "jungle" -> getUnitFuncs().unitRemoveJungle();
            default -> answer(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void map(Command command) {
        switch (command.getSubCategory()) {
            case "show" -> this.mapShow(command);
            case "move" -> this.moveMap(command);
            default -> answer(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void moveMap(Command command) {
        command.abbreviate("amount", "a");
        try {
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
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            getUnitFuncs().unitMoveTo(selectedUnit, location);
            setCamera(location);
            answer("unit moved to " + location + " successfully");
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void foundCity(Command command) {
        try {
            if (!command.getSubSubCategory().equals("city")) {
                answer(CommandResponse.INVALID_SUBSUBCOMMAND);
            }
            City city = GameController.foundCity(this.selectedUnit);
            answer("city found successfully: " + city.getName());
        } catch (CommandException e) {
            answer(e);
        }
    }

    private void cityCitizenModify(Command command, boolean isAssigning) {
        command.abbreviate("position", "p");
        try {
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
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            String combatType;
            if ((combatType = command.getSubSubCategory()).equals("noncombat") || combatType.equals("combat")) {
                CombatController.AttackCity(this.selectedCity, location, combatType);
            } else {
                answer(CommandResponse.INVALID_SUBSUBCOMMAND);
                return;
            }
            answer("city attack successful");
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void unitAttack(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CombatController.AttackUnit(this.selectedUnit, location);
            answer("unit attack successful");
        } catch (CommandException e) {
            answer(e);
        }
    }

    public void unitBuildImprovement(ImprovementEnum improvement) {
        try {
            GameController.buildImprovement(this.selectedUnit, improvement);
            answer("improvement built on " + this.selectedUnit.getLocation() + " successfully");
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
}
