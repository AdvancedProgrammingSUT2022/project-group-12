package Views;
import Controllers.ValidateGameMenuFuncs.*;
import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Exceptions.CommandException;
import Models.Location;

import java.util.List;

public class GameMenu extends Menu {

    private final InfoFuncs infoFuncs;
    private final MapFuncs mapFuncs;
    private final SelectFuncs selectFuncs;
    private final UnitFuncs unitFuncs;
    private Location gridCord;

    public GameMenu(GameController controller) {
        this.infoFuncs = new InfoFuncs(GameController.game);
        this.mapFuncs = new MapFuncs(GameController.game);
        this.selectFuncs = new SelectFuncs(GameController.game);
        this.unitFuncs = new UnitFuncs(GameController.game);
    }

    public InfoFuncs getInfoFuncs() {
        return infoFuncs;
    }

    public SelectFuncs getSelectFuncs() {
        return selectFuncs;
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
            default -> System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void city(Command command) {
        switch (command.getSubCategory()) {
//            ?
//            case "attack" -> UnitOtherFuncs.CityAttack(command);
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
        switch (command.getType()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> getSelectFuncs().selectCity(command);
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void selectUnit(Command command) {
        switch (command.getSubSubCategory()) {
            case "combat" -> getSelectFuncs().selectCombatUnit(command);
            case "noncombat" -> getSelectFuncs().selectNonCombatUnit(command);
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void unit(Command command) {
        switch (command.getSubCategory()) {
            case "moveTo" -> getUnitFuncs().unitMoveTo(command);
            case "sleep" -> getUnitFuncs().unitSleep(command);
            case "alert" -> getUnitFuncs().unitAlert();
            case "fortify" -> getUnitFuncs().unitFortify(command);
            case "garrison" -> getUnitFuncs().unitGarrison(command);
            case "setup" -> getUnitFuncs().unitSetup(command);
            case "attack" -> getUnitFuncs().unitAttack(command);
            case "found" -> getUnitFuncs().unitFound(command);
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
        try {
            command.assertOptions(List.of("amount"));
            command.assertOptionType("amount", "integer");
        } catch (CommandException e) {
            e.print();
            return;
        }
        switch (command.getSubSubCategory()) {
            case "right" -> getMapFuncs().moveMapByDirection(command, "right");
            case "left" -> getMapFuncs().moveMapByDirection(command, "left");
            case "up" -> getMapFuncs().moveMapByDirection(command, "up");
            case "down" -> getMapFuncs().moveMapByDirection(command, "down");
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

}
