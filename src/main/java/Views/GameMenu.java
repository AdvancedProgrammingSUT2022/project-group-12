package Views;

import Controllers.CombatController;
import Controllers.GameController;
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

import java.lang.reflect.InvocationTargetException;
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
            case "end" -> this.end(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void end(Command command) {
        switch (command.getSubCategory()) {
            case "turn" -> {
                try {
                    GameController.getGame().endCurrentTurn();
                } catch (GameException e) {
                    e.print();
                    break;
                }
                this.startNewTurn();
            }
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void startNewTurn() {
        System.out.println("end of turn");
        System.out.println("------------------------------");
        GameController.getGame().startNextTurn();
        startOfTurnInfo(GameController.getGame().getCurrentCivilization());
    }

    private void city(Command command) {
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
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
        try {
            command.assertOptions(List.of("unit"));
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName);
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
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
        try {
            command.assertOptions(List.of("unit"));
            String unitName = command.getOption("unit");
            UnitEnum unit = UnitEnum.valueOf(unitName);
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
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
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
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
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
            case "notifications" -> getInfoFuncs().notifInfo();
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
            GameController.getGame().getCurrentCivilization().setCurrentSelectedGridLocation(selectedUnit.getLocation());
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
            if (command.getOption("position") != null) {
                this.selectedCity = selectCityByPosition(command.getLocationOption("position"));
            } else if (command.getOption("name") != null) {
                this.selectedCity = selectCityByName(command.getOption("name"));
            } else {
                new CommandException(CommandResponse.MISSING_REQUIRED_OPTION, "name/position").print();
            }
        } catch (CommandException e) {
            e.print();
            return;
        }
        System.out.println("city selected: " + selectedCity.getName());
    }

    private void unit(Command command) {
        // watch for method names carefully
        switch (command.getSubCategory().trim()) {
            case "move" -> this.unitMove(command);
            case "found" -> this.foundCity(command);
            case "build" -> this.unitBuild(command);
            case "remove" -> this.unitRemove(command);
            case "attack" -> this.unitAttack(command);
            case "fortify" -> this.unitFortify(command);
            case "sleep" -> this.applyCommandOnSelectedUnit("sleepUnit", "unit slept successfully");
            case "alert" -> this.applyCommandOnSelectedUnit("alertUnit", "unit alerted successfully");
            case "wake" -> this.applyCommandOnSelectedUnit("wakeUpUnit", "unit waked up successfully");
            case "delete" -> this.applyCommandOnSelectedUnit("deleteUnit", "unit deleted successfully");
            case "repair" -> this.applyCommandOnSelectedUnit("unitRepairTile", "tile repaired successfully");
            case "cancel" -> this.applyCommandOnSelectedUnit("cancelMissionUnit", "unit mission canceled successfully");
            case "setup" -> this.applyCommandOnSelectedUnit("setupUnit", "siege unit has set up successfully");
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    public void applyCommandOnSelectedUnit(String controllerMethodName, String successMessage) {
        if (this.selectedUnit == null) {
            new CommandException(CommandResponse.UNIT_NOT_SELECTED).print();
            return;
        }
        try {
            GameController.class.getMethod(controllerMethodName, Unit.class).invoke(null, this.selectedUnit);
            System.out.println(successMessage);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof CommandException eCommand) {
                eCommand.print();
            }
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
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
            command.assertOptionType("amount", "integer");
        } catch (CommandException e) {
            e.print();
            return;
        }
        if (!List.of("right", "left", "up", "down").contains(command.getSubSubCategory())) {
            System.out.println(CommandResponse.INVALID_DIRECTION);
            return;
        }
        getMapFuncs().moveMapByDirection(command, command.getSubSubCategory());
        getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
    }

    private void unitMove(Command command) {
        command.abbreviate("position", "p");
        try {
            command.assertOptions(List.of("position"));
            System.out.println(getUnitFuncs().unitMoveTo((command.getLocationOption("position")), selectedUnit));
            getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
        } catch (CommandException e) {
            e.print();
            return;
        }
    }

    private void foundCity(Command command) {
        if (this.selectedUnit == null) {
            new CommandException(CommandResponse.UNIT_NOT_SELECTED).print();
            return;
        }
        try {
            if (!command.getSubSubCategory().equals("city")) {
                System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
            }
            City city = getUnitFuncs().foundCity(this.selectedUnit.getLocation());
            System.out.println("city found successfully: " + city.getName());
        } catch (CommandException e) {
            e.print();
        }
    }

    public City selectCityByPosition(Location location) throws CommandException {
        if (!GameController.getGame().getTileGrid().isLocationValid(location)) {
            throw new CommandException(CommandResponse.INVALID_POSITION);
        }
        City city = GameController.getGame().getTileGrid().getTile(location).getCity();
        Civilization civ = GameController.getGame().getCurrentCivilization();
        if (city == null || city.getCivilization() != civ) {
            throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
        }
        return city;
    }

    public City selectCityByName(String name) throws CommandException {
        Civilization civ = GameController.getGame().getCurrentCivilization();
        for (City city : civ.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        throw new CommandException(CommandResponse.CITY_DOES_NOT_EXISTS);
    }

    private void cityCitizenModify(Command command, boolean isAssigning) {
        command.abbreviate("position", "p");
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
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
        if (this.selectedUnit == null) {
            new CommandException(CommandResponse.UNIT_NOT_SELECTED).print();
            return;
        }
        GameController.buildImprovement(this.selectedUnit, improvement);
    }
}
