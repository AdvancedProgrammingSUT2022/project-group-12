package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestHandler;
import Project.Models.Location;
import Project.Models.Resource;
import Project.Utils.CommandResponse;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CivilizationPanelView implements ViewController {

    @FXML
    private MenuButton resourcesMenuButton;
    @FXML
    private MenuButton warMenu;
    @FXML
    private MenuButton unitsMenu;
    @FXML
    private MenuButton tilesMenu;
    @FXML
    private Text cityCount;
    @FXML
    private Text food;
    @FXML
    private Text beaker;
    @FXML
    private Text gold;
    @FXML
    private Text happiness;
    @FXML
    private Text civName;

    public void initialize() {
        initTilesMenu();
        initWarMenu();
        initUnitMenu();
        initResourceMenu();
        civName.setText(DatabaseQuerier.getCurrentCivName());
        happiness.setText(String.valueOf(DatabaseQuerier.getHappinessOfCurrentCiv()));
        beaker.setText(String.valueOf(DatabaseQuerier.getScienceOfCurrentCiv()));
        gold.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
        //todo : cities
        cityCount.setText(String.valueOf(DatabaseQuerier.getCurrentCivCitiesNames().size()));
        food.setText(String.valueOf(DatabaseQuerier.getFoodOfCurrentCiv()));
    }

    private void initResourceMenu() {
        ArrayList<Resource> resources = DatabaseQuerier.getCivResources();
        for (Resource resource : resources) {
            MenuItem menuItem = new MenuItem(resource.getResourceEnum().name().toLowerCase());
            resourcesMenuButton.getItems().add(menuItem);
        }
    }

    private void initTilesMenu() {
        ArrayList<Location> ownedTilesLocations = DatabaseQuerier.getCivTilesLocations();
        for (Location location : ownedTilesLocations) {
            MenuItem item = new MenuItem(String.valueOf(location));
            tilesMenu.getItems().add(item);
        }
//        MenuItem item = new MenuItem(String.valueOf(civilization.getOwnedTiles().size()));
//        tilesMenu.getItems().add(item);
    }

    private void initUnitMenu() {
        String command = "info units";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        ArrayList<String> unitNames = new ArrayList<>(DatabaseQuerier.getCurrentCivUnitsNames());
        ArrayList<Location> unitLocations = new ArrayList<>(DatabaseQuerier.getCurrentCivUnitsLocation());
        for (int i = 0; i < unitLocations.size(); i++) {
            MenuItem item = new MenuItem(unitNames.get(i) + " " + unitLocations.get(i).toString());
            unitsMenu.getItems().add(item);
        }
    }

    private void initWarMenu() {
        String command = "info military";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        for (String otherCivilizationsName : DatabaseQuerier.getCurrentCivInWarWith()) {
            MenuItem item = new MenuItem(otherCivilizationsName);
            warMenu.getItems().add(item);
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
