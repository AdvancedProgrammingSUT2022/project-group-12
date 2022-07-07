package Project.Views;

import Project.Controllers.GameController;
import Project.Enums.UnitStates;
import Project.Models.Location;
import Project.Models.Units.Unit;
import Project.Utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;

public class UnitPanelView implements ViewController {

    @FXML
    private Button moveUnitBtn;
    @FXML
    private Text unitType;
    @FXML
    private Text unitCivilization;
    @FXML
    private Text unitXLocation;
    @FXML
    private Text unitYLocation;
    @FXML
    private Text unitState;
    @FXML
    private Text unitHealth;
    @FXML
    private Text combatStrength;
    @FXML
    private Spinner<Integer> xSpinner;
    private int locationX;
    private SpinnerValueFactory<Integer> xValueFactory;
    @FXML
    private Spinner<Integer> ySpinner;
    private int locationY;
    private SpinnerValueFactory<Integer> yValueFactory;
    private Unit unit;

    public void initialize() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        initializeSpinners();
        unitType.setText(unit.getType().name());
        unitCivilization.setText(unit.getCivilization().getName());
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow() + 1));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol() + 1));
        unitHealth.setText(String.valueOf(unit.getHealth()));
        combatStrength.setText(String.valueOf(unit.getType().getCombatStrength()));
        unitState.setText(unit.getState().toString());
    }

    private void initializeSpinners() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        xValueFactory.setValue(locationX);
        xSpinner.setValueFactory(xValueFactory);
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = xSpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });

        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        yValueFactory.setValue(locationY);
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }

    public void moveUnit() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        // todo : check destination for other units
//        if () {
//            moveUnitBtn.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
//            return;
//        }
        unit.setLocation(new Location(locationX--, locationY--));
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow() + 1));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol() + 1));
    }

    public void sleep() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.SLEEP);
        unitState.setText("SLEEP");
    }

    public void alert() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.ALERT);
        unitState.setText("ALERT");
    }

    public void fortify() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.FORTIFY);
        unitState.setText("FORTIFY");
    }

    public void awake() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.AWAKE);
        unitState.setText("AWAKE");
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }
}
