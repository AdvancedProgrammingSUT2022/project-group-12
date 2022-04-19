package Views;

import Controllers.Command;

public class GameMenu extends Menu {
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
        switch (command.getType()) {
            case "info research" -> this.researchInfo();
            case "info units" -> this.unitsInfo();
            case "info cities" -> this.citiesInfo();
            case "info diplomacy" -> this.diplomacyInfo();
            case "info victory" -> this.victoryInfo();
            case "info demographics" -> this.dempgraphicsInfo();
            case "info notifications" -> this.notifInfo();
            case "info military" -> this.militaryInfo();
            case "info economic" -> this.ecoInfo();
            case "info diplomatic" -> this.diplomaticInfo();
            case "info deals" -> this.dealsInfo();
        }
    }

    private void select(Command command) {
        switch (command.getType()) {
            case "unit" -> this.selectUnit(command);
            case "city" -> this.selectCity(command);
        }
    }

    private void unit(Command command) {
        switch (command.getType()) {
            case "moveTo" -> this.unitMoveTo(command);
            case "sleep" -> this.unitSleep();
            case "alert" -> this.unitAlert();
            case "fortify" -> this.unitFortify(command);
            case "garrison" -> this.unitGarrison();
            case "setup" -> this.unitSetup(command);
            case "attack" -> this.unitAttack();
            case "found" -> this.unitFound(command);
            case "cancel" -> this.unitCancel(command);
            case "wake" -> this.unitWake();
            case "delete" -> this.unitDelete();
            case "build" -> this.unitBuild(command);
            case "remove" -> this.unitRemove(command);
            case "repair" -> this.unitRepair(command);
        }
    }

    private void unitBuild(Command command) {
        switch (command.getType()) {
            case "road" -> this.unitBuildRoad();
            case "railRoad" -> this.unitBuildRailRoad();
            case "farm" -> this.unitBuildFarm();
            case "mine" -> this.unitBuildMine();
            case "tradingPost" -> this.unitBuildTradingPost();
            case "lumberMill" -> this.unitBuildLumberMill();
            case "pasture" -> this.unitBuildPasture();
            case "camp" -> this.unitBuildCamp();
            case "plantation" -> this.unitBuildPlantation();
            case "query" -> this.unitBuildQuery();
        }
    }

    private void unitRemove(Command command) {
        switch (command.getType()) {
            case "route" -> this.unitRemoveRoute();
            case "jungle" -> this.unitRemoveJungle();
        }
    }

    private void map(Command command) {
        switch (command.getType()) {
            case "show" -> this.showMap(command);
            case "move" -> this.moveMap(command);
        }
    }

    private void moveMap(Command command) {
        switch (command.getType()) {
            case "right" -> this.moveMapRight(command);
            case "left" -> this.moveMapLeft(command);
            case "up" -> this.moveMalUp(command);
            case "down" -> this.moveMapDown(command);
        }
    }

    private void showMap(Command command) {
        switch (command.getType()) {
            case "position" -> this.citiesInfo();
            case "cityName" -> this.diplomacyInfo();
        }
    }

    private void moveMapDown(Command command) {
    }

    private void moveMalUp(Command command) {
    }

    private void moveMapLeft(Command command) {
    }

    private void moveMapRight(Command command) {
    }

    private void unitDelete() {
    }

    private void unitWake() {
    }

    private void unitCancel(Command command) {
    }

    private void unitFound(Command command) {
    }

    private void unitAttack() {
    }

    private void unitSetup(Command command) {
    }

    private void unitGarrison() {
    }

    private void unitFortify(Command command) {
    }

    private void unitAlert() {
    }

    private void unitSleep() {
    }

    private void unitMoveTo(Command command) {
    }

    private void unitRepair(Command command) {

    }

    private void unitRemoveJungle() {
    }

    private void unitRemoveRoute() {
    }

    private void unitBuildQuery() {
    }

    private void unitBuildPlantation() {
    }

    private void unitBuildCamp() {
    }

    private void unitBuildPasture() {
    }

    private void unitBuildLumberMill() {
    }

    private void unitBuildTradingPost() {
    }

    private void unitBuildMine() {
    }

    private void unitBuildFarm() {
    }

    private void unitBuildRailRoad() {
    }

    private void unitBuildRoad() {
    }

    private void selectCity(Command command) {

    }

    private void selectUnit(Command command) {

    }

    private void dealsInfo() {
    }

    private void diplomaticInfo() {
    }

    private void ecoInfo() {
    }

    private void militaryInfo() {
    }

    private void notifInfo() {
    }

    private void dempgraphicsInfo() {
    }

    private void victoryInfo() {
    }

    private void diplomacyInfo() {
    }

    private void unitsInfo() {
    }

    private void citiesInfo() {
    }

    private void researchInfo() {
    }
}
