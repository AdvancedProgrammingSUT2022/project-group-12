package Views;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Models.Game;
import Models.Location;
import Views.ViewFuncs.*;

public class GameMenu extends Menu {

    private final Game game;
    private final InfoFuncs infoFuncs;
    private final MapFuncs mapFuncs;
    private final SelectFuncs selectFuncs;
    private final UnitBuildFuncs unitBuildFuncs;
    private final UnitOtherFuncs unitOtherFuncs;
    private Location gridCord;

    public GameMenu(GameController controller) {
        this.game = GameController.game;
        this.infoFuncs = new InfoFuncs(GameController.game);
        this.mapFuncs = new MapFuncs(GameController.game);
        this.selectFuncs = new SelectFuncs(GameController.game);
        this.unitBuildFuncs = new UnitBuildFuncs(GameController.game);
        this.unitOtherFuncs = new UnitOtherFuncs(GameController.game);
    }

    public Game getGame() {
        return game;
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
        }
    }

    private void info(Command command) {
        switch (command.getType().trim()) {
            case "info research" -> getInfoFuncs().researchInfo();
            case "info units" -> getInfoFuncs().unitsInfo();
            case "info cities" -> getInfoFuncs().citiesInfo();
            case "info diplomacy" -> getInfoFuncs().diplomacyInfo();
            case "info victory" -> getInfoFuncs().victoryInfo();
            case "info demographics" -> getInfoFuncs().demographicsInfo();
            case "info notifications" -> getInfoFuncs().notifInfo();
            case "info military" -> getInfoFuncs().militaryInfo();
            case "info economic" -> getInfoFuncs().ecoInfo();
            case "info diplomatic" -> getInfoFuncs().diplomaticInfo();
            case "info deals" -> getInfoFuncs().dealsInfo();
        }
    }

    private void select(Command command) {
        switch (command.getType().trim()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> getSelectFuncs().selectCity(command);
        }
    }

    private void selectUnit(Command command) {
        try {
            switch (command.getSubSubCategory()) {
                case "combat" -> getSelectFuncs().selectCombatUnit(command);
                case "noncombat" -> getSelectFuncs().selectNonCombatUnit(command);
                default -> System.out.println(CommandResponse.INVALID_COMMAND);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }


    private void unit(Command command) {
        switch (command.getSubCategory().trim()) {
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
        }
    }

    private void unitBuild(Command command) {
        switch (command.getSubSubCategory().trim()) {
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
        }
    }

    private void unitRemove(Command command) {
        switch (command.getSubSubCategory().trim()) {
            case "route" -> getUnitOtherFuncs().unitRemoveRoute();
            case "jungle" -> getUnitOtherFuncs().unitRemoveJungle();
        }
    }

    private void map(Command command) {
        switch (command.getSubCategory().trim()) {
            case "show" -> getMapFuncs().showMap(command);
            case "move" -> this.moveMap(command);
        }
    }

    private void moveMap(Command command) {
        switch (command.getSubSubCategory().trim()) {
            case "right" -> getMapFuncs().moveMapByDirection(command, "right");
            case "left" -> getMapFuncs().moveMapByDirection(command, "left");
            case "up" -> getMapFuncs().moveMapByDirection(command, "up");
            case "down" -> getMapFuncs().moveMapByDirection(command, "down");
            default -> System.out.println(CommandResponse.INVALID_DIRECTION);
        }
    }

}
