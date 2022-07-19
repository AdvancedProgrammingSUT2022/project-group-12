package Project.Client.Views;

import Project.Enums.FeatureEnum;
import Project.Enums.ImprovementEnum;
import Project.SharedModels.Tiles.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class TilePanelView implements ViewController {
    @FXML
    private Text resourcesMenu;
    @FXML
    private MenuButton improvementsMenu;
    @FXML
    private Text type;
    @FXML
    private Text civ;
    @FXML
    private Text locationX;
    @FXML
    private Text locationY;
    @FXML
    private Text combatUnit;
    @FXML
    private Text nonCombatUnit;
    @FXML
    private Text road;
    @FXML
    private Text river;
    @FXML
    private MenuButton featureMenu;
    @FXML
    private Text damaged;

    public void initialize() {
        Tile tile = MenuStack.getInstance().getCookies().getSelectedTile();
        locationX.setText(String.valueOf(tile.getLocation().getRow()));
        locationY.setText(String.valueOf(tile.getLocation().getCol()));

        if (tile.getCombatUnit() == null)
            combatUnit.setText("None");
        else
            combatUnit.setText(tile.getCombatUnit().getUnitType().name());
        if (tile.getNonCombatUnit() == null)
            nonCombatUnit.setText("None");
        else
            nonCombatUnit.setText(tile.getNonCombatUnit().getUnitType().name());
        road.setText(String.valueOf(tile.hasRoad()));
        river.setText(String.valueOf(tile.hasRiver()));
        damaged.setText(String.valueOf(tile.isDamaged()));
//        civ.setText(MenuStack.getInstance().getCookies().getName());
        type.setText(tile.getTerrain().getTerrainType().name());
        initMenu();

    }

    private void initMenu() {
        initImprovementMenu();
        initFeatureMenu();
        initResourceMenu();
    }

    private void initImprovementMenu() {
        Tile tile = MenuStack.getInstance().getCookies().getSelectedTile();
        if (tile.getImprovements() == null)
            return;
        for (ImprovementEnum improvementEnum : tile.getImprovements()) {
            MenuItem item = new MenuItem(improvementEnum.name());
            improvementsMenu.getItems().add(item);
        }
    }

    private void initFeatureMenu() {
        Tile tile = MenuStack.getInstance().getCookies().getSelectedTile();
        if (tile.getTerrain().getFeatures() == null)
            return;
        for (FeatureEnum featureEnum : tile.getTerrain().getFeatures()) {
            MenuItem item = new MenuItem(featureEnum.name());
            featureMenu.getItems().add(item);
        }
    }

    private void initResourceMenu() {
        Tile tile = MenuStack.getInstance().getCookies().getSelectedTile();
        if (tile.getTerrain().getResource() == null)
            resourcesMenu.setText("NULL");
        else
            resourcesMenu.setText(tile.getTerrain().getResource().name());
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
