package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Models.Civilization;
import Project.Models.Location;
import Project.Models.Resource;
import Project.Models.Units.Unit;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
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
    private MenuButton democracyMenu;
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
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        initDemocracyMenu();
        initTilesMenu();
        initWarMenu();
        initUnitMenu();
        initResourceMenu();
        civName.setText(civilization.getName());
        happiness.setText(String.valueOf(DatabaseQuerier.getHappinessOfCurrentCiv()));
        beaker.setText(String.valueOf(DatabaseQuerier.getScienceOfCurrentCiv()));
        gold.setText(String.valueOf(DatabaseQuerier.getGoldOfCurrentCiv()));
        //todo : cities
        cityCount.setText(String.valueOf(civilization.getCities().size()));
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
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        String command = "info units";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        for (Unit unit : DatabaseQuerier.getCurrentCivilizationUnits()) {
            MenuItem item = new MenuItem(unit.getType().name() + " " + unit.getLocation().toString());
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

    private void initDemocracyMenu() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        //logic hasn't implemented
        String command = "info democracy";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        for (Civilization otherCivilizations : civilization.getIsInEconomicRelation()) {
            MenuItem item = new MenuItem(otherCivilizations.getName());



            democracyMenu.getItems().add(item);
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
