package Views.ViewFuncs;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Enums.GameEnums.ImprovementEnum;
import Enums.GameEnums.TerrainEnum;
import Enums.GameEnums.UnitEnum;
import Models.Civilization;
import Models.Game;
import Models.Tiles.Tile;

public class UnitOtherFuncs extends UnitFuncs{

    public UnitOtherFuncs(Game game) {
        super(game);
    }

    public void unitDelete(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit")) {
            case "non combat" -> {
                response = validateForNonCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.deleteNonCombatUnit(currentCivilization, currentTile);
            }
            case "combat" -> {
                response = validateForCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.deleteCombatUnit(currentCivilization, currentTile);
            }
            default -> response = CommandResponse.CommandMissingRequiredOption;
        }
        System.out.println(response.isOK() ? "unit deleted successfully" : response);
    }

    private CommandResponse validateForNonCombatUnit(Tile currentTile, Civilization civilization) {
        if (!(civilization.getCurrentTile().getNonCombatUnit().getType() == null)) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.WRONG_UNIT;
        }
        return CommandResponse.OK;
    }

    private CommandResponse validateForCombatUnit(Tile currentTile, Civilization civilizaion) {
        if (!(civilizaion.getCurrentTile().getCombatUnit().getType() == null)) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilizaion.getCurrentTile().getCombatUnit().getCiv() == civilizaion)) {
            return CommandResponse.WRONG_UNIT;
        }
        return CommandResponse.OK;
    }

    public void unitWake(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit")) {
            case "non combat" -> {
                response = validateForNonCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.wakeUpNonCombatUnit(currentCivilization, currentTile);
            }
            case "combat" -> {
                response = validateForCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.wakeUpCombatUnit(currentCivilization, currentTile);
            }
            default -> response = CommandResponse.CommandMissingRequiredOption;
        }
        System.out.println(response.isOK() ? "unit waked up successfully" : response);
    }

    public void unitCancel(Command command) {
        if (!command.getSubSubCategory().equals("mission")) {
            System.out.println(CommandResponse.INVALID_COMMAND);
            return;
        }
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response;
        switch (command.getOption("unit")) {
            case "non combat" -> {
                response = validateForNonCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.CancelMissionNonCombatUnit(currentCivilization, currentTile);
            }
            case "combat" -> {
                response = validateForCombatUnit(currentTile, currentCivilization);
                if (response.isOK()) GameController.CancelMissionCombatUnit(currentCivilization, currentTile);
            }
            default -> response = CommandResponse.CommandMissingRequiredOption;
        }
        System.out.println(response.isOK() ? "unit waked up successfully" : response);
    }

    public void unitFound(Command command) {
        if (!command.getSubSubCategory().equals("city")) {
            System.out.println(CommandResponse.INVALID_COMMAND);
            return;
        }
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateTileForFoundingCity(currentTile, currentCivilization);
        String message;
        if (response.isOK()) System.out.println(GameController.FoundCity(currentTile));
        else System.out.println(response);
    }

    private CommandResponse validateTileForFoundingCity(Tile currentTile, Civilization civilization) {
        if (currentTile.getNonCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.SETTLER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!isPossibleToBuildCity(currentTile)) {
            return CommandResponse.IMPOSSIBLE_CITY;
        }
        return CommandResponse.OK;
    }

    private boolean isPossibleToBuildCity(Tile currentTile) {
        //TODO : need at least 4 tile around it to build the city
        return true;
    }

    public void unitAttack(Command command) {
        String key;
        if ((key = command.getOption("position")) == null) {
            System.out.println(CommandResponse.CommandMissingRequiredOption);
            return;
        }
        String[] coordinates = key.split("\\s+");
        Civilization civilizaion = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = isCorrectPosition((coordinates[0]), (coordinates[1]), this.getGame());
        int row = 0, col = 0;
        if (response.isOK()) {
            row = Integer.parseInt(coordinates[0]);
            col = Integer.parseInt(coordinates[1]);
        }
        System.out.println(response.isOK() ? GameController.AttackUnit(row, col, this.getGame(), currentTile, civilizaion) : response);
    }

    public void unitSetup(Command command) {
    }

    public void unitGarrison(Command command) {
        Civilization civilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateForGarrison(currentTile, civilization);
        if (response.isOK()) System.out.println(GameController.garrsionUnit(currentTile, civilization));
        else System.out.println(response);
    }

    private CommandResponse validateForGarrison(Tile currentTile, Civilization civilization) {
        if (currentTile.getCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(civilization.getCurrentTile().getCity() == null)) {
            return CommandResponse.CITY_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;

    }

    public void unitFortify(Command command) {
        Civilization civilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        try {
            if (command.getSubSubCategory().equals("heal")) {
                CommandResponse response = validateForCombatUnit(currentTile, civilization);
                if (response.isOK()) GameController.fortifyHealUnit(currentTile, civilization);
                else System.out.println(response);
            } else {
                System.out.println(CommandResponse.INVALID_COMMAND);
            }
        } catch (Exception e) {
            CommandResponse response = validateForCombatUnit(currentTile, civilization);
            if (response.isOK()) System.out.println(GameController.fortifyUnit(currentTile, civilization));
            else System.out.println(response);
        }
    }

    public void unitAlert() {
        Civilization civilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateForCombatUnit(currentTile, civilization);
        if (response.isOK()) System.out.println(GameController.AlertUnit(currentTile, civilization));
        else System.out.println(response);
    }

    public void unitSleep(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response;
        try {
            switch (command.getSubSubCategory()) {
                case "non combat" -> {
                    response = validateForNonCombatUnit(currentTile, currentCivilization);
                    if (response.isOK())
                        System.out.println(GameController.sleepNonCombatUnit(currentCivilization, currentTile));
                }
                case "combat" -> {
                    response = validateForCombatUnit(currentTile, currentCivilization);
                    if (response.isOK())
                        System.out.println(GameController.sleepCombatUnit(currentCivilization, currentTile));
                }
                default -> response = CommandResponse.CommandMissingRequiredOption;
            }
            if (!response.isOK()) System.out.println(response);
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    public void unitMoveTo(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        if (command.getSubSubCategory().equals("noncombat")) {
            String key;
            if ((key = command.getOption("position")) != null) {
                try {
                    String[] coordinates = key.split("\\s+");
                    CommandResponse response = validateTileForMovingUnit(currentTile, currentCivilization, coordinates[0], coordinates[1], "noncombat");
                    System.out.println(response.isOK() ? GameController.moveNonCombatUnit(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), currentTile, currentCivilization) : response);
                } catch (Exception e) {
                    System.out.println(CommandResponse.INVALID_POSITION);
                }
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } else if (command.getSubSubCategory().equals("combat")) {
            String key;
            if ((key = command.getOption("position")) != null) {
                try {
                    String[] coordinates = key.split("\\s+");
                    CommandResponse response = validateTileForMovingUnit(currentTile, currentCivilization, coordinates[0], coordinates[1], "combat");
                    System.out.println(response.isOK() ? GameController.moveCombatUnit(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), currentTile, currentCivilization) : response);
                } catch (Exception e) {
                    System.out.println(CommandResponse.INVALID_POSITION);
                }
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } else {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private CommandResponse validateTileForMovingUnit(Tile currentTile, Civilization civilization, String row_s, String col_s, String combat) {
        CommandResponse response = isCorrectPosition(row_s, col_s, this.getGame());
        int row,col;
        if (!response.isOK()) {
            return response;
        }else {
            row=Integer.parseInt(row_s); col=Integer.parseInt(col_s);
        }
        if (combat.equals("noncombat")) {
            if (currentTile.getNonCombatUnit() == null) {
                return CommandResponse.UNIT_DOES_NOT_EXISTS;
            }
            if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
                return CommandResponse.NOT_HAVING_UNIT;
            }
            if(getGame().getTileGrid().getTiles().get(row).get(col).getNonCombatUnit() != null){
                return CommandResponse.TILE_IS_FULL;
            }
        } else {
            if (currentTile.getCombatUnit() == null) {
                return CommandResponse.UNIT_DOES_NOT_EXISTS;
            }
            if (!(civilization.getCurrentTile().getCombatUnit().getCiv() == civilization)) {
                return CommandResponse.NOT_HAVING_UNIT;
            }
            if(getGame().getTileGrid().getTiles().get(row).get(col).getCombatUnit() != null){
                return CommandResponse.TILE_IS_FULL;
            }
        }
        return CommandResponse.OK;
    }

    public void unitRepair(Command command) {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateTileForRepairing(currentTile, currentCivilization);
        if (response.isOK()) System.out.println(GameController.RepairTile(currentTile));
        else System.out.println(response);
    }

    private CommandResponse validateTileForRepairing(Tile currentTile, Civilization civilization) {
        if (currentTile.getNonCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!isDamaged(currentTile)) {
            return CommandResponse.NOT_DAMAGED;
        }
        return CommandResponse.OK;
    }

    private boolean isDamaged(Tile currentTile) {
        return currentTile.isDamaged();
    }

    public void unitRemoveJungle() {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateTileForRemovingJungle(currentTile, currentCivilization);
        if (response.isOK()) System.out.println(GameController.RemoveJungle(currentTile));
        else System.out.println(response);
    }

    private CommandResponse validateTileForRemovingJungle(Tile currentTile, Civilization civilization) {
        if (currentTile.getNonCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!isJungleExists(currentTile)) {
            return CommandResponse.JUNGLE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }

    private boolean isJungleExists(Tile currentTile) {
        //TODO : complete
        return currentTile.getTerrain().getFeatures().contains(TerrainEnum.JUNGLE);
    }

    public void unitRemoveRoute() {
        Civilization currentCivilization = getCurrentCivilization();
        Tile currentTile = getCurrentTile();
        CommandResponse response = validateTileForRemovingRoute(currentTile, currentCivilization);
        if (response.isOK()) {
            if (isExists(currentTile, ImprovementEnum.RailRoad))
                System.out.println(GameController.RemoveRoute(currentTile, ImprovementEnum.RailRoad));
            if (isExists(currentTile, ImprovementEnum.ROAD))
                System.out.println(GameController.RemoveRoute(currentTile, ImprovementEnum.ROAD));
        } else {
            System.out.println(response);
        }
    }
    private CommandResponse validateTileForRemovingRoute(Tile currentTile, Civilization civilization) {
        if (currentTile.getNonCombatUnit() == null) {
            return CommandResponse.UNIT_DOES_NOT_EXISTS;
        }
        if (!(civilization.getCurrentTile().getNonCombatUnit().getCiv() == civilization)) {
            return CommandResponse.NOT_HAVING_UNIT;
        }
        if (!(currentTile.getNonCombatUnit().getType() == UnitEnum.WORKER)) {
            return CommandResponse.WRONG_UNIT;
        }
        if (!isExists(currentTile, ImprovementEnum.ROAD) && !isExists(currentTile, ImprovementEnum.RailRoad)) {
            return CommandResponse.ROUTE_DOES_NOT_EXISTS;
        }
        return CommandResponse.OK;
    }


}
