package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.Units.Unit;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class UnitsPanelView implements ViewController {
    @FXML
    private VBox availableUnits;
    @FXML
    private Text selectedUnit;

    private Unit selected;

    private ArrayList<Unit> units;

    public void initialize() {
        units = DatabaseQuerier.getCurrentCivilizationUnits();
        initBox();
        selected = null;
    }

    private void initBox() {
        for (Unit unit : units) {
            Text text = new Text(unit.getUnitType().name());
            text.setOnMouseEntered(mouseEvent -> text.setCursor(Cursor.HAND));
            text.setOnMouseClicked(mouseEvent -> {
                selectedUnit.setText(unit.getUnitType().name());
                selected = unit;
            });
            availableUnits.getChildren().add(text);
        }
    }

    public void gotoUnitPage() {
        if (selected == null)
            return;
        MenuStack.getInstance().getCookies().setSelectedUnit(selected);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("UnitPanelPage"));
    }
}
