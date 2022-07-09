package Project.Views;

import Project.App;
import Project.Controllers.GameController;
import Project.Enums.UnitStates;
import Project.Models.Tiles.Hex;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.Unit;
import Project.ServerViews.RequestHandler;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
        unitXLocation.setText(String.valueOf(unit.getLocation().getRow() + 1));
        unitYLocation.setText(String.valueOf(unit.getLocation().getCol() + 1));
        unitHealth.setText(String.valueOf(unit.getHealth()));
        combatStrength.setText(String.valueOf(unit.getType().getCombatStrength()));
        unitState.setText(unit.getState().toString());
    }

    private void initializeSpinners() {
        initializeXSpinner(xSpinner,moveUnitBtn);
        initializeYSpinner(ySpinner,moveUnitBtn);
        initializeXSpinner(xAttackSpinner,attackUnitBtn1);
        initializeYSpinner(yAttackSpinner,attackUnitBtn1);
    }

    private void initializeYSpinner(Spinner<Integer> ySpinner,Button moveUnitBtn) {
        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
        yValueFactory.setValue(unit.getLocation().getCol());
        ySpinner.setValueFactory(yValueFactory);
        ySpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationY = (int) ySpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });
    }
    private void initializeXSpinner(Spinner<Integer> xSpinner,Button spinnerButton) {
        unit = GameController.getGame().getCurrentCivilization().getSelectedUnit();
        xValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_WIDTH);
        xValueFactory.setValue(unit.getLocation().getRow());
        xSpinner.setValueFactory(xValueFactory);
        xSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            locationX = xSpinner.getValue();
            moveUnitBtn.setStyle("-fx-border-color: none;");
        });

        yValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Constants.TILEGRID_HEIGHT);
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
//        Hex hex = GameController.getGameTile(unit.getLocation()).getHex();
//        unit.getGraphicUnit().setLayoutX(hex.getCenterX());
//        unit.getGraphicUnit().setLayoutY(hex.getCenterY());
//        unitXLocation.setText(String.valueOf(locationX));
//        unitYLocation.setText(String.valueOf(locationY));
        if (response.isOK()) back();
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

    public void attackUnit(ActionEvent actionEvent) {
        int locationX = xSpinner.getValue();
        int locationY = ySpinner.getValue();
        String combatOrNonCombat = (unit instanceof CombatUnit) ? "Combat" : "NonCombat";
        String command = "select unit " + combatOrNonCombat + " -p " + unit.getLocation().getRow() + " " + unit.getLocation().getCol();
        RequestHandler.getInstance().handle(command);
        command = "unit attack -p " + locationX + " " + locationY;
        CommandResponse response = RequestHandler.getInstance().handle(command);
        // response = CommandResponse.INVALID_COMMAND;
        if(response.equals(CommandResponse.OK)){

        }else {
                Dialog dialog = new Dialog<>();
                dialog.getDialogPane().setContent(createTextPane(response.toString()));
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
                dialog.showAndWait();
        }


    }
    public Pane createTextPane(String response){
        Pane pane = new Pane();
        pane.setPrefHeight(70);
        TextFlow textFlow = new TextFlow(new Text(response));
        textFlow.setTranslateY(20);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(textFlow);
        return pane;

    }
}
