package Project.Views;

import Project.Controllers.GameController;
import Project.Enums.FeatureEnum;
import Project.Models.Terrains.Terrain;
import Project.Models.Tiles.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class TilePanelView implements ViewController {

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
        Tile tile = GameController.getGame().getCurrentCivilization().getSelectedTile();
        locationX.setText(String.valueOf(tile.getLocation().getRow()));
        locationY.setText(String.valueOf(tile.getLocation().getCol()));

        if (tile.getCombatUnit() == null)
            combatUnit.setText("None");
        else
            combatUnit.setText(tile.getCombatUnit().getType().name());
        if (tile.getNonCombatUnit() == null)
            nonCombatUnit.setText("None");
        else
            nonCombatUnit.setText(tile.getNonCombatUnit().getType().name());
        road.setText(String.valueOf(tile.hasRoad()));
        river.setText(String.valueOf(tile.hasRiver()));
        damaged.setText(String.valueOf(tile.isDamaged()));
        civ.setText(GameController.getGame().getCurrentCivilization().getName());
        type.setText(tile.getTerrain().getTerrainType().name());
        initMenu();

    }

    private void initMenu() {
        Tile tile = GameController.getGame().getCurrentCivilization().getSelectedTile();
        if (tile.getTerrain().getFeatures() == null)
            return;
        for (FeatureEnum featureEnum : tile.getTerrain().getFeatures()) {
            MenuItem item = new MenuItem(featureEnum.name());
            featureMenu.getItems().add(item);
        }
    }
    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
