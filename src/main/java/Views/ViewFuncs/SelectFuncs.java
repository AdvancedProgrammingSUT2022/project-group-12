package Views.ViewFuncs;

import Controllers.Command;
import Controllers.GameController;
import Enums.CommandResponse;
import Models.Tiles.Tile;

public class SelectFuncs extends GameMemuFuncs{
    private void selectUnit(Command command) {
        try {
            switch (command.getSubSubCategory()) {
                case "combat" -> selectCombatUnit(command);
                case "noncombat" -> selectNonCombatUnit(command);
                default -> System.out.println(CommandResponse.INVALID_COMMAND);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void selectNonCombatUnit(Command command) {
        try {
            Tile currentTile = getCurrentTile();
            String key;
            if ((key = command.getOption("position")) != null) {
                String[] coordinates = key.split("\\s+");
                CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1], this.getGame());
                System.out.println(response.isOK() ? GameController.showNonCombatInfo(currentTile.getNonCombatUnit()) : response);
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

    private void selectCombatUnit(Command command) {
        try {
            Tile currentTile = getCurrentTile();
            String key;
            if ((key = command.getOption("position")) != null) {
                String[] coordinates = key.split("\\s+");
                CommandResponse response = isCorrectPosition(coordinates[0], coordinates[1], this.getGame());
                System.out.println(response.isOK() ? GameController.showCombatInfo(currentTile.getCombatUnit()) : response);
            } else {
                System.out.println(CommandResponse.CommandMissingRequiredOption);
            }
        } catch (Exception e) {
            System.out.println(CommandResponse.INVALID_COMMAND);
        }
    }

}
