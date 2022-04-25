package Views;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Exceptions.CommandException;
import Models.Game;
import Models.Location;
import Views.ViewFuncs.*;

import java.util.List;

public class GameMenu extends Menu {

    private final InfoFuncs infoFuncs;
    private final MapFuncs mapFuncs;
    private final SelectFuncs selectFuncs;
    private final UnitBuildFuncs unitBuildFuncs;
    private final UnitOtherFuncs unitOtherFuncs;
    private final Game game;
    private Location gridCord;

    public GameMenu(GameController controller) {
        this.game = GameController.game;
        this.infoFuncs = new InfoFuncs(GameController.game);
        this.mapFuncs = new MapFuncs(GameController.game);
        this.selectFuncs = new SelectFuncs(GameController.game);
        this.unitBuildFuncs = new UnitBuildFuncs(GameController.game);
        this.unitOtherFuncs = new UnitOtherFuncs(GameController.game);
    }

    public InfoFuncs getInfoFuncs() {
        return infoFuncs;
    }

    public SelectFuncs getSelectFuncs() {
        return selectFuncs;
    }

    public UnitBuildFuncs getUnitBuildFuncs() {
        return unitBuildFuncs;
    }

    public UnitOtherFuncs getUnitOtherFuncs() {
        return unitOtherFuncs;
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
        switch (command.getSubCategory()){
            case "attack" -> UnitOtherFuncs.CityAttack(command);
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
            case "moveTo" -> getUnitOtherFuncs().unitMoveTo(command);
            case "sleep" -> getUnitOtherFuncs().unitSleep(command);
            case "alert" -> getUnitOtherFuncs().unitAlert();
            case "fortify" -> getUnitOtherFuncs().unitFortify(command);
            case "garrison" -> getUnitOtherFuncs().unitGarrison(command);
            case "setup" -> getUnitOtherFuncs().unitSetup(command);
            case "attack" -> getUnitOtherFuncs().unitAttack(command);
            case "found" -> getUnitOtherFuncs().unitFound(command);
            case "cancel" -> getUnitOtherFuncs().unitCancel(command);
            case "wake" -> getUnitOtherFuncs().unitWake(command);
            case "delete" -> getUnitOtherFuncs().unitDelete(command);
            case "build" -> this.unitBuild(command);
            case "remove" -> this.unitRemove(command);
            case "repair" -> getUnitOtherFuncs().unitRepair(command);
            default -> System.out.println(CommandResponse.INVALID_SUBCOMMAND);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory()) {
            case "road" -> getUnitBuildFuncs().unitBuildRoad();
            case "railRoad" -> getUnitBuildFuncs().unitBuildRailRoad();
            case "farm" -> getUnitBuildFuncs().unitBuildFarm();
            case "mine" -> getUnitBuildFuncs().unitBuildMine();
            case "tradingPost" -> getUnitBuildFuncs().unitBuildTradingPost();
            case "lumberMill" -> getUnitBuildFuncs().unitBuildLumberMill();
            case "pasture" -> getUnitBuildFuncs().unitBuildPasture();
            case "camp" -> getUnitBuildFuncs().unitBuildCamp();
            case "plantation" -> getUnitBuildFuncs().unitBuildPlantation();
            case "quarry" -> getUnitBuildFuncs().unitBuildQuarry();
            default -> System.out.println(CommandResponse.INVALID_SUBSUBCOMMAND);
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory()) {
            case "route" -> getUnitOtherFuncs().unitRemoveRoute();
            case "jungle" -> getUnitOtherFuncs().unitRemoveJungle();
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
