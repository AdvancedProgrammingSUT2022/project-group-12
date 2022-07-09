package Project.Views;

import Project.Controllers.GameController;
import Project.Models.Civilization;
import Project.Models.Tiles.Tile;
import Project.Models.Units.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class CivilizationPanelView implements ViewController {

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
        civName.setText(civilization.getName());
        happiness.setText(String.valueOf(civilization.getHappiness()));
        beaker.setText(String.valueOf(civilization.getBeaker()));
        gold.setText(String.valueOf(civilization.getGold()));
        cityCount.setText(String.valueOf(civilization.getCities().size()));
        food.setText(String.valueOf(civilization.getFood()));
    }

    private void initTilesMenu() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        for (Tile tile : civilization.getOwnedTiles()) {
            MenuItem item = new MenuItem(tile.getLocation().toString());
            tilesMenu.getItems().add(item);
        }
    }

    private void initUnitMenu() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        for (Unit unit : civilization.getUnits()) {
            MenuItem item = new MenuItem(unit.getType().name() + " " + unit.getLocation().toString());
            unitsMenu.getItems().add(item);
        }
    }

    private void initWarMenu() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        for (Civilization otherCivilizations : civilization.getIsInWarWith()) {
            MenuItem item = new MenuItem(otherCivilizations.getName());
            warMenu.getItems().add(item);
        }
    }

    private void initDemocracyMenu() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
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