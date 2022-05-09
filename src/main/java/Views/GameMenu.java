package Views;

import Controllers.GameController;
import Controllers.ValidateGameMenuFuncs.InfoFuncs;
import Controllers.ValidateGameMenuFuncs.MapFuncs;
import Controllers.ValidateGameMenuFuncs.UnitFuncs;
import Enums.ImprovementEnum;
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

    public GameMenu(GameController controller) {
        this.infoFuncs = new InfoFuncs(GameController.getGame());
        this.mapFuncs = new MapFuncs(GameController.getGame());
        this.unitFuncs = new UnitFuncs(GameController.getGame());
    }

    public static void printError(CommandResponse commandResponse) {
        System.out.println(commandResponse);
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
                GameController.getGame().startNextTurn();
            }
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void city(Command command) {
        switch (command.getSubCategory() + " " + command.getSubSubCategory()) {
            case "assign citizen" -> cityAssignCitizen(command);
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
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
            case "combat" -> this.selectUnit(command, true);
            case "noncombat" -> this.selectUnit(command, false);
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void selectUnit(Command command, boolean isCombatUnit) {
        try {
            Location location = command.getLocationOption("position");
            GameController.getGame().getTileGrid().assertLocationValid(location);
            selectedUnit = GameController.getGame().getSelectedUnit(GameController.getGame().getCurrentCivilization(), location, isCombatUnit);
            GameController.getGame().getCurrentCivilization().setCurrentSelectedGridLocation(selectedUnit.getLocation());
            GameController.getGame().getCurrentCivilization().setCurrentTile(GameController.getGame().getTileGrid().getTile(selectedUnit.getLocation()));
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

        switch (command.getSubCategory().trim()) {
            case "move" -> unitMove(command);
            case "sleep" -> getUnitFuncs().unitSleep(command);
            case "alert" -> getUnitFuncs().unitAlert();
            case "fortify" -> getUnitFuncs().unitFortify(command);
            case "garrison" -> getUnitFuncs().unitGarrison(command);
            case "setup" -> getUnitFuncs().unitSetup(command);
            case "attack" -> getUnitFuncs().unitAttack(command);
            case "found" -> foundCity(command);
            case "cancel" -> getUnitFuncs().unitCancel(command);
            case "wake" -> getUnitFuncs().unitWake(command);
            case "delete" -> getUnitFuncs().unitDelete(command);
            case "build" -> this.unitBuild(command);
            case "remove" -> this.unitRemove(command);
            case "repair" -> getUnitFuncs().unitRepair(command);
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "road" -> getUnitFuncs().unitBuild(ImprovementEnum.ROAD);
            case "railRoad" -> getUnitFuncs().unitBuild(ImprovementEnum.RAILROAD);
            case "farm" -> getUnitFuncs().unitBuild(ImprovementEnum.FARM);
            case "mine" -> getUnitFuncs().unitBuild(ImprovementEnum.MINE);
            case "tradingPost" -> getUnitFuncs().unitBuild(ImprovementEnum.TRADING_POST);
            case "lumberMill" -> getUnitFuncs().unitBuild(ImprovementEnum.LUMBER_MILL);
            case "pasture" -> getUnitFuncs().unitBuild(ImprovementEnum.PASTURE);
            case "camp" -> getUnitFuncs().unitBuild(ImprovementEnum.CAMP);
            case "plantation" -> getUnitFuncs().unitBuild(ImprovementEnum.CULTIVATION);
            case "quarry" -> getUnitFuncs().unitBuild(ImprovementEnum.STONE_MINE);
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory()) {
            case "route" -> getUnitFuncs().unitRemoveRoute();
            case "jungle" -> getUnitFuncs().unitRemoveJungle();
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
            if (!List.of("combat", "noncombat").contains(command.getSubSubCategory())) {
                System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
                return;
            }
            command.assertOptions(List.of("position"));
            System.out.println(getUnitFuncs().unitMoveTo((command.getLocationOption("position")), command.getSubSubCategory()));
            getMapFuncs().showMapPosition(GameController.getGame().getCurrentCivilization().getCurrentSelectedGridLocation());
        } catch (CommandException e) {
            e.print();
            return;
        }
    }

    private void foundCity(Command command) {
        try {
            if (!command.getSubSubCategory().equals("city")) {
                System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
            }
            City city = getUnitFuncs().foundCity();
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

    private void cityAssignCitizen(Command command) {
        command.abbreviate("position", "p");
        if (selectedCity == null) new CommandException(CommandResponse.CITY_NOT_SELECTED).print();
        try {
            command.assertOptions(List.of("position"));
            Location location = command.getLocationOption("position");
            GameController.cityAssignCitizen(selectedCity, location);
            System.out.println("citizen successfully assigned on " + location);
        } catch (CommandException e) {
            e.print();
        }
    }
}
