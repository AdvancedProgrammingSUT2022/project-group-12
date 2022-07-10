package Project.Views;

import Project.Controllers.GameController;
import Project.Enums.UnitStates;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import Project.ServerViews.RequestHandler;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class UnitPanelView implements ViewController {

    public Button attackUnitBtn1;
    @FXML
    private Text name;
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
    @FXML
    private Spinner<Integer> xAttackSpinner;
    @FXML
    private Spinner<Integer> yAttackSpinner;
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
        name.setText(unit.getType().name());
        unitType.setText(unit.getType().name());
        unitCivilization.setText(unit.getCivilization().getName());
        locationX = unit.getLocation().getRow();
        locationY = unit.getLocation().getCol();
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow()));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol()));
        unitHealth.setText(String.valueOf(unit.getHealth()));
        combatStrength.setText(String.valueOf(unit.getType().getCombatStrength()));
        unitState.setText(unit.getState().toString());
    }

    private void initializeSpinners() {
        initializeXSpinner(xSpinner, moveUnitBtn);
        initializeYSpinner(ySpinner, moveUnitBtn);
        initializeXSpinner(xAttackSpinner, attackUnitBtn1);
        initializeYSpinner(yAttackSpinner, attackUnitBtn1);
    }

    private void initializeYSpinner(Spinner<Integer> ySpinner, Button moveUnitBtn) {
        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_HEIGHT - 1);
        System.out.println("x: " + unit.getLocation().getRow());
        System.out.println("y: " + unit.getLocation().getCol());
        yValueFactory.setValue(unit.getLocation().getCol());
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }

    private void initializeXSpinner(Spinner<Integer> xSpinner, Button spinnerButton) {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_WIDTH - 1);
        xValueFactory.setValue(unit.getLocation().getRow());
        xSpinner.setValueFactory(xValueFactory);
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = xSpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });

        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Constants.TILEGRID_HEIGHT - 1);
        yValueFactory.setValue(unit.getLocation().getCol());
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
        int locationX = xSpinner.getValue();
        int locationY = ySpinner.getValue();
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        command = "unit move -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        if (response.isOK()) back();
    }

    public void sleep() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.SLEEP);
        String command = "unit sleep";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("SLEEP");
    }

    public void alert() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.ALERT);
        String command = "unit alert";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("ALERT");
    }

    public void fortify() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.FORTIFY);
        String command = "unit fortify";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("FORTIFY");
    }

    public void awake() {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        unit.setState(UnitStates.AWAKE);
        String command = "unit wake";
        CommandResponse response = RequestHandler.getInstance().handle(command);
        unitState.setText("AWAKE");
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }

    public void exit() {
        System.exit(0);
    }

    public void attackUnit(ActionEvent actionEvent) {
        int locationX = xSpinner.getValue();
        int locationY = ySpinner.getValue();
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        command = "unit attack -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        // response = CommandResponse.INVALID_COMMAND;
        if (response.equals(CommandResponse.OK)) {
            back();
        } else {
            Dialog dialog = new Dialog<>();
            dialog.getDialogPane().setContent(createTextPane(response.toString()));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.showAndWait();
        }


    }

    public Pane createTextPane(String response) {
        Pane pane = new Pane();
        pane.setPrefHeight(70);
        TextFlow textFlow = new TextFlow(new Text(response));
        textFlow.setTranslateY(20);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(textFlow);
        return pane;

    }
}
