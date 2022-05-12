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

    @Override
    public void firstRun() {
        startOfTurnInfo(GameController.getGame().getCurrentCivilization());
    }

    public static void printError(CommandResponse commandResponse) {
        System.out.println(commandResponse);
    }

    public void startOfTurnInfo(Civilization civilization) {
        System.out.println("turn of: " + civilization.getName());
    }

    public InfoFuncs getInfoFuncs() {
        return infoFuncs;
    }

    public UnitFuncs getUnitFuncs() {
        return unitFuncs;
    }

    public MapFuncs getMapFuncs() {
        return mapFuncs;
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
            case "city" -> this.city(command);
            case "cheat" -> this.cheat(command);
            case "end" -> this.end(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheat(Command command) {
        switch (command.getSubCategory()) {
            case "increase" -> this.cheatIncrease(command);
            case "spawn" -> this.cheatSpawn(command);
            case "map" -> this.cheatMap(command);
            case "finish" -> this.cheatFinish(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatFinish(Command command) {
        switch (command.getSubSubCategory()) {
            case "products" -> this.cheatFinishProducts();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatFinishProducts() {
        if (this.selectedCity == null) {
            new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
            return;
        }
        try {
            CheatCodeController.getInstance().finishProducts(this.selectedCity);
            System.out.println("production of " + this.selectedCity.getName() + " finished successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cheatMap(Command command) {
        switch (command.getSubSubCategory()) {
            case "reveal" -> this.cheatMapReveal(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatMapReveal(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CheatCodeController.getInstance().revealTile(location);
            setCamera(location);
            System.out.println("tile " + location + " revealed successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void setCamera(Location location) {
        GameController.getGame().getCurrentCivilization().setCurrentSelectedGridLocation(location);
    }

    private void cheatSpawn(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> this.cheatSpawnUnit(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
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
            CheatCodeController.getInstance().spawnUnit(unit, location);
            setCamera(location);
            System.out.println(unitName + " spawned at " + location + " successfully");
        } catch (CommandException e) {
            e.print();
        } catch (IllegalArgumentException e) {
            new CommandException(CommandResponse.INVALID_UNIT_NAME).print();
        }
    }

    private void cheatIncrease(Command command) {
        switch (command.getSubSubCategory()) {
            case "gold" -> this.cheatIncreaseGold(command);
            case "production" -> this.cheatIncreaseProduction(command);
            case "food" -> this.cheatIncreaseFood(command);
            case "happiness" -> this.cheatIncreaseHappiness(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cheatIncreaseHappiness(Command command) {
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseHappiness(amount);
            System.out.println("happiness increased " + amount + " units successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cheatIncreaseGold(Command command) {
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseGold(amount);
            System.out.println(amount + " gold added successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cheatIncreaseProduction(Command command) {
        if (this.selectedCity == null) {
            new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
            return;
        }
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseProduction(this.selectedCity, amount);
            System.out.println(amount + " production added to " + this.selectedCity.getName() + " successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cheatIncreaseFood(Command command) {
        if (this.selectedCity == null) {
            new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
            return;
        }
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            CheatCodeController.getInstance().increaseFood(this.selectedCity, amount);
            System.out.println(amount + " food added to " + this.selectedCity.getName() + " successfully");
        } catch (CommandException e) {
            e.print();
        }
    }

    private void end(Command command) {
        switch (command.getSubCategory()) {
            case "turn" -> this.endTurn();
            case "game" -> this.endGame();
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void endGame() {
        MenuStack.getInstance().popMenu();
        System.out.println("game ended successfully");
    }

    private void endTurn() {
        try {
            GameController.getGame().endCurrentTurn();
        } catch (GameException e) {
            e.print();
            return;
        }
        this.startNewTurn();
    }

    private void startNewTurn() {
        System.out.println("end of turn");
        System.out.println("------------------------------");
        GameController.getGame().startNextTurn();
        startOfTurnInfo(GameController.getGame().getCurrentCivilization());
    }

    private void city(Command command) {
        if (this.selectedCity == null) {
            new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
            return;
        }
        switch (command.getSubCategory()) {
            case "citizen" -> cityCitizen(command);
            case "build" -> cityBuild(command);
            case "buy" -> cityBuy(command);
            case "attack" -> cityAttack(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "unit" -> cityBuildUnit(command);
            case "building" -> cityBuildBuilding(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
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
            System.out.println(unitName + " added to production queue of " + this.selectedCity.getName());
        } catch (CommandException e) {
            e.print();
        } catch (IllegalArgumentException e) {
            new CommandException(CommandResponse.INVALID_UNIT_NAME).print();
        }
    }

    private void cityBuy(Command command) {
        switch (command.getSubSubCategory()) {
            case "tile" -> cityBuyTile(command);
            case "unit" -> cityBuyUnit(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityBuyUnit(Command command) {
        command.abbreviate("unit", "u");
        try {
            command.assertOptions(List.of("unit"));
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName.toUpperCase());
            GameController.cityBuyUnit(selectedCity, unit);
            System.out.println(unitName + " bought and added at " + this.selectedCity.getName());
        } catch (CommandException e) {
            e.print();
        } catch (IllegalArgumentException e) {
            new CommandException(CommandResponse.INVALID_UNIT_NAME).print();
        }
    }

    private void cityBuyTile(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            GameController.cityBuyTile(selectedCity, location);
            System.out.println("tile " + location + " bought for " + this.selectedCity.getName());
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cityCitizen(Command command) {
        switch (command.getSubSubCategory()) {
            case "assign" -> cityCitizenModify(command, true);
            case "unassign" -> cityCitizenModify(command, false);
            case "lock" -> cityCitizenChangeLock(command, true);
            case "unlock" -> cityCitizenChangeLock(command, false);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void cityCitizenChangeLock(Command command, boolean lock) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            GameController.cityCitizenSetLock(selectedCity, location, lock);
            if (lock) {
                System.out.println("citizen successfully locked on " + location);
            } else {
                System.out.println("citizen successfully unlocked from " + location);
            }
        } catch (CommandException e) {
            e.print();
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
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void select(Command command) {
        switch (command.getSubCategory()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void selectUnit(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
        } catch (CommandException e) {
            e.print();
            return;
        }
        switch (command.getSubSubCategory()) {
            case "combat" -> this.setSelectedUnit(command, true);
            case "noncombat" -> this.setSelectedUnit(command, false);
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void setSelectedUnit(Command command, boolean isCombatUnit) {
        try {
            Location location = command.getLocationOption("position");
            GameController.getGame().getTileGrid().assertLocationValid(location);
            selectedUnit = GameController.getGame().getSelectedUnit(GameController.getGame().getCurrentCivilization(), location, isCombatUnit);
            setCamera(location);
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("unit selected: " + selectedUnit.getType().name());
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
                new CommandException(CommandResponse.MISSING_REQUIRED_OPTION, "name/position").print();
            }
            setCamera(this.selectedCity.getLocation());
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("city selected: " + selectedCity.getName());
    }

    private void unit(Command command) {
        if (this.selectedUnit == null) {
            new CommandException(CommandResponse.UNIT_NOT_SELECTED).print();
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
                default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
            }
        } catch (CommandException e) {
            e.print();
        }
    }

    private void unitSleep() throws CommandException {
        GameController.sleepUnit(this.selectedUnit);
        System.out.println("unit slept successfully");
    }


    private void unitAlert() throws CommandException {
        GameController.alertUnit(this.selectedUnit);
        System.out.println("unit alerted successfully");
    }

    private void unitWakeUp() throws CommandException {
        GameController.wakeUpUnit(this.selectedUnit);
        System.out.println("unit waked up successfully");
    }

    private void unitDelete() {
        GameController.deleteUnit(this.selectedUnit);
        System.out.println("unit deleted successfully");
    }


    private void unitRepairTile() throws CommandException {
        GameController.unitRepairTile(this.selectedUnit);
        System.out.println("tile repaired successfully");
    }

    private void unitCancel() {
        GameController.cancelMissionUnit(this.selectedUnit);
        System.out.println("unit mission canceled successfully");
    }

    private void unitSetup() throws CommandException {
        UnitCombatController.setupUnit(this.selectedUnit);
        System.out.println("siege unit has set up successfully");
    }

    private void unitFortify(Command command) {
        try {
            unitFuncs.unitFortify(selectedUnit, command);
        } catch (CommandException e) {
            e.print();
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
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory()) {
//            case "route" -> getUnitFuncs().unitRemoveRoute();
//            case "jungle" -> getUnitFuncs().unitRemoveJungle();
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void map(Command command) {
        switch (command.getSubCategory()) {
            case "show" -> getMapFuncs().showMap(command);
            case "move" -> this.moveMap(command);
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void moveMap(Command command) {
        command.abbreviate("amount", "a");
        try {
            command.assertOptions(List.of("amount"));
            int amount = command.getIntOption("amount");
            if (!List.of("right", "left", "up", "down").contains(command.getSubSubCategory())) {
                new CommandException(CommandResponse.INVALID_DIRECTION).print();
                return;
            }
            getMapFuncs().moveMapByDirection(command.getSubSubCategory(), amount);
            getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
        } catch (CommandException e) {
            e.print();
        }
    }

    private void unitMove(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            getUnitFuncs().unitMoveTo(selectedUnit, location);
            System.out.println("unit moved to " + location + " successfully");
            getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
            setCamera(location);
        } catch (CommandException e) {
            e.print();
        }
    }

    private void foundCity(Command command) {
        try {
            if (!command.getSubSubCategory().equals("city")) {
                System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
            }
            City city = GameController.foundCity(this.selectedUnit);
            System.out.println("city found successfully: " + city.getName());
        } catch (CommandException e) {
            e.print();
        }
    }

    private void cityCitizenModify(Command command, boolean isAssigning) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            if (isAssigning) {
                GameController.cityAssignCitizen(selectedCity, location);
                System.out.println("citizen successfully assigned on " + location);
            } else {
                GameController.cityUnassignCitizen(selectedCity, location);
                System.out.println("citizen successfully unassigned from " + location);
            }
        } catch (CommandException e) {
            e.print();
        }
    }

    public void cityAttack(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CombatController.AttackCity(this.selectedCity, location);
            System.out.println("city attack successful");
        } catch (CommandException e) {
            e.print();
        }
    }

    public void unitAttack(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            CombatController.AttackUnit(this.selectedUnit, location);
            System.out.println("unit attack successful");
        } catch (CommandException e) {
            e.print();
        }
    }

    public void unitBuildImprovement(ImprovementEnum improvement) {
        GameController.buildImprovement(this.selectedUnit, improvement);
    }
}
